package br.com.cwi.api.service;

import br.com.cwi.api.factories.DiariaFactory;
import br.com.cwi.api.factories.RequestFactory;
import br.com.cwi.crescer.api.controller.request.DiariaRequest;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.service.AlterarDiariaGoogleCalendarService;
import br.com.cwi.crescer.api.service.AlterarDiariaService;
import br.com.cwi.crescer.api.service.core.BuscarDiariaService;
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
public class AlterarDiariaServiceTest {

    @InjectMocks
    private AlterarDiariaService tested;

    @Mock
    private DiariaRepository diariaRepository;

    @Mock
    private BuscarDiariaService buscarDiariaService;

    @Mock
    private ValidarProprietarioService validarProprietarioService;

    @Mock
    private AlterarDiariaGoogleCalendarService alterarDiariaGoogleCalendarService;

    @Test
    @DisplayName("Deve alterar diaria completo")
    void deveAlterarDiariaCompleto() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        Long diariaId = diaria.getId();
        DiariaRequest request = RequestFactory.getDiariaMedioRequest();

        when(buscarDiariaService.porId(diariaId)).thenReturn(diaria);

        tested.alterar(diariaId, request);

        verify(buscarDiariaService).porId(diariaId);
        verify(validarProprietarioService).daDiaria(diaria);
        verify(diariaRepository).save(diaria);
        verify(alterarDiariaGoogleCalendarService).alterar(diaria);


        assertEquals(diaria.getNome(), request.getNome());
        assertEquals(diaria.getDescricao(), request.getDescricao());
        assertEquals(diaria.getDificuldade(), request.getDificuldade());
        assertEquals(diaria.getHora(), request.getHora());

    }

    @Test
    @DisplayName("Não deve alterar caso não encontre o diaria")
    void naoDeveAlterarNaoEncontre() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        Long diariaId = diaria.getId();
        DiariaRequest request = RequestFactory.getDiariaMedioRequest();

        doThrow(ResponseStatusException.class)
                .when(buscarDiariaService).porId(diariaId);


        assertThrows(ResponseStatusException.class, () -> {
            tested.alterar(diariaId, request);
        });

        verify(buscarDiariaService).porId(diariaId);
        verify(validarProprietarioService, never()).daDiaria(diaria);
        verify(diariaRepository, never()).save(diaria);
        verify(alterarDiariaGoogleCalendarService, never()).alterar(diaria);
    }

    @Test
    @DisplayName("Não deve alterar caso não seja o proprietário do diaria")
    void naoDeveAlterarCasoProprietarioErrado() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        Long diariaId = diaria.getId();
        DiariaRequest request = RequestFactory.getDiariaMedioRequest();

        when(buscarDiariaService.porId(diariaId)).thenReturn(diaria);
        doThrow(ResponseStatusException.class)
                .when(validarProprietarioService).daDiaria(diaria);

        assertThrows(ResponseStatusException.class, () -> {
            tested.alterar(diariaId, request);
        });

        verify(buscarDiariaService).porId(diariaId);
        verify(validarProprietarioService).daDiaria(diaria);
        verify(diariaRepository, never()).save(diaria);
        verify(alterarDiariaGoogleCalendarService, never()).alterar(diaria);
    }

}
