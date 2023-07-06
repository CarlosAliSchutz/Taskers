package br.com.cwi.api.mapper;

import br.com.cwi.api.factories.DiaFactory;
import br.com.cwi.crescer.api.controller.response.DiaResponse;
import br.com.cwi.crescer.api.domain.Dia;
import br.com.cwi.crescer.api.mapper.DiaResponseMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DiaResponseMapperTest {

    @Test
    @DisplayName("Deve retornar o response quando receber o dia domingo")
    void deveRetornarOResponseQuandoReceberODiaDomingo() {

        Dia dia = DiaFactory.getDomingo();

        DiaResponse response = DiaResponseMapper.toResponse(dia);

        assertEquals(dia.getId(), response.getId());
        assertEquals(dia.getNome(), response.getNome());
    }

    @Test
    @DisplayName("Deve retornar o response quando receber o dia segunda")
    void deveRetornarOResponseQuandoReceberODiaSegunda() {

        Dia dia = DiaFactory.getSegunda();

        DiaResponse response = DiaResponseMapper.toResponse(dia);

        assertEquals(dia.getId(), response.getId());
        assertEquals(dia.getNome(), response.getNome());
    }

    @Test
    @DisplayName("Deve lançar erro quando o dia for nulo")
    void deveLancarErroQuandoDiaForNulo() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            DiaResponse response = DiaResponseMapper.toResponse(null);
        });
    }
}
