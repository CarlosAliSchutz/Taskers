package br.com.cwi.api.service.core;

import br.com.cwi.api.factories.HabitoFactory;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.repository.HabitoRepository;
import br.com.cwi.crescer.api.service.core.BuscarHabitoService;
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
public class BuscarHabitoServiceTest {

    @InjectMocks
    private BuscarHabitoService tested;

    @Mock
    private HabitoRepository habitoRepository;

    @Mock
    private FoiRemovidoValidator foiRemovidoValidator;

    @Test
    @DisplayName("Deve buscar diaria por id")
    void deveRetornarHabito() {
        Habito habito = HabitoFactory.getHabitoFacil();
        when(habitoRepository.findById(habito.getId())).thenReturn(Optional.of(habito));

        Habito retorno = tested.porId(habito.getId());

        verify(foiRemovidoValidator).validar(habito.isAtivo());
        verify(habitoRepository).findById(habito.getId());
        assertEquals(habito, retorno);
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrar habito")
    void deveRetornarErro() {

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.porId(1L));

        assertEquals("Hábito não encontrado", exception.getReason());
    }

}
