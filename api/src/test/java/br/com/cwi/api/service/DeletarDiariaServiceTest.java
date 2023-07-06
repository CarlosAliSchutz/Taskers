package br.com.cwi.api.service;

import br.com.cwi.api.factories.DiariaFactory;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.service.DeletarDiariaGoogleCalendarService;
import br.com.cwi.crescer.api.service.DeletarDiariaService;
import br.com.cwi.crescer.api.service.core.BuscarDiariaService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeletarDiariaServiceTest {

    @InjectMocks
    private DeletarDiariaService tested;

    @Mock
    private DiariaRepository diariaRepository;

    @Mock
    private BuscarDiariaService buscarDiariaService;

    @Mock
    private ValidarProprietarioService validarProprietarioService;

    @Mock
    private DeletarDiariaGoogleCalendarService deletarDiariaGoogleCalendarService;


    @Test
    @DisplayName("Deve deletar diaria")
    void deveDeletarDiaria() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        Long diariaId = diaria.getId();

        when(buscarDiariaService.porId(diariaId)).thenReturn(diaria);

        tested.deletar(diariaId);

        verify(buscarDiariaService).porId(diariaId);
        verify(validarProprietarioService).daDiaria(diaria);
        verify(diariaRepository).save(diaria);
        verify(deletarDiariaGoogleCalendarService).deletar(diaria);

        assertFalse(diaria.isAtivo());

    }

    @Test
    @DisplayName("Não deve deletar caso não encontre a diaria")
    void naoDeveDeletarNaoEncontre() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        Long diariaId = diaria.getId();

        doThrow(ResponseStatusException.class)
                .when(buscarDiariaService).porId(diariaId);


        assertThrows(ResponseStatusException.class, () -> {
            tested.deletar(diariaId);
        });

        verify(buscarDiariaService).porId(diariaId);
        verify(validarProprietarioService, never()).daDiaria(diaria);
        verify(diariaRepository, never()).save(diaria);
        verify(deletarDiariaGoogleCalendarService, never()).deletar(diaria);

    }

    @Test
    @DisplayName("Não deve deletar caso não seja o proprietário da diaria")
    void naoDeveDeletarCasoProprietarioErrado() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        Long diariaId = diaria.getId();

        when(buscarDiariaService.porId(diariaId)).thenReturn(diaria);
        doThrow(ResponseStatusException.class)
                .when(validarProprietarioService).daDiaria(diaria);

        assertThrows(ResponseStatusException.class, () -> {
            tested.deletar(diariaId);
        });

        verify(buscarDiariaService).porId(diariaId);
        verify(validarProprietarioService).daDiaria(diaria);
        verify(diariaRepository, never()).save(diaria);
        verify(deletarDiariaGoogleCalendarService, never()).deletar(diaria);

    }

}
