package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.domain.CosmeticoUsuario;
import br.com.cwi.crescer.api.repository.CosmeticoUsuarioRepository;
import br.com.cwi.crescer.api.security.controller.response.UsuarioResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.mapper.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalharUsuarioLogadoService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private CosmeticoUsuarioRepository cosmeticoUsuarioRepository;

    public UsuarioResponse detalhar() {
        Usuario usuario = usuarioAutenticadoService.get();

        List<CosmeticoUsuario> cosmeticosEquipados = cosmeticoUsuarioRepository.findAllByUsuarioIdAndEquipadoIsTrue(usuario.getId());

        return UsuarioMapper.toResponse(usuario, cosmeticosEquipados);
    }
}
