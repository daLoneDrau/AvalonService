package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONActionChitEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONActionChitRepository
extends CrudRepository<AVALONActionChitEntity, Long> {
	/**
	 * Retrieves a list of action chits by their fatigueAsterisk.
	 * @param fatigueAsterisk the fatigueAsterisk
	 * @return {@link List}<{@link AVALONActionChitEntity}>
	 */
	List<AVALONActionChitEntity> findByFatigueAsterisk(Long fatigueAsterisk);
}
