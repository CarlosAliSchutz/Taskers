package br.com.cwi.api.util;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.util.AtribuiExperienciaETaskcoinDaMissaoNoUsuario;
import br.com.cwi.crescer.api.util.GeraAfazerParaCalendarioGoogle;
import com.google.api.services.calendar.model.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AtribuiExperienciaETaskcoinDaMissaoNoUsuarioTest {

    @Test
    @DisplayName("Deve Atribuir Experiencia E Taskcoin Da Missao No Usuario")
    void deveAtribuirExperienciaETaskcoinDaMissaoNoUsuario() {
        int TASKCOINS_RECEBIDAS = 10;
        int EXPERIENCIA_RECEBIDA = 50;
        Usuario usuario = UsuarioFactory.getUsuario();

        Usuario usuarioAntigo = UsuarioFactory.getUsuario();
        AtribuiExperienciaETaskcoinDaMissaoNoUsuario.atribuir(usuario);

        assertEquals(usuarioAntigo.getTaskcoin(), usuario.getTaskcoin() - TASKCOINS_RECEBIDAS);
        assertEquals(usuarioAntigo.getExperiencia(), usuario.getExperiencia() - EXPERIENCIA_RECEBIDA);
    }
}
