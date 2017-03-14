package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONHexClearingTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONHexClearingTypeRepository
extends CrudRepository<AVALONHexClearingTypeEntity, Long> {
	/**
	 * Retrieves a list of hex clearing types by their code.
	 * @param code the code
	 * @return {@link List}<{@link AVALONHexClearingTypeEntity}>
	 */
	List<AVALONHexClearingTypeEntity> findByCode(String code);
}
