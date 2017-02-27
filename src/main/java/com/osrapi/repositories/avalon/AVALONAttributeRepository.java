package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONAttributeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONAttributeRepository
extends CrudRepository<AVALONAttributeEntity, Long> {
	/**
	 * Retrieves a list of attributes by their code.
	 * @param code the code
	 * @return {@link List}<{@link AVALONAttributeEntity}>
	 */
	List<AVALONAttributeEntity> findByCode(String code);
	/**
	 * Retrieves a list of attributes by their description.
	 * @param description the description
	 * @return {@link List}<{@link AVALONAttributeEntity}>
	 */
	List<AVALONAttributeEntity> findByDescription(String description);
	/**
	 * Retrieves a list of attributes by their name.
	 * @param name the name
	 * @return {@link List}<{@link AVALONAttributeEntity}>
	 */
	List<AVALONAttributeEntity> findByName(String name);
}
