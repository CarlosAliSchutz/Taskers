package br.com.cwi.api.service;

import br.com.cwi.api.factories.UsuarioConquistaFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.UsuarioConquista;
import br.com.cwi.crescer.api.repository.UsuarioConquistaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.service.RealizarConquistaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static br.com.cwi.crescer.api.domain.TipoConquista.COMPRAR_COSMETICO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RealizarConquistaServiceTest {

    @InjectMocks
    private RealizarConquistaService tested;

    @Mock
    private UsuarioConquistaRepository usuarioConquistaRepository;

    @Captor
    private ArgumentCaptor<UsuarioConquista> usuarioConquistaCaptor;

    @Test
    @DisplayName("Deve Alterar Aumentar A Progressao Da Conquista Em Um")
    void deveAlterarAumentarAProgressaoDaConquistaEmUm() {
        Usuario usuario = UsuarioFactory.getUsuario();
        UsuarioConquista usuarioConquista = UsuarioConquistaFactory.getUsuarioConquistaInconcluida();
        List<UsuarioConquista> conquistas = new ArrayList<>();
        conquistas.add(usuarioConquista);

        when(usuarioConquistaRepository.findByUsuarioIdAndConquistaTipo(usuario.getId(), COMPRAR_COSMETICO))
                .thenReturn(conquistas);

        tested.realizar(usuario.getId(), COMPRAR_COSMETICO);

        verify(usuarioConquistaRepository).findByUsuarioIdAndConquistaTipo(usuario.getId(), COMPRAR_COSMETICO);
        verify(usuarioConquistaRepository).save(usuarioConquistaCaptor.capture());

        UsuarioConquista usuarioConquistaSalva = usuarioConquistaCaptor.getValue();

        assertFalse(usuarioConquistaSalva.isConcluida());
        assertEquals(usuarioConquista.getProgresso(),usuarioConquistaSalva.getProgresso());
        assertEquals(usuarioConquista.getId(),usuarioConquistaSalva.getId());
        assertEquals(usuarioConquista.getConquista().getId(),usuarioConquistaSalva.getConquista().getId());
    }
    @Test
    @DisplayName("Nao Deve Aumentar A Progessao Da Conquista Quando Ja Estiver Concluida")
    void naoDeveAumentarAProgessaoDaConquistaQuandoJaEstiverConcluida() {
        Usuario usuario = UsuarioFactory.getUsuario();
        UsuarioConquista usuarioConquista = UsuarioConquistaFactory.getUsuarioConquistaConcluida();
        List<UsuarioConquista> conquistas = new ArrayList<>();
        conquistas.add(usuarioConquista);

        when(usuarioConquistaRepository.findByUsuarioIdAndConquistaTipo(usuario.getId(), COMPRAR_COSMETICO))
                .thenReturn(conquistas);

        tested.realizar(usuario.getId(), COMPRAR_COSMETICO);

        verify(usuarioConquistaRepository).findByUsuarioIdAndConquistaTipo(usuario.getId(), COMPRAR_COSMETICO);
        verify(usuarioConquistaRepository, never()).save(usuarioConquista);
    }

    @Test
    @DisplayName("Deve Realizar A Conquista Quando Possuir O Numero De Progessao Desejado")
    void deveRealizarAConquistaQuandoPossuirONumeroDeProgessaoDesejado() {
        Usuario usuario = UsuarioFactory.getUsuario();
        UsuarioConquista usuarioConquista = UsuarioConquistaFactory.getUsuarioConquistaInconcluida();
        usuarioConquista.setProgresso(4);
        List<UsuarioConquista> conquistas = new ArrayList<>();
        conquistas.add(usuarioConquista);

        when(usuarioConquistaRepository.findByUsuarioIdAndConquistaTipo(usuario.getId(), COMPRAR_COSMETICO))
                .thenReturn(conquistas);

        tested.realizar(usuario.getId(), COMPRAR_COSMETICO);

        verify(usuarioConquistaRepository).findByUsuarioIdAndConquistaTipo(usuario.getId(), COMPRAR_COSMETICO);
        verify(usuarioConquistaRepository).save(usuarioConquistaCaptor.capture());

        UsuarioConquista usuarioConquistaSalva = usuarioConquistaCaptor.getValue();

        assertTrue(usuarioConquistaSalva.isConcluida());
        assertEquals(usuarioConquista.getProgresso(),usuarioConquistaSalva.getProgresso());
        assertEquals(usuarioConquista.getId(),usuarioConquistaSalva.getId());
        assertEquals(usuarioConquista.getConquista().getId(),usuarioConquistaSalva.getConquista().getId());
    }
}
