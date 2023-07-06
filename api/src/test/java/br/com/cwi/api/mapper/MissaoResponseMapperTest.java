package br.com.cwi.api.mapper;

import br.com.cwi.api.factories.MissaoFactory;
import br.com.cwi.crescer.api.controller.response.MissaoResponse;
import br.com.cwi.crescer.api.domain.MissaoAfazer;
import br.com.cwi.crescer.api.domain.MissaoDiaria;
import br.com.cwi.crescer.api.domain.MissaoHabito;
import br.com.cwi.crescer.api.mapper.MissaoResponseMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MissaoResponseMapperTest {

    @Test
    @DisplayName("Deve Retornar O Response Quando Receber Uma Missao De Afazer")
    void deveRetornarOResponseQuandoReceberUmaMissaoDeAfazer() {

        MissaoAfazer missaoAfazer = MissaoFactory.getMissaoAfazer();

        MissaoResponse response = MissaoResponseMapper.toResponse(missaoAfazer);

        assertEquals(missaoAfazer.getId(), response.getId());
        assertEquals(missaoAfazer.getDescricao(), response.getDescricao());
        assertEquals(missaoAfazer.getNome(), response.getNome());
        assertEquals(missaoAfazer.isConcluida(), response.isConcluida());
    }
    @Test
    @DisplayName("Deve Retornar O Response Quando Receber Uma Missao De Habito")
    void deveRetornarOResponseQuandoReceberUmaMissaoDeHabito() {

        MissaoHabito missaoHabito = MissaoFactory.getMissaoHabito();

        MissaoResponse response = MissaoResponseMapper.toResponse(missaoHabito);

        assertEquals(missaoHabito.getId(), response.getId());
        assertEquals(missaoHabito.getDescricao(), response.getDescricao());
        assertEquals(missaoHabito.getNome(), response.getNome());
        assertEquals(missaoHabito.isConcluida(), response.isConcluida());
        assertEquals(missaoHabito.getExecucoesNecessarias(), response.getExecucoesNecessarias());

    }
    @Test
    @DisplayName("Deve Retornar O Response Quando Receber Uma Missao De Diaria")
    void deveRetornarOResponseQuandoReceberUmaMissaoDeDiaria() {

        MissaoDiaria missaoDiaria = MissaoFactory.getMissaoDiaria();

        MissaoResponse response = MissaoResponseMapper.toResponse(missaoDiaria);

        assertEquals(missaoDiaria.getId(), response.getId());
        assertEquals(missaoDiaria.getDescricao(), response.getDescricao());
        assertEquals(missaoDiaria.getNome(), response.getNome());
        assertEquals(missaoDiaria.isConcluida(), response.isConcluida());
    }




}
