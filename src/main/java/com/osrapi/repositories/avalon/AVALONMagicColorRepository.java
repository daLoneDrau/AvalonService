package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONMagicColorEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONMagicColorRepository
extends CrudRepository<AVALONMagicColorEntity, Long> {
	/**
	 * Retrieves a list of magic colors by their longName.
	 * @param longName the longName
	 * @return {@link List}<{@link AVALONMagicColorEntity}>
	 */
	List<AVALONMagicColorEntity> findByLongName(String longName);
	/**
	 * Retrieves a list of magic colors by their shortName.
	 * @param shortName the shortName
	 * @return {@link List}<{@link AVALONMagicColorEntity}>
	 */
	List<AVALONMagicColorEntity> findByShortName(String shortName);
}
