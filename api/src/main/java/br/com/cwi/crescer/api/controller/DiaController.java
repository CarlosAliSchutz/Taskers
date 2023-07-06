package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.response.DiaResponse;
import br.com.cwi.crescer.api.service.ListarDiasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/dias")
public class DiaController {

    @Autowired
    private ListarDiasService listarDiasService;

    @GetMapping
    @ResponseStatus(OK)
    public Page<DiaResponse> listar(Pageable pageable) {
        return listarDiasService.listar(pageable);
    }
}
