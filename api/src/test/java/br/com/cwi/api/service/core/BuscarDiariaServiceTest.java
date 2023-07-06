package br.com.cwi.api.service.core;

import br.com.cwi.api.factories.DiariaFactory;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.service.core.BuscarDiariaService;
import br.com.cwi.crescer.api.validator.FoiRemovidoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarDiariaServiceTest {

    @InjectMocks
    private BuscarDiariaService tested;

    @Mock
    private DiariaRepository diariaRepository;

    @Mock
    private FoiRemovidoValidator foiRemovidoValidator;

    @Test
    @DisplayName("Deve buscar diaria por id")
    void deveRetornarDiaria() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        when(diariaRepository.findById(diaria.getId())).thenReturn(Optional.of(diaria));

        Diaria retorno = tested.porId(diaria.getId());

        verify(foiRemovidoValidator).validar(diaria.isAtivo());
        verify(diariaRepository).findById(diaria.getId());
        assertEquals(diaria, retorno);
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrar diaria")
    void deveRetornarErro() {

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.porId(1L));

        assertEquals("Diária não encontrada", exception.getReason());
    }

}
