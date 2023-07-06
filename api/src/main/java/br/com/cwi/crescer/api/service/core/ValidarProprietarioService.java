package br.com.cwi.crescer.api.service.core;

import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.domain.Notificacao;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service
public class ValidarProprietarioService {

    @Autowired
    private UsuarioAutenticadoService autenticadoService;

    public void doHabito(Habito habito) {
        if(!habito.getProprietario().equals(autenticadoService.get())) {
            throw new ResponseStatusException(FORBIDDEN, "Você não é proprietário deste hábito");
        }
    }

    public void doAfazer(Afazer afazer) {
        if(!afazer.getProprietario().equals(autenticadoService.get())) {
            throw new ResponseStatusException(FORBIDDEN, "Você não é proprietário deste afazer");
        }
    }

    public void daDiaria(Diaria diaria) {
        if(!diaria.getProprietario().equals(autenticadoService.get())) {
            throw new ResponseStatusException(FORBIDDEN, "Você não é proprietário desta diária");
        }
    }

    public void daNotificacao(Notificacao notificacao) {
        if(!notificacao.getProprietario().equals(autenticadoService.get())) {
            throw new ResponseStatusException(FORBIDDEN, "Você não é proprietário desta notificação");
        }
    }
}
