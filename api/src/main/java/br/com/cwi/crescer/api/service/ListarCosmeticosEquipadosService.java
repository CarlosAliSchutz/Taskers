package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.CosmeticoResponse;
import br.com.cwi.crescer.api.mapper.CosmeticoResponseMapper;
import br.com.cwi.crescer.api.repository.CosmeticoRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarCosmeticosEquipadosService {

    @Autowired
    private CosmeticoRepository cosmeticoRepository;

    @Autowired
    private UsuarioAutenticadoService autenticadoService;

    public List<CosmeticoResponse> listar() {
        Long usuarioId = autenticadoService.getId();
        return cosmeticoRepository.buscarCosmeticosEquipados(usuarioId)
                .stream()
                .map(CosmeticoResponseMapper::toResponse)
                .collect(Collectors.toList());
    }
}
