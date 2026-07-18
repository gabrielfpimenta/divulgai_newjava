package com.itb.inf2am.divulgai.model.repository;

import com.itb.inf2am.divulgai.dto.ServicoPesquisaDTO;
import com.itb.inf2am.divulgai.model.entity.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    @Query("""
            SELECT new com.itb.inf2am.divulgai.dto.ServicoPesquisaDTO(
                s.id,
                s.nome,
                s.descricao,
                s.contador,
                s.statusServico,
                c.id,
                c.nome,
                p.id,
                p.nome,
                p.cidade,
                p.uf,
                p.statusPrestador,
                r.id,
                r.cidade,
                r.uf,
                r.zona
            )
            FROM Servico s
            LEFT JOIN s.categoria c
            LEFT JOIN s.prestador p
            LEFT JOIN p.regiao r
            WHERE (:statusServico IS NULL OR s.statusServico = :statusServico)
              AND (:statusPrestador IS NULL OR p.statusPrestador = :statusPrestador)
              AND (:categoriaId IS NULL OR c.id = :categoriaId)
              AND (:cidade IS NULL OR p.cidade = :cidade)
              AND (:uf IS NULL OR p.uf = :uf)
              AND (:nomeServico IS NULL OR LOWER(s.nome) LIKE LOWER(CONCAT(:nomeServico, '%')))
            """)
    Page<ServicoPesquisaDTO> pesquisarServicos(
            @Param("nomeServico") String nomeServico,
            @Param("categoriaId") Long categoriaId,
            @Param("cidade") String cidade,
            @Param("uf") String uf,
            @Param("statusServico") String statusServico,
            @Param("statusPrestador") String statusPrestador,
            Pageable pageable
    );
}
