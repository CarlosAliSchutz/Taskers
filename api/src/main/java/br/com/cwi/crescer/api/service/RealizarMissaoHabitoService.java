package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.domain.MissaoHabito;
import br.com.cwi.crescer.api.repository.MissaoHabitoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.util.AtribuiExperienciaETaskcoinDaMissaoNoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static br.com.cwi.crescer.api.domain.TipoConquista.REALIZAR_MISSAO;

@Service
public class RealizarMissaoHabitoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MissaoHabitoRepository missaoHabitoRepository;

    @Autowired
    private RealizarConquistaService realizarConquistaService;

    @Transactional
    public void realizar(Usuario usuario, Habito habito) {

        Optional<MissaoHabito> missaoHabito = missaoHabitoRepository
                .findByProprietarioIdAndHabitoIdAndConcluidaFalse(usuario.getId(), habito.getId());

        if (missaoHabito.isPresent()){
            if (habito.getExecucoes() >= missaoHabito.get().getExecucoesNecessarias()){
                missaoHabito.get().setConcluida(true);
                missaoHabitoRepository.save(missaoHabito.get());

                AtribuiExperienciaETaskcoinDaMissaoNoUsuario.atribuir(usuario);
                usuarioRepository.save(usuario);

                realizarConquistaService.realizar(usuario.getId(), REALIZAR_MISSAO);
            }
        }
    }

}
