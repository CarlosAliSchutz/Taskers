package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.core.BuscarAfazerService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.cwi.crescer.api.domain.TipoConquista.FINALIZAR_AFAZERES;
import static br.com.cwi.crescer.api.util.CalculaXP.calcularXPdoAfazer;

@Service
public class FinalizarAfazerService {

    @Autowired
    private AfazerRepository afazerRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BuscarAfazerService buscarAfazerService;

    @Autowired
    private UsuarioAutenticadoService autenticadoService;

    @Autowired
    private ValidarProprietarioService validarProprietarioService;

    @Autowired
    private RealizarMissaoAfazerService realizarMissaoAfazerService;

    @Autowired
    private RealizarConquistaService realizarConquistaService;

    @Transactional
    public void finalizar(Long afazerId) {
        Afazer afazer = buscarAfazerService.porId(afazerId);
        Usuario usuario = autenticadoService.get();
        Integer experiencia = calcularXPdoAfazer(afazer.getDificuldade());

        validarProprietarioService.doAfazer(afazer);

        if(afazer.isFinalizado()) {
            afazer.setFinalizado(false);
            usuario.setExperiencia(usuario.getExperiencia()-experiencia);
        }
        else {
            afazer.setFinalizado(true);
            usuario.setExperiencia(usuario.getExperiencia()+experiencia);
            realizarMissaoAfazerService.realizar(usuario, afazerId);
            realizarConquistaService.realizar(usuario.getId(), FINALIZAR_AFAZERES);
        }

        afazerRepository.save(afazer);
        usuarioRepository.save(usuario);
    }
}
