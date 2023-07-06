package br.com.cwi.crescer.api.domain;

import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Afazer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private boolean finalizado;
    @Enumerated(STRING)
    private Dificuldade dificuldade;
    private boolean ativo;

    private String eventoGoogleCalendarId;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario proprietario;
}
