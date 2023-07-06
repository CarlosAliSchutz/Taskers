package br.com.cwi.crescer.api.domain;

import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
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
public class Diaria {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private boolean finalizado;
    @Enumerated(STRING)
    private Dificuldade dificuldade;
    private LocalTime hora;
    private boolean ativo;
    private String eventoGoogleCalendarId;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario proprietario;

    @ManyToMany
    @JoinTable(
            name = "diaria_dia",
            joinColumns = @JoinColumn(name = "diaria_id"),
            inverseJoinColumns = @JoinColumn(name = "dia_id")
    )
    private List<Dia> dias = new ArrayList<>();
}

