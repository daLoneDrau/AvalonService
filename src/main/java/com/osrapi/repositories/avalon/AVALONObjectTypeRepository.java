package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONObjectTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONObjectTypeRepository
extends CrudRepository<AVALONObjectTypeEntity, Long> {
	/**
	 * Retrieves a list of object types by their code.
	 * @param code the code
	 * @return {@link List}<{@link AVALONObjectTypeEntity}>
	 */
	List<AVALONObjectTypeEntity> findByCode(String code);
	/**
	 * Retrieves a list of object types by their flag.
	 * @param flag the flag
	 * @return {@link List}<{@link AVALONObjectTypeEntity}>
	 */
	List<AVALONObjectTypeEntity> findByFlag(Long flag);
}
