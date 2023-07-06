package br.com.cwi.crescer.api.scheduled;

import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.service.core.NowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResetarDiariasService {

    @Autowired
    private DiariaRepository diariaRepository;

    @Autowired
    private NowService nowService;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void resetar(){
        Long diaDaSemanaEmNumero = nowService.numeroDiaDaSemana();

        List<Diaria> diarias = diariaRepository.findByAtivoTrueAndDiasId(diaDaSemanaEmNumero);

        diarias.forEach(diaria -> diaria.setFinalizado(false));
        diariaRepository.saveAll(diarias);
    }
}