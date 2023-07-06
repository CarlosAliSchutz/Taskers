package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.util.GeraAfazerParaCalendarioGoogle;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static java.util.Objects.nonNull;

@Service
public class AlterarAfazerGoogleCalendarService {

    private static final String CALENDAR_ID = "primary";

    @Autowired
    private GerarCalendarioGoogleApiService gerarCalendarioGoogleApi;

    @Transactional
    public void alterar(Afazer afazer) {

        if (nonNull(afazer.getEventoGoogleCalendarId())) {

            Calendar service = gerarCalendarioGoogleApi.gerar();
            Event tarefaNoGoogleCalendar = GeraAfazerParaCalendarioGoogle.gerar(afazer);

            try {
                service.events()
                        .update(CALENDAR_ID, afazer.getEventoGoogleCalendarId(), tarefaNoGoogleCalendar)
                        .execute();
            } catch (IOException ignored) {
            }
        }
    }
}

