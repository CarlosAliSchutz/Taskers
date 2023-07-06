package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.HabitoRequest;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.repository.HabitoRepository;
import br.com.cwi.crescer.api.service.core.BuscarHabitoService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlterarHabitoService {

    @Autowired
    private HabitoRepository habitoRepository;

    @Autowired
    private BuscarHabitoService buscarHabitoService;

    @Autowired
    private ValidarProprietarioService validarProprietarioService;

    @Transactional
    public void alterar(Long habitoId, HabitoRequest request) {
        Habito habito = buscarHabitoService.porId(habitoId);

        validarProprietarioService.doHabito(habito);

        habito.setNome(request.getNome());
        habito.setDescricao(request.getDescricao());
        habito.setDificuldade(request.getDificuldade());

        habitoRepository.save(habito);
    }
}
