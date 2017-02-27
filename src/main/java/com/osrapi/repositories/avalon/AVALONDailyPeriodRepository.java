package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONDailyPeriodEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONDailyPeriodRepository
extends CrudRepository<AVALONDailyPeriodEntity, Long> {
	/**
	 * Retrieves a list of daily periods by their name.
	 * @param name the name
	 * @return {@link List}<{@link AVALONDailyPeriodEntity}>
	 */
	List<AVALONDailyPeriodEntity> findByName(String name);
}
