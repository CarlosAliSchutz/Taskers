package br.com.cwi.crescer.api.security.validator;

import br.com.cwi.crescer.api.security.domain.Provider;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Component
public class UsuarioProviderGoogleValidator {

    public void validar(Usuario usuario) {
        if (usuario.getProvider().equals(Provider.GOOGLE)){
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Não é possivel alterar os dados de uma conta Google");
        }
    }
}
