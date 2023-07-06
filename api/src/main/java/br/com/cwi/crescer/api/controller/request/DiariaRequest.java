package br.com.cwi.crescer.api.controller.request;

import br.com.cwi.crescer.api.domain.Dificuldade;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.List;

@Getter @Setter
public class DiariaRequest {

    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotBlank
    @Size(max = 255)
    private String descricao;

    @NotNull
    private Dificuldade dificuldade;

    @NotNull
    private LocalTime hora;

    @NotNull
    @Size(max = 7)
    private List<Long> dias;

}
