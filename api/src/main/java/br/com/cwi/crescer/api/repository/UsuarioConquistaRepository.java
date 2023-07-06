package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.TipoConquista;
import br.com.cwi.crescer.api.domain.UsuarioConquista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioConquistaRepository extends JpaRepository<UsuarioConquista, Long> {
    List<UsuarioConquista> findByUsuarioIdAndConquistaTipo(Long usuarioId, TipoConquista tipo);

    Page<UsuarioConquista> findByUsuarioId(Long usuarioId, Pageable pageable);
}
