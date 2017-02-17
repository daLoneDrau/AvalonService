package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONGroupEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONGroupRepository
extends CrudRepository<AVALONGroupEntity, Long> {
	/**
	 * Retrieves a list of groups by their name.
	 * @param name the name
	 * @return {@link List}<{@link AVALONGroupEntity}>
	 */
	List<AVALONGroupEntity> findByName(String name);
}
