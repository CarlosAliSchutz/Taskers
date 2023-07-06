package br.com.cwi.api.service.core;

import br.com.cwi.api.factories.CosmeticoFactory;
import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.domain.CosmeticosPadroes;
import br.com.cwi.crescer.api.repository.CosmeticoRepository;
import br.com.cwi.crescer.api.service.core.BuscarCosmeticoService;
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
public class BuscarCosmeticoServiceTest {

    @InjectMocks
    private BuscarCosmeticoService tested;

    @Mock
    private CosmeticoRepository cosmeticoRepository;

    @Test
    @DisplayName("Deve buscar cosmetico por id")
    void deveRetornarCosmeticoPorId() {
        Cosmetico cosmetico = CosmeticoFactory.getCosmeticoCenario();
        when(cosmeticoRepository.findById(cosmetico.getId())).thenReturn(Optional.of(cosmetico));

        Cosmetico retorno = tested.porId(cosmetico.getId());

        verify(cosmeticoRepository).findById(cosmetico.getId());
        assertEquals(cosmetico, retorno);
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrar cosmetico por id")
    void deveRetornarErroQuandoNaoEncontrarPorId() {

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.porId(1L));

        assertEquals("Cosmético não encontrado", exception.getReason());
    }

    @Test
    @DisplayName("Deve buscar cosmetico por id")
    void deveRetornarCosmeticoPorNome() {
        Cosmetico cosmetico = CosmeticoFactory.getCosmeticoCenario();
        when(cosmeticoRepository.findByNome(cosmetico.getNome())).thenReturn(Optional.of(cosmetico));

        Cosmetico retorno = tested.porNome(cosmetico.getNome());

        verify(cosmeticoRepository).findByNome(cosmetico.getNome());
        assertEquals(cosmetico, retorno);
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrar cosmetico")
    void deveRetornarErroQuandoNaoEncontrarPorNome() {

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.porNome("Nome teste para dar erro"));

        assertEquals("Cosmético não encontrado", exception.getReason());
    }
}
