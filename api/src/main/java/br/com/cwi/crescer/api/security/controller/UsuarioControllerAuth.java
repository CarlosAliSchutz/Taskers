package br.com.cwi.crescer.api.security.controller;

import br.com.cwi.crescer.api.security.controller.request.EditarSenhaEsquecidaRequest;
import br.com.cwi.crescer.api.security.controller.request.EsqueciSenhaRequest;
import br.com.cwi.crescer.api.security.controller.request.IncluirUsuarioRequest;
import br.com.cwi.crescer.api.security.controller.response.UsuarioResponse;
import br.com.cwi.crescer.api.security.service.IncluirUsuarioService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.security.service.UsuarioSenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")
public class UsuarioControllerAuth {

    @Autowired
    private IncluirUsuarioService incluirUsuarioService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private UsuarioSenhaService usuarioSenhaService;

    @PostMapping("/login")
    @ResponseStatus(OK)
    public UsuarioResponse login() {
        return usuarioAutenticadoService.getResponse();
    }

    @PostMapping("/registrar")
    @ResponseStatus(CREATED)
    public void incluir(@Valid @RequestBody IncluirUsuarioRequest request) {
        incluirUsuarioService.incluir(request);
    }

    @PostMapping("/esqueci-senha")
    @ResponseStatus(CREATED)
    public void esqueciSenha(@RequestBody @Valid EsqueciSenhaRequest request) {
        usuarioSenhaService.gerarToken(request);
    }

    @PutMapping("/recuperar-senha/{token}")
    @ResponseStatus(OK)
    public void trocarSenha(@Valid @RequestBody EditarSenhaEsquecidaRequest request, @PathVariable String token) {
        usuarioSenhaService.trocarSenha(request, token);
    }

}

