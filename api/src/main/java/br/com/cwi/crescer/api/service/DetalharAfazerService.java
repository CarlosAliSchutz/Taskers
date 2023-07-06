package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.AfazerDetalhadoResponse;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.mapper.AfazerDetalhadoResponseMapper;
import br.com.cwi.crescer.api.service.core.BuscarAfazerService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalharAfazerService {

    @Autowired
    private BuscarAfazerService buscarAfazerService;

    @Autowired
    private ValidarProprietarioService validarProprietarioService;

    public AfazerDetalhadoResponse detalhar(Long id) {
        Afazer afazer = buscarAfazerService.porId(id);
        validarProprietarioService.doAfazer(afazer);

        return AfazerDetalhadoResponseMapper.toResponse(afazer);
    }
}
