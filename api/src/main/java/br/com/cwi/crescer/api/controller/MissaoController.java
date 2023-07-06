package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.response.MissaoResponse;
import br.com.cwi.crescer.api.service.ListarMissoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/missoes")
public class MissaoController {

    @Autowired
    private ListarMissoesService listarMissoesService;

    @GetMapping
    @ResponseStatus(OK)
    public List<MissaoResponse> listarMissoes() {
        return listarMissoesService.listar();
    }
}
