package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.request.DiariaRequest;
import br.com.cwi.crescer.api.controller.response.DiariaDetalhadaResponse;
import br.com.cwi.crescer.api.controller.response.DiariaResumidaResponse;
import br.com.cwi.crescer.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/diarias")
public class DiariaController {

    @Autowired
    private IncluirDiariaService incluirDiariaService;

    @Autowired
    private DetalharDiariaService detalharDiariaService;

    @Autowired
    private ListarDiariasService listarDiariasService;

    @Autowired
    private AlterarDiariaService alterarDiariaService;

    @Autowired
    private DeletarDiariaService deletarDiariaService;

    @Autowired
    private RealizarDiariaService realizarDiariaService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void incluir(@Valid @RequestBody DiariaRequest request) {
        incluirDiariaService.incluir(request);
    }

    @GetMapping
    @ResponseStatus(OK)
    public Page<DiariaResumidaResponse> listar(@RequestParam("text") String text, Pageable pageable) {

        return listarDiariasService.listar(text, pageable);
    }

    @GetMapping("/{diariaId}")
    @ResponseStatus(OK)
    public DiariaDetalhadaResponse detalhar(@PathVariable Long diariaId) {

        return detalharDiariaService.detalhar(diariaId);
    }

    @PutMapping("/{diariaId}")
    @ResponseStatus(OK)
    public void alterar(@PathVariable Long diariaId, @Valid @RequestBody DiariaRequest request) {
        alterarDiariaService.alterar(diariaId, request);
    }

    @DeleteMapping("/{diariaId}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@PathVariable Long diariaId) {
        deletarDiariaService.deletar(diariaId);
    }

    @PutMapping("/{diariaId}/realizar")
    @ResponseStatus(OK)
    public void realizar(@PathVariable Long diariaId) {
        realizarDiariaService.realizar(diariaId);
    }

}
