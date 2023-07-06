package br.com.cwi.api.service;

import br.com.cwi.api.factories.DiariaFactory;
import br.com.cwi.api.factories.MissaoFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.domain.MissaoDiaria;
import br.com.cwi.crescer.api.repository.MissaoDiariaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.service.RealizarConquistaService;
import br.com.cwi.crescer.api.service.RealizarMissaoDiariaService;
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
public class RealizarMissaoDiariaServiceTest {

    @InjectMocks
    private RealizarMissaoDiariaService tested;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RealizarConquistaService realizarConquistaService;

    @Mock
    private MissaoDiariaRepository missaoDiariaRepository;

    @Test
    @DisplayName("Deve realizar a missao")
    void deveRealizarAMissao() {
        Usuario usuario = UsuarioFactory.getUsuario();

        Diaria diaria = DiariaFactory.getDiariaFacil();

        MissaoDiaria missaoDiaria = MissaoFactory.getMissaoDiaria();
        missaoDiaria.setProprietario(usuario);
        missaoDiaria.setDiaria(diaria);

        when(missaoDiariaRepository.findByProprietarioIdAndDiariaIdAndConcluidaFalse(usuario.getId(),diaria.getId()))
                .thenReturn(Optional.of(missaoDiaria));

        tested.realizar(usuario, diaria.getId());

        verify(missaoDiariaRepository).findByProprietarioIdAndDiariaIdAndConcluidaFalse(usuario.getId(),diaria.getId());
        verify(usuarioRepository).save(usuario);
        verify(missaoDiariaRepository).save(missaoDiaria);
        verify(realizarConquistaService).realizar(usuario.getId(), REALIZAR_MISSAO);

        assertTrue(missaoDiaria.isConcluida());
    }

    @Test
    @DisplayName("nao Deve Realizar A Missao Quando nao encontrar a missao")
    void naoDeveRealizarAMissaoQuandoNaoEncontraAMissao() {
        Usuario usuario = UsuarioFactory.getUsuario();

        Diaria diaria = DiariaFactory.getDiariaFacil();

        MissaoDiaria missaoDiaria = MissaoFactory.getMissaoDiaria();
        missaoDiaria.setProprietario(usuario);
        missaoDiaria.setDiaria(diaria);

        when(missaoDiariaRepository.findByProprietarioIdAndDiariaIdAndConcluidaFalse(usuario.getId(),diaria.getId()))
                .thenReturn(Optional.empty());

        tested.realizar(usuario, diaria.getId());

        verify(missaoDiariaRepository).findByProprietarioIdAndDiariaIdAndConcluidaFalse(usuario.getId(),diaria.getId());
        verify(usuarioRepository, never()).save(usuario);
        verify(missaoDiariaRepository, never()).save(missaoDiaria);
        verify(realizarConquistaService, never()).realizar(usuario.getId(), REALIZAR_MISSAO);

        assertFalse(missaoDiaria.isConcluida());
    }
}
