package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONAdvantageEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONAdvantageRepository
extends CrudRepository<AVALONAdvantageEntity, Long> {
	/**
	 * Retrieves a list of advantages by their description.
	 * @param description the description
	 * @return {@link List}<{@link AVALONAdvantageEntity}>
	 */
	List<AVALONAdvantageEntity> findByDescription(String description);
	/**
	 * Retrieves a list of advantages by their flag.
	 * @param flag the flag
	 * @return {@link List}<{@link AVALONAdvantageEntity}>
	 */
	List<AVALONAdvantageEntity> findByFlag(Long flag);
	/**
	 * Retrieves a list of advantages by their name.
	 * @param name the name
	 * @return {@link List}<{@link AVALONAdvantageEntity}>
	 */
	List<AVALONAdvantageEntity> findByName(String name);
}
