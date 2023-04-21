package com.imss.sivimss.serviciosexternos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imss.sivimss.serviciosexternos.model.dto.NivelesResponse;
import com.imss.sivimss.serviciosexternos.model.entity.NivelEntity;

public interface NivelRepository extends JpaRepository<NivelEntity, Integer> {

	@Query(value = "SELECT SV.ID_OFICINA AS idNivel, SV.DES_NIVELOFICINA AS desNivel FROM SVC_NIVEL_OFICINA SV ORDER BY SV.DES_NIVELOFICINA ASC ", nativeQuery = true)
	Optional<List<NivelesResponse>> obtenerNiveles();
}
