package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.response.NotificacaoResponse;
import br.com.cwi.crescer.api.service.AlterarNotificacoesEmailService;
import br.com.cwi.crescer.api.service.DeletarNotificacaoService;
import br.com.cwi.crescer.api.service.ListarNotificacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    @Autowired
    private ListarNotificacoesService listarNotificacoesService;

    @Autowired
    private DeletarNotificacaoService deletarNotificacaoService;

    @Autowired
    private AlterarNotificacoesEmailService alterarNotificacoesEmailService;

    @GetMapping
    @ResponseStatus(OK)
    public Page<NotificacaoResponse> listar(Pageable pageable) {
        return listarNotificacoesService.listar(pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        deletarNotificacaoService.deletar(id);
    }

    @PutMapping("/email")
    @ResponseStatus(OK)
    public void alterarNotificacoesEmail() {
        alterarNotificacoesEmailService.alterar();
    }

}
