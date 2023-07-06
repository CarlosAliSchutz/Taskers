package br.com.cwi.api.service;

import br.com.cwi.api.factories.HabitoFactory;
import br.com.cwi.api.factories.RequestFactory;
import br.com.cwi.crescer.api.controller.request.HabitoRequest;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.repository.HabitoRepository;
import br.com.cwi.crescer.api.service.AlterarHabitoService;
import br.com.cwi.crescer.api.service.core.BuscarHabitoService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlterarHabitoServiceTest {

    @InjectMocks
    private AlterarHabitoService tested;

    @Mock
    private HabitoRepository habitoRepository;

    @Mock
    private BuscarHabitoService buscarHabitoService;

    @Mock
    private ValidarProprietarioService validarProprietarioService;

    @Test
    @DisplayName("Deve alterar habito completo")
    void deveAlterarHabitoCompleto() {
        Habito habito = HabitoFactory.getHabitoFacil();
        Long habitoId = habito.getId();
        HabitoRequest request = RequestFactory.getHabitoMedioRequest();

        when(buscarHabitoService.porId(habitoId)).thenReturn(habito);

        tested.alterar(habitoId, request);

        verify(buscarHabitoService).porId(habitoId);
        verify(validarProprietarioService).doHabito(habito);
        verify(habitoRepository).save(habito);

        assertEquals(habito.getNome(), request.getNome());
        assertEquals(habito.getDescricao(), request.getDescricao());
        assertEquals(habito.getDificuldade(), request.getDificuldade());

    }

    @Test
    @DisplayName("Não deve alterar caso não encontre o habito")
    void naoDeveAlterarNaoEncontre() {
        Habito habito = HabitoFactory.getHabitoFacil();
        Long habitoId = habito.getId();
        HabitoRequest request = RequestFactory.getHabitoMedioRequest();

        doThrow(ResponseStatusException.class)
                .when(buscarHabitoService).porId(habitoId);


        assertThrows(ResponseStatusException.class, () -> {
            tested.alterar(habitoId, request);
        });

        verify(buscarHabitoService).porId(habitoId);
        verify(validarProprietarioService, never()).doHabito(habito);
        verify(habitoRepository, never()).save(habito);
    }

    @Test
    @DisplayName("Não deve alterar caso não seja o proprietário do habito")
    void naoDeveAlterarCasoProprietarioErrado() {
        Habito habito = HabitoFactory.getHabitoFacil();
        Long habitoId = habito.getId();
        HabitoRequest request = RequestFactory.getHabitoMedioRequest();

        when(buscarHabitoService.porId(habitoId)).thenReturn(habito);
        doThrow(ResponseStatusException.class)
                .when(validarProprietarioService).doHabito(habito);

        assertThrows(ResponseStatusException.class, () -> {
            tested.alterar(habitoId, request);
        });

        verify(buscarHabitoService).porId(habitoId);
        verify(validarProprietarioService).doHabito(habito);
        verify(habitoRepository, never()).save(habito);

    }

}
