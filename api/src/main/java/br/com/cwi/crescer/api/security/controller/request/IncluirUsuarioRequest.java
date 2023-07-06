package br.com.cwi.crescer.api.security.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class IncluirUsuarioRequest {

    @NotBlank
    @Size(max = 255)
    private String nomeCompleto;
    @NotBlank
    @Email
    @Size(max = 255)
    private String email;
    @Size(max = 512)
    private String imagemPerfil;
    @NotBlank
    @Size(max = 128)
    private String senha;

}

