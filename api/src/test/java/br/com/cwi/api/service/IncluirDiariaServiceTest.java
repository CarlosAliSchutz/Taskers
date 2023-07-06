package br.com.cwi.api.service;

import br.com.cwi.api.factories.RequestFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.controller.request.DiariaRequest;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.IncluirDiariaGoogleCalendarService;
import br.com.cwi.crescer.api.service.IncluirDiariaService;
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
import static br.com.cwi.crescer.api.domain.TipoConquista.INCLUIR_DIARIAS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IncluirDiariaServiceTest {

    @InjectMocks
    private IncluirDiariaService tested;
    @Mock
    private DiariaRepository diariaRepository;
    @Mock
    private UsuarioAutenticadoService autenticadoService;
    @Mock
    private IncluirDiariaGoogleCalendarService incluirDiariaGoogleCalendarService;
    @Mock
    private RealizarConquistaService realizarConquistaService;

    @Captor
    private ArgumentCaptor<Diaria> diariaCaptor;

    @Test
    @DisplayName("Deve incluir diaria completo")
    void deveIncluirDiariaCompleto() {
        Usuario usuario = UsuarioFactory.getUsuario();
        DiariaRequest request = RequestFactory.getDiariaFacilRequest();

        when(autenticadoService.get()).thenReturn(usuario);

        tested.incluir(request);

        verify(autenticadoService).get();
        verify(diariaRepository).save(diariaCaptor.capture());

        Diaria diaria = diariaCaptor.getValue();

        verify(incluirDiariaGoogleCalendarService).incluir(usuario, diaria);
        verify(realizarConquistaService).realizar(usuario.getId(), INCLUIR_DIARIAS);

        assertEquals(diaria.getNome(), request.getNome());
        assertEquals(diaria.getDescricao(), request.getDescricao());
        assertEquals(diaria.getDificuldade(), request.getDificuldade());
        assertEquals(diaria.getHora(), request.getHora());
        assertFalse(diaria.isFinalizado());
        assertTrue(diaria.isAtivo());

    }

    @Test
    @DisplayName("Deve incluir diaria com dificuldade fácil")
    void deveIncluirDiariaFacil() {
        Usuario usuario = UsuarioFactory.getUsuario();
        DiariaRequest request = RequestFactory.getDiariaFacilRequest();

        when(autenticadoService.get()).thenReturn(usuario);

        tested.incluir(request);

        verify(autenticadoService).get();
        verify(diariaRepository).save(diariaCaptor.capture());

        Diaria diaria = diariaCaptor.getValue();

        verify(incluirDiariaGoogleCalendarService).incluir(usuario, diaria);
        verify(realizarConquistaService).realizar(usuario.getId(), INCLUIR_DIARIAS);

        assertEquals(diaria.getDificuldade(), FACIL);
    }

    @Test
    @DisplayName("Deve incluir diaria com dificuldade médio")
    void deveIncluirDiariaMedio() {
        Usuario usuario = UsuarioFactory.getUsuario();
        DiariaRequest request = RequestFactory.getDiariaMedioRequest();

        when(autenticadoService.get()).thenReturn(usuario);

        tested.incluir(request);

        verify(autenticadoService).get();
        verify(diariaRepository).save(diariaCaptor.capture());

        Diaria diaria = diariaCaptor.getValue();

        verify(incluirDiariaGoogleCalendarService).incluir(usuario, diaria);
        verify(realizarConquistaService).realizar(usuario.getId(), INCLUIR_DIARIAS);

        assertEquals(diaria.getDificuldade(), MEDIO);
    }

    @Test
    @DisplayName("Deve incluir diaria com dificuldade difícil")
    void deveIncluirDiariaDificil() {
        Usuario usuario = UsuarioFactory.getUsuario();
        DiariaRequest request = RequestFactory.getDiariaDificilRequest();

        when(autenticadoService.get()).thenReturn(usuario);

        tested.incluir(request);

        verify(autenticadoService).get();
        verify(diariaRepository).save(diariaCaptor.capture());

        Diaria diaria = diariaCaptor.getValue();

        verify(incluirDiariaGoogleCalendarService).incluir(usuario, diaria);
        verify(realizarConquistaService).realizar(usuario.getId(), INCLUIR_DIARIAS);

        assertEquals(diaria.getDificuldade(), DIFICIL);
    }
}
