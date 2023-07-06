package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.MissaoDiaria;
import br.com.cwi.crescer.api.repository.MissaoDiariaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.util.AtribuiExperienciaETaskcoinDaMissaoNoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static br.com.cwi.crescer.api.domain.TipoConquista.REALIZAR_MISSAO;

@Service
public class RealizarMissaoDiariaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MissaoDiariaRepository missaoDiariaRepository;

    @Autowired
    private RealizarConquistaService realizarConquistaService;

    @Transactional
    public void realizar(Usuario usuario, Long idDiaria) {

        Optional<MissaoDiaria> missaoDiaria = missaoDiariaRepository
                .findByProprietarioIdAndDiariaIdAndConcluidaFalse(usuario.getId(), idDiaria);

        if (missaoDiaria.isPresent()){
            missaoDiaria.get().setConcluida(true);
            missaoDiariaRepository.save(missaoDiaria.get());

            AtribuiExperienciaETaskcoinDaMissaoNoUsuario.atribuir(usuario);
            usuarioRepository.save(usuario);

            realizarConquistaService.realizar(usuario.getId(), REALIZAR_MISSAO);
        }
    }

}
