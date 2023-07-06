package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.NotificacaoResponse;
import br.com.cwi.crescer.api.mapper.NotificacaoResponseMapper;
import br.com.cwi.crescer.api.repository.NotificacaoRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarNotificacoesService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public Page<NotificacaoResponse> listar(Pageable pageable) {
        Long usuarioId = usuarioAutenticadoService.getId();

        return notificacaoRepository.findAllByAtivoAndProprietarioId(true, usuarioId, pageable)
                .map(NotificacaoResponseMapper::toResponse);
    }
}
