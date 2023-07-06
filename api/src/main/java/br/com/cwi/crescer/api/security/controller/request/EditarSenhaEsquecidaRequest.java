package br.com.cwi.crescer.api.security.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EditarSenhaEsquecidaRequest {

    @NotBlank
    private String senha;

}
