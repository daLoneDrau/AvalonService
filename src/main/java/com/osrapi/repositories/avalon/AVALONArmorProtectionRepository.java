package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONArmorProtectionEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONArmorProtectionRepository
extends CrudRepository<AVALONArmorProtectionEntity, Long> {
	/**
	 * Retrieves a list of armor protections by their name.
	 * @param name the name
	 * @return {@link List}<{@link AVALONArmorProtectionEntity}>
	 */
	List<AVALONArmorProtectionEntity> findByName(String name);
}
