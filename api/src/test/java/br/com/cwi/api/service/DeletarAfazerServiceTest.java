package br.com.cwi.api.service;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import br.com.cwi.crescer.api.service.DeletarAfazerGoogleCalendarService;
import br.com.cwi.crescer.api.service.DeletarAfazerService;
import br.com.cwi.crescer.api.service.core.BuscarAfazerService;
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
public class DeletarAfazerServiceTest {

    @InjectMocks
    private DeletarAfazerService tested;

    @Mock
    private AfazerRepository afazerRepository;

    @Mock
    private BuscarAfazerService buscarAfazerService;

    @Mock
    private ValidarProprietarioService validarProprietarioService;

    @Mock
    private DeletarAfazerGoogleCalendarService deletarAfazerGoogleCalendarService;

    @Test
    @DisplayName("Deve deletar afazer")
    void deveDeletarAfazer() {
        Afazer afazer = AfazerFactory.getAfazerFacil();
        Long afazerId = afazer.getId();

        when(buscarAfazerService.porId(afazerId)).thenReturn(afazer);

        tested.deletar(afazerId);

        verify(buscarAfazerService).porId(afazerId);
        verify(validarProprietarioService).doAfazer(afazer);
        verify(afazerRepository).save(afazer);
        verify(deletarAfazerGoogleCalendarService).deletar(afazer);

        assertFalse(afazer.isAtivo());

    }

    @Test
    @DisplayName("Não deve deletar caso não encontre o afazer")
    void naoDeveDeletarNaoEncontre() {
        Afazer afazer = AfazerFactory.getAfazerFacil();
        Long afazerId = afazer.getId();

        doThrow(ResponseStatusException.class)
                .when(buscarAfazerService).porId(afazerId);


        assertThrows(ResponseStatusException.class, () -> {
            tested.deletar(afazerId);
        });

        verify(buscarAfazerService).porId(afazerId);
        verify(validarProprietarioService, never()).doAfazer(afazer);
        verify(afazerRepository, never()).save(afazer);
        verify(deletarAfazerGoogleCalendarService, never()).deletar(afazer);

    }

    @Test
    @DisplayName("Não deve deletar caso não seja o proprietário do afazer")
    void naoDeveDeletarCasoProprietarioErrado() {
        Afazer afazer = AfazerFactory.getAfazerFacil();
        Long afazerId = afazer.getId();

        when(buscarAfazerService.porId(afazerId)).thenReturn(afazer);
        doThrow(ResponseStatusException.class)
                .when(validarProprietarioService).doAfazer(afazer);

        assertThrows(ResponseStatusException.class, () -> {
            tested.deletar(afazerId);
        });

        verify(buscarAfazerService).porId(afazerId);
        verify(validarProprietarioService).doAfazer(afazer);
        verify(afazerRepository, never()).save(afazer);
        verify(deletarAfazerGoogleCalendarService, never()).deletar(afazer);


    }

}
