package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.DiaResponse;
import br.com.cwi.crescer.api.mapper.DiaResponseMapper;
import br.com.cwi.crescer.api.repository.DiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarDiasService {

    @Autowired
    private DiaRepository diaRepository;

    public Page<DiaResponse> listar(Pageable pageable) {

        return diaRepository.findAll(pageable)
                .map(DiaResponseMapper::toResponse);
    }
}
