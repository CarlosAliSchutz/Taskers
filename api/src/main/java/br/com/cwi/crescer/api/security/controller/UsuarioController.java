package br.com.cwi.crescer.api.security.controller;

import br.com.cwi.crescer.api.security.controller.request.EditarSenhaRequest;
import br.com.cwi.crescer.api.security.controller.request.EditarUsuarioRequest;
import br.com.cwi.crescer.api.security.controller.response.UsuarioResponse;
import br.com.cwi.crescer.api.security.service.DetalharUsuarioLogadoService;
import br.com.cwi.crescer.api.security.service.EditarSenhaUsuarioService;
import br.com.cwi.crescer.api.security.service.EditarUsuarioService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private EditarUsuarioService editarUsuarioService;

    @Autowired
    private EditarSenhaUsuarioService editarSenhaUsuarioService;

    @Autowired
    private DetalharUsuarioLogadoService detalharUsuarioLogadoService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @PutMapping
    @ResponseStatus(OK)
    public void editar(@Valid @RequestBody EditarUsuarioRequest request) {
        editarUsuarioService.editar(request);
    }

    @PutMapping("/editar-senha")
    @ResponseStatus(OK)
    public void editarSenha(@Valid @RequestBody EditarSenhaRequest request) {
        editarSenhaUsuarioService.editar(request);
    }

    @GetMapping("/me")
    @ResponseStatus(OK)
    public UsuarioResponse detalharUsuarioLogado() {
        return detalharUsuarioLogadoService.detalhar();
    }

    @GetMapping("/validar")
    @ResponseStatus(OK)
    public UsuarioResponse validarUsuarioLogado() {
        return usuarioAutenticadoService.getResponse();
    }

}

