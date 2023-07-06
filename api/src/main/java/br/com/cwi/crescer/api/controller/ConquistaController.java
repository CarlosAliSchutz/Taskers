package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.response.UsuarioConquistaResponse;
import br.com.cwi.crescer.api.service.ListarConquistasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/conquistas")
public class ConquistaController {

    @Autowired
    private ListarConquistasService listarConquistasService;

    @GetMapping
    @ResponseStatus(OK)
    public Page<UsuarioConquistaResponse> listar(Pageable pageable) {
        return listarConquistasService.listar(pageable);
    }

}
