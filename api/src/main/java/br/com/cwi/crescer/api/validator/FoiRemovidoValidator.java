package br.com.cwi.crescer.api.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class FoiRemovidoValidator {

    public void validar(boolean ativo) {
        if(!ativo) {
            throw new ResponseStatusException(BAD_REQUEST, "Já foi removido");
        }
    }

}
