package br.com.cwi.crescer.api.service.core;

import br.com.cwi.crescer.api.domain.Notificacao;
import br.com.cwi.crescer.api.repository.NotificacaoRepository;
import br.com.cwi.crescer.api.validator.FoiRemovidoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class BuscarNotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private FoiRemovidoValidator foiRemovidoValidator;

    public Notificacao porId(Long id) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Notificação não encontrada"));

        foiRemovidoValidator.validar(notificacao.isAtivo());

        return notificacao;
    }

}
