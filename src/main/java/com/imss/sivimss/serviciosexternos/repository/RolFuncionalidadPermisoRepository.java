package com.imss.sivimss.serviciosexternos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imss.sivimss.serviciosexternos.model.entity.RolFuncionalidadPermisoEntity;

public interface RolFuncionalidadPermisoRepository extends JpaRepository<RolFuncionalidadPermisoEntity, Integer> {

	Optional<List<RolFuncionalidadPermisoEntity>> findByRolAndFuncionalidadAndPermisosAndEstatus(Integer rol,
			Integer funcionalidad, Integer permisos, Integer estatus);
}
