package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONAttackTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONAttackTypeRepository
extends CrudRepository<AVALONAttackTypeEntity, Long> {
	/**
	 * Retrieves a list of attack types by their name.
	 * @param name the name
	 * @return {@link List}<{@link AVALONAttackTypeEntity}>
	 */
	List<AVALONAttackTypeEntity> findByName(String name);
}
