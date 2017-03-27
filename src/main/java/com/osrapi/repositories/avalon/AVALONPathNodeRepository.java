package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONPathNodeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONPathNodeRepository
extends CrudRepository<AVALONPathNodeEntity, Long> {
	/**
	 * Retrieves a list of path nodes by their sortOrder.
	 * @param sortOrder the sortOrder
	 * @return {@link List}<{@link AVALONPathNodeEntity}>
	 */
	List<AVALONPathNodeEntity> findBySortOrder(Long sortOrder);
}
