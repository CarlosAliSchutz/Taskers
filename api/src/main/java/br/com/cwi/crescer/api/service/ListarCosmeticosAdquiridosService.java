package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.CosmeticoResponse;
import br.com.cwi.crescer.api.mapper.CosmeticoResponseMapper;
import br.com.cwi.crescer.api.repository.CosmeticoRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarCosmeticosAdquiridosService {
    
    @Autowired
    private CosmeticoRepository cosmeticoRepository;

    @Autowired
    private UsuarioAutenticadoService autenticadoService;

    public Page<CosmeticoResponse> listar(Pageable pageable) {
        Long usuarioId = autenticadoService.getId();

        return cosmeticoRepository.buscarCosmeticosAdquiridos(usuarioId, pageable)
                .map(CosmeticoResponseMapper::toResponse);
    }
}
