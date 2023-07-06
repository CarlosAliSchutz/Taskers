package br.com.cwi.api.mapper;

import br.com.cwi.api.factories.DiaFactory;
import br.com.cwi.api.factories.DiariaFactory;
import br.com.cwi.crescer.api.controller.response.DiariaDetalhadaResponse;
import br.com.cwi.crescer.api.domain.Dia;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.mapper.DiariaDetalhadaResponseMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DiariaDetalhadaMapperTest {

    @Test
    @DisplayName("Deve retornar o response completo quando receber uma diaria facil")
    void deveRetornarOResponseFacil() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        diaria.setDias(DiaFactory.getListaVazia());

        DiariaDetalhadaResponse response = DiariaDetalhadaResponseMapper.toResponse(diaria);

        assertEquals(diaria.getId(), response.getId());
        assertEquals(diaria.getNome(), response.getNome());
        assertEquals(diaria.getDescricao(), response.getDescricao());
        assertEquals(diaria.getDificuldade(), response.getDificuldade());
        assertEquals(diaria.getHora().format(DateTimeFormatter.ofPattern("HH:mm")), response.getHora());
        assertEquals(diaria.isFinalizado(), response.isFinalizado());
        assertEquals(diaria.getDias().size(), response.getDias().size());
    }

    @Test
    @DisplayName("Deve retornar o response completo quando receber uma diaria finalizada")
    void deveRetornarOResponseFinalizada() {

        Diaria diaria = DiariaFactory.getDiariaFinalizado();
        diaria.setDias(DiaFactory.getListaVazia());

        DiariaDetalhadaResponse response = DiariaDetalhadaResponseMapper.toResponse(diaria);

        assertEquals(diaria.getId(), response.getId());
        assertEquals(diaria.getNome(), response.getNome());
        assertEquals(diaria.getDescricao(), response.getDescricao());
        assertEquals(diaria.getDificuldade(), response.getDificuldade());
        assertEquals(diaria.getHora().format(DateTimeFormatter.ofPattern("HH:mm")), response.getHora());
        assertEquals(diaria.isFinalizado(), response.isFinalizado());
        assertEquals(diaria.getDias().size(), response.getDias().size());
    }

    @Test
    @DisplayName("Deve retornar o response completo quando receber uma diaria com dias")
    public void deveRetornarOResponseComDias() {
        Diaria diaria = DiariaFactory.getDiariaFinalizado();
        List<Dia> dias = DiaFactory.getLista();
        diaria.setDias(dias);

        DiariaDetalhadaResponse response = DiariaDetalhadaResponseMapper.toResponse(diaria);

        assertEquals(diaria.getId(), response.getId());
        assertEquals(diaria.getNome(), response.getNome());
        assertEquals(diaria.getDescricao(), response.getDescricao());
        assertEquals(diaria.getDificuldade(), response.getDificuldade());
        assertEquals(diaria.getHora().format(DateTimeFormatter.ofPattern("HH:mm")), response.getHora());
        assertEquals(diaria.isFinalizado(), response.isFinalizado());
        assertEquals(diaria.getDias().size(), response.getDias().size());
        assertEquals(diaria.getDias().get(0).getNome(), response.getDias().get(0).getNome());
        assertEquals(diaria.getDias().get(1).getNome(), response.getDias().get(1).getNome());
    }

    @Test
    @DisplayName("Deve lançar erro quando diaria for nula")
    void deveLancarErroQuandoDiariaForNula() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            DiariaDetalhadaResponse response = DiariaDetalhadaResponseMapper.toResponse(null);
        });
    }
}
