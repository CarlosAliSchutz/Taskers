package br.com.cwi.crescer.api.security.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EsqueciSenhaRequest {

    @Email(message = "E-mail precisa ser válido.")
    @NotBlank
    private String email;
}
