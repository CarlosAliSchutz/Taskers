package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.security.domain.Provider;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.util.GeraDiariaParaCalendarioGoogle;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class IncluirDiariaGoogleCalendarService {

    @Autowired
    private DiariaRepository diariaRepository;
    @Autowired
    private GerarCalendarioGoogleApiService gerarCalendarioGoogleApi;

    private static final String CALENDAR_ID = "primary";

    @Transactional
    public void incluir(Usuario usuario, Diaria diaria) {

        if(usuario.getProvider().equals(Provider.GOOGLE)) {
            Calendar service = gerarCalendarioGoogleApi.gerar();
            Event tarefaNoGoogleCalendar = GeraDiariaParaCalendarioGoogle.gerar(diaria);

            try {
                tarefaNoGoogleCalendar = service.events().
                        insert(CALENDAR_ID, tarefaNoGoogleCalendar).
                        execute();

                diaria.setEventoGoogleCalendarId(tarefaNoGoogleCalendar.getId());
                diariaRepository.save(diaria);
            } catch (IOException ignored) {
            }
        }
    }
}
