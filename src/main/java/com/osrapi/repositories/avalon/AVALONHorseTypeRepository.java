package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONHorseTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONHorseTypeRepository
extends CrudRepository<AVALONHorseTypeEntity, Long> {
	/**
	 * Retrieves a list of horse types by their name.
	 * @param name the name
	 * @return {@link List}<{@link AVALONHorseTypeEntity}>
	 */
	List<AVALONHorseTypeEntity> findByName(String name);
}
