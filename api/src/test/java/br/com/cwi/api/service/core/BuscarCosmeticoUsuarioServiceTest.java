package br.com.cwi.api.service.core;

import br.com.cwi.api.factories.CosmeticoFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.domain.CosmeticoUsuario;
import br.com.cwi.crescer.api.repository.CosmeticoUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.service.core.BuscarCosmeticoUsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarCosmeticoUsuarioServiceTest {

    @InjectMocks
    private BuscarCosmeticoUsuarioService tested;

    @Mock
    private CosmeticoUsuarioRepository cosmeticoUsuarioRepository;

    @Test
    @DisplayName("Deve buscar a relação entre cosmetico e usuario")
    void deveRetornarARelacaoEntreUsuarioeECosmetico() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Cosmetico cosmetico = CosmeticoFactory.getCosmeticoRoupa();

        CosmeticoUsuario cosmeticoUsuario = new CosmeticoUsuario();
        cosmeticoUsuario.setUsuario(usuario);
        cosmeticoUsuario.setCosmetico(cosmetico);

        when(cosmeticoUsuarioRepository.findByUsuarioAndCosmetico(usuario,cosmetico)).thenReturn(Optional.of(cosmeticoUsuario));

        CosmeticoUsuario retorno = tested.buscar(usuario, cosmetico);

        verify(cosmeticoUsuarioRepository).findByUsuarioAndCosmetico(usuario, cosmetico);
        assertEquals(cosmeticoUsuario, retorno);
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrar a relação")
    void deveRetornarErro() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Cosmetico cosmetico = CosmeticoFactory.getCosmeticoRoupa();

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.buscar(usuario, cosmetico));

        assertEquals("Você não possui esse cosmético", exception.getReason());
    }

}
