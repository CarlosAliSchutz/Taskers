package br.com.cwi.api.service;

import br.com.cwi.api.factories.HabitoFactory;
import br.com.cwi.api.factories.MissaoFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.domain.MissaoHabito;
import br.com.cwi.crescer.api.repository.MissaoHabitoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.service.RealizarConquistaService;
import br.com.cwi.crescer.api.service.RealizarMissaoHabitoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.cwi.crescer.api.domain.TipoConquista.REALIZAR_MISSAO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RealizarMissaoHabitoServiceTest {

    @InjectMocks
    private RealizarMissaoHabitoService tested;

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RealizarConquistaService realizarConquistaService;

    @Mock
    private MissaoHabitoRepository missaoHabitoRepository;

    @Test
    @DisplayName("Deve realizar a missao")
    void deveRealizarAMissao() {
        Usuario usuario = UsuarioFactory.getUsuario();
        MissaoHabito missaoHabito = MissaoFactory.getMissaoHabito();
        Habito habito = HabitoFactory.getHabitoFacil();
        habito.setExecucoes(5);
        missaoHabito.setProprietario(usuario);
        missaoHabito.setHabito(habito);

        when(missaoHabitoRepository.findByProprietarioIdAndHabitoIdAndConcluidaFalse(usuario.getId(),habito.getId()))
                .thenReturn(Optional.of(missaoHabito));

        tested.realizar(usuario,habito);

        verify(missaoHabitoRepository).findByProprietarioIdAndHabitoIdAndConcluidaFalse(usuario.getId(),habito.getId());
        verify(usuarioRepository).save(usuario);
        verify(missaoHabitoRepository).save(missaoHabito);
        verify(realizarConquistaService).realizar(usuario.getId(), REALIZAR_MISSAO);

        assertTrue(missaoHabito.isConcluida());
    }


    @Test
    @DisplayName("nao Deve Realizar A Missao Quando O Habito Nao Possui O Minimo De Execucoes")
    void naoDeveRealizarAMissaoQuandoOHabitoNaoPossuiOMinimoDeExecucoes() {
        Usuario usuario = UsuarioFactory.getUsuario();
        MissaoHabito missaoHabito = MissaoFactory.getMissaoHabito();
        Habito habito = HabitoFactory.getHabitoFacil();
        missaoHabito.setProprietario(usuario);
        missaoHabito.setHabito(habito);

        when(missaoHabitoRepository.findByProprietarioIdAndHabitoIdAndConcluidaFalse(usuario.getId(),habito.getId()))
                .thenReturn(Optional.of(missaoHabito));

        tested.realizar(usuario,habito);

        verify(missaoHabitoRepository).findByProprietarioIdAndHabitoIdAndConcluidaFalse(usuario.getId(),habito.getId());
        verify(usuarioRepository, never()).save(usuario);
        verify(missaoHabitoRepository, never()).save(missaoHabito);
        verify(realizarConquistaService, never()).realizar(usuario.getId(), REALIZAR_MISSAO);

        assertFalse(missaoHabito.isConcluida());
    }

    @Test
    @DisplayName("nao Deve Realizar A Missao Quando nao encontrar a missao")
    void naoDeveRealizarAMissaoQuandoNaoEncontraAMissao() {
        Usuario usuario = UsuarioFactory.getUsuario();
        MissaoHabito missaoHabito = MissaoFactory.getMissaoHabito();
        Habito habito = HabitoFactory.getHabitoFacil();
        missaoHabito.setProprietario(usuario);
        missaoHabito.setHabito(habito);

        when(missaoHabitoRepository.findByProprietarioIdAndHabitoIdAndConcluidaFalse(usuario.getId(),habito.getId()))
                .thenReturn(Optional.empty());

        tested.realizar(usuario,habito);

        verify(missaoHabitoRepository).findByProprietarioIdAndHabitoIdAndConcluidaFalse(usuario.getId(),habito.getId());
        verify(usuarioRepository, never()).save(usuario);
        verify(missaoHabitoRepository, never()).save(missaoHabito);
        verify(realizarConquistaService, never()).realizar(usuario.getId(), REALIZAR_MISSAO);

        assertFalse(missaoHabito.isConcluida());
    }
}
