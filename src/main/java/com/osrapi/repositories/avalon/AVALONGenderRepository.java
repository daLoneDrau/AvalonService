package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONGenderEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONGenderRepository
extends CrudRepository<AVALONGenderEntity, Long> {
	/**
	 * Retrieves a list of genders by their description.
	 * @param description the description
	 * @return {@link List}<{@link AVALONGenderEntity}>
	 */
	List<AVALONGenderEntity> findByDescription(String description);
	/**
	 * Retrieves a list of genders by their name.
	 * @param name the name
	 * @return {@link List}<{@link AVALONGenderEntity}>
	 */
	List<AVALONGenderEntity> findByName(String name);
}
