package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONArmorConditionEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONArmorConditionRepository
extends CrudRepository<AVALONArmorConditionEntity, Long> {
	/**
	 * Retrieves a list of armor conditions by their name.
	 * @param name the name
	 * @return {@link List}<{@link AVALONArmorConditionEntity}>
	 */
	List<AVALONArmorConditionEntity> findByName(String name);
}
