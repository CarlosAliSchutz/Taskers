package br.com.cwi.api.service;

import br.com.cwi.api.factories.CosmeticoFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.domain.CosmeticoUsuario;
import br.com.cwi.crescer.api.repository.CosmeticoUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.EquiparCosmeticoService;
import br.com.cwi.crescer.api.service.core.BuscarCosmeticoService;
import br.com.cwi.crescer.api.service.core.BuscarCosmeticoUsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EquiparCosmeticoServiceTest {

    @InjectMocks
    private EquiparCosmeticoService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CosmeticoUsuarioRepository cosmeticoUsuarioRepository;

    @Mock
    private BuscarCosmeticoUsuarioService buscarCosmeticoUsuarioService;

    @Mock
    private BuscarCosmeticoService buscarCosmeticoService;

    @Test
    @DisplayName("Deve equipar o cosmetico quando não possuir outro cosmetico equipado")
    void deveEquiparOCosmeticoQuandoNaoPossuirOutroCosmeticoEquipado() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Cosmetico cosmetico = CosmeticoFactory.getCosmeticoCenario();

        CosmeticoUsuario cosmeticoUsuario = new CosmeticoUsuario();
        cosmeticoUsuario.setUsuario(usuario);
        cosmeticoUsuario.setCosmetico(cosmetico);

        when(buscarCosmeticoService.porId(cosmetico.getId())).thenReturn(cosmetico);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarCosmeticoUsuarioService.buscar(usuario, cosmetico)).thenReturn(cosmeticoUsuario);
        when(cosmeticoUsuarioRepository
                .findByUsuarioAndCosmeticoTipoAndEquipado(usuario, cosmetico.getTipo(), true)).thenReturn(null);

        tested.equipar(cosmetico.getId());

        verify(buscarCosmeticoUsuarioService).buscar(usuario, cosmetico);
        verify(buscarCosmeticoService).porId(cosmetico.getId());
        verify(usuarioAutenticadoService).get();
        verify(cosmeticoUsuarioRepository)
                .findByUsuarioAndCosmeticoTipoAndEquipado(usuario, cosmetico.getTipo(), true);
        verify(usuarioRepository).save(usuario);

        assertTrue(cosmeticoUsuario.isEquipado());
    }

    @Test
    @DisplayName("Deve lancar erro quando já estiver equipado")
    void deveDeveLancarErroQuandoJaEstiverEquipado() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Cosmetico cosmetico = CosmeticoFactory.getCosmeticoCenario();

        CosmeticoUsuario cosmeticoUsuario = new CosmeticoUsuario();
        cosmeticoUsuario.setUsuario(usuario);
        cosmeticoUsuario.setCosmetico(cosmetico);
        cosmeticoUsuario.setEquipado(true);

        when(buscarCosmeticoService.porId(cosmetico.getId())).thenReturn(cosmetico);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarCosmeticoUsuarioService.buscar(usuario, cosmetico)).thenReturn(cosmeticoUsuario);
        when(cosmeticoUsuarioRepository
                .findByUsuarioAndCosmeticoTipoAndEquipado(usuario, cosmetico.getTipo(), true)).thenReturn(cosmeticoUsuario);

        assertThrows(ResponseStatusException.class, () -> {
            tested.equipar(cosmetico.getId());
        });

        verify(buscarCosmeticoUsuarioService).buscar(usuario, cosmetico);
        verify(buscarCosmeticoService).porId(cosmetico.getId());
        verify(usuarioAutenticadoService).get();
        verify(cosmeticoUsuarioRepository)
                .findByUsuarioAndCosmeticoTipoAndEquipado(usuario, cosmetico.getTipo(), true);
        verify(usuarioRepository, never()).save(usuario);

        assertTrue(cosmeticoUsuario.isEquipado());
    }

    @Test
    @DisplayName("Deve desequipar o cosmetico equipado e equipar o novo cosmetico")
    void deveDesequiparOCosmeticoEquipadoEEquiparONovoCosmetico() {
        Usuario usuario = UsuarioFactory.getUsuario();

        Cosmetico cosmeticoEquipado = CosmeticoFactory.getCosmeticoCenario();
        CosmeticoUsuario cosmeticoUsuarioEquipado = new CosmeticoUsuario();
        cosmeticoUsuarioEquipado.setId(1L);
        cosmeticoUsuarioEquipado.setUsuario(usuario);
        cosmeticoUsuarioEquipado.setCosmetico(cosmeticoEquipado);
        cosmeticoUsuarioEquipado.setEquipado(true);

        Cosmetico cosmeticoParaSerEquipado = CosmeticoFactory.getCosmeticoCenario();
        CosmeticoUsuario cosmeticoUsuarioParaSerEquipado = new CosmeticoUsuario();
        cosmeticoUsuarioParaSerEquipado.setId(2L);
        cosmeticoUsuarioParaSerEquipado.setUsuario(usuario);
        cosmeticoUsuarioParaSerEquipado.setCosmetico(cosmeticoParaSerEquipado);
        cosmeticoUsuarioParaSerEquipado.setEquipado(false);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarCosmeticoService.porId(cosmeticoParaSerEquipado.getId())).thenReturn(cosmeticoParaSerEquipado);
        when(buscarCosmeticoUsuarioService.buscar(usuario, cosmeticoParaSerEquipado)).thenReturn(cosmeticoUsuarioParaSerEquipado);
        when(cosmeticoUsuarioRepository
                .findByUsuarioAndCosmeticoTipoAndEquipado(usuario, cosmeticoParaSerEquipado.getTipo(), true)).thenReturn(cosmeticoUsuarioEquipado);

        tested.equipar(cosmeticoParaSerEquipado.getId());

        verify(buscarCosmeticoUsuarioService).buscar(usuario, cosmeticoParaSerEquipado);
        verify(buscarCosmeticoService).porId(cosmeticoParaSerEquipado.getId());
        verify(usuarioAutenticadoService).get();
        verify(cosmeticoUsuarioRepository)
                .findByUsuarioAndCosmeticoTipoAndEquipado(usuario, cosmeticoParaSerEquipado.getTipo(), true);
        verify(usuarioRepository).save(usuario);

        assertFalse(cosmeticoUsuarioEquipado.isEquipado());
        assertTrue(cosmeticoUsuarioParaSerEquipado.isEquipado());
    }


}

