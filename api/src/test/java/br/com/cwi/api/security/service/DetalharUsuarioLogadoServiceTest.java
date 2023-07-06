package br.com.cwi.api.security.service;

import br.com.cwi.api.factories.CosmeticoFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.domain.CosmeticoUsuario;
import br.com.cwi.crescer.api.repository.CosmeticoUsuarioRepository;
import br.com.cwi.crescer.api.security.controller.response.UsuarioResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.DetalharUsuarioLogadoService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DetalharUsuarioLogadoServiceTest {
    @InjectMocks
    private DetalharUsuarioLogadoService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private CosmeticoUsuarioRepository cosmeticoUsuarioRepository;

    @Test
    @DisplayName("Deve detalhar usuario logado")
    public void deveDetalharUsuarioLogado() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Cosmetico cosmetico = CosmeticoFactory.getCosmeticoCenario();

        CosmeticoUsuario cosmeticoUsuario = new CosmeticoUsuario();
        cosmeticoUsuario.setUsuario(usuario);
        cosmeticoUsuario.setCosmetico(cosmetico);

        List<CosmeticoUsuario> cosmeticosDoUsuario = new ArrayList<>();
        cosmeticosDoUsuario.add(cosmeticoUsuario);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(cosmeticoUsuarioRepository.findAllByUsuarioIdAndEquipadoIsTrue(usuario.getId())).thenReturn(cosmeticosDoUsuario);

        UsuarioResponse response = tested.detalhar();

        verify(usuarioAutenticadoService).get();
        verify(cosmeticoUsuarioRepository).findAllByUsuarioIdAndEquipadoIsTrue(usuario.getId());

        assertEquals(response.getId(), usuario.getId());
        assertEquals(response.getNomeCompleto(), usuario.getNomeCompleto());
        assertEquals(response.getEmail(), usuario.getEmail());
        assertEquals(response.getImagemPerfil(), usuario.getImagemPerfil());
        assertEquals(response.getCosmeticosEquipados().get(0).getId(), cosmeticoUsuario.getCosmetico().getId());
    }

    @Test
    @DisplayName("Deve detalhar usuario logado sem cosmeticos")
    public void deveDetalharUsuarioLogadoSemCosmeticos() {
        Usuario usuario = UsuarioFactory.getUsuario();

        List<CosmeticoUsuario> cosmeticosDoUsuario = new ArrayList<>();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(cosmeticoUsuarioRepository.findAllByUsuarioIdAndEquipadoIsTrue(usuario.getId())).thenReturn(cosmeticosDoUsuario);

        UsuarioResponse response = tested.detalhar();

        verify(usuarioAutenticadoService).get();
        verify(cosmeticoUsuarioRepository).findAllByUsuarioIdAndEquipadoIsTrue(usuario.getId());

        assertEquals(response.getId(), usuario.getId());
        assertEquals(response.getNomeCompleto(), usuario.getNomeCompleto());
        assertEquals(response.getEmail(), usuario.getEmail());
        assertEquals(response.getImagemPerfil(), usuario.getImagemPerfil());
        assertEquals(response.getCosmeticosEquipados(), new ArrayList<>());
    }

}
