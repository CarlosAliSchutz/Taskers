package br.com.cwi.crescer.api.service.core;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class NowService {

    public LocalTime horaAtual() {
        return LocalTime.now();
    }

    public Long numeroDiaDaSemana(){
        return (long) LocalDate.now().getDayOfWeek().getValue();
    }

}
