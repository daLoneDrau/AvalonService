package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONHexClearingEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONHexClearingRepository
extends CrudRepository<AVALONHexClearingEntity, Long> {
	/**
	 * Retrieves a list of hex clearings by their number.
	 * @param number the number
	 * @return {@link List}<{@link AVALONHexClearingEntity}>
	 */
	List<AVALONHexClearingEntity> findByNumber(Long number);
	/**
	 * Retrieves a list of hex clearings by their code.
	 * @param code the code
	 * @return {@link List}<{@link AVALONHexClearingEntity}>
	 */
	List<AVALONHexClearingEntity> findByCode(String code);
}
