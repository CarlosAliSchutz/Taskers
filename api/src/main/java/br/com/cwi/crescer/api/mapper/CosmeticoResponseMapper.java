package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.CosmeticoResponse;
import br.com.cwi.crescer.api.domain.Cosmetico;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CosmeticoResponseMapper {

    public static CosmeticoResponse toResponse(Cosmetico entity){
        return CosmeticoResponse
                .builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .tipo(entity.getTipo())
                .valor(entity.getValor())
                .build();
    }

}
