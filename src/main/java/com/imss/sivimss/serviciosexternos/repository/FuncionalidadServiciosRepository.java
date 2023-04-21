package com.imss.sivimss.serviciosexternos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imss.sivimss.serviciosexternos.model.entity.FuncionalidadServiciosEntity;

@Repository
public interface FuncionalidadServiciosRepository extends JpaRepository<FuncionalidadServiciosEntity, Integer> {

	Optional<FuncionalidadServiciosEntity> findByNombreAndEstatus(String nombre, Integer estatus);

	@Query(value = "SELECT svs.*   "
			+ "FROM SVC_ROL_FUNCIONALIDAD_PERMISO srfp   "
			+ "inner join SVC_ROL sr on sr.ID_ROL =srfp .ID_ROL    "
			+ "INNER join SVC_PERMISO sp on sp.ID_PERMISO = srfp .ID_PERMISO    "
			+ "INNER  join SVC_FUNCIONALIDAD svsf on svsf .ID_FUNCIONALIDAD = srfp .ID_FUNCIONALIDAD    "
			+ "INNER join SVC_FUNCIONALIDAD_SERVICIO svs on svs.ID_FUNCIONALIDAD = srfp .ID_FUNCIONALIDAD AND svs.ID_PERMISO = srfp .ID_PERMISO    "
			+ "where srfp .ID_ROL = :idRol and srfp .ID_FUNCIONALIDAD = :idFuncionalidad and srfp.CVE_ESTATUS = '1' and svs.NOM_SERVICIO = :servicio  ", nativeQuery = true)
	Optional<FuncionalidadServiciosEntity> buscarServicio(@Param("idRol") Integer idRol,
			@Param("idFuncionalidad") Integer idFuncionalidad, @Param("servicio") String servicio);

}
