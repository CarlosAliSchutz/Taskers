package br.com.cwi.crescer.api.util;

import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AtribuiExperienciaETaskcoinDaMissaoNoUsuario {

    private static final int TASKCOINS_RECEBIDAS = 10;
    private static final int EXPERIENCIA_RECEBIDA = 50;

    public static void atribuir(Usuario usuario){
        usuario.setTaskcoin(usuario.getTaskcoin() + TASKCOINS_RECEBIDAS);
        usuario.setExperiencia(usuario.getExperiencia() + EXPERIENCIA_RECEBIDA);
    }

}
