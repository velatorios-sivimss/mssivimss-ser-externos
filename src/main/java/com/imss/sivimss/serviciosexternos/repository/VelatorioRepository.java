package com.imss.sivimss.serviciosexternos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imss.sivimss.serviciosexternos.model.dto.VelatorioResponse;
import com.imss.sivimss.serviciosexternos.model.entity.VelatorioEntity;

public interface VelatorioRepository extends JpaRepository<VelatorioEntity, Integer> {

	@Query(value = "SELECT ID_VELATORIO as idVelatorio, DES_VELATORIO as nomVelatorio FROM SVC_VELATORIO "
			+ " WHERE ID_DELEGACION = ?1 "
			+ " ORDER BY ID_VELATORIO ASC", nativeQuery = true)
	Optional<List<VelatorioResponse>> buscarVelatorio(Integer idDelegacion);

}
