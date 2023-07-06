package br.com.cwi.api.mapper;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.crescer.api.controller.response.AfazerResumidoResponse;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.mapper.AfazerResumidoResponseMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AfazerResumidoResponseMapperTest {

    @Test
    @DisplayName("Deve retornar o response resumido nao finalizado quando receber um afazer nao finalizado")
    void deveRetornarOResponseNaoFinalizado() {

        Afazer afazer = AfazerFactory.getAfazerFacil();

        AfazerResumidoResponse response = AfazerResumidoResponseMapper.toResponse(afazer);

        assertEquals(afazer.getId(), response.getId());
        assertEquals(afazer.isFinalizado(), response.isFinalizado());
        assertEquals(afazer.getNome(), response.getNome());
    }

    @Test
    @DisplayName("Deve retornar o response resumido finalizado quando receber um afazer finalizado")
    void deveRetornarOResponseFinalizado() {

        Afazer afazer = AfazerFactory.getAfazerFinalizado();

        AfazerResumidoResponse response = AfazerResumidoResponseMapper.toResponse(afazer);

        assertEquals(afazer.getId(), response.getId());
        assertEquals(afazer.isFinalizado(), response.isFinalizado());
        assertEquals(afazer.getNome(), response.getNome());
    }

    @Test
    @DisplayName("Deve lançar erro quando afazer for nulo")
    void deveLancarErroQuandoAfazerForNulo() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            AfazerResumidoResponse response = AfazerResumidoResponseMapper.toResponse(null);
        });
    }
}
