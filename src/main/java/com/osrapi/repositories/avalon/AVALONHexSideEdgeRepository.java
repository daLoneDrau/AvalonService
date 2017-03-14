package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONHexSideEdgeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONHexSideEdgeRepository
extends CrudRepository<AVALONHexSideEdgeEntity, Long> {
	/**
	 * Retrieves a list of hex side edges by their side.
	 * @param side the side
	 * @return {@link List}<{@link AVALONHexSideEdgeEntity}>
	 */
	List<AVALONHexSideEdgeEntity> findBySide(Long side);
}
