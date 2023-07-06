package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Notificacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    Page<Notificacao> findAllByAtivoAndProprietarioId(boolean b, Long id, Pageable pageable);
}
