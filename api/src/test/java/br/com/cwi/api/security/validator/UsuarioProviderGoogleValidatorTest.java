package br.com.cwi.api.security.validator;

import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.validator.UsuarioProviderGoogleValidator;
import br.com.cwi.crescer.api.validator.ExecucaoNegativaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class UsuarioProviderGoogleValidatorTest {

    @InjectMocks
    private UsuarioProviderGoogleValidator tested;

    @Test
    @DisplayName("Deve Dar Erro Quando Usuario For Provider Google")
    void deveDarErroQuandoUsuarioForProviderGoogle() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.validar(usuario);
        });
    }

    @Test
    @DisplayName("Nao Deve Dar Erro Quando Usuario Nao For Provider Google")
    void naoDeveDarErroQuandoUsuarioNaoForProviderGoogle() {
        Usuario usuario = UsuarioFactory.getUsuarioComXP();

        tested.validar(usuario);
    }

}
