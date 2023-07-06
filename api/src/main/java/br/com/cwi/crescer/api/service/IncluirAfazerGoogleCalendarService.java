package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import br.com.cwi.crescer.api.security.domain.Provider;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.util.GeraAfazerParaCalendarioGoogle;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@Service
public class IncluirAfazerGoogleCalendarService {

    private static final String CALENDAR_ID = "primary";

    @Autowired
    private AfazerRepository afazerRepository;
    @Autowired
    private GerarCalendarioGoogleApiService gerarCalendarioGoogleApi;


    @Transactional
    public void incluir(Usuario usuario, Afazer afazer) {

        if(usuario.getProvider().equals(Provider.GOOGLE)) {
            Calendar service = gerarCalendarioGoogleApi.gerar();
            Event tarefaNoGoogleCalendar = GeraAfazerParaCalendarioGoogle.gerar(afazer);

            try {
                tarefaNoGoogleCalendar = service.events().
                        insert(CALENDAR_ID, tarefaNoGoogleCalendar).
                        execute();

                afazer.setEventoGoogleCalendarId(tarefaNoGoogleCalendar.getId());
                afazerRepository.save(afazer);
            } catch (IOException ignored) {
            }
        }
    }
}
