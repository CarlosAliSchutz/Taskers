package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.DiariaResumidaResponse;
import br.com.cwi.crescer.api.mapper.DiariaResumidaResponseMapper;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarDiariasService {

    @Autowired
    private DiariaRepository diariaRepository;

    @Autowired
    private UsuarioAutenticadoService autenticadoService;

    public Page<DiariaResumidaResponse> listar(String text, Pageable pageable) {
        Long usuarioId = autenticadoService.getId();

        return diariaRepository.findByAtivoAndProprietarioIdAndNomeContainingIgnoreCase(true, usuarioId, text, pageable)
                .map(DiariaResumidaResponseMapper::toResponse);
    }
}
