package br.com.cwi.api.service;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.api.factories.MissaoFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.domain.MissaoAfazer;
import br.com.cwi.crescer.api.repository.MissaoAfazerRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.service.RealizarConquistaService;
import br.com.cwi.crescer.api.service.RealizarMissaoAfazerService;
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
public class RealizarMissaoAfazerServiceTest {

    @InjectMocks
    private RealizarMissaoAfazerService tested;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private MissaoAfazerRepository missaoAfazerRepository;

    @Mock
    private RealizarConquistaService realizarConquistaService;

    @Test
    @DisplayName("Deve alterar para finalizado caso não esteja")
    void deveRealizarAMissao() {
        Usuario usuario = UsuarioFactory.getUsuario();

        Afazer afazer = AfazerFactory.getAfazerFacil();

        MissaoAfazer missaoAfazer = MissaoFactory.getMissaoAfazer();
        missaoAfazer.setProprietario(usuario);
        missaoAfazer.setAfazer(afazer);

        when(missaoAfazerRepository.findByProprietarioIdAndAfazerIdAndConcluidaFalse(usuario.getId(),afazer.getId()))
                .thenReturn(Optional.of(missaoAfazer));

        tested.realizar(usuario, afazer.getId());

        verify(missaoAfazerRepository).findByProprietarioIdAndAfazerIdAndConcluidaFalse(usuario.getId(),afazer.getId());
        verify(usuarioRepository).save(usuario);
        verify(missaoAfazerRepository).save(missaoAfazer);
        verify(realizarConquistaService).realizar(usuario.getId(), REALIZAR_MISSAO);

        assertTrue(missaoAfazer.isConcluida());
    }

    @Test
    @DisplayName("nao Deve Realizar A Missao Quando nao encontrar a missao")
    void naoDeveRealizarAMissaoQuandoNaoEncontraAMissao() {
        Usuario usuario = UsuarioFactory.getUsuario();

        Afazer afazer = AfazerFactory.getAfazerFacil();

        MissaoAfazer missaoAfazer = MissaoFactory.getMissaoAfazer();
        missaoAfazer.setProprietario(usuario);
        missaoAfazer.setAfazer(afazer);

        when(missaoAfazerRepository.findByProprietarioIdAndAfazerIdAndConcluidaFalse(usuario.getId(),afazer.getId()))
                .thenReturn(Optional.empty());

        tested.realizar(usuario, afazer.getId());

        verify(missaoAfazerRepository).findByProprietarioIdAndAfazerIdAndConcluidaFalse(usuario.getId(),afazer.getId());
        verify(usuarioRepository, never()).save(usuario);
        verify(missaoAfazerRepository, never()).save(missaoAfazer);
        verify(realizarConquistaService, never()).realizar(usuario.getId(), REALIZAR_MISSAO);

        assertFalse(missaoAfazer.isConcluida());
    }
}
