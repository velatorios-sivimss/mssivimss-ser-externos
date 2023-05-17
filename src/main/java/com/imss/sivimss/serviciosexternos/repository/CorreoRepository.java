package com.imss.sivimss.serviciosexternos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imss.sivimss.serviciosexternos.model.dto.CorreoResponse;
import com.imss.sivimss.serviciosexternos.model.entity.CorreoEntity;

public interface CorreoRepository extends JpaRepository<CorreoEntity, Integer> {

	@Query(value = "SELECT stc.ASUNTO AS asunto,stc.CUERPO_CORREO AS cuerpoCorreo "
			+ " FROM svc_tipo_correo stc "
			+ " WHERE stc.TIPO_CORREO = ?1 "
			, nativeQuery = true)
	Optional<CorreoResponse> buscarCuerpoCorreo(String tipoCorreo);

}
