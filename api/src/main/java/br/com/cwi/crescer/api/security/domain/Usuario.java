package br.com.cwi.crescer.api.security.domain;

import br.com.cwi.crescer.api.domain.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Getter @Setter @EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String nomeCompleto;
    @Column(unique = true)
    private String email;
    private String imagemPerfil;
    private String senha;
    private Integer experiencia;
    private Integer taskcoin;
    private boolean ativo;
    private boolean notificacoesEmail;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Permissao> permissoes = new ArrayList<>();

    public void adicionarPermissao(Permissao permissao) {
        this.permissoes.add(permissao);
        permissao.setUsuario(this);
    }

    @OneToMany(mappedBy = "usuario")
    private List<CosmeticoUsuario> usuarioCosmeticos = new ArrayList<>();
    @OneToMany(mappedBy = "usuario")
    private List<UsuarioConquista> usuarioConquistas = new ArrayList<>();


    @OneToOne(mappedBy = "proprietario")
    private MissaoHabito missaoHabito;
    @OneToOne(mappedBy = "proprietario")
    private MissaoDiaria missaodiaria;
    @OneToOne(mappedBy = "proprietario")
    private MissaoAfazer missaoAfazer;

}
