package br.com.cwi.api.service;

import br.com.cwi.api.factories.HabitoFactory;
import br.com.cwi.crescer.api.controller.response.HabitoDetalhadoResponse;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.service.DetalharHabitoService;
import br.com.cwi.crescer.api.service.core.BuscarHabitoService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DetalharHabitoServiceTest {
    @InjectMocks
    private DetalharHabitoService tested;

    @Mock
    private BuscarHabitoService buscarHabitoService;

    @Mock
    private ValidarProprietarioService validarProprietarioService;

    @Test
    @DisplayName("Deve detalhar o habito executado")
    public void deveDetalharOHabitoExecutado() {
        Habito habito = HabitoFactory.getHabitoExecutado();

        when(buscarHabitoService.porId(habito.getId())).thenReturn(habito);

        HabitoDetalhadoResponse response = tested.detalhar(habito.getId());

        verify(buscarHabitoService).porId(habito.getId());
        verify(validarProprietarioService).doHabito(habito);

        assertEquals(response.getId(), habito.getId());
        assertEquals(response.getNome(), habito.getNome());
        assertEquals(response.getDescricao(), habito.getDescricao());
        assertEquals(response.getDificuldade(), habito.getDificuldade());
        assertEquals(response.getExecucoes(), habito.getExecucoes());
    }


    @Test
    @DisplayName("Deve detalhar o habito facil")
    public void deveDetalharOHabito() {
        Habito habito = HabitoFactory.getHabitoFacil();

        when(buscarHabitoService.porId(habito.getId())).thenReturn(habito);

        HabitoDetalhadoResponse response = tested.detalhar(habito.getId());

        verify(buscarHabitoService).porId(habito.getId());
        verify(validarProprietarioService).doHabito(habito);

        assertEquals(response.getId(), habito.getId());
        assertEquals(response.getNome(), habito.getNome());
        assertEquals(response.getDescricao(), habito.getDescricao());
        assertEquals(response.getDificuldade(), habito.getDificuldade());
        assertEquals(response.getExecucoes(), habito.getExecucoes());
    }
}
