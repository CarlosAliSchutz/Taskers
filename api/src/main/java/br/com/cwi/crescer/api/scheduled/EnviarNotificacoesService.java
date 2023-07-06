package br.com.cwi.crescer.api.scheduled;

import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.domain.Notificacao;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.repository.NotificacaoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.EnviarEmailService;
import br.com.cwi.crescer.api.service.core.NowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnviarNotificacoesService {

    private static final String ROTA_PRIVADA_WEBSOCKET = "/notificacao";
    private static final String TEXTO_PADRAO_PARA_NOTIFICACAO = "Está na hora de realizar a diária: ";

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private DiariaRepository diariaRepository;

    @Autowired
    private EnviarEmailService enviarEmailService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NowService nowService;

    @Transactional
    @Scheduled(cron = "0 * * * * *")
    public void enviar() {
        LocalTime agora = nowService.horaAtual();
        Long diaDaSemanaEmNumero = nowService.numeroDiaDaSemana();

        List<Diaria> diarias = diariaRepository.findByAtivoTrueAndHoraAndDiasId(agora, diaDaSemanaEmNumero);

        enviarESalvarNotificacoes(diarias);
    }

    private void enviarESalvarNotificacoes(List<Diaria> diarias) {
        List<Notificacao> notificacoes = diarias.stream()
                .map(diaria -> {
                    String tituloNotificacao = diaria.getNome();
                    String textoNotificacao = TEXTO_PADRAO_PARA_NOTIFICACAO + diaria.getDescricao();

                    Notificacao notificacao = gerarNotificaoParaUsuario(
                            tituloNotificacao, textoNotificacao, diaria.getProprietario());

                    simpMessagingTemplate.convertAndSendToUser(
                            diaria.getProprietario().getEmail(), ROTA_PRIVADA_WEBSOCKET, textoNotificacao);

                    if (diaria.getProprietario().isNotificacoesEmail()) {
                        enviarEmailService.enviar(diaria.getProprietario().getEmail(), tituloNotificacao, textoNotificacao);
                    }

                    return notificacao;
                })
                .collect(Collectors.toList());

        notificacaoRepository.saveAll(notificacoes);
    }

    private Notificacao gerarNotificaoParaUsuario(String titulo, String texto, Usuario destinario) {
        Notificacao notificacao = new Notificacao();
        notificacao.setProprietario(destinario);
        notificacao.setTitulo(titulo);
        notificacao.setTexto(texto);
        notificacao.setAtivo(true);
        return notificacao;
    }

}