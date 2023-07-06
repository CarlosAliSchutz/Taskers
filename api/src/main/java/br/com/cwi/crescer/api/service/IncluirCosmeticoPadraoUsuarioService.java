package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.domain.CosmeticoUsuario;
import br.com.cwi.crescer.api.domain.CosmeticosPadroes;
import br.com.cwi.crescer.api.repository.CosmeticoUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.service.core.BuscarCosmeticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class IncluirCosmeticoPadraoUsuarioService {

    @Autowired
    private BuscarCosmeticoService buscarCosmeticoService;

    @Autowired
    private CosmeticoUsuarioRepository cosmeticoUsuarioRepository;

    @Transactional
    public void incluir(Usuario usuario) {
        List<String> nomesCosmeticosPadroesEquipados = Arrays.asList(
                CosmeticosPadroes.HOMEMPADRAO.getNome(),
                CosmeticosPadroes.PLANTAPADRAO.getNome(),
                CosmeticosPadroes.PETPADRAO.getNome(),
                CosmeticosPadroes.FUNDOPADRAO.getNome()
        );

        List<Cosmetico> cosmeticosPadroes = nomesCosmeticosPadroesEquipados.stream()
                .map(buscarCosmeticoService::porNome)
                .collect(Collectors.toList());

        Cosmetico cosmeticoPadraoOpcional = buscarCosmeticoService.porNome(CosmeticosPadroes.MULHERPADRAO.getNome());

        List<CosmeticoUsuario> cosmeticosDoUsuario = Stream.concat(
                        cosmeticosPadroes.stream().map(cosmetico -> CosmeticoUsuario.builder()
                                .cosmetico(cosmetico)
                                .equipado(true)
                                .usuario(usuario)
                                .build()),
                        Stream.of(CosmeticoUsuario.builder()
                                .cosmetico(cosmeticoPadraoOpcional)
                                .equipado(false)
                                .usuario(usuario)
                                .build()))
                .collect(Collectors.toList());

        cosmeticoUsuarioRepository.saveAll(cosmeticosDoUsuario);
    }
}
