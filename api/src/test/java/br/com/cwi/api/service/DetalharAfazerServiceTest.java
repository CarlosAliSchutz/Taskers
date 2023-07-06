package br.com.cwi.api.service;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.crescer.api.controller.response.AfazerDetalhadoResponse;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.service.DetalharAfazerService;
import br.com.cwi.crescer.api.service.core.BuscarAfazerService;
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
public class DetalharAfazerServiceTest {
    @InjectMocks
    private DetalharAfazerService tested;

    @Mock
    private BuscarAfazerService buscarAfazerService;

    @Mock
    private ValidarProprietarioService validarProprietarioService;

    @Test
    @DisplayName("Deve detalhar o afazer facil")
    public void deveDetalharOAfazerFacil() {
        Afazer afazer = AfazerFactory.getAfazerFacil();

        when(buscarAfazerService.porId(afazer.getId())).thenReturn(afazer);

        AfazerDetalhadoResponse response = tested.detalhar(afazer.getId());

        verify(buscarAfazerService).porId(afazer.getId());
        verify(validarProprietarioService).doAfazer(afazer);


        assertEquals(response.getId(), afazer.getId());
        assertEquals(response.getNome(), afazer.getNome());
        assertEquals(response.getDescricao(), afazer.getDescricao());
        assertEquals(response.getDificuldade(), afazer.getDificuldade());
    }


    @Test
    @DisplayName("Deve detalhar o afazer finalizado")
    public void deveDetalharOAfazerFinalizado() {
        Afazer afazer = AfazerFactory.getAfazerFinalizado();

        when(buscarAfazerService.porId(afazer.getId())).thenReturn(afazer);

        AfazerDetalhadoResponse response = tested.detalhar(afazer.getId());

        verify(buscarAfazerService).porId(afazer.getId());
        verify(validarProprietarioService).doAfazer(afazer);

        assertEquals(response.getId(), afazer.getId());
        assertEquals(response.getNome(), afazer.getNome());
        assertEquals(response.getDescricao(), afazer.getDescricao());
        assertEquals(response.getDificuldade(), afazer.getDificuldade());
    }
}
