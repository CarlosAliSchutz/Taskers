package br.com.cwi.api.service;

import br.com.cwi.api.factories.DiaFactory;
import br.com.cwi.api.factories.DiariaFactory;
import br.com.cwi.crescer.api.controller.response.DiariaDetalhadaResponse;
import br.com.cwi.crescer.api.domain.Dia;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.service.DetalharDiariaService;
import br.com.cwi.crescer.api.service.core.BuscarDiariaService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DetalharDiariaServiceTest {
    @InjectMocks
    private DetalharDiariaService tested;

    @Mock
    private BuscarDiariaService buscarDiariaService;

    @Mock
    private ValidarProprietarioService validarProprietarioService;

    @Test
    @DisplayName("Deve detalhar a diaria facil")
    public void deveDetalharADiariaFacil() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        diaria.setDias(DiaFactory.getListaVazia());

        when(buscarDiariaService.porId(diaria.getId())).thenReturn(diaria);

        DiariaDetalhadaResponse response = tested.detalhar(diaria.getId());

        verify(buscarDiariaService).porId(diaria.getId());
        verify(validarProprietarioService).daDiaria(diaria);

        assertEquals(response.getId(), diaria.getId());
        assertEquals(response.getNome(), diaria.getNome());
        assertEquals(response.getDescricao(), diaria.getDescricao());
        assertEquals(response.getDificuldade(), diaria.getDificuldade());
        assertEquals(response.getDias().size(), diaria.getDias().size());
    }

    @Test
    @DisplayName("Deve detalhar a diaria finalizada")
    public void deveDetalharADiariaFinalizada() {
        Diaria diaria = DiariaFactory.getDiariaFinalizado();
        diaria.setDias(DiaFactory.getListaVazia());

        when(buscarDiariaService.porId(diaria.getId())).thenReturn(diaria);

        DiariaDetalhadaResponse response = tested.detalhar(diaria.getId());

        verify(buscarDiariaService).porId(diaria.getId());
        verify(validarProprietarioService).daDiaria(diaria);

        assertEquals(response.getId(), diaria.getId());
        assertEquals(response.getNome(), diaria.getNome());
        assertEquals(response.getDescricao(), diaria.getDescricao());
        assertEquals(response.getDificuldade(), diaria.getDificuldade());
        assertEquals(response.getDias().size(), diaria.getDias().size());
    }

    @Test
    @DisplayName("Deve detalhar a diaria com dias")
    public void deveDetalharADiariaComDias() {
        Diaria diaria = DiariaFactory.getDiariaFinalizado();
        List<Dia> dias = DiaFactory.getLista();
        diaria.setDias(dias);

        when(buscarDiariaService.porId(diaria.getId())).thenReturn(diaria);

        DiariaDetalhadaResponse response = tested.detalhar(diaria.getId());

        verify(buscarDiariaService).porId(diaria.getId());
        verify(validarProprietarioService).daDiaria(diaria);

        assertEquals(response.getId(), diaria.getId());
        assertEquals(response.getNome(), diaria.getNome());
        assertEquals(response.getDescricao(), diaria.getDescricao());
        assertEquals(response.getDificuldade(), diaria.getDificuldade());
        assertEquals(response.getDias().size(), diaria.getDias().size());
        assertEquals(response.getDias().get(0).getNome(), diaria.getDias().get(0).getNome());
        assertEquals(response.getDias().get(1).getNome(), diaria.getDias().get(1).getNome());
    }
}
