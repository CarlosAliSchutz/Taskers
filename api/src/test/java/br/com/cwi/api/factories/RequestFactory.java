package br.com.cwi.api.factories;

import br.com.cwi.crescer.api.controller.request.AfazerRequest;
import br.com.cwi.crescer.api.controller.request.DiariaRequest;
import br.com.cwi.crescer.api.controller.request.HabitoRequest;

import java.time.LocalTime;
import java.util.ArrayList;

import static br.com.cwi.crescer.api.domain.Dificuldade.*;

public class RequestFactory {

    public static HabitoRequest getHabitoFacilRequest() {
        HabitoRequest request = new HabitoRequest();
        request.setNome("Habito");
        request.setDescricao("Habito Teste");
        request.setDificuldade(FACIL);
        return request;
    }

    public static HabitoRequest getHabitoMedioRequest() {
        HabitoRequest request = new HabitoRequest();
        request.setNome("Habito");
        request.setDescricao("Habito Teste");
        request.setDificuldade(MEDIO);
        return request;
    }

    public static HabitoRequest getHabitoDificilRequest() {
        HabitoRequest request = new HabitoRequest();
        request.setNome("Habito");
        request.setDescricao("Habito Teste");
        request.setDificuldade(DIFICIL);
        return request;
    }

    public static AfazerRequest getAfazerFacilRequest() {
        AfazerRequest request = new AfazerRequest();
        request.setNome("Afazer");
        request.setDescricao("Afazer Teste");
        request.setDificuldade(FACIL);
        return request;
    }

    public static AfazerRequest getAfazerMedioRequest() {
        AfazerRequest request = new AfazerRequest();
        request.setNome("Afazer");
        request.setDescricao("Afazer Teste");
        request.setDificuldade(MEDIO);
        return request;
    }

    public static AfazerRequest getAfazerDificilRequest() {
        AfazerRequest request = new AfazerRequest();
        request.setNome("Afazer");
        request.setDescricao("Afazer Teste");
        request.setDificuldade(DIFICIL);
        return request;
    }



    public static DiariaRequest getDiariaFacilRequest() {
        DiariaRequest request = new DiariaRequest();
        request.setNome("Diaria");
        request.setDescricao("Diaria Teste");
        request.setDificuldade(FACIL);
        request.setHora(LocalTime.now());
        request.setDias(new ArrayList<>());
        return request;
    }

    public static DiariaRequest getDiariaMedioRequest() {
        DiariaRequest request = new DiariaRequest();
        request.setNome("Diaria");
        request.setDescricao("Diaria Teste");
        request.setDificuldade(MEDIO);
        request.setHora(LocalTime.now());
        request.setDias(new ArrayList<>());
        return request;
    }

    public static DiariaRequest getDiariaDificilRequest() {
        DiariaRequest request = new DiariaRequest();
        request.setNome("Diaria");
        request.setDescricao("Diaria Teste");
        request.setDificuldade(DIFICIL);
        request.setHora(LocalTime.now());
        request.setDias(new ArrayList<>());
        return request;
    }

}
