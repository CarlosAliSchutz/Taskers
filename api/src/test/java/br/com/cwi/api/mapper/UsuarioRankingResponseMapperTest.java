package br.com.cwi.api.mapper;

import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.controller.response.UsuarioRankingResponse;
import br.com.cwi.crescer.api.mapper.UsuarioRankingResponseMapper;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UsuarioRankingResponseMapperTest {

    @Test
    @DisplayName("Deve retornar o response quando receber um usuario com experiencia")
    void deveRetornarOResponseQuandoReceberUmUsuarioComXp() {

        Usuario usuario = UsuarioFactory.getUsuarioComXP();
        Long posicaoUsuario = 3L;

        UsuarioRankingResponse response = UsuarioRankingResponseMapper.toResponse(usuario, posicaoUsuario);

        assertEquals(usuario.getNomeCompleto(), response.getNomeCompleto());
        assertEquals(usuario.getImagemPerfil(), response.getImagemPerfil());
        assertEquals(posicaoUsuario, response.getPosicaoRanking());
        assertEquals(usuario.getExperiencia(), response.getExperiencia());
    }

    @Test
    @DisplayName("Deve retornar o response quando receber um usuario sem experiencia")
    void deveRetornarOResponseQuandoReceberUmUsuarioSemXp() {

        Usuario usuario = UsuarioFactory.getUsuario();
        Long posicaoUsuario = 10L;

        UsuarioRankingResponse response = UsuarioRankingResponseMapper.toResponse(usuario, posicaoUsuario);

        assertEquals(usuario.getNomeCompleto(), response.getNomeCompleto());
        assertEquals(usuario.getImagemPerfil(), response.getImagemPerfil());
        assertEquals(posicaoUsuario, response.getPosicaoRanking());
        assertEquals(usuario.getExperiencia(), response.getExperiencia());
    }


    @Test
    @DisplayName("Deve lançar erro quando notificacao for nula")
    void deveLancarErroQuandoNotificacaoForNula() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            UsuarioRankingResponse response = UsuarioRankingResponseMapper.toResponse(null, null);
        });
    }
}
