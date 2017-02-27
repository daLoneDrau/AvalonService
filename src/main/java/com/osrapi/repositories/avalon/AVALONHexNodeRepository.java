package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONHexNodeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONHexNodeRepository
extends CrudRepository<AVALONHexNodeEntity, Long> {
	/**
	 * Retrieves a list of hex nodes by their description.
	 * @param description the description
	 * @return {@link List}<{@link AVALONHexNodeEntity}>
	 */
	List<AVALONHexNodeEntity> findByDescription(String description);
	/**
	 * Retrieves a list of hex nodes by their flag.
	 * @param flag the flag
	 * @return {@link List}<{@link AVALONHexNodeEntity}>
	 */
	List<AVALONHexNodeEntity> findByFlag(Long flag);
	/**
	 * Retrieves a list of hex nodes by their name.
	 * @param name the name
	 * @return {@link List}<{@link AVALONHexNodeEntity}>
	 */
	List<AVALONHexNodeEntity> findByName(String name);
}
