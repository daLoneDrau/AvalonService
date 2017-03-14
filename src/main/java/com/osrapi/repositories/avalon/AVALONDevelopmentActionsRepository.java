package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONDevelopmentActionsEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONDevelopmentActionsRepository
extends CrudRepository<AVALONDevelopmentActionsEntity, Long> {
	/**
	 * Retrieves a list of development actionss by their quantity.
	 * @param quantity the quantity
	 * @return {@link List}<{@link AVALONDevelopmentActionsEntity}>
	 */
	List<AVALONDevelopmentActionsEntity> findByQuantity(Long quantity);
	/**
	 * Retrieves a list of development actionss by their code.
	 * @param code the code
	 * @return {@link List}<{@link AVALONDevelopmentActionsEntity}>
	 */
	List<AVALONDevelopmentActionsEntity> findByCode(String code);
}
