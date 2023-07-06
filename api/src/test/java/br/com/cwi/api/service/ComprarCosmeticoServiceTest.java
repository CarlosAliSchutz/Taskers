package br.com.cwi.api.service;

import br.com.cwi.api.factories.CosmeticoFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.domain.CosmeticoUsuario;
import br.com.cwi.crescer.api.repository.CosmeticoUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.ComprarCosmeticoService;
import br.com.cwi.crescer.api.service.RealizarConquistaService;
import br.com.cwi.crescer.api.service.core.BuscarCosmeticoService;
import br.com.cwi.crescer.api.service.core.ValidarUsuarioPossuiCosmeticoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.crescer.api.domain.TipoConquista.COMPRAR_COSMETICO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComprarCosmeticoServiceTest {

    @InjectMocks
    private ComprarCosmeticoService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private CosmeticoUsuarioRepository cosmeticoUsuarioRepository;
    @Mock
    private BuscarCosmeticoService buscarCosmeticoService;
    @Mock
    private ValidarUsuarioPossuiCosmeticoService validarUsuarioPossuiCosmeticoService;
    @Mock
    private RealizarConquistaService realizarConquistaService;
    @Captor
    private ArgumentCaptor<CosmeticoUsuario> cosmeticoCaptor;

    @Test
    @DisplayName("Deve comprar o cosmetico")
    void deveComprarOCosmetico() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Cosmetico cosmetico = CosmeticoFactory.getCosmeticoCenario();

        CosmeticoUsuario cosmeticoUsuario = new CosmeticoUsuario();
        cosmeticoUsuario.setUsuario(usuario);
        cosmeticoUsuario.setCosmetico(cosmetico);

        when(buscarCosmeticoService.porId(cosmetico.getId())).thenReturn(cosmetico);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        tested.comprar(cosmetico.getId());

        verify(validarUsuarioPossuiCosmeticoService).validarSeNaoPossui(usuario, cosmetico);
        verify(buscarCosmeticoService).porId(cosmetico.getId());
        verify(usuarioAutenticadoService).get();
        verify(usuarioRepository).save(usuario);
        verify(cosmeticoUsuarioRepository).save(cosmeticoUsuario);
        verify(realizarConquistaService).realizar(usuario.getId(), COMPRAR_COSMETICO);

        verify(cosmeticoUsuarioRepository).save(cosmeticoCaptor.capture());

        CosmeticoUsuario entityCosmeticoUsuario = cosmeticoCaptor.getValue();

        assertEquals(cosmeticoUsuario.getCosmetico(), entityCosmeticoUsuario.getCosmetico());
        assertEquals(cosmeticoUsuario.getUsuario(), entityCosmeticoUsuario.getUsuario());
    }
    @Test
    @DisplayName("Não deve comprar o cosmetico quando não tiver taskcoin")
    void naoDeveComprarOCosmeticoQuandoNaoTiverTaskcoin() {
        Usuario usuario = UsuarioFactory.getUsuarioComXP();
        Cosmetico cosmetico = CosmeticoFactory.getCosmeticoRoupa();

        when(buscarCosmeticoService.porId(cosmetico.getId())).thenReturn(cosmetico);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.comprar(cosmetico.getId());
        });

        verify(validarUsuarioPossuiCosmeticoService).validarSeNaoPossui(usuario, cosmetico);
        verify(cosmeticoUsuarioRepository, never()).save(any());
        verify(usuarioRepository, never()).save(any());
        verify(realizarConquistaService, never()).realizar(usuario.getId(), COMPRAR_COSMETICO);
    }

    @Test
    @DisplayName("Não deve comprar o cosmetico quando já possuir")
    void naoDeveComprarOCosmeticoQuandoJaPossuir() {
        Usuario usuario = UsuarioFactory.getUsuarioComXP();
        Cosmetico cosmetico = CosmeticoFactory.getCosmeticoRoupa();

        when(buscarCosmeticoService.porId(cosmetico.getId())).thenReturn(cosmetico);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        doThrow(ResponseStatusException.class)
                .when(validarUsuarioPossuiCosmeticoService).validarSeNaoPossui(usuario,cosmetico);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.comprar(cosmetico.getId());
        });

        verify(validarUsuarioPossuiCosmeticoService).validarSeNaoPossui(usuario, cosmetico);
        verify(cosmeticoUsuarioRepository, never()).save(any());
        verify(usuarioRepository, never()).save(any());
        verify(realizarConquistaService, never()).realizar(usuario.getId(), COMPRAR_COSMETICO);
    }
}
