package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.DiariaRequest;
import br.com.cwi.crescer.api.domain.Dia;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.service.core.BuscarDiariaService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlterarDiariaService {

    @Autowired
    private DiariaRepository diariaRepository;

    @Autowired
    private BuscarDiariaService buscarDiariaService;

    @Autowired
    private ValidarProprietarioService validarProprietarioService;

    @Autowired
    private AlterarDiariaGoogleCalendarService alterarDiariaGoogleCalendarService;

    @Transactional
    public void alterar(Long diariaId, DiariaRequest request) {
        Diaria diaria = buscarDiariaService.porId(diariaId);

        validarProprietarioService.daDiaria(diaria);

        diaria.setNome(request.getNome());
        diaria.setDescricao(request.getDescricao());
        diaria.setDificuldade(request.getDificuldade());
        diaria.setHora(request.getHora());
        diaria.setDias(buildDiasDiaria(request.getDias()));

        diariaRepository.save(diaria);
        alterarDiariaGoogleCalendarService.alterar(diaria);
    }

    public static List<Dia> buildDiasDiaria(List<Long> diasId) {
        return diasId
                .stream()
                .map(id -> Dia.builder().id(id).build())
                .collect(Collectors.toList());
    }
}
