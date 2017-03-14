package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONHexPathEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONHexPathRepository
extends CrudRepository<AVALONHexPathEntity, Long> {
	/**
	 * Retrieves a list of hex paths by their code.
	 * @param code the code
	 * @return {@link List}<{@link AVALONHexPathEntity}>
	 */
	List<AVALONHexPathEntity> findByCode(String code);
}
