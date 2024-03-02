package com.fran.repository;

import com.fran.entity.Mensaje;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

	Optional<List<Mensaje>> findByRemitenteIdAndDestinatarioId(Long remitenteId, Long destinatarioId);
}