package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.request.HabitoRequest;
import br.com.cwi.crescer.api.controller.response.HabitoDetalhadoResponse;
import br.com.cwi.crescer.api.controller.response.HabitoResumidoResponse;
import br.com.cwi.crescer.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/habitos")
public class HabitoController {

    @Autowired
    private ListarHabitosService listarHabitosService;

    @Autowired
    private IncluirHabitoService incluirHabitoService;

    @Autowired
    private DeletarHabitoService deletarHabitoService;

    @Autowired
    private AlterarHabitoService alterarHabitoService;

    @Autowired
    private AumentarHabitoService aumentarHabitoService;

    @Autowired
    private DetalharHabitoService detalharHabitoService;

    @GetMapping
    @ResponseStatus(OK)
    public Page<HabitoResumidoResponse> listar(@RequestParam("text") String text, Pageable pageable) {
        return listarHabitosService.listar(text, pageable);
    }

    @GetMapping("/{habitoId}")
    @ResponseStatus(OK)
    public HabitoDetalhadoResponse detalhar(@PathVariable Long habitoId) {
        return detalharHabitoService.detalhar(habitoId);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void incluir(@Valid @RequestBody HabitoRequest request) {
        incluirHabitoService.incluir(request);
    }

    @DeleteMapping("/{habitoId}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@PathVariable Long habitoId) {
        deletarHabitoService.deletar(habitoId);
    }

    @PutMapping("/{habitoId}")
    @ResponseStatus(OK)
    public void alterar(@PathVariable Long habitoId,
                                           @Valid @RequestBody HabitoRequest request) {
        alterarHabitoService.alterar(habitoId, request);
    }

    @PutMapping("/{habitoId}/aumentar")
    @ResponseStatus(OK)
    public void aumentar(@PathVariable Long habitoId) {
        aumentarHabitoService.aumentar(habitoId);
    }

}
