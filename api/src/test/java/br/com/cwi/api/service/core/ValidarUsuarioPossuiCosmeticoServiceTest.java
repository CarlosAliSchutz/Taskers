package br.com.cwi.api.service.core;

import br.com.cwi.api.factories.CosmeticoFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.repository.CosmeticoUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.service.core.ValidarUsuarioPossuiCosmeticoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidarUsuarioPossuiCosmeticoServiceTest {

    @InjectMocks
    private ValidarUsuarioPossuiCosmeticoService tested;

    @Mock
    private CosmeticoUsuarioRepository cosmeticoUsuarioRepository;

    @Test
    @DisplayName("Nao deve passar quando usuario possuir o cosmetico")
    void naoDevePassarQuandoUsuarioPossuirOCosmetico() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Cosmetico cosmetico = CosmeticoFactory.getCosmeticoRoupa();

        when(cosmeticoUsuarioRepository.existsByUsuarioAndCosmetico(usuario, cosmetico)).thenReturn(true);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.validarSeNaoPossui(usuario, cosmetico));

        assertEquals("Cosmético já adquirido", exception.getReason());
    }
    @Test
    @DisplayName("Deve passar quando usuario nao possuir o cosmetico")
    void devePassarQuandoUsuarioNaoPossuirOCosmetico() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Cosmetico cosmetico = CosmeticoFactory.getCosmeticoRoupa();

        when(cosmeticoUsuarioRepository.existsByUsuarioAndCosmetico(usuario, cosmetico)).thenReturn(false);

        tested.validarSeNaoPossui(usuario, cosmetico);

        verify(cosmeticoUsuarioRepository).existsByUsuarioAndCosmetico(usuario, cosmetico);

    }
}
