package br.com.cwi.crescer.api.util;

import br.com.cwi.crescer.api.domain.Diaria;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class GeraDiariaParaCalendarioGoogle {

    public static Event gerar(Diaria diaria) {

        ZoneId zonaHoraria = ZoneId.systemDefault();
        LocalDate dataAtual = LocalDate.now();
        LocalTime horaDaDiaria = diaria.getHora();
        LocalDateTime dataEHoraDaDiaria = dataAtual.atTime(horaDaDiaria);
        DateTime horaDeInicio = new DateTime(dataEHoraDaDiaria.atZone(zonaHoraria).toInstant().toString());
        DateTime horaDoFinal = new DateTime(dataEHoraDaDiaria.atZone(zonaHoraria).toInstant().toString());

        List<String> diasDaDiariaAbreviados = new ArrayList<>();
        diaria.getDias().forEach(dia -> diasDaDiariaAbreviados.add(transformarNumeroEmDiaDaSemanaPadraoGoogle(dia.getId())));

        String todosOsDiasDaDiaria = String.join(",", diasDaDiariaAbreviados);

        Event tarefaNoGoogleCalendar = new Event()
                .setSummary(diaria.getNome())
                .setDescription(diaria.getDescricao());

        EventDateTime inicioDaTarefa = new EventDateTime()
                .setDateTime(horaDeInicio)
                .setTimeZone(zonaHoraria.toString());

        EventDateTime fimDaTarefa = new EventDateTime()
                .setDateTime(horaDoFinal)
                .setTimeZone(zonaHoraria.toString());

        String[] recorrenciaDaTarefa = new String[]{"RRULE:FREQ=WEEKLY;BYDAY="+ todosOsDiasDaDiaria +";"};

        tarefaNoGoogleCalendar.setStart(inicioDaTarefa);
        tarefaNoGoogleCalendar.setEnd(fimDaTarefa);
        tarefaNoGoogleCalendar.setRecurrence(Arrays.asList(recorrenciaDaTarefa));

        return tarefaNoGoogleCalendar;
    }

    private static String transformarNumeroEmDiaDaSemanaPadraoGoogle(Long id){
        String diaAbreviado = null;

        if (id == 1L) {
            diaAbreviado = "MO";
        } else if (id == 2L) {
            diaAbreviado = "TU";
        } else if (id == 3L) {
            diaAbreviado = "WE";
        } else if (id == 4L) {
            diaAbreviado = "TH";
        } else if (id == 5L) {
            diaAbreviado = "FR";
        } else if (id == 6L) {
            diaAbreviado = "SA";
        } else if (id == 7L) {
            diaAbreviado = "SU";
        }

        return diaAbreviado;
    }
}
