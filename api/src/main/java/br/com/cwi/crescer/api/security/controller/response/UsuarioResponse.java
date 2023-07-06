package br.com.cwi.crescer.api.security.controller.response;

import br.com.cwi.crescer.api.controller.response.CosmeticoResponse;
import br.com.cwi.crescer.api.security.domain.Provider;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsuarioResponse {

    private Long id;
    private String nomeCompleto;
    private String imagemPerfil;
    private String email;
    private Integer experiencia;
    private Integer taskcoin;
    private boolean notificacoesPorEmail;
    private Provider provedor;
    private List<CosmeticoResponse> cosmeticosEquipados;

}


