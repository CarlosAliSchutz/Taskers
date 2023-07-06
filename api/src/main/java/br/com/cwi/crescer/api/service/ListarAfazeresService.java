package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.AfazerResumidoResponse;
import br.com.cwi.crescer.api.mapper.AfazerResumidoResponseMapper;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarAfazeresService {

    @Autowired
    private AfazerRepository afazerRepository;

    @Autowired
    private UsuarioAutenticadoService autenticadoService;

    public Page<AfazerResumidoResponse> listar(String text, Pageable pageable) {
        Long usuarioId = autenticadoService.getId();

        return afazerRepository.findByAtivoAndProprietarioIdAndNomeContainingIgnoreCase(true,usuarioId, text, pageable)
                .map(AfazerResumidoResponseMapper::toResponse);
    }
}
