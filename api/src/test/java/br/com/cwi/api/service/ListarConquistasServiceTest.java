package br.com.cwi.api.service;

import br.com.cwi.api.factories.HabitoFactory;
import br.com.cwi.api.factories.UsuarioConquistaFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.controller.response.HabitoResumidoResponse;
import br.com.cwi.crescer.api.controller.response.UsuarioConquistaResponse;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.domain.UsuarioConquista;
import br.com.cwi.crescer.api.repository.HabitoRepository;
import br.com.cwi.crescer.api.repository.UsuarioConquistaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.ListarConquistasService;
import br.com.cwi.crescer.api.service.ListarHabitosService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarConquistasServiceTest {

    @InjectMocks
    private ListarConquistasService tested;

    @Mock
    private UsuarioConquistaRepository usuarioConquistaRepository;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Test
    @DisplayName("Deve listar todos as conquistas do usuário")
    void deveListarConquistas() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Pageable pageable = PageRequest.of(0,5);
        List<UsuarioConquista> usuarioConquistas = UsuarioConquistaFactory.getLista();
        Page<UsuarioConquista> usuarioConquistasPaginado = new PageImpl<>(usuarioConquistas);

        when(usuarioAutenticadoService.getId()).thenReturn(usuario.getId());
        when(usuarioConquistaRepository.findByUsuarioId(usuario.getId(),pageable)).thenReturn(usuarioConquistasPaginado);

        Page<UsuarioConquistaResponse> response = tested.listar(pageable);

        verify(usuarioAutenticadoService).getId();
        verify(usuarioConquistaRepository).findByUsuarioId(usuario.getId(),pageable);

        assertEquals(usuarioConquistas.size(), response.getSize());
        assertEquals(usuarioConquistas.get(0).getId(), response.getContent().get(0).getId());
        assertEquals(usuarioConquistas.get(1).getId(), response.getContent().get(1).getId());
        assertEquals(usuarioConquistas.get(2).getId(), response.getContent().get(2).getId());
    }
    @Test
    @DisplayName("Deve Listar Conquistas Vazias Quando Nao Houver Conquistas Do Usuario")
    void deveListarConquistasVaziasQuandoNaoHouverConquistasDoUsuario() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Pageable pageable = PageRequest.of(0,5);
        List<UsuarioConquista> usuarioConquistas = UsuarioConquistaFactory.getListaVazia();
        Page<UsuarioConquista> usuarioConquistasPaginado = new PageImpl<>(usuarioConquistas);

        when(usuarioAutenticadoService.getId()).thenReturn(usuario.getId());
        when(usuarioConquistaRepository.findByUsuarioId(usuario.getId(),pageable)).thenReturn(usuarioConquistasPaginado);

        Page<UsuarioConquistaResponse> response = tested.listar(pageable);

        verify(usuarioAutenticadoService).getId();
        verify(usuarioConquistaRepository).findByUsuarioId(usuario.getId(),pageable);

        assertEquals(usuarioConquistas.size(), response.getSize());
    }

}
