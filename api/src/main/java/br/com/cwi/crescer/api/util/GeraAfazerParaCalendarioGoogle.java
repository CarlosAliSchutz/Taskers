package br.com.cwi.crescer.api.util;

import br.com.cwi.crescer.api.domain.Afazer;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZoneId;

@UtilityClass
public class GeraAfazerParaCalendarioGoogle {

    public static Event gerar(Afazer afazer) {
        ZoneId zonaHoraria = ZoneId.systemDefault();
        LocalDateTime horaAtual = LocalDateTime.now();
        DateTime horaDeInicio = new DateTime(horaAtual.atZone(zonaHoraria).toInstant().toString());
        DateTime horaDoFinal = new DateTime(horaAtual.atZone(zonaHoraria).toInstant().toString());

        Event tarefaNoGoogleCalendar = new Event()
                .setSummary(afazer.getNome())
                .setDescription(afazer.getDescricao());

        EventDateTime inicioDaTarefa = new EventDateTime()
                .setDateTime(horaDeInicio)
                .setTimeZone(zonaHoraria.toString());

        EventDateTime fimDaTarefa = new EventDateTime()
                .setDateTime(horaDoFinal)
                .setTimeZone(zonaHoraria.toString());

        tarefaNoGoogleCalendar.setStart(inicioDaTarefa);
        tarefaNoGoogleCalendar.setEnd(fimDaTarefa);

        return tarefaNoGoogleCalendar;
    }
}
