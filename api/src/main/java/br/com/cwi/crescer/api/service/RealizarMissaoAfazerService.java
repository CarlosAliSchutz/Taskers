package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.MissaoAfazer;
import br.com.cwi.crescer.api.repository.MissaoAfazerRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.util.AtribuiExperienciaETaskcoinDaMissaoNoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static br.com.cwi.crescer.api.domain.TipoConquista.REALIZAR_MISSAO;

@Service
public class RealizarMissaoAfazerService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MissaoAfazerRepository missaoAfazerRepository;

    @Autowired
    private RealizarConquistaService realizarConquistaService;


    @Transactional
    public void realizar(Usuario usuario, Long idAfazer) {

        Optional<MissaoAfazer> missaoAfazer = missaoAfazerRepository
                .findByProprietarioIdAndAfazerIdAndConcluidaFalse(usuario.getId(), idAfazer);

        if (missaoAfazer.isPresent()){
            missaoAfazer.get().setConcluida(true);
            missaoAfazerRepository.save(missaoAfazer.get());

            AtribuiExperienciaETaskcoinDaMissaoNoUsuario.atribuir(usuario);
            usuarioRepository.save(usuario);

            realizarConquistaService.realizar(usuario.getId(), REALIZAR_MISSAO);
        }
    }

}
