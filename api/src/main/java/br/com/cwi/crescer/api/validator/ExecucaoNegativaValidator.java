package br.com.cwi.crescer.api.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class ExecucaoNegativaValidator {

    public void estaZerado(Integer execucoes) {
        if(execucoes==0) {
            throw new ResponseStatusException(BAD_REQUEST, "Você não pode diminuir para abaixo de zero");
        }
    }

}
