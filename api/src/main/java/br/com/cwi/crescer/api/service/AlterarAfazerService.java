package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.AfazerRequest;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import br.com.cwi.crescer.api.service.core.BuscarAfazerService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlterarAfazerService {

    @Autowired
    private AfazerRepository afazerRepository;

    @Autowired
    private BuscarAfazerService buscarAfazerService;

    @Autowired
    private ValidarProprietarioService validarProprietarioService;

    @Autowired
    private AlterarAfazerGoogleCalendarService alterarAfazerGoogleCalendarService;

    @Transactional
    public void alterar(Long afazerId, AfazerRequest request) {
        Afazer afazer = buscarAfazerService.porId(afazerId);

        validarProprietarioService.doAfazer(afazer);

        afazer.setNome(request.getNome());
        afazer.setDescricao(request.getDescricao());
        afazer.setDificuldade(request.getDificuldade());

        afazerRepository.save(afazer);
        alterarAfazerGoogleCalendarService.alterar(afazer);
    }
}
