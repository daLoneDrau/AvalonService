package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONHexTileTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONHexTileTypeRepository
extends CrudRepository<AVALONHexTileTypeEntity, Long> {
	/**
	 * Retrieves a list of hex tile types by their code.
	 * @param code the code
	 * @return {@link List}<{@link AVALONHexTileTypeEntity}>
	 */
	List<AVALONHexTileTypeEntity> findByCode(String code);
}
