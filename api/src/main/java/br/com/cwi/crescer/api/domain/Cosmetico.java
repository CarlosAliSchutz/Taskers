package br.com.cwi.crescer.api.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class Cosmetico {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nome;
    @Enumerated(STRING)
    private TipoCosmetico tipo;
    private Integer valor;

    @OneToMany(mappedBy = "cosmetico")
    private List<CosmeticoUsuario> cosmeticoUsuarios = new ArrayList<>();
}

