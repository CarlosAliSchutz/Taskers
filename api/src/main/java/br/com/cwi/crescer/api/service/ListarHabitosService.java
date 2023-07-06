package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.HabitoResumidoResponse;
import br.com.cwi.crescer.api.mapper.HabitoResumidoResponseMapper;
import br.com.cwi.crescer.api.repository.HabitoRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarHabitosService {

    @Autowired
    private HabitoRepository habitoRepository;

    @Autowired
    private UsuarioAutenticadoService autenticadoService;

    public Page<HabitoResumidoResponse> listar(String text, Pageable pageable) {
        Long usuarioId = autenticadoService.getId();

        return habitoRepository.findByAtivoAndProprietarioIdAndNomeContainingIgnoreCase(true, usuarioId, text, pageable)
                .map(HabitoResumidoResponseMapper::toResponse);
    }
}
