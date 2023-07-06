package br.com.cwi.crescer.api.service.core;

import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.repository.CosmeticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class BuscarCosmeticoService {

    @Autowired
    private CosmeticoRepository cosmeticoRepository;

    public Cosmetico porId(Long id) {

        return cosmeticoRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cosmético não encontrado"));
    }

    public Cosmetico porNome(String nome) {

        return cosmeticoRepository.findByNome(nome)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cosmético não encontrado"));
    }

}
