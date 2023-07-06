package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.UsuarioConquistaResponse;
import br.com.cwi.crescer.api.mapper.UsuarioConquistaResponseMapper;
import br.com.cwi.crescer.api.repository.UsuarioConquistaRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarConquistasService {

    @Autowired
    private UsuarioConquistaRepository usuarioConquistaRepository;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public Page<UsuarioConquistaResponse> listar(Pageable pageable) {
        Long usuarioId = usuarioAutenticadoService.getId();

        return usuarioConquistaRepository.findByUsuarioId(usuarioId, pageable)
                .map(UsuarioConquistaResponseMapper::toResponse);
    }
}
