package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Notificacao;
import br.com.cwi.crescer.api.repository.NotificacaoRepository;
import br.com.cwi.crescer.api.service.core.BuscarNotificacaoService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletarNotificacaoService {

    @Autowired
    private BuscarNotificacaoService buscarNotificacaoService;

    @Autowired
    private ValidarProprietarioService validarProprietarioService;

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Transactional
    public void deletar(Long id) {
        Notificacao notificacao = buscarNotificacaoService.porId(id);

        validarProprietarioService.daNotificacao(notificacao);

        notificacao.setAtivo(false);

        notificacaoRepository.save(notificacao);
    }
}
