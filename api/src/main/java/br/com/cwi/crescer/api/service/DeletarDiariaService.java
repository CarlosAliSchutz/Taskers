package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.service.core.BuscarDiariaService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletarDiariaService {

    @Autowired
    private DiariaRepository diariaRepository;

    @Autowired
    private BuscarDiariaService buscarDiariaService;

    @Autowired
    private ValidarProprietarioService validarProprietarioService;

    @Autowired
    private DeletarDiariaGoogleCalendarService deletarDiariaGoogleCalendarService;

    @Transactional
    public void deletar(Long diariaId) {
        Diaria diaria = buscarDiariaService.porId(diariaId);

        validarProprietarioService.daDiaria(diaria);

        diaria.setAtivo(false);

        diariaRepository.save(diaria);
        deletarDiariaGoogleCalendarService.deletar(diaria);
    }
}
