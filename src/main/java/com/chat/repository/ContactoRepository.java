package com.chat.repository;

import com.chat.entity.Contacto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {

	boolean existsByNombre(String nombre);

	List<Contacto> findByNombreIn(List<String> nombres);
}