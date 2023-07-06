package br.com.cwi.crescer.api.service.core;

import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.repository.HabitoRepository;
import br.com.cwi.crescer.api.validator.FoiRemovidoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class BuscarHabitoService {

    @Autowired
    private HabitoRepository habitoRepository;

    @Autowired
    private FoiRemovidoValidator foiRemovidoValidator;

    public Habito porId(Long habitoId) {
        Habito habito = habitoRepository.findById(habitoId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Hábito não encontrado"));

        foiRemovidoValidator.validar(habito.isAtivo());

        return habito;
    }

}
