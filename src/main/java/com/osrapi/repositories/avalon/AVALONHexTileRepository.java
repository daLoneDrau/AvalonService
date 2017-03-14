package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONHexTileEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONHexTileRepository
extends CrudRepository<AVALONHexTileEntity, Long> {
	/**
	 * Retrieves a list of hex tiles by their abbreviation.
	 * @param abbreviation the abbreviation
	 * @return {@link List}<{@link AVALONHexTileEntity}>
	 */
	List<AVALONHexTileEntity> findByAbbreviation(String abbreviation);
	/**
	 * Retrieves a list of hex tiles by their name.
	 * @param name the name
	 * @return {@link List}<{@link AVALONHexTileEntity}>
	 */
	List<AVALONHexTileEntity> findByName(String name);
}
