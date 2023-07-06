package br.com.cwi.api.scheduled;

import br.com.cwi.api.factories.DiariaFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.repository.NotificacaoRepository;
import br.com.cwi.crescer.api.scheduled.EnviarNotificacoesService;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.EnviarEmailService;
import br.com.cwi.crescer.api.service.core.NowService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnviarNotificacoesServiceTest {

    @InjectMocks
    private EnviarNotificacoesService tested;

    @Mock
    private NotificacaoRepository notificacaoRepository;
    @Mock
    private DiariaRepository diariaRepository;
    @Mock
    private EnviarEmailService enviarEmailService;
    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;
    @Mock
    private NowService nowService;

    @Test
    @DisplayName("Deve Enviar Notificacao Para Todos Os Usuarios")
    void deveEnviarNotificacaoParaTodosOsUsuarios() {
        Usuario usuario = UsuarioFactory.getUsuario();
        List<Diaria> diarias = DiariaFactory.getLista();

        diarias.get(0).setProprietario(usuario);
        diarias.get(1).setProprietario(usuario);
        diarias.get(2).setProprietario(usuario);

        String ROTA_PRIVADA_WEBSOCKET = "/notificacao";
        Long diaDaSemana = 1L;
        LocalTime hora = LocalTime.of(13,20, 0);

        when(nowService.numeroDiaDaSemana()).thenReturn(diaDaSemana);
        when(nowService.horaAtual()).thenReturn(hora);
        when(diariaRepository.findByAtivoTrueAndHoraAndDiasId(hora,diaDaSemana)).thenReturn(diarias);

        tested.enviar();

        verify(nowService).numeroDiaDaSemana();
        verify(nowService).horaAtual();
        verify(diariaRepository).findByAtivoTrueAndHoraAndDiasId(hora,diaDaSemana);

        verify(simpMessagingTemplate, times(3)).convertAndSendToUser(
                any(), eq(ROTA_PRIVADA_WEBSOCKET), any());
        verify(enviarEmailService, times(3)).enviar(any(), any(), any());

        verify(notificacaoRepository).saveAll(any());
    }

    @Test
    @DisplayName("Deve Enviar Notificacao Apenas Para Os Usuarios Que Possuem A Preferencia De Email")
    void deveEnviarNotificacaoApenasParaOsUsuariosQuePossuemAPreferenciaDeEmail() {
        Usuario usuarioComNotificacao = UsuarioFactory.getUsuario();
        Usuario usuarioSemNotificacao = UsuarioFactory.getUsuarioComXP();
        List<Diaria> diarias = DiariaFactory.getLista();

        diarias.get(0).setProprietario(usuarioComNotificacao);
        diarias.get(1).setProprietario(usuarioSemNotificacao);
        diarias.get(2).setProprietario(usuarioSemNotificacao);

        String ROTA_PRIVADA_WEBSOCKET = "/notificacao";
        Long diaDaSemana = 1L;
        LocalTime hora = LocalTime.of(13,20, 0);

        when(nowService.numeroDiaDaSemana()).thenReturn(diaDaSemana);
        when(nowService.horaAtual()).thenReturn(hora);
        when(diariaRepository.findByAtivoTrueAndHoraAndDiasId(hora,diaDaSemana)).thenReturn(diarias);

        tested.enviar();

        verify(nowService).numeroDiaDaSemana();
        verify(nowService).horaAtual();
        verify(diariaRepository).findByAtivoTrueAndHoraAndDiasId(hora,diaDaSemana);

        verify(simpMessagingTemplate, times(3)).convertAndSendToUser(
                any(), eq(ROTA_PRIVADA_WEBSOCKET), any());
        verify(enviarEmailService, times(1)).enviar(any(), any(), any());

        verify(notificacaoRepository).saveAll(any());
    }

    @Test
    @DisplayName("Nao Deve Lancar Erro Quando Nao Haver Diarias")
    void naoDeveLancarErroQuandoNaoHaverDiarias() {
        List<Diaria> diarias = DiariaFactory.getListaVazia();

        Long diaDaSemana = 1L;
        LocalTime hora = LocalTime.of(13,20, 0);
        String ROTA_PRIVADA_WEBSOCKET = "/notificacao";

        when(nowService.numeroDiaDaSemana()).thenReturn(diaDaSemana);
        when(nowService.horaAtual()).thenReturn(hora);
        when(diariaRepository.findByAtivoTrueAndHoraAndDiasId(hora,diaDaSemana)).thenReturn(diarias);

        tested.enviar();

        verify(nowService).numeroDiaDaSemana();
        verify(nowService).horaAtual();
        verify(diariaRepository).findByAtivoTrueAndHoraAndDiasId(hora,diaDaSemana);

        verify(simpMessagingTemplate,never()).convertAndSendToUser(
                any(), eq(ROTA_PRIVADA_WEBSOCKET), any());
        verify(enviarEmailService, never()).enviar(any(), any(), any());

        verify(notificacaoRepository).saveAll(any());
    }
}
