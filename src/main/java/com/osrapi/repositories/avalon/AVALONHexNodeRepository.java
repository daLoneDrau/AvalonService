package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONHexNodeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONHexNodeRepository
extends CrudRepository<AVALONHexNodeEntity, Long> {
	/**
	 * Retrieves a list of hex nodes by their code.
	 * @param code the code
	 * @return {@link List}<{@link AVALONHexNodeEntity}>
	 */
	List<AVALONHexNodeEntity> findByCode(String code);
}
