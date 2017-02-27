package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONNodeTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONNodeTypeRepository
extends CrudRepository<AVALONNodeTypeEntity, Long> {
	/**
	 * Retrieves a list of node types by their code.
	 * @param code the code
	 * @return {@link List}<{@link AVALONNodeTypeEntity}>
	 */
	List<AVALONNodeTypeEntity> findByCode(String code);
	/**
	 * Retrieves a list of node types by their description.
	 * @param description the description
	 * @return {@link List}<{@link AVALONNodeTypeEntity}>
	 */
	List<AVALONNodeTypeEntity> findByDescription(String description);
}
