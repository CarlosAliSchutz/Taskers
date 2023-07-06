package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.core.BuscarDiariaService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.cwi.crescer.api.domain.TipoConquista.REALIZAR_DIARIAS;
import static br.com.cwi.crescer.api.util.CalculaXP.calcularXPdaDiaria;

@Service
public class RealizarDiariaService {

    @Autowired
    private DiariaRepository diariaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BuscarDiariaService buscarDiariaService;

    @Autowired
    private UsuarioAutenticadoService autenticadoService;

    @Autowired
    private ValidarProprietarioService validarProprietarioService;

    @Autowired
    private RealizarMissaoDiariaService realizarMissaoDiariaService;

    @Autowired
    private RealizarConquistaService realizarConquistaService;

    @Transactional
    public void realizar(Long diariaId) {
        Diaria diaria = buscarDiariaService.porId(diariaId);
        Usuario usuario = autenticadoService.get();
        Integer experiencia = calcularXPdaDiaria(diaria.getDificuldade());

        validarProprietarioService.daDiaria(diaria);

        if(diaria.isFinalizado()) {
            diaria.setFinalizado(false);
            usuario.setExperiencia(usuario.getExperiencia()-experiencia);
        }
        else {
            diaria.setFinalizado(true);
            usuario.setExperiencia(usuario.getExperiencia()+experiencia);
            realizarMissaoDiariaService.realizar(usuario, diariaId);
            realizarConquistaService.realizar(usuario.getId(), REALIZAR_DIARIAS);
        }

        diariaRepository.save(diaria);
        usuarioRepository.save(usuario);
    }
}
