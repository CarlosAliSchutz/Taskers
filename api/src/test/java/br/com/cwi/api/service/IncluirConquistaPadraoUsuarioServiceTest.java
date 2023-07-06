package br.com.cwi.api.service;

import br.com.cwi.api.factories.ConquistaFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.Conquista;
import br.com.cwi.crescer.api.domain.TipoConquista;
import br.com.cwi.crescer.api.repository.ConquistaRepository;
import br.com.cwi.crescer.api.repository.UsuarioConquistaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.service.IncluirConquistasPadraoUsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IncluirConquistaPadraoUsuarioServiceTest {

    @InjectMocks
    private IncluirConquistasPadraoUsuarioService tested;

    @Mock
    private ConquistaRepository conquistaRepository;

    @Mock
    private UsuarioConquistaRepository usuarioConquistaRepository;

    @Test
    @DisplayName("Deve incluir conquistas padroes")
    void deveIncluirConquistasPadroes() {
        Usuario usuario = UsuarioFactory.getUsuario();
        List<Conquista> conquistas = ConquistaFactory.getLista();

        List<TipoConquista> tiposConquista = Arrays.asList(
                TipoConquista.REALIZAR_HABITOS,
                TipoConquista.INCLUIR_HABITOS,
                TipoConquista.REALIZAR_DIARIAS,
                TipoConquista.INCLUIR_DIARIAS,
                TipoConquista.FINALIZAR_AFAZERES,
                TipoConquista.INCLUIR_AFAZERES,
                TipoConquista.COMPRAR_COSMETICO,
                TipoConquista.REALIZAR_MISSAO
        );

        tiposConquista.forEach(tipoConquista -> {
            when(conquistaRepository.findByTipo(tipoConquista)).thenReturn(conquistas);
        });

        tested.incluir(usuario);

        tiposConquista.forEach(tipoConquista -> {
            verify(conquistaRepository).findByTipo(tipoConquista);
        });
        verify(usuarioConquistaRepository).saveAll(any());
    }

}
