package br.com.cwi.crescer.api.domain;

import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class MissaoDiaria {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private boolean concluida;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario proprietario;

    @OneToOne
    @JoinColumn(name = "diaria_id")
    private Diaria diaria;

}
