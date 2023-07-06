package br.com.cwi.api.service;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.api.factories.RequestFactory;
import br.com.cwi.crescer.api.controller.request.AfazerRequest;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import br.com.cwi.crescer.api.service.AlterarAfazerGoogleCalendarService;
import br.com.cwi.crescer.api.service.AlterarAfazerService;
import br.com.cwi.crescer.api.service.core.BuscarAfazerService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlterarAfazerServiceTest {

    @InjectMocks
    private AlterarAfazerService tested;

    @Mock
    private AfazerRepository afazerRepository;

    @Mock
    private BuscarAfazerService buscarAfazerService;

    @Mock
    private ValidarProprietarioService validarProprietarioService;

    @Mock
    private AlterarAfazerGoogleCalendarService alterarAfazerGoogleCalendarService;

    @Test
    @DisplayName("Deve alterar afazer completo")
    void deveAlterarAfazerCompleto() {
        Afazer afazer = AfazerFactory.getAfazerFacil();
        Long afazerId = afazer.getId();
        AfazerRequest request = RequestFactory.getAfazerMedioRequest();

        when(buscarAfazerService.porId(afazerId)).thenReturn(afazer);

        tested.alterar(afazerId, request);

        verify(buscarAfazerService).porId(afazerId);
        verify(validarProprietarioService).doAfazer(afazer);
        verify(afazerRepository).save(afazer);
        verify(alterarAfazerGoogleCalendarService).alterar(afazer);

        assertEquals(afazer.getNome(), request.getNome());
        assertEquals(afazer.getDescricao(), request.getDescricao());
        assertEquals(afazer.getDificuldade(), request.getDificuldade());

    }

    @Test
    @DisplayName("Não deve alterar caso não encontre o afazer")
    void naoDeveAlterarNaoEncontre() {
        Afazer afazer = AfazerFactory.getAfazerFacil();
        Long afazerId = afazer.getId();
        AfazerRequest request = RequestFactory.getAfazerMedioRequest();

        doThrow(ResponseStatusException.class)
                .when(buscarAfazerService).porId(afazerId);


        assertThrows(ResponseStatusException.class, () -> {
            tested.alterar(afazerId, request);
        });

        verify(buscarAfazerService).porId(afazerId);
        verify(validarProprietarioService, never()).doAfazer(afazer);
        verify(afazerRepository, never()).save(afazer);
        verify(alterarAfazerGoogleCalendarService, never()).alterar(afazer);

    }

    @Test
    @DisplayName("Não deve alterar caso não seja o proprietário do afazer")
    void naoDeveAlterarCasoProprietarioErrado() {
        Afazer afazer = AfazerFactory.getAfazerFacil();
        Long afazerId = afazer.getId();
        AfazerRequest request = RequestFactory.getAfazerMedioRequest();

        when(buscarAfazerService.porId(afazerId)).thenReturn(afazer);
        doThrow(ResponseStatusException.class)
                .when(validarProprietarioService).doAfazer(afazer);

        assertThrows(ResponseStatusException.class, () -> {
            tested.alterar(afazerId, request);
        });

        verify(buscarAfazerService).porId(afazerId);
        verify(validarProprietarioService).doAfazer(afazer);
        verify(afazerRepository, never()).save(afazer);
        verify(alterarAfazerGoogleCalendarService, never()).alterar(afazer);

    }

}
