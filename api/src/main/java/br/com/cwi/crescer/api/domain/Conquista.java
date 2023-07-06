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
public class Conquista {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private Integer objetivo;
    @Enumerated(STRING)
    private TipoConquista tipo;
    @Enumerated(STRING)
    private Dificuldade dificuldade;

    @OneToMany(mappedBy = "conquista")
    private List<UsuarioConquista> usuarioConquistas = new ArrayList<>();


}
