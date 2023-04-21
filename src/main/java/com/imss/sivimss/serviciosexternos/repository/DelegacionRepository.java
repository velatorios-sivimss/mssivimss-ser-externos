package com.imss.sivimss.serviciosexternos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imss.sivimss.serviciosexternos.model.dto.DelegacionResponse;
import com.imss.sivimss.serviciosexternos.model.entity.DelegacionEntity;

public interface DelegacionRepository extends JpaRepository<DelegacionEntity, Integer> {

	@Query(value = "SELECT ID_DELEGACION as idDelegacion, DES_DELEGACION as desDelegacion FROM SVC_DELEGACION ORDER BY ID_DELEGACION ASC", nativeQuery = true)
	Optional<List<DelegacionResponse>> buscarDelegacion();

}
