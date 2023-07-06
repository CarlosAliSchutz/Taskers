package br.com.cwi.crescer.api.service.core;

import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import br.com.cwi.crescer.api.validator.FoiRemovidoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class BuscarAfazerService {

    @Autowired
    private AfazerRepository afazerRepository;

    @Autowired
    private FoiRemovidoValidator foiRemovidoValidator;

    public Afazer porId(Long afazerId) {
        Afazer afazer = afazerRepository.findById(afazerId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Afazer não encontrado"));

        foiRemovidoValidator.validar(afazer.isAtivo());

        return afazer;
    }

}
