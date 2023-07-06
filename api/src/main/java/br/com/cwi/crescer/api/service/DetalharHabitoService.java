package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.HabitoDetalhadoResponse;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.mapper.HabitoDetalhadoResponseMapper;
import br.com.cwi.crescer.api.service.core.BuscarHabitoService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalharHabitoService {

    @Autowired
    private BuscarHabitoService buscarHabitoService;

    @Autowired
    private ValidarProprietarioService validarProprietarioService;

    public HabitoDetalhadoResponse detalhar(Long id) {
        Habito habito = buscarHabitoService.porId(id);
        validarProprietarioService.doHabito(habito);

        return HabitoDetalhadoResponseMapper.toResponse(habito);
    }
}
