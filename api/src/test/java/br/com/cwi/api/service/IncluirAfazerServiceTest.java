package br.com.cwi.api.service;

import br.com.cwi.api.factories.RequestFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.controller.request.AfazerRequest;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.IncluirAfazerGoogleCalendarService;
import br.com.cwi.crescer.api.service.IncluirAfazerService;
import br.com.cwi.crescer.api.service.RealizarConquistaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.crescer.api.domain.Dificuldade.*;
import static br.com.cwi.crescer.api.domain.TipoConquista.INCLUIR_AFAZERES;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IncluirAfazerServiceTest {

    @InjectMocks
    private IncluirAfazerService tested;

    @Mock
    private AfazerRepository afazerRepository;
    @Mock
    private UsuarioAutenticadoService autenticadoService;
    @Mock
    private IncluirAfazerGoogleCalendarService incluirAfazerGoogleCalendarService;
    @Mock
    private RealizarConquistaService realizarConquistaService;

    @Captor
    private ArgumentCaptor<Afazer> afazerCaptor;

    @Test
    @DisplayName("Deve incluir afazer completo")
    void deveIncluirAfazerCompleto() {
        Usuario usuario = UsuarioFactory.getUsuario();
        AfazerRequest request = RequestFactory.getAfazerFacilRequest();

        when(autenticadoService.get()).thenReturn(usuario);

        tested.incluir(request);

        verify(autenticadoService).get();
        verify(afazerRepository).save(afazerCaptor.capture());

        Afazer afazer = afazerCaptor.getValue();

        verify(incluirAfazerGoogleCalendarService).incluir(usuario, afazer);
        verify(realizarConquistaService).realizar(usuario.getId(), INCLUIR_AFAZERES);

        assertEquals(afazer.getNome(), request.getNome());
        assertEquals(afazer.getDescricao(), request.getDescricao());
        assertEquals(afazer.getDificuldade(), request.getDificuldade());
        assertFalse(afazer.isFinalizado());
        assertTrue(afazer.isAtivo());

    }

    @Test
    @DisplayName("Deve incluir afazer com dificuldade fácil")
    void deveIncluirAfazerFacil() {
        Usuario usuario = UsuarioFactory.getUsuario();
        AfazerRequest request = RequestFactory.getAfazerFacilRequest();

        when(autenticadoService.get()).thenReturn(usuario);

        tested.incluir(request);

        verify(autenticadoService).get();
        verify(afazerRepository).save(afazerCaptor.capture());

        Afazer afazer = afazerCaptor.getValue();

        verify(incluirAfazerGoogleCalendarService).incluir(usuario, afazer);
        verify(realizarConquistaService).realizar(usuario.getId(), INCLUIR_AFAZERES);

        assertEquals(afazer.getDificuldade(), FACIL);
    }

    @Test
    @DisplayName("Deve incluir afazer com dificuldade médio")
    void deveIncluirAfazerMedio() {
        Usuario usuario = UsuarioFactory.getUsuario();
        AfazerRequest request = RequestFactory.getAfazerMedioRequest();

        when(autenticadoService.get()).thenReturn(usuario);

        tested.incluir(request);

        verify(autenticadoService).get();
        verify(afazerRepository).save(afazerCaptor.capture());

        Afazer afazer = afazerCaptor.getValue();

        verify(incluirAfazerGoogleCalendarService).incluir(usuario, afazer);
        verify(realizarConquistaService).realizar(usuario.getId(), INCLUIR_AFAZERES);

        assertEquals(afazer.getDificuldade(), MEDIO);
    }

    @Test
    @DisplayName("Deve incluir afazer com dificuldade difícil")
    void deveIncluirAfazerDificil() {
        Usuario usuario = UsuarioFactory.getUsuario();
        AfazerRequest request = RequestFactory.getAfazerDificilRequest();

        when(autenticadoService.get()).thenReturn(usuario);

        tested.incluir(request);

        verify(autenticadoService).get();
        verify(afazerRepository).save(afazerCaptor.capture());

        Afazer afazer = afazerCaptor.getValue();

        verify(incluirAfazerGoogleCalendarService).incluir(usuario, afazer);
        verify(realizarConquistaService).realizar(usuario.getId(), INCLUIR_AFAZERES);

        assertEquals(afazer.getDificuldade(), DIFICIL);
    }
}
