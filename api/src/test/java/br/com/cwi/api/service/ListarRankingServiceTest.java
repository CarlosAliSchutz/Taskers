package br.com.cwi.api.service;

import br.com.cwi.api.factories.NotificacaoFactory;
import br.com.cwi.api.factories.SimpleFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.controller.response.NotificacaoResponse;
import br.com.cwi.crescer.api.controller.response.UsuarioRankingResponse;
import br.com.cwi.crescer.api.domain.Notificacao;
import br.com.cwi.crescer.api.mapper.UsuarioRankingResponseMapper;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.service.ListarRankingService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarRankingServiceTest {

    @InjectMocks
    private ListarRankingService tested;

    @Mock
    private UsuarioRepository usuarioRepository;


    @Test
    @DisplayName("Deve listar todos os usuarios no rank")
    void deveListarTodosOsUsuariosDoRank() {
        Pageable pageable = PageRequest.of(0,5);
        List<Usuario> usuarios = UsuarioFactory.getLista();
        Page<Usuario> usuariosPagindos = new PageImpl<>(usuarios);
        String nomePadrao = "";

        when(usuarioRepository.findByAtivoTrueAndNomeCompletoContainingIgnoreCase(nomePadrao,pageable))
                .thenReturn(usuariosPagindos);
        usuarios.forEach(usuario -> {
            when(usuarioRepository.findPosicaoUsuario(usuario.getExperiencia(),usuario.getId())).thenReturn(SimpleFactory.getRandomLong());
        });

        Page<UsuarioRankingResponse> response = tested.listar(nomePadrao, pageable);

        verify(usuarioRepository).findByAtivoTrueAndNomeCompletoContainingIgnoreCase(nomePadrao,pageable);
        usuarios.forEach(usuario -> {
            verify(usuarioRepository).findPosicaoUsuario(usuario.getExperiencia(), usuario.getId());
        });

        assertEquals(usuariosPagindos.getSize(), response.getContent().size());
        assertEquals(usuariosPagindos.getContent().get(0).getExperiencia(), response.getContent().get(0).getExperiencia());
        assertEquals(usuariosPagindos.getContent().get(1).getExperiencia(), response.getContent().get(1).getExperiencia());
        assertEquals(usuariosPagindos.getContent().get(2).getExperiencia(), response.getContent().get(2).getExperiencia());
    }
    @Test
    @DisplayName("Deve listar o usuario filtrado pro nome")
    void deveListarOUsuarioFiltradoPorNome() {
        Pageable pageable = PageRequest.of(0,5);
        String nomeFiltrado = "nome filtrado";
        Usuario usuario = UsuarioFactory.getUsuario();
        usuario.setNomeCompleto(nomeFiltrado);

        Page<Usuario> usuariosPagindos = new PageImpl<>(Collections.singletonList(usuario));

        when(usuarioRepository.findByAtivoTrueAndNomeCompletoContainingIgnoreCase(nomeFiltrado,pageable))
                .thenReturn(usuariosPagindos);
        when(usuarioRepository.findPosicaoUsuario(usuario.getExperiencia(),usuario.getId())).thenReturn(SimpleFactory.getRandomLong());

        Page<UsuarioRankingResponse> response = tested.listar(nomeFiltrado, pageable);

        verify(usuarioRepository).findByAtivoTrueAndNomeCompletoContainingIgnoreCase(nomeFiltrado,pageable);
        verify(usuarioRepository).findPosicaoUsuario(usuario.getExperiencia(), usuario.getId());

        assertEquals(1,response.getContent().size());
        assertEquals(usuariosPagindos.getContent().get(0).getExperiencia(), response.getContent().get(0).getExperiencia());
    }

}
