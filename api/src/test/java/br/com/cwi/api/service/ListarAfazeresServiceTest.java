package br.com.cwi.api.service;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.controller.response.AfazerResumidoResponse;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.ListarAfazeresService;
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
public class ListarAfazeresServiceTest {

    @InjectMocks
    private ListarAfazeresService tested;

    @Mock
    private AfazerRepository afazerRepository;

    @Mock
    private UsuarioAutenticadoService autenticadoService;

    @Test
    @DisplayName("Deve listar todos os afazeres do usuário")
    void deveListarAfazeres() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Pageable pageable = PageRequest.of(0,5);
        List<Afazer> afazer = AfazerFactory.getLista();
        String text = "Teste";
        Page<Afazer> afazersPaginadas = new PageImpl<>(afazer);

        when(autenticadoService.getId()).thenReturn(usuario.getId());
        when(afazerRepository.findByAtivoAndProprietarioIdAndNomeContainingIgnoreCase
                (true, usuario.getId(), text, pageable)).thenReturn(afazersPaginadas);

        Page<AfazerResumidoResponse> response = tested.listar(text, pageable);

        verify(afazerRepository).findByAtivoAndProprietarioIdAndNomeContainingIgnoreCase
                (true, usuario.getId(), text, pageable);
        verify(autenticadoService).getId();

        assertEquals(afazer.size(), response.getSize());
        assertEquals(afazer.get(0).getId(), response.getContent().get(0).getId());
        assertEquals(afazer.get(1).getId(), response.getContent().get(1).getId());
        assertEquals(afazer.get(2).getId(), response.getContent().get(2).getId());
    }

    @Test
    @DisplayName("Deve retornar lista vazia caso não houver afazers")
    void deveRetornarListaVazia() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Pageable pageable = PageRequest.of(0,5);
        List<Afazer> afazer = AfazerFactory.getListaVazia();
        String text = "Teste";
        Page<Afazer> afazersPaginadas = new PageImpl<>(afazer);

        when(autenticadoService.getId()).thenReturn(usuario.getId());
        when(afazerRepository.findByAtivoAndProprietarioIdAndNomeContainingIgnoreCase
                (true, usuario.getId(), text, pageable)).thenReturn(afazersPaginadas);

        Page<AfazerResumidoResponse> response = tested.listar(text, pageable);

        verify(afazerRepository).findByAtivoAndProprietarioIdAndNomeContainingIgnoreCase
                (true, usuario.getId(), text, pageable);
        verify(autenticadoService).getId();

        assertEquals(afazer.size(), response.getSize());
    }

}
