package br.com.cwi.api.mapper;

import br.com.cwi.api.factories.CosmeticoFactory;
import br.com.cwi.crescer.api.controller.response.CosmeticoResponse;
import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.mapper.CosmeticoResponseMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CosmeticoResponseMapperTest {

    @Test
    @DisplayName("Deve retornar o response quando receber um cosmetico roupa")
    void deveRetornarOResponseQuandoReceberUmCosmeticoRoupa() {

        Cosmetico cosmetico = CosmeticoFactory.getCosmeticoRoupa();

        CosmeticoResponse response = CosmeticoResponseMapper.toResponse(cosmetico);

        assertEquals(cosmetico.getId(), response.getId());
        assertEquals(cosmetico.getTipo(), response.getTipo());
        assertEquals(cosmetico.getNome(), response.getNome());
        assertEquals(cosmetico.getValor(), response.getValor());
    }

    @Test
    @DisplayName("Deve retornar o response quando receber um cosmetico de cenario")
    void deveRetornarOResponseQuandoReceberUmCosmeticoCenario() {

        Cosmetico cosmetico = CosmeticoFactory.getCosmeticoCenario();

        CosmeticoResponse response = CosmeticoResponseMapper.toResponse(cosmetico);

        assertEquals(cosmetico.getId(), response.getId());
        assertEquals(cosmetico.getTipo(), response.getTipo());
        assertEquals(cosmetico.getNome(), response.getNome());
        assertEquals(cosmetico.getValor(), response.getValor());
    }


    @Test
    @DisplayName("Deve lançar erro quando cosmetico for nulo")
    void deveLancarErroQuandoConquistaForNula() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            CosmeticoResponse response = CosmeticoResponseMapper.toResponse(null);
        });
    }
}
