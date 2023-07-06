package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.TipoConquista;
import br.com.cwi.crescer.api.domain.UsuarioConquista;
import br.com.cwi.crescer.api.repository.UsuarioConquistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RealizarConquistaService {

    @Autowired
    private UsuarioConquistaRepository usuarioConquistaRepository;

    @Transactional
    public void realizar(Long usuarioId, TipoConquista tipo) {
        usuarioConquistaRepository.findByUsuarioIdAndConquistaTipo(usuarioId, tipo)
        .forEach(this::realizarConquista);
    }

    private void realizarConquista(UsuarioConquista usuarioConquista) {
        if(!usuarioConquista.isConcluida()) {
            Integer progresso = usuarioConquista.getProgresso();
            usuarioConquista.setProgresso(progresso+1);

            if(usuarioConquista.getProgresso() >= usuarioConquista.getConquista().getObjetivo()) {
                usuarioConquista.setConcluida(true);
            }
            usuarioConquistaRepository.save(usuarioConquista);
        }
    }
}
