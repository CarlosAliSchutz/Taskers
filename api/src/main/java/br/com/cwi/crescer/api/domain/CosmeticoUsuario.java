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
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class CosmeticoUsuario {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private boolean equipado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cosmetico_id")
    private Cosmetico cosmetico;
}
