package br.com.cwi.api.factories;

import br.com.cwi.crescer.api.domain.Afazer;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;

import static br.com.cwi.crescer.api.domain.Dificuldade.FACIL;

public class CalendarGoogleApiFactory {


    public static Calendar getCalendar() {
        return new Calendar
                .Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance(),
                new Credential.Builder(BearerToken.authorizationHeaderAccessMethod())
                .build())
                .setApplicationName("Calendario Test")
                .build();
    }
}
