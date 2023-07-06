package br.com.cwi.api.factories;

import br.com.cwi.crescer.api.domain.Dia;

import java.util.List;

public class DiaFactory {

    public static Dia getSegunda() {
        return Dia.builder()
                .id(1L)
                .nome("Segunda")
                .build();
    }

    public static Dia getTerca() {
        return Dia.builder()
                .id(2L)
                .nome("Terca")
                .build();
    }

    public static Dia getQuarta() {
        return Dia.builder()
                .id(3L)
                .nome("Quarta")
                .build();
    }

    public static Dia getQuinta() {
        return Dia.builder()
                .id(4L)
                .nome("Quinta")
                .build();
    }

    public static Dia getSexta() {
        return Dia.builder()
                .id(5L)
                .nome("Sexta")
                .build();
    }

    public static Dia getSabado() {
        return Dia.builder()
                .id(6L)
                .nome("Sabado")
                .build();
    }

    public static Dia getDomingo() {
        return Dia.builder()
                .id(7L)
                .nome("Domingo")
                .build();
    }

    public static List<Dia> getLista() {
        return List.of(
                getSegunda(), getDomingo()
        );
    }

    public static List<Dia> getListaCompleta() {
        return List.of(
                getSegunda(), getTerca(), getQuarta(), getQuinta(), getSexta(), getSabado(), getDomingo()
        );
    }


    public static List<Dia> getListaVazia() {
        return List.of();
    }


}
