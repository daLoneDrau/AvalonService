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
	 * Retrieves a list of action chits by their speed.
	 * @param speed the speed
	 * @return {@link List}<{@link AVALONActionChitEntity}>
	 */
	List<AVALONActionChitEntity> findBySpeed(Long speed);
	/**
	 * Retrieves a list of action chits by their fatigueAsterisk.
	 * @param fatigueAsterisk the fatigueAsterisk
	 * @return {@link List}<{@link AVALONActionChitEntity}>
	 */
	List<AVALONActionChitEntity> findByFatigueAsterisk(Long fatigueAsterisk);
	/**
	 * Retrieves a list of action chits by their code.
	 * @param code the code
	 * @return {@link List}<{@link AVALONActionChitEntity}>
	 */
	List<AVALONActionChitEntity> findByCode(String code);
}
