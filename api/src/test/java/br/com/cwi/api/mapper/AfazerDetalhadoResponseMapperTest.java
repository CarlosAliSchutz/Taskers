package br.com.cwi.api.mapper;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.crescer.api.controller.response.AfazerDetalhadoResponse;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.mapper.AfazerDetalhadoResponseMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AfazerDetalhadoResponseMapperTest {

    @Test
    @DisplayName("Deve retornar o response completo nao finalizado quando receber um afazer nao finalizado")
    void deveRetornarOResponseNaoFinalizado() {

        Afazer afazer = AfazerFactory.getAfazerFacil();

        AfazerDetalhadoResponse response = AfazerDetalhadoResponseMapper.toResponse(afazer);

        assertEquals(afazer.getId(), response.getId());
        assertEquals(afazer.isFinalizado(), response.isFinalizado());
        assertEquals(afazer.getNome(), response.getNome());
        assertEquals(afazer.getDescricao(), response.getDescricao());
        assertEquals(afazer.getDificuldade(), response.getDificuldade());
    }

    @Test
    @DisplayName("Deve retornar o response completo finalizado quando receber um afazer finalizado")
    void deveRetornarOResponseFinalizado() {

        Afazer afazer = AfazerFactory.getAfazerFinalizado();

        AfazerDetalhadoResponse response = AfazerDetalhadoResponseMapper.toResponse(afazer);

        assertEquals(afazer.getId(), response.getId());
        assertEquals(afazer.isFinalizado(), response.isFinalizado());
        assertEquals(afazer.getNome(), response.getNome());
        assertEquals(afazer.getDescricao(), response.getDescricao());
        assertEquals(afazer.getDificuldade(), response.getDificuldade());
    }

    @Test
    @DisplayName("Deve lançar erro quando afazer for nulo")
    void deveLancarErroQuandoAfazerForNulo() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            AfazerDetalhadoResponse response = AfazerDetalhadoResponseMapper.toResponse(null);
        });
    }
}
