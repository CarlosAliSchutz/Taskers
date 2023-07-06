package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.DiariaDetalhadaResponse;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.mapper.DiariaDetalhadaResponseMapper;
import br.com.cwi.crescer.api.service.core.BuscarDiariaService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DetalharDiariaService {

    @Autowired
    private BuscarDiariaService buscarDiariaService;

    @Autowired
    private ValidarProprietarioService validarProprietarioService;

    @Transactional
    public DiariaDetalhadaResponse detalhar(Long id) {
        Diaria diaria = buscarDiariaService.porId(id);
        validarProprietarioService.daDiaria(diaria);

        return DiariaDetalhadaResponseMapper.toResponse(diaria);
    }
}
