package br.com.cwi.crescer.api.security.mapper;

import br.com.cwi.crescer.api.controller.response.CosmeticoResponse;
import br.com.cwi.crescer.api.domain.CosmeticoUsuario;
import br.com.cwi.crescer.api.mapper.CosmeticoResponseMapper;
import br.com.cwi.crescer.api.security.controller.request.IncluirUsuarioRequest;
import br.com.cwi.crescer.api.security.controller.response.UsuarioResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UsuarioMapper {

    public static Usuario toEntity(IncluirUsuarioRequest request) {
        Usuario entity = new Usuario();

        entity.setNomeCompleto(request.getNomeCompleto());
        entity.setEmail(request.getEmail());
        entity.setImagemPerfil(request.getImagemPerfil());
        entity.setSenha(request.getSenha());

        return entity;
    }


    public static UsuarioResponse toResponse(Usuario entity) {
        UsuarioResponse response = new UsuarioResponse();

        response.setId(entity.getId());
        response.setExperiencia(entity.getExperiencia());
        response.setTaskcoin(entity.getTaskcoin());
        response.setEmail(entity.getEmail());
        response.setImagemPerfil(entity.getImagemPerfil());
        response.setNomeCompleto(entity.getNomeCompleto());
        response.setNotificacoesPorEmail(entity.isNotificacoesEmail());
        response.setProvedor(entity.getProvider());

        return response;
    }

    public static UsuarioResponse toResponse(Usuario entity, List<CosmeticoUsuario> cosmeticosEquipados) {
        UsuarioResponse response = new UsuarioResponse();

        response.setId(entity.getId());
        response.setExperiencia(entity.getExperiencia());
        response.setTaskcoin(entity.getTaskcoin());
        response.setEmail(entity.getEmail());
        response.setImagemPerfil(entity.getImagemPerfil());
        response.setNomeCompleto(entity.getNomeCompleto());
        response.setCosmeticosEquipados(buildCosmeticoResponse(cosmeticosEquipados));
        response.setNotificacoesPorEmail(entity.isNotificacoesEmail());
        response.setProvedor(entity.getProvider());

        return response;
    }

    public static List<CosmeticoResponse> buildCosmeticoResponse(List<CosmeticoUsuario> cosmeticosEquipados) {
        return cosmeticosEquipados
                .stream()
                .map(cosmetico -> CosmeticoResponseMapper.toResponse(cosmetico.getCosmetico()))
                .collect(Collectors.toList());
    }
}

