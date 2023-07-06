package br.com.cwi.api.factories;

import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.domain.TipoCosmetico;

import java.util.List;

public class CosmeticoFactory {

    public static Cosmetico getCosmeticoCenario() {
        return Cosmetico.builder()
                .id(SimpleFactory.getRandomLong())
                .nome("Teste")
                .tipo(TipoCosmetico.CENARIO)
                .valor(35)
                .build();
    }

    public static Cosmetico getCosmeticoRoupa() {
        return Cosmetico.builder()
                .id(SimpleFactory.getRandomLong())
                .nome("Teste")
                .tipo(TipoCosmetico.ROUPA)
                .valor(100)
                .build();
    }

    public static List<Cosmetico> getLista() {
        return List.of(
                getCosmeticoCenario(), getCosmeticoCenario(), getCosmeticoRoupa()
        );
    }

    public static List<Cosmetico> getListaVazia() {
        return List.of();
    }



}
