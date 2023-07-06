package br.com.cwi.api.mapper;

import br.com.cwi.api.factories.ConquistaFactory;
import br.com.cwi.crescer.api.controller.response.ConquistaResponse;
import br.com.cwi.crescer.api.domain.Conquista;
import br.com.cwi.crescer.api.mapper.ConquistaResponseMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ConquistaResponseMapperTest {

    @Test
    @DisplayName("Deve retornar o response quando receber uma conquista facil")
    void deveRetornarOResponseQuandoReceberConquistaFacil() {

        Conquista conquista = ConquistaFactory.getConquistaFacil();

        ConquistaResponse response = ConquistaResponseMapper.toResponse(conquista);

        assertEquals(conquista.getId(), response.getId());
        assertEquals(conquista.getObjetivo(), response.getObjetivo());
        assertEquals(conquista.getTipo(), response.getTipo());
        assertEquals(conquista.getNome(), response.getNome());
        assertEquals(conquista.getDificuldade(), response.getDificuldade());
    }

    @Test
    @DisplayName("Deve retornar o response quando receber uma conquista media")
    void deveRetornarOResponseQuandoReceberConquistaMedia() {

        Conquista conquista = ConquistaFactory.getConquistaMedia();

        ConquistaResponse response = ConquistaResponseMapper.toResponse(conquista);

        assertEquals(conquista.getId(), response.getId());
        assertEquals(conquista.getObjetivo(), response.getObjetivo());
        assertEquals(conquista.getTipo(), response.getTipo());
        assertEquals(conquista.getNome(), response.getNome());
        assertEquals(conquista.getDificuldade(), response.getDificuldade());
    }

    @Test
    @DisplayName("Deve retornar o response quando receber uma conquista dificil")
    void deveRetornarOResponseQuandoReceberConquistaDificil() {

        Conquista conquista = ConquistaFactory.getConquistaDificil();

        ConquistaResponse response = ConquistaResponseMapper.toResponse(conquista);

        assertEquals(conquista.getId(), response.getId());
        assertEquals(conquista.getObjetivo(), response.getObjetivo());
        assertEquals(conquista.getTipo(), response.getTipo());
        assertEquals(conquista.getNome(), response.getNome());
        assertEquals(conquista.getDificuldade(), response.getDificuldade());
    }


    @Test
    @DisplayName("Deve lançar erro quando conquista for nula")
    void deveLancarErroQuandoConquistaForNula() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            ConquistaResponse response = ConquistaResponseMapper.toResponse(null);
        });
    }
}
