package br.com.cwi.crescer.api.security.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SenhaToken {

    private String email;
    private Long criadoEm;
}
