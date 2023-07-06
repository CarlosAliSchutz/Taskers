package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.response.UsuarioRankingResponse;
import br.com.cwi.crescer.api.service.ListarRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    @Autowired
    private ListarRankingService listarRankingService;

    @GetMapping
    @ResponseStatus(OK)
    public Page<UsuarioRankingResponse> listar(@RequestParam(defaultValue = "") String nome, Pageable pageable) {
        return listarRankingService.listar(nome, pageable);
    }

}
