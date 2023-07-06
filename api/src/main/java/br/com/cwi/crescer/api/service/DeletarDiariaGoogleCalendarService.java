package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import com.google.api.services.calendar.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static java.util.Objects.nonNull;

@Service
public class  DeletarDiariaGoogleCalendarService {

    private static final String CALENDAR_ID = "primary";
    @Autowired
    private DiariaRepository diariaRepository;
    @Autowired
    private GerarCalendarioGoogleApiService gerarCalendarioGoogleApi;

    @Transactional
    public void deletar(Diaria diaria) {

        if(nonNull(diaria.getEventoGoogleCalendarId())) {

            Calendar service = gerarCalendarioGoogleApi.gerar();

            try {
                service.events()
                        .delete(CALENDAR_ID, diaria.getEventoGoogleCalendarId())
                        .execute();
            } catch (IOException ignored) {
            }

            diaria.setEventoGoogleCalendarId(null);
            diariaRepository.save(diaria);
        }
    }
}
