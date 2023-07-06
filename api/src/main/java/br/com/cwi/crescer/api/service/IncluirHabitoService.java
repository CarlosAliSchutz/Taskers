package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.HabitoRequest;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.repository.HabitoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.cwi.crescer.api.domain.TipoConquista.INCLUIR_HABITOS;
import static br.com.cwi.crescer.api.mapper.HabitoRequestMapper.toEntity;

@Service
public class IncluirHabitoService {

    @Autowired
    private HabitoRepository habitoRepository;

    @Autowired
    private UsuarioAutenticadoService autenticadoService;

    @Autowired
    private RealizarConquistaService realizarConquistaService;

    @Transactional
    public void incluir(HabitoRequest request) {
        Usuario usuarioLogado = autenticadoService.get();

        Habito habito = toEntity(request);

        habito.setExecucoes(0);
        habito.setAtivo(true);
        habito.setProprietario(usuarioLogado);

        habitoRepository.save(habito);

        realizarConquistaService.realizar(usuarioLogado.getId(), INCLUIR_HABITOS);
    }
}
