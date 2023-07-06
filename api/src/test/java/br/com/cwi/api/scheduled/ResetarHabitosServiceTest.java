package br.com.cwi.api.scheduled;

import br.com.cwi.api.factories.HabitoFactory;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.repository.HabitoRepository;
import br.com.cwi.crescer.api.scheduled.ResetarHabitosService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResetarHabitosServiceTest {

    @InjectMocks
    private ResetarHabitosService tested;

    @Mock
    private HabitoRepository habitoRepository;

    @Test
    @DisplayName("Deve Resetar os habitos")
    void deveResetarOsHabitos() {
        List<Habito> habitos = HabitoFactory.getLista();

        when(habitoRepository.findAllByAtivoTrue()).thenReturn(habitos);

        tested.resetar();

        verify(habitoRepository).findAllByAtivoTrue();
        verify(habitoRepository).saveAll(habitos);

        assertEquals(0, habitos.get(0).getExecucoes());
        assertEquals(0, habitos.get(1).getExecucoes());
        assertEquals(0, habitos.get(2).getExecucoes());
    }

    @Test
    @DisplayName("Nao Deve Lancar Erro Quando A Lista De Habitos For Vazia")
    void naoDeveLancarErroQuandoAListaDeHabitosForVazia() {
        List<Habito> habitos = HabitoFactory.getListaVazia();

        when(habitoRepository.findAllByAtivoTrue()).thenReturn(habitos);

        tested.resetar();

        verify(habitoRepository).findAllByAtivoTrue();
        verify(habitoRepository).saveAll(habitos);

        assertEquals(0, habitos.size());
    }
}
