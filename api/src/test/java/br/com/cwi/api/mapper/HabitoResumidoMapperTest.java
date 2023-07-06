package br.com.cwi.api.mapper;

import br.com.cwi.api.factories.HabitoFactory;
import br.com.cwi.crescer.api.controller.response.HabitoResumidoResponse;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.mapper.HabitoResumidoResponseMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class HabitoResumidoMapperTest {

    @Test
    @DisplayName("Deve retornar o response resumido quando receber um habito facil")
    void deveRetornarOResponseFacil() {

        Habito habito = HabitoFactory.getHabitoFacil();

        HabitoResumidoResponse response = HabitoResumidoResponseMapper.toResponse(habito);

        assertEquals(habito.getId(), response.getId());
        assertEquals(habito.getNome(), response.getNome());
        assertEquals(habito.getExecucoes(), response.getExecucoes());
    }

    @Test
    @DisplayName("Deve retornar o response resumido quando receber um habito com 3 execucoes")
    void deveRetornarOResponseComExecucoes() {

        Habito habito = HabitoFactory.getHabitoFacil();
        habito.setExecucoes(3);

        HabitoResumidoResponse response = HabitoResumidoResponseMapper.toResponse(habito);

        assertEquals(habito.getId(), response.getId());
        assertEquals(habito.getNome(), response.getNome());
        assertEquals(habito.getExecucoes(), response.getExecucoes());
    }

    @Test
    @DisplayName("Deve lançar erro quando habito for nulo")
    void deveLancarErroQuandoHabitoForNulo() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            HabitoResumidoResponse response = HabitoResumidoResponseMapper.toResponse(null);
        });
    }
}
