package br.com.cwi.crescer.api.security.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class GerarSenhaCriptografadaService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String getSenhaCriptografada(String senhaAberta) {
        return passwordEncoder.encode(senhaAberta);
    }

}
