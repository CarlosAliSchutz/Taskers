package br.com.cwi.api.mapper;

import br.com.cwi.crescer.api.controller.request.AfazerRequest;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.domain.Dificuldade;
import br.com.cwi.crescer.api.mapper.AfazerRequestMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AfazerRequestMapperTest {

    @Test
    @DisplayName("Deve retornar o afazer quando receber um afazer facil")
    void deveRetornarOAfazerFacil() {
        AfazerRequest request = new AfazerRequest();
        request.setNome("Afazer testo ");
        request.setDescricao("decricao teste");
        request.setDificuldade(Dificuldade.FACIL);

        Afazer afazer = AfazerRequestMapper.toEntity(request);

        assertEquals(afazer.getNome(), request.getNome());
        assertEquals(afazer.getDescricao(), request.getDescricao());
        assertEquals(afazer.getDificuldade(), request.getDificuldade());
    }

    @Test
    @DisplayName("Deve retornar o afazer quando receber um afazer dificil")
    void deveRetornarOAfazerDificil() {
        AfazerRequest request = new AfazerRequest();
        request.setNome("Afazer testo ");
        request.setDescricao("decricao teste");
        request.setDificuldade(Dificuldade.DIFICIL);

        Afazer afazer = AfazerRequestMapper.toEntity(request);

        assertEquals(afazer.getNome(), request.getNome());
        assertEquals(afazer.getDescricao(), request.getDescricao());
        assertEquals(afazer.getDificuldade(), request.getDificuldade());
    }

}
