package br.com.cwi.crescer.api.service.core;

import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.repository.CosmeticoUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ValidarUsuarioPossuiCosmeticoService {

    @Autowired
    private CosmeticoUsuarioRepository cosmeticoUsuarioRepository;
    public void validarSeNaoPossui(Usuario usuario, Cosmetico cosmetico){
        if(cosmeticoUsuarioRepository.existsByUsuarioAndCosmetico(usuario, cosmetico)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cosmético já adquirido");
        }
    }
}
