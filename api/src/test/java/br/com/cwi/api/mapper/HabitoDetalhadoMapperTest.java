package br.com.cwi.api.mapper;

import br.com.cwi.api.factories.HabitoFactory;
import br.com.cwi.crescer.api.controller.response.HabitoDetalhadoResponse;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.mapper.HabitoDetalhadoResponseMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class HabitoDetalhadoMapperTest {

    @Test
    @DisplayName("Deve retornar o response completo quando receber um habito facil")
    void deveRetornarOResponseFacil() {

        Habito habito = HabitoFactory.getHabitoFacil();

        HabitoDetalhadoResponse response = HabitoDetalhadoResponseMapper.toResponse(habito);

        assertEquals(habito.getId(), response.getId());
        assertEquals(habito.getNome(), response.getNome());
        assertEquals(habito.getDescricao(), response.getDescricao());
        assertEquals(habito.getDificuldade(), response.getDificuldade());
        assertEquals(habito.getExecucoes(), response.getExecucoes());
    }

    @Test
    @DisplayName("Deve retornar o response completo quando receber um habito com 3 execucoes")
    void deveRetornarOResponseComExecucoes() {

        Habito habito = HabitoFactory.getHabitoFacil();
        habito.setExecucoes(3);

        HabitoDetalhadoResponse response = HabitoDetalhadoResponseMapper.toResponse(habito);

        assertEquals(habito.getId(), response.getId());
        assertEquals(habito.getNome(), response.getNome());
        assertEquals(habito.getDescricao(), response.getDescricao());
        assertEquals(habito.getDificuldade(), response.getDificuldade());
        assertEquals(habito.getExecucoes(), response.getExecucoes());
    }

    @Test
    @DisplayName("Deve lançar erro quando habito for nulo")
    void deveLancarErroQuandoHabitoForNulo() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            HabitoDetalhadoResponse response = HabitoDetalhadoResponseMapper.toResponse(null);
        });
    }
}
