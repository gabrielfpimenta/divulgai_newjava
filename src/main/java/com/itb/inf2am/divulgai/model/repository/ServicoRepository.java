package com.itb.inf2am.divulgai.model.repository;

import com.itb.inf2am.divulgai.model.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    @Modifying
    @Query("UPDATE Servico s SET s.contador = COALESCE(s.contador, 0) + 1 WHERE s.id = :id")
    int incrementarContadorDirect(@Param("id") Long id);

}