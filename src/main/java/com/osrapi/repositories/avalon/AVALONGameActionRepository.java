package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONGameActionEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONGameActionRepository
extends CrudRepository<AVALONGameActionEntity, Long> {
	/**
	 * Retrieves a list of game actions by their name.
	 * @param name the name
	 * @return {@link List}<{@link AVALONGameActionEntity}>
	 */
	List<AVALONGameActionEntity> findByName(String name);
}
