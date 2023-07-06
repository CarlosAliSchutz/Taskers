package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.util.GeraDiariaParaCalendarioGoogle;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static java.util.Objects.nonNull;

@Service
public class AlterarDiariaGoogleCalendarService {

    private static final String CALENDAR_ID = "primary";
    @Autowired
    private GerarCalendarioGoogleApiService gerarCalendarioGoogleApi;

    @Transactional
    public void alterar(Diaria diaria) {

        if(nonNull(diaria.getEventoGoogleCalendarId())){

            Calendar service = gerarCalendarioGoogleApi.gerar();
            Event tarefaNoGoogleCalendar = GeraDiariaParaCalendarioGoogle.gerar(diaria);

            try {
                service.events()
                        .update(CALENDAR_ID, diaria.getEventoGoogleCalendarId(), tarefaNoGoogleCalendar)
                        .execute();
            } catch (IOException ignored) {
            }
        }
    }
}
