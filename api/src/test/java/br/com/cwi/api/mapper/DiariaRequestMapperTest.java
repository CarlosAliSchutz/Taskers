package br.com.cwi.api.mapper;

import br.com.cwi.crescer.api.controller.request.DiariaRequest;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.domain.Dificuldade;
import br.com.cwi.crescer.api.mapper.DiariaRequestMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DiariaRequestMapperTest {

    @Test
    @DisplayName("Deve retornar a diaria quando receber uma diaria facil")
    void deveRetornarADiariaFacil() {
        DiariaRequest request = new DiariaRequest();
        request.setNome("Afazer testo ");
        request.setDescricao("decricao teste");
        request.setDificuldade(Dificuldade.FACIL);
        request.setHora(LocalTime.now());
        request.setDias(new ArrayList<>());

        Diaria diaria = DiariaRequestMapper.toEntity(request);

        assertEquals(diaria.getNome(), request.getNome());
        assertEquals(diaria.getDescricao(), request.getDescricao());
        assertEquals(diaria.getDificuldade(), request.getDificuldade());
        assertEquals(diaria.getHora(), request.getHora());
    }

    @Test
    @DisplayName("Deve retornar a diaria quando receber uma diaria dificil")
    void deveRetornarADiariaDificil() {
        DiariaRequest request = new DiariaRequest();
        request.setNome("Afazer testo ");
        request.setDescricao("decricao teste");
        request.setDificuldade(Dificuldade.DIFICIL);
        request.setHora(LocalTime.now());
        request.setDias(Arrays.asList(1L,2L));

        Diaria diaria = DiariaRequestMapper.toEntity(request);

        assertEquals(diaria.getNome(), request.getNome());
        assertEquals(diaria.getDescricao(), request.getDescricao());
        assertEquals(diaria.getDificuldade(), request.getDificuldade());
        assertEquals(diaria.getHora(), request.getHora());
    }

}
