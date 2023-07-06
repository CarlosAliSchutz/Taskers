package br.com.cwi.api.service;

import br.com.cwi.api.factories.RequestFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.controller.request.HabitoRequest;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.repository.HabitoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.IncluirHabitoService;
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
import static br.com.cwi.crescer.api.domain.TipoConquista.INCLUIR_HABITOS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IncluirHabitoServiceTest {

    @InjectMocks
    private IncluirHabitoService tested;

    @Mock
    private HabitoRepository habitoRepository;
    @Mock
    private UsuarioAutenticadoService autenticadoService;
    @Mock
    private RealizarConquistaService realizarConquistaService;

    @Captor
    private ArgumentCaptor<Habito> habitoCaptor;

    @Test
    @DisplayName("Deve incluir hábito completo")
    void deveIncluirHabitoCompleto() {
        Usuario usuario = UsuarioFactory.getUsuario();
        HabitoRequest request = RequestFactory.getHabitoFacilRequest();

        when(autenticadoService.get()).thenReturn(usuario);

        tested.incluir(request);

        verify(autenticadoService).get();
        verify(habitoRepository).save(habitoCaptor.capture());

        Habito habito = habitoCaptor.getValue();

        verify(realizarConquistaService).realizar(usuario.getId(), INCLUIR_HABITOS);

        assertEquals(habito.getNome(), request.getNome());
        assertEquals(habito.getDescricao(), request.getDescricao());
        assertEquals(habito.getDificuldade(), request.getDificuldade());
        assertEquals(habito.getExecucoes(), 0);
        assertTrue(habito.isAtivo());
    }

    @Test
    @DisplayName("Deve incluir hábito com dificuldade fácil")
    void deveIncluirHabitoFacil() {
        Usuario usuario = UsuarioFactory.getUsuario();
        HabitoRequest request = RequestFactory.getHabitoFacilRequest();

        when(autenticadoService.get()).thenReturn(usuario);

        tested.incluir(request);

        verify(autenticadoService).get();
        verify(habitoRepository).save(habitoCaptor.capture());

        Habito habito = habitoCaptor.getValue();

        verify(realizarConquistaService).realizar(usuario.getId(), INCLUIR_HABITOS);

        assertEquals(habito.getDificuldade(), FACIL);

    }

    @Test
    @DisplayName("Deve incluir hábito com dificuldade médio")
    void deveIncluirHabitoMedio() {
        Usuario usuario = UsuarioFactory.getUsuario();
        HabitoRequest request = RequestFactory.getHabitoMedioRequest();

        when(autenticadoService.get()).thenReturn(usuario);

        tested.incluir(request);

        verify(autenticadoService).get();
        verify(habitoRepository).save(habitoCaptor.capture());

        Habito habito = habitoCaptor.getValue();

        verify(realizarConquistaService).realizar(usuario.getId(), INCLUIR_HABITOS);

        assertEquals(habito.getDificuldade(), MEDIO);

    }

    @Test
    @DisplayName("Deve incluir hábito com dificuldade Difícil")
    void deveIncluirHabitoDificil() {
        Usuario usuario = UsuarioFactory.getUsuario();
        HabitoRequest request = RequestFactory.getHabitoDificilRequest();

        when(autenticadoService.get()).thenReturn(usuario);

        tested.incluir(request);

        verify(autenticadoService).get();
        verify(habitoRepository).save(habitoCaptor.capture());

        Habito habito = habitoCaptor.getValue();

        verify(realizarConquistaService).realizar(usuario.getId(), INCLUIR_HABITOS);

        assertEquals(habito.getDificuldade(), DIFICIL);

    }
}
