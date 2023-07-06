package br.com.cwi.crescer.api.scheduled;

import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.repository.HabitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResetarHabitosService {

    @Autowired
    private HabitoRepository habitoRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void resetar(){
        List<Habito> habitos = habitoRepository.findAllByAtivoTrue();

        habitos.forEach(habito -> habito.setExecucoes(0));
        habitoRepository.saveAll(habitos);
    }

}