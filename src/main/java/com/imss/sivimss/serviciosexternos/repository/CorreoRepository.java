package com.imss.sivimss.serviciosexternos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imss.sivimss.serviciosexternos.model.dto.CorreoResponse;
import com.imss.sivimss.serviciosexternos.model.entity.CorreoEntity;

public interface CorreoRepository extends JpaRepository<CorreoEntity, Integer> {

	@Query(value = "SELECT STC.ASUNTO AS ASUNTO,STC.CUERPO_CORREO AS cuerpoCorreo "
			+ " FROM SVC_TIPO_CORREO STC "
			+ " WHERE STC.TIPO_CORREO = ?1 "
			, nativeQuery = true)
	Optional<CorreoResponse> buscarCuerpoCorreo(String tipoCorreo);

}
