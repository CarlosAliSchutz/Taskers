package br.com.cwi.api.mapper;

import br.com.cwi.crescer.api.controller.request.HabitoRequest;
import br.com.cwi.crescer.api.domain.Dificuldade;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.mapper.HabitoRequestMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class HabitoRequestMapperTest {

    @Test
    @DisplayName("Deve retornar o habito quando receber um habito facil")
    void deveRetornarOHabitoFacil() {
        HabitoRequest request = new HabitoRequest();
        request.setNome("Afazer testo ");
        request.setDescricao("decricao teste");
        request.setDificuldade(Dificuldade.FACIL);

        Habito habito = HabitoRequestMapper.toEntity(request);

        assertEquals(habito.getNome(), request.getNome());
        assertEquals(habito.getDescricao(), request.getDescricao());
        assertEquals(habito.getDificuldade(), request.getDificuldade());
    }

    @Test
    @DisplayName("Deve retornar o habito quando receber um habito dificil")
    void deveRetornarOHabitoDificil() {
        HabitoRequest request = new HabitoRequest();
        request.setNome("Afazer testo ");
        request.setDescricao("decricao teste");
        request.setDificuldade(Dificuldade.DIFICIL);

        Habito habito = HabitoRequestMapper.toEntity(request);

        assertEquals(habito.getNome(), request.getNome());
        assertEquals(habito.getDescricao(), request.getDescricao());
        assertEquals(habito.getDificuldade(), request.getDificuldade());
    }

}
