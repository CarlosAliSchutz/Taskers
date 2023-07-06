package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.MissaoResponse;
import br.com.cwi.crescer.api.domain.MissaoAfazer;
import br.com.cwi.crescer.api.domain.MissaoDiaria;
import br.com.cwi.crescer.api.domain.MissaoHabito;
import br.com.cwi.crescer.api.mapper.MissaoResponseMapper;
import br.com.cwi.crescer.api.repository.MissaoAfazerRepository;
import br.com.cwi.crescer.api.repository.MissaoDiariaRepository;
import br.com.cwi.crescer.api.repository.MissaoHabitoRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListarMissoesService {

    @Autowired
    private UsuarioAutenticadoService autenticadoService;

    @Autowired
    private MissaoAfazerRepository missaoAfazerRepository;

    @Autowired
    private MissaoDiariaRepository missaoDiariaRepository;

    @Autowired
    private MissaoHabitoRepository missaoHabitoRepository;

    public List<MissaoResponse> listar() {
        Long usuarioId = autenticadoService.getId();

        MissaoAfazer missaoAfazer = missaoAfazerRepository.findByProprietarioId(usuarioId);
        MissaoHabito missaoHabito = missaoHabitoRepository.findByProprietarioId(usuarioId);
        MissaoDiaria missaoDiaria = missaoDiariaRepository.findByProprietarioId(usuarioId);

        List<MissaoResponse> missoes = new ArrayList<>();

        if (missaoAfazer != null) {
            missoes.add(MissaoResponseMapper.toResponse(missaoAfazer));
        }
        if (missaoHabito != null) {
            missoes.add(MissaoResponseMapper.toResponse(missaoHabito));
        }
        if (missaoDiaria != null) {
            missoes.add(MissaoResponseMapper.toResponse(missaoDiaria));
        }

        return missoes;
    }
}
