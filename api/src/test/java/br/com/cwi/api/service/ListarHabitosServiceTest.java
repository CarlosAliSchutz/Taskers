package br.com.cwi.api.service;

import br.com.cwi.api.factories.HabitoFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.controller.response.HabitoResumidoResponse;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.repository.HabitoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.ListarHabitosService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarHabitosServiceTest {

    @InjectMocks
    private ListarHabitosService tested;

    @Mock
    private HabitoRepository habitoRepository;

    @Mock
    private UsuarioAutenticadoService autenticadoService;

    @Test
    @DisplayName("Deve listar todos os habitos do usuário")
    void deveListarHabitos() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Pageable pageable = PageRequest.of(0,5);
        List<Habito> habito = HabitoFactory.getLista();
        String text = "Teste";
        Page<Habito> habitosPaginadas = new PageImpl<>(habito);

        when(autenticadoService.getId()).thenReturn(usuario.getId());
        when(habitoRepository.findByAtivoAndProprietarioIdAndNomeContainingIgnoreCase
                (true, usuario.getId(), text, pageable)).thenReturn(habitosPaginadas);

        Page<HabitoResumidoResponse> response = tested.listar(text, pageable);

        verify(habitoRepository).findByAtivoAndProprietarioIdAndNomeContainingIgnoreCase
                (true, usuario.getId(), text, pageable);
        verify(autenticadoService).getId();

        assertEquals(habito.size(), response.getSize());
        assertEquals(habito.get(0).getId(), response.getContent().get(0).getId());
        assertEquals(habito.get(1).getId(), response.getContent().get(1).getId());
        assertEquals(habito.get(2).getId(), response.getContent().get(2).getId());
    }

    @Test
    @DisplayName("Deve retornar lista vazia caso não houver habitos")
    void deveRetornarListaVazia() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Pageable pageable = PageRequest.of(0,5);
        List<Habito> habito = HabitoFactory.getListaVazia();
        String text = "Teste";
        Page<Habito> habitosPaginadas = new PageImpl<>(habito);

        when(autenticadoService.getId()).thenReturn(usuario.getId());
        when(habitoRepository.findByAtivoAndProprietarioIdAndNomeContainingIgnoreCase
                (true, usuario.getId(), text, pageable)).thenReturn(habitosPaginadas);

        Page<HabitoResumidoResponse> response = tested.listar(text, pageable);

        verify(habitoRepository).findByAtivoAndProprietarioIdAndNomeContainingIgnoreCase
                (true, usuario.getId(), text, pageable);
        verify(autenticadoService).getId();

        assertEquals(habito.size(), response.getSize());
    }

}
