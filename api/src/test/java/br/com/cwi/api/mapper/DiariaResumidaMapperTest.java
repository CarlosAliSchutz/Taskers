package br.com.cwi.api.mapper;

import br.com.cwi.api.factories.DiariaFactory;
import br.com.cwi.crescer.api.controller.response.DiariaResumidaResponse;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.mapper.DiariaResumidaResponseMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DiariaResumidaMapperTest {

    @Test
    @DisplayName("Deve retornar o response resumido quando receber uma diaria facil")
    void deveRetornarOResponseFacil() {

        Diaria diaria = DiariaFactory.getDiariaFacil();

        DiariaResumidaResponse response = DiariaResumidaResponseMapper.toResponse(diaria);

        assertEquals(diaria.getId(), response.getId());
        assertEquals(diaria.getNome(), response.getNome());
        assertEquals(diaria.getHora().format(DateTimeFormatter.ofPattern("HH:mm")), response.getHora());
        assertEquals(diaria.isFinalizado(), response.isFinalizado());
    }

    @Test
    @DisplayName("Deve retornar o response resumido quando receber uma diaria finalizada")
    void deveRetornarOResponseFinalizada() {

        Diaria diaria = DiariaFactory.getDiariaFinalizado();

        DiariaResumidaResponse response = DiariaResumidaResponseMapper.toResponse(diaria);

        assertEquals(diaria.getId(), response.getId());
        assertEquals(diaria.getNome(), response.getNome());
        assertEquals(diaria.getHora().format(DateTimeFormatter.ofPattern("HH:mm")), response.getHora());
        assertEquals(diaria.isFinalizado(), response.isFinalizado());
    }

    @Test
    @DisplayName("Deve lançar erro quando diaria for nula")
    void deveLancarErroQuandoDiariaForNula() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            DiariaResumidaResponse response = DiariaResumidaResponseMapper.toResponse(null);
        });
    }
}
