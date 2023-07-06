package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.repository.HabitoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.core.BuscarHabitoService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.cwi.crescer.api.domain.TipoConquista.REALIZAR_HABITOS;
import static br.com.cwi.crescer.api.util.CalculaXP.calcularXPdoHabito;

@Service
public class AumentarHabitoService {

    @Autowired
    private HabitoRepository habitoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BuscarHabitoService buscarHabitoService;

    @Autowired
    private UsuarioAutenticadoService autenticadoService;

    @Autowired
    private ValidarProprietarioService validarProprietarioService;

    @Autowired
    private RealizarMissaoHabitoService realizarMissaoHabitoService;

    @Autowired
    private RealizarConquistaService realizarConquistaService;

    @Transactional
    public void aumentar(Long habitoId) {
        Habito habito = buscarHabitoService.porId(habitoId);
        Usuario usuario = autenticadoService.get();
        Integer experiencia = calcularXPdoHabito(habito.getDificuldade());

        validarProprietarioService.doHabito(habito);

        habito.setExecucoes(habito.getExecucoes()+1);
        usuario.setExperiencia(usuario.getExperiencia()+experiencia);

        habitoRepository.save(habito);
        usuarioRepository.save(usuario);

        realizarMissaoHabitoService.realizar(usuario, habito);
        realizarConquistaService.realizar(usuario.getId(), REALIZAR_HABITOS);
    }
}
