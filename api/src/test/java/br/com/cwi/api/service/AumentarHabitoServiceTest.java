package br.com.cwi.api.service;

import br.com.cwi.api.factories.HabitoFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.repository.HabitoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.AumentarHabitoService;
import br.com.cwi.crescer.api.service.RealizarConquistaService;
import br.com.cwi.crescer.api.service.RealizarMissaoHabitoService;
import br.com.cwi.crescer.api.service.core.BuscarHabitoService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.crescer.api.domain.TipoConquista.REALIZAR_HABITOS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AumentarHabitoServiceTest {

    private final Integer QUANTIDADE_DE_EXECUCOES = 1;
    private final Integer QUANTIDADE_DE_XP_NO_FACIL = 5;

    @InjectMocks
    private AumentarHabitoService tested;

    @Mock
    private HabitoRepository habitoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private BuscarHabitoService buscarHabitoService;
    @Mock
    private UsuarioAutenticadoService autenticadoService;
    @Mock
    private ValidarProprietarioService validarProprietarioService;
    @Mock
    private RealizarMissaoHabitoService realizarMissaoHabitoService;
    @Mock
    private RealizarConquistaService realizarConquistaService;

    @Test
    @DisplayName("Deve aumentar a contagem de execuções do hábito")
    void deveAumentarExecucoes() {
        Habito habito = HabitoFactory.getHabitoFacil();
        Long habitoId = habito.getId();
        Usuario usuario = UsuarioFactory.getUsuario();

        when(buscarHabitoService.porId(habitoId)).thenReturn(habito);
        when(autenticadoService.get()).thenReturn(usuario);

        tested.aumentar(habitoId);

        verify(buscarHabitoService).porId(habitoId);
        verify(autenticadoService).get();
        verify(validarProprietarioService).doHabito(habito);
        verify(usuarioRepository).save(usuario);
        verify(habitoRepository).save(habito);
        verify(realizarMissaoHabitoService).realizar(usuario, habito);
        verify(realizarConquistaService).realizar(usuario.getId(), REALIZAR_HABITOS);

        assertEquals(habito.getExecucoes(), QUANTIDADE_DE_EXECUCOES);

    }


    @Test
    @DisplayName("Deve aumentar a quantidade de experiência do usuário")
    void deveAumentarExperiencia() {
        Habito habito = HabitoFactory.getHabitoFacil();
        Long habitoId = habito.getId();
        Usuario usuario = UsuarioFactory.getUsuario();

        when(buscarHabitoService.porId(habitoId)).thenReturn(habito);
        when(autenticadoService.get()).thenReturn(usuario);

        tested.aumentar(habitoId);

        verify(buscarHabitoService).porId(habitoId);
        verify(autenticadoService).get();
        verify(validarProprietarioService).doHabito(habito);
        verify(usuarioRepository).save(usuario);
        verify(habitoRepository).save(habito);
        verify(realizarMissaoHabitoService).realizar(usuario, habito);
        verify(realizarConquistaService).realizar(usuario.getId(), REALIZAR_HABITOS);

        assertEquals(usuario.getExperiencia(), QUANTIDADE_DE_XP_NO_FACIL);
    }
    @Test
    @DisplayName("Não deve aumentar caso não encontre o habito")
    void naoDeveAumentarCasoNaoEncontrado() {
        Habito habito = HabitoFactory.getHabitoFacil();
        Long habitoId = habito.getId();
        Usuario usuario = UsuarioFactory.getUsuario();

        doThrow(ResponseStatusException.class)
                .when(buscarHabitoService).porId(habitoId);


        assertThrows(ResponseStatusException.class, () -> {
            tested.aumentar(habitoId);
        });

        verify(buscarHabitoService).porId(habitoId);
        verify(autenticadoService, never()).get();
        verify(validarProprietarioService, never()).doHabito(habito);
        verify(usuarioRepository, never()).save(usuario);
        verify(habitoRepository, never()).save(habito);
        verify(realizarMissaoHabitoService, never()).realizar(usuario, habito);
        verify(realizarConquistaService, never()).realizar(usuario.getId(), REALIZAR_HABITOS);
    }

    @Test
    @DisplayName("Não deve aumentar caso não seja o proprietário do habito")
    void naoDeveAumentarCasoNaoSejaProprietario() {
        Habito habito = HabitoFactory.getHabitoFacil();
        Long habitoId = habito.getId();
        Usuario usuario = UsuarioFactory.getUsuario();

        when(buscarHabitoService.porId(habitoId)).thenReturn(habito);
        when(autenticadoService.get()).thenReturn(usuario);
        doThrow(ResponseStatusException.class)
                .when(validarProprietarioService).doHabito(habito);


        assertThrows(ResponseStatusException.class, () -> {
            tested.aumentar(habitoId);
        });

        verify(buscarHabitoService).porId(habitoId);
        verify(autenticadoService).get();
        verify(validarProprietarioService).doHabito(habito);
        verify(usuarioRepository, never()).save(usuario);
        verify(habitoRepository, never()).save(habito);
        verify(realizarMissaoHabitoService, never()).realizar(usuario, habito);
        verify(realizarConquistaService, never()).realizar(usuario.getId(), REALIZAR_HABITOS);
    }

}
