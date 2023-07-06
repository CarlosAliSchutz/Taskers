package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.DiariaRequest;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.cwi.crescer.api.domain.TipoConquista.INCLUIR_DIARIAS;
import static br.com.cwi.crescer.api.mapper.DiariaRequestMapper.toEntity;

@Service
public class IncluirDiariaService {

    @Autowired
    private DiariaRepository diariaRepository;

    @Autowired
    private UsuarioAutenticadoService autenticadoService;

    @Autowired
    private IncluirDiariaGoogleCalendarService incluirDiariaGoogleCalendarService;

    @Autowired
    private RealizarConquistaService realizarConquistaService;

    @Transactional
    public void incluir(DiariaRequest request) {
        Usuario usuarioLogado = autenticadoService.get();

        Diaria diaria = toEntity(request);

        diaria.setProprietario(usuarioLogado);
        diaria.setAtivo(true);
        diaria.setFinalizado(false);

        diariaRepository.save(diaria);
        incluirDiariaGoogleCalendarService.incluir(usuarioLogado, diaria);

        realizarConquistaService.realizar(usuarioLogado.getId(), INCLUIR_DIARIAS);
    }
}
