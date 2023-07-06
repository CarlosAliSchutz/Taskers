package br.com.cwi.api.factories;

import br.com.cwi.crescer.api.security.domain.Provider;
import br.com.cwi.crescer.api.security.domain.Usuario;

import java.util.List;

public class UsuarioFactory {

    public static Usuario getUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(SimpleFactory.getRandomLong());
        usuario.setNomeCompleto("Usuário de teste");
        usuario.setEmail("teste@cwi.com.br");
        usuario.setSenha("123");
        usuario.setExperiencia(0);
        usuario.setTaskcoin(100);
        usuario.setProvider(Provider.GOOGLE);
        usuario.setNotificacoesEmail(true);
        usuario.setImagemPerfil("url.com");
        usuario.setAtivo(true);
        return usuario;
    }
    public static Usuario getUsuarioComXP() {
        Usuario usuario = new Usuario();
        usuario.setId(SimpleFactory.getRandomLong());
        usuario.setNomeCompleto("Usuário de teste");
        usuario.setEmail("teste@cwi.com.br");
        usuario.setSenha("123");
        usuario.setExperiencia(5);
        usuario.setTaskcoin(0);
        usuario.setProvider(Provider.LOCAL);
        usuario.setNotificacoesEmail(false);
        usuario.setImagemPerfil("url.com");
        usuario.setAtivo(true);
        return usuario;
    }


    public static List<Usuario> getLista() {
        return List.of(
                getUsuario(), getUsuario(), getUsuario()
        );
    }

    public static List<Usuario> getListaVazia() {
        return List.of();
    }

}
