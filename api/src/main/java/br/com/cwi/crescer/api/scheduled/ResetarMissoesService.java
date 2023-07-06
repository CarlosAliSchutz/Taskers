package br.com.cwi.crescer.api.scheduled;

import br.com.cwi.crescer.api.domain.*;
import br.com.cwi.crescer.api.repository.*;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class ResetarMissoesService {

    private static final int NUMERO_MAXIMO_EXECUCOES_MISSAO_HABITO = 5;
    private static final PageRequest FILTRO_PARA_UM_RESULTADO = PageRequest.of(0, 1);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AfazerRepository afazerRepository;
    @Autowired
    private DiariaRepository diariaRepository;
    @Autowired
    private HabitoRepository habitoRepository;

    @Autowired
    private MissaoAfazerRepository missaoAfazerRepository;
    @Autowired
    private MissaoDiariaRepository missaoDiariaRepository;
    @Autowired
    private MissaoHabitoRepository missaoHabitoRepository;

    @Transactional
    @Scheduled(cron = "0 12 16 * * *")
    public void resetar() {
        missaoAfazerRepository.deleteAll();
        missaoDiariaRepository.deleteAll();
        missaoHabitoRepository.deleteAll();

        int numeroDaPagina = 0;
        int tamanhoDaPagina = 100;
        Page<Usuario> paginaDeUsuarios;

        do {
            PageRequest filtroParaUsuarios = PageRequest.of(numeroDaPagina, tamanhoDaPagina);
            paginaDeUsuarios = usuarioRepository.findAllByAtivoTrue(filtroParaUsuarios);

            numeroDaPagina++;
            resetarMissoesDosUsuarios(paginaDeUsuarios.getContent());

        } while (numeroDaPagina < paginaDeUsuarios.getTotalPages());
    }

    private void resetarMissoesDosUsuarios(List<Usuario> usuarios) {
        usuarios.forEach(usuario -> {
            buscarEAlterarMissaoAfazer(usuario);
            buscarEAlterarMissaoHabito(usuario);
            buscarEAlterarMissaoDiaria(usuario);
        });
    }

    private void buscarEAlterarMissaoAfazer(Usuario usuario) {
        List<Afazer> afazeresDisponiveis = afazerRepository
                .buscarAfazerDisponivelAleatorio(usuario.getId(), FILTRO_PARA_UM_RESULTADO).getContent();

        if (!afazeresDisponiveis.isEmpty()) {
            Afazer afazerAleatorioDisponivel = afazeresDisponiveis.get(0);

            MissaoAfazer missaoAfazer = new MissaoAfazer();

            missaoAfazer.setAfazer(afazerAleatorioDisponivel);
            missaoAfazer.setProprietario(usuario);
            missaoAfazer.setConcluida(false);
            missaoAfazer.setNome("Faça um afazer");
            missaoAfazer.setDescricao("Realize o afazer: " + afazerAleatorioDisponivel.getNome());

            missaoAfazerRepository.save(missaoAfazer);
        }
    }

    private void buscarEAlterarMissaoHabito(Usuario usuario) {
        List<Habito> habitoDisponivel = habitoRepository
                .buscarHabitoDisponivelAleatorio(usuario.getId(), FILTRO_PARA_UM_RESULTADO).getContent();

        if (!habitoDisponivel.isEmpty()) {
            Habito habitoAleatorioDisponivel = habitoDisponivel.get(0);

            MissaoHabito missaoHabito = new MissaoHabito();

            int execucoesAleatoriasHabito = new Random().nextInt(NUMERO_MAXIMO_EXECUCOES_MISSAO_HABITO) + 1;

            missaoHabito.setHabito(habitoAleatorioDisponivel);
            missaoHabito.setProprietario(usuario);
            missaoHabito.setExecucoesNecessarias(execucoesAleatoriasHabito);
            missaoHabito.setConcluida(false);
            missaoHabito.setNome("Faça um hábito");
            missaoHabito.setDescricao(
                    "Execute o hábito: " + habitoAleatorioDisponivel.getNome() + " " + execucoesAleatoriasHabito + " vezes");

            missaoHabitoRepository.save(missaoHabito);
        }
    }

    private void buscarEAlterarMissaoDiaria(Usuario usuario) {
        List<Diaria> diariaDisponivel = diariaRepository
                .buscarDiariaDisponivelAleatoria(usuario.getId(), FILTRO_PARA_UM_RESULTADO).getContent();

        if (!diariaDisponivel.isEmpty()) {
            Diaria diariaAleatoriaDisponivel = diariaDisponivel.get(0);

            MissaoDiaria missaoDiaria = new MissaoDiaria();

            missaoDiaria.setDiaria(diariaAleatoriaDisponivel);
            missaoDiaria.setProprietario(usuario);
            missaoDiaria.setConcluida(false);
            missaoDiaria.setNome("Faça uma diária");
            missaoDiaria.setDescricao("Realize a diária: " + diariaAleatoriaDisponivel.getNome());

            missaoDiariaRepository.save(missaoDiaria);
        }
    }
}



