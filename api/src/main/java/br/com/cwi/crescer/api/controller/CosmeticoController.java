package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.response.CosmeticoResponse;
import br.com.cwi.crescer.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/cosmeticos")
public class CosmeticoController {

    @Autowired
    private ListarCosmeticosDisponiveisService listarCosmeticosDisponiveisService;

    @Autowired
    private ListarCosmeticosAdquiridosService listarCosmeticosAdquiridosService;

    @Autowired
    private ListarCosmeticosEquipadosService listarCosmeticosEquipadosService;

    @Autowired
    private ComprarCosmeticoService comprarCosmeticoService;

    @Autowired
    private EquiparCosmeticoService equiparCosmeticoService;

    @GetMapping("/disponiveis")
    @ResponseStatus(OK)
    public Page<CosmeticoResponse> listarDisponiveis(Pageable pageable) {
        return listarCosmeticosDisponiveisService.listar(pageable);
    }

    @GetMapping("/adquiridos")
    @ResponseStatus(OK)
    public Page<CosmeticoResponse> listarAdquiridos(Pageable pageable) {
        return listarCosmeticosAdquiridosService.listar(pageable);
    }

    @GetMapping("/equipados")
    @ResponseStatus(OK)
    public List<CosmeticoResponse> listarEquipados() {
        return listarCosmeticosEquipadosService.listar();
    }

    @PostMapping("/{id}/comprar")
    @ResponseStatus(CREATED)
    public void comprar(@PathVariable Long id) {
        comprarCosmeticoService.comprar(id);
    }

    @PutMapping("/{id}/equipar")
    @ResponseStatus(OK)
    public void equipar(@PathVariable Long id) {
        equiparCosmeticoService.equipar(id);
    }

}
