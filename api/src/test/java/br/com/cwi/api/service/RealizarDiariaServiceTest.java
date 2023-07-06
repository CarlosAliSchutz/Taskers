package br.com.cwi.api.service;

import br.com.cwi.api.factories.DiariaFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.RealizarConquistaService;
import br.com.cwi.crescer.api.service.RealizarDiariaService;
import br.com.cwi.crescer.api.service.RealizarMissaoDiariaService;
import br.com.cwi.crescer.api.service.core.BuscarDiariaService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.crescer.api.domain.TipoConquista.REALIZAR_DIARIAS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RealizarDiariaServiceTest {

    private final Integer QUANTIDADE_DE_XP_NO_FACIL = 25;

    @InjectMocks
    private RealizarDiariaService tested;

    @Mock
    private DiariaRepository diariaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BuscarDiariaService buscarDiariaService;
    @Mock
    private UsuarioAutenticadoService autenticadoService;
    @Mock
    private ValidarProprietarioService validarProprietarioService;
    @Mock
    private RealizarMissaoDiariaService realizarMissaoDiariaService;
    @Mock
    private RealizarConquistaService realizarConquistaService;

    @Test
    @DisplayName("Deve alterar para finalizado caso não esteja")
    void deveAlterarParaFinalizado() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        Long diariaId = diaria.getId();
        Usuario usuario = UsuarioFactory.getUsuario();

        when(buscarDiariaService.porId(diariaId)).thenReturn(diaria);
        when(autenticadoService.get()).thenReturn(usuario);

        tested.realizar(diariaId);

        verify(buscarDiariaService).porId(diariaId);
        verify(autenticadoService).get();
        verify(validarProprietarioService).daDiaria(diaria);
        verify(usuarioRepository).save(usuario);
        verify(diariaRepository).save(diaria);
        verify(realizarMissaoDiariaService).realizar(usuario, diariaId);
        verify(realizarConquistaService).realizar(usuario.getId(), REALIZAR_DIARIAS);


        assertEquals(diaria.isFinalizado(), true);

    }

    @Test
    @DisplayName("Deve alterar para não finalizado caso já esteja")
    void deveAlterarParaNaoFinalizado() {
        Diaria diaria = DiariaFactory.getDiariaFinalizado();
        Long diariaId = diaria.getId();
        Usuario usuario = UsuarioFactory.getUsuario();

        when(buscarDiariaService.porId(diariaId)).thenReturn(diaria);
        when(autenticadoService.get()).thenReturn(usuario);

        tested.realizar(diariaId);

        verify(buscarDiariaService).porId(diariaId);
        verify(autenticadoService).get();
        verify(validarProprietarioService).daDiaria(diaria);
        verify(usuarioRepository).save(usuario);
        verify(diariaRepository).save(diaria);
        verify(realizarMissaoDiariaService, never()).realizar(usuario, diariaId);
        verify(realizarConquistaService, never()).realizar(usuario.getId(), REALIZAR_DIARIAS);

        assertEquals(diaria.isFinalizado(), false);

    }


    @Test
    @DisplayName("Deve aumentar a quantidade de experiência do usuário")
    void deveAumentarExperiencia() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        Long diariaId = diaria.getId();
        Usuario usuario = UsuarioFactory.getUsuario();

        when(buscarDiariaService.porId(diariaId)).thenReturn(diaria);
        when(autenticadoService.get()).thenReturn(usuario);

        tested.realizar(diariaId);

        verify(buscarDiariaService).porId(diariaId);
        verify(autenticadoService).get();
        verify(validarProprietarioService).daDiaria(diaria);
        verify(usuarioRepository).save(usuario);
        verify(diariaRepository).save(diaria);
        verify(realizarMissaoDiariaService).realizar(usuario, diariaId);
        verify(realizarConquistaService).realizar(usuario.getId(), REALIZAR_DIARIAS);

        assertEquals(usuario.getExperiencia(), QUANTIDADE_DE_XP_NO_FACIL);
    }
    @Test
    @DisplayName("Não deve realizar caso não encontre o diaria")
    void naoDeveFinalizarCasoNaoEncontrado() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        Long diariaId = diaria.getId();
        Usuario usuario = UsuarioFactory.getUsuario();

        doThrow(ResponseStatusException.class)
                .when(buscarDiariaService).porId(diariaId);


        assertThrows(ResponseStatusException.class, () -> {
            tested.realizar(diariaId);
        });

        verify(buscarDiariaService).porId(diariaId);
        verify(autenticadoService, never()).get();
        verify(validarProprietarioService, never()).daDiaria(diaria);
        verify(usuarioRepository, never()).save(usuario);
        verify(diariaRepository, never()).save(diaria);
        verify(realizarMissaoDiariaService, never()).realizar(usuario, diariaId);
        verify(realizarConquistaService, never()).realizar(usuario.getId(), REALIZAR_DIARIAS);
    }

    @Test
    @DisplayName("Não deve realizar caso não seja o proprietário do diaria")
    void naoDeveFinalizarCasoNaoSejaProprietario() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        Long diariaId = diaria.getId();
        Usuario usuario = UsuarioFactory.getUsuario();

        when(buscarDiariaService.porId(diariaId)).thenReturn(diaria);
        when(autenticadoService.get()).thenReturn(usuario);
        doThrow(ResponseStatusException.class)
                .when(validarProprietarioService).daDiaria(diaria);


        assertThrows(ResponseStatusException.class, () -> {
            tested.realizar(diariaId);
        });

        verify(buscarDiariaService).porId(diariaId);
        verify(autenticadoService).get();
        verify(validarProprietarioService).daDiaria(diaria);
        verify(usuarioRepository, never()).save(usuario);
        verify(diariaRepository, never()).save(diaria);
        verify(realizarMissaoDiariaService, never()).realizar(usuario, diariaId);
        verify(realizarConquistaService, never()).realizar(usuario.getId(), REALIZAR_DIARIAS);
    }

}
