package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.AfazerRequest;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.cwi.crescer.api.domain.TipoConquista.INCLUIR_AFAZERES;
import static br.com.cwi.crescer.api.mapper.AfazerRequestMapper.toEntity;

@Service
public class IncluirAfazerService {

    @Autowired
    private AfazerRepository afazerRepository;

    @Autowired
    private UsuarioAutenticadoService autenticadoService;

    @Autowired
    private IncluirAfazerGoogleCalendarService incluirAfazerGoogleCalendarService;

    @Autowired
    private RealizarConquistaService realizarConquistaService;

    @Transactional
    public void incluir(AfazerRequest request) {
        Usuario usuarioLogado = autenticadoService.get();

        Afazer afazer = toEntity(request);

        afazer.setProprietario(usuarioLogado);
        afazer.setAtivo(true);
        afazer.setFinalizado(false);

        afazerRepository.save(afazer);
        incluirAfazerGoogleCalendarService.incluir(usuarioLogado, afazer);

        realizarConquistaService.realizar(usuarioLogado.getId(), INCLUIR_AFAZERES);
    }
}
