package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import br.com.cwi.crescer.api.service.core.BuscarAfazerService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletarAfazerService {

    @Autowired
    private AfazerRepository afazerRepository;

    @Autowired
    private BuscarAfazerService buscarAfazerService;

    @Autowired
    private ValidarProprietarioService validarProprietarioService;

    @Autowired
    private DeletarAfazerGoogleCalendarService deletarAfazerGoogleCalendarService;

    @Transactional
    public void deletar(Long afazerId) {
        Afazer afazer = buscarAfazerService.porId(afazerId);

        validarProprietarioService.doAfazer(afazer);

        afazer.setAtivo(false);

        afazerRepository.save(afazer);
        deletarAfazerGoogleCalendarService.deletar(afazer);

    }
}
