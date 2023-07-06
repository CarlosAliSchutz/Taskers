package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import com.google.api.services.calendar.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static java.util.Objects.nonNull;

@Service
public class DeletarAfazerGoogleCalendarService {

    private static final String CALENDAR_ID = "primary";

    @Autowired
    private AfazerRepository afazerRepository;

    @Autowired
    private GerarCalendarioGoogleApiService gerarCalendarioGoogleApi;

    @Transactional
    public void deletar(Afazer afazer) {

        if(nonNull(afazer.getEventoGoogleCalendarId())) {

            Calendar service = gerarCalendarioGoogleApi.gerar();

            try {
                service.events()
                        .delete(CALENDAR_ID, afazer.getEventoGoogleCalendarId())
                        .execute();
            } catch (IOException ignored) {
            }

            afazer.setEventoGoogleCalendarId(null);
            afazerRepository.save(afazer);
        }
    }
}
