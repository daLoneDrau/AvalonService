package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONActionTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONActionTypeRepository
extends CrudRepository<AVALONActionTypeEntity, Long> {
	/**
	 * Retrieves a list of action types by their name.
	 * @param name the name
	 * @return {@link List}<{@link AVALONActionTypeEntity}>
	 */
	List<AVALONActionTypeEntity> findByName(String name);
}
