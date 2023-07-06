package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.request.AfazerRequest;
import br.com.cwi.crescer.api.controller.response.AfazerDetalhadoResponse;
import br.com.cwi.crescer.api.controller.response.AfazerResumidoResponse;
import br.com.cwi.crescer.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/afazeres")
public class AfazerController {

    @Autowired
    private IncluirAfazerService incluirAfazerService;

    @Autowired
    private ListarAfazeresService listarAfazeresService;

    @Autowired
    private AlterarAfazerService alterarAfazerService;

    @Autowired
    private DeletarAfazerService deletarAfazerService;

    @Autowired
    private FinalizarAfazerService finalizarAfazerService;

    @Autowired
    private DetalharAfazerService detalharAfazerService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void incluir(@Valid @RequestBody AfazerRequest request) {
         incluirAfazerService.incluir(request);
    }

    @GetMapping("/{afazerId}")
    @ResponseStatus(OK)
    public AfazerDetalhadoResponse detalhar(@PathVariable Long afazerId) {
        return detalharAfazerService.detalhar(afazerId);
    }

    @GetMapping
    @ResponseStatus(OK)
    public Page<AfazerResumidoResponse> listar(@RequestParam("text") String text, Pageable pageable) {
        return listarAfazeresService.listar(text, pageable);
    }

    @PutMapping("/{afazerId}")
    @ResponseStatus(OK)
    public void alterar(@PathVariable Long afazerId, @Valid @RequestBody AfazerRequest request) {
        alterarAfazerService.alterar(afazerId, request);
    }

    @DeleteMapping("/{afazerId}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@PathVariable Long afazerId) {
        deletarAfazerService.deletar(afazerId);
    }

    @PutMapping("/{afazerId}/finalizar")
    @ResponseStatus(OK)
    public void finalizar(@PathVariable Long afazerId) {
        finalizarAfazerService.finalizar(afazerId);
    }
}
