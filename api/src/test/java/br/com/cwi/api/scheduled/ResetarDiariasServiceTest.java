package br.com.cwi.api.scheduled;

import br.com.cwi.api.factories.DiariaFactory;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.scheduled.ResetarDiariasService;
import br.com.cwi.crescer.api.service.core.NowService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResetarDiariasServiceTest {

    @InjectMocks
    private ResetarDiariasService tested;

    @Mock
    private DiariaRepository diariaRepository;

    @Mock
    private NowService nowService;

    @Test
    @DisplayName("Deve Resetar as diarias")
    void deveResetarAsDiarias() {
        List<Diaria> diarias = DiariaFactory.getLista();
        Long diaDaSemana = 1L;

        when(nowService.numeroDiaDaSemana()).thenReturn(diaDaSemana);
        when(diariaRepository.findByAtivoTrueAndDiasId(diaDaSemana)).thenReturn(diarias);

        tested.resetar();

        verify(nowService).numeroDiaDaSemana();
        verify(diariaRepository).findByAtivoTrueAndDiasId(diaDaSemana);
        verify(diariaRepository).saveAll(diarias);

        assertFalse(diarias.get(0).isFinalizado());
        assertFalse(diarias.get(1).isFinalizado());
        assertFalse(diarias.get(2).isFinalizado());
    }

    @Test
    @DisplayName("Nao Deve Lancar Erro Quando A Lista De Diarias For Vazia")
    void naoDeveLancarErroQuandoAListaDeDiariasForVazia() {
        List<Diaria> diarias = DiariaFactory.getListaVazia();
        Long diaDaSemana = 1L;

        when(nowService.numeroDiaDaSemana()).thenReturn(diaDaSemana);
        when(diariaRepository.findByAtivoTrueAndDiasId(diaDaSemana)).thenReturn(diarias);

        tested.resetar();

        verify(nowService).numeroDiaDaSemana();
        verify(diariaRepository).findByAtivoTrueAndDiasId(diaDaSemana);
        verify(diariaRepository).saveAll(diarias);

        assertEquals(0, diarias.size());
    }
}
