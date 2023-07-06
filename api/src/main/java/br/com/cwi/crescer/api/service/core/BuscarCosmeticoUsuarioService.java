package br.com.cwi.crescer.api.service.core;

import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.domain.CosmeticoUsuario;
import br.com.cwi.crescer.api.repository.CosmeticoUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class BuscarCosmeticoUsuarioService {

    @Autowired
    private CosmeticoUsuarioRepository cosmeticoUsuarioRepository;

    public CosmeticoUsuario buscar(Usuario usuario, Cosmetico cosmetico) {

        return cosmeticoUsuarioRepository.findByUsuarioAndCosmetico(usuario, cosmetico)
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Você não possui esse cosmético"));
    }

}
