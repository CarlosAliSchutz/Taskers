package br.com.cwi.api.service;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.FinalizarAfazerService;
import br.com.cwi.crescer.api.service.RealizarConquistaService;
import br.com.cwi.crescer.api.service.RealizarMissaoAfazerService;
import br.com.cwi.crescer.api.service.core.BuscarAfazerService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.crescer.api.domain.TipoConquista.FINALIZAR_AFAZERES;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FinalizarAfazerServiceTest {

    private final Integer QUANTIDADE_DE_XP_NO_FACIL = 20;

    @InjectMocks
    private FinalizarAfazerService tested;

    @Mock
    private AfazerRepository afazerRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private BuscarAfazerService buscarAfazerService;
    @Mock
    private UsuarioAutenticadoService autenticadoService;
    @Mock
    private ValidarProprietarioService validarProprietarioService;
    @Mock
    private RealizarMissaoAfazerService realizarMissaoAfazerService;
    @Mock
    private RealizarConquistaService realizarConquistaService;

    @Test
    @DisplayName("Deve alterar para finalizado caso não esteja")
    void deveAlterarParaFinalizado() {
        Afazer afazer = AfazerFactory.getAfazerFacil();
        Long afazerId = afazer.getId();
        Usuario usuario = UsuarioFactory.getUsuario();

        when(buscarAfazerService.porId(afazerId)).thenReturn(afazer);
        when(autenticadoService.get()).thenReturn(usuario);

        tested.finalizar(afazerId);

        verify(buscarAfazerService).porId(afazerId);
        verify(autenticadoService).get();
        verify(validarProprietarioService).doAfazer(afazer);
        verify(usuarioRepository).save(usuario);
        verify(afazerRepository).save(afazer);
        verify(realizarMissaoAfazerService).realizar(usuario, afazerId);
        verify(realizarConquistaService).realizar(usuario.getId(), FINALIZAR_AFAZERES);

        assertTrue(afazer.isFinalizado());

    }

    @Test
    @DisplayName("Deve alterar para não finalizado caso já esteja")
    void deveAlterarParaNaoFinalizado() {
        Afazer afazer = AfazerFactory.getAfazerFinalizado();
        Long afazerId = afazer.getId();
        Usuario usuario = UsuarioFactory.getUsuario();

        when(buscarAfazerService.porId(afazerId)).thenReturn(afazer);
        when(autenticadoService.get()).thenReturn(usuario);

        tested.finalizar(afazerId);

        verify(buscarAfazerService).porId(afazerId);
        verify(autenticadoService).get();
        verify(validarProprietarioService).doAfazer(afazer);
        verify(usuarioRepository).save(usuario);
        verify(afazerRepository).save(afazer);
        verify(realizarMissaoAfazerService, never()).realizar(usuario, afazerId);
        verify(realizarConquistaService, never()).realizar(usuario.getId(), FINALIZAR_AFAZERES);

        assertFalse(afazer.isFinalizado());

    }


    @Test
    @DisplayName("Deve aumentar a quantidade de experiência do usuário")
    void deveAumentarExperiencia() {
        Afazer afazer = AfazerFactory.getAfazerFacil();
        Long afazerId = afazer.getId();
        Usuario usuario = UsuarioFactory.getUsuario();

        when(buscarAfazerService.porId(afazerId)).thenReturn(afazer);
        when(autenticadoService.get()).thenReturn(usuario);

        tested.finalizar(afazerId);

        verify(buscarAfazerService).porId(afazerId);
        verify(autenticadoService).get();
        verify(validarProprietarioService).doAfazer(afazer);
        verify(usuarioRepository).save(usuario);
        verify(afazerRepository).save(afazer);
        verify(realizarMissaoAfazerService).realizar(usuario, afazerId);
        verify(realizarConquistaService).realizar(usuario.getId(), FINALIZAR_AFAZERES);

        assertEquals(usuario.getExperiencia(), QUANTIDADE_DE_XP_NO_FACIL);
    }
    @Test
    @DisplayName("Não deve finalizar caso não encontre o afazer")
    void naoDeveFinalizarCasoNaoEncontrado() {
        Afazer afazer = AfazerFactory.getAfazerFacil();
        Long afazerId = afazer.getId();
        Usuario usuario = UsuarioFactory.getUsuario();

        doThrow(ResponseStatusException.class)
                .when(buscarAfazerService).porId(afazerId);


        assertThrows(ResponseStatusException.class, () -> {
            tested.finalizar(afazerId);
        });

        verify(buscarAfazerService).porId(afazerId);
        verify(autenticadoService, never()).get();
        verify(validarProprietarioService, never()).doAfazer(afazer);
        verify(usuarioRepository, never()).save(usuario);
        verify(afazerRepository, never()).save(afazer);
        verify(realizarMissaoAfazerService, never()).realizar(usuario, afazerId);
        verify(realizarConquistaService, never()).realizar(usuario.getId(), FINALIZAR_AFAZERES);
    }

    @Test
    @DisplayName("Não deve finalizar caso não seja o proprietário do afazer")
    void naoDeveFinalizarCasoNaoSejaProprietario() {
        Afazer afazer = AfazerFactory.getAfazerFacil();
        Long afazerId = afazer.getId();
        Usuario usuario = UsuarioFactory.getUsuario();

        when(buscarAfazerService.porId(afazerId)).thenReturn(afazer);
        when(autenticadoService.get()).thenReturn(usuario);
        doThrow(ResponseStatusException.class)
                .when(validarProprietarioService).doAfazer(afazer);


        assertThrows(ResponseStatusException.class, () -> {
            tested.finalizar(afazerId);
        });

        verify(buscarAfazerService).porId(afazerId);
        verify(autenticadoService).get();
        verify(validarProprietarioService).doAfazer(afazer);
        verify(usuarioRepository, never()).save(usuario);
        verify(afazerRepository, never()).save(afazer);
        verify(realizarMissaoAfazerService, never()).realizar(usuario, afazerId);
        verify(realizarConquistaService, never()).realizar(usuario.getId(), FINALIZAR_AFAZERES);
    }

}
