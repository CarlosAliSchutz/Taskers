package br.com.cwi.api.service.core;

import br.com.cwi.api.factories.*;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.domain.Notificacao;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidarProprietarioServiceTest {

    @InjectMocks
    private ValidarProprietarioService tested;

    @Mock
    private UsuarioAutenticadoService autenticadoService;

    @Test
    @DisplayName("Deve retornar erro quando não encontrar o proprietario do afazer")
    void deveRetornarErroQuandoNaoEncontrarProprietarioDoAfazer() {
        Usuario usuarioNaoLogado = UsuarioFactory.getUsuarioComXP();

        Afazer afazer = AfazerFactory.getAfazerFacil();
        afazer.setProprietario(usuarioNaoLogado);
        Usuario usuarioLogado = UsuarioFactory.getUsuario();

        when(autenticadoService.get()).thenReturn(usuarioLogado);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.doAfazer(afazer));

        verify(autenticadoService).get();

        assertEquals("Você não é proprietário deste afazer", exception.getReason());
    }
    @Test
    @DisplayName("Deve passar quando encontrar o proprietario do afazer")
    void devePassarQuandoEncontrarProprietarioDoAfazer() {
        Afazer afazer = AfazerFactory.getAfazerFacil();
        Usuario usuarioLogado = UsuarioFactory.getUsuario();
        afazer.setProprietario(usuarioLogado);

        when(autenticadoService.get()).thenReturn(usuarioLogado);
        tested.doAfazer(afazer);
        verify(autenticadoService).get();
    }
    @Test
    @DisplayName("Deve retornar erro quando não encontrar o proprietario da diaria")
    void deveRetornarErroQuandoNaoEncontrarProprietarioDaDiaria() {
        Usuario usuarioNaoLogado = UsuarioFactory.getUsuarioComXP();

        Diaria diaria = DiariaFactory.getDiariaFacil();
        diaria.setProprietario(usuarioNaoLogado);
        Usuario usuarioLogado = UsuarioFactory.getUsuario();

        when(autenticadoService.get()).thenReturn(usuarioLogado);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.daDiaria(diaria));
        verify(autenticadoService).get();
        assertEquals("Você não é proprietário desta diária", exception.getReason());
    }
    @Test
    @DisplayName("Deve passar quando encontrar o proprietario da diaria")
    void devePassarQuandoEncontrarProprietarioDaDiaria() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        Usuario usuarioLogado = UsuarioFactory.getUsuario();
        diaria.setProprietario(usuarioLogado);

        when(autenticadoService.get()).thenReturn(usuarioLogado);
        tested.daDiaria(diaria);
        verify(autenticadoService).get();
    }
    @Test
    @DisplayName("Deve retornar erro quando não encontrar o proprietario do habito")
    void deveRetornarErroQuandoNaoEncontrarProprietarioDoHabito() {
        Usuario usuarioNaoLogado = UsuarioFactory.getUsuarioComXP();


        Habito habito = HabitoFactory.getHabitoFacil();
        habito.setProprietario(usuarioNaoLogado);
        Usuario usuarioLogado = UsuarioFactory.getUsuario();

        when(autenticadoService.get()).thenReturn(usuarioLogado);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.doHabito(habito));
        verify(autenticadoService).get();

        assertEquals("Você não é proprietário deste hábito", exception.getReason());
    }
    @Test
    @DisplayName("Deve passar quando encontrar o proprietario do habito")
    void devePassarQuandoEncontrarProprietarioDoHabito() {
        Habito habito = HabitoFactory.getHabitoFacil();
        Usuario usuarioLogado = UsuarioFactory.getUsuario();
        habito.setProprietario(usuarioLogado);

        when(autenticadoService.get()).thenReturn(usuarioLogado);
        tested.doHabito(habito);
        verify(autenticadoService).get();

    }

    @Test
    @DisplayName("Deve passar quando encontrar o proprietario da notificacao")
    void devePassarQuandoEncontrarProprietarioDaNotificacao() {
        Notificacao notificacao = NotificacaoFactory.getNotificacaoAtiva();
        Usuario usuarioLogado = UsuarioFactory.getUsuario();
        notificacao.setProprietario(usuarioLogado);

        when(autenticadoService.get()).thenReturn(usuarioLogado);
        tested.daNotificacao(notificacao);

        verify(autenticadoService).get();
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrar o proprietario da notificacao")
    void deveRetornarErroQuandoNaoEncontrarProprietarioDaNotificacao() {
        Usuario usuarioNaoLogado = UsuarioFactory.getUsuarioComXP();

        Notificacao notificacao = NotificacaoFactory.getNotificacaoAtiva();
        notificacao.setProprietario(usuarioNaoLogado);
        Usuario usuarioLogado = UsuarioFactory.getUsuario();

        when(autenticadoService.get()).thenReturn(usuarioLogado);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.daNotificacao(notificacao));
        verify(autenticadoService).get();

        assertEquals("Você não é proprietário desta notificação", exception.getReason());
    }
}
