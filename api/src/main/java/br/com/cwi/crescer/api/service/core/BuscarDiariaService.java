package br.com.cwi.crescer.api.service.core;

import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.validator.FoiRemovidoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class BuscarDiariaService {

    @Autowired
    private DiariaRepository diariaRepository;

    @Autowired
    private FoiRemovidoValidator foiRemovidoValidator;

    public Diaria porId(Long diariaId) {
        Diaria diaria = diariaRepository.findById(diariaId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Diária não encontrada"));

        foiRemovidoValidator.validar(diaria.isAtivo());

        return diaria;
    }

}
