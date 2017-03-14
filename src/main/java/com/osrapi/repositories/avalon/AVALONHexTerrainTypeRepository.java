package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONHexTerrainTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONHexTerrainTypeRepository
extends CrudRepository<AVALONHexTerrainTypeEntity, Long> {
	/**
	 * Retrieves a list of hex terrain types by their code.
	 * @param code the code
	 * @return {@link List}<{@link AVALONHexTerrainTypeEntity}>
	 */
	List<AVALONHexTerrainTypeEntity> findByCode(String code);
}
