package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.repository.HabitoRepository;
import br.com.cwi.crescer.api.service.core.BuscarHabitoService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletarHabitoService {

    @Autowired
    private HabitoRepository habitoRepository;

    @Autowired
    private BuscarHabitoService buscarHabitoService;

    @Autowired
    private ValidarProprietarioService validarProprietarioService;

    @Transactional
    public void deletar(Long habitoId) {
        Habito habito = buscarHabitoService.porId(habitoId);

        validarProprietarioService.doHabito(habito);

        habito.setAtivo(false);

        habitoRepository.save(habito);

    }
}
