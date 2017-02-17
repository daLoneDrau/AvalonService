package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONEquipmentElementTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONEquipmentElementTypeRepository
extends CrudRepository<AVALONEquipmentElementTypeEntity, Long> {
	/**
	 * Retrieves a list of equipment element types by their code.
	 * @param code the code
	 * @return {@link List}<{@link AVALONEquipmentElementTypeEntity}>
	 */
	List<AVALONEquipmentElementTypeEntity> findByCode(String code);
	/**
	 * Retrieves a list of equipment element types by their value.
	 * @param value the value
	 * @return {@link List}<{@link AVALONEquipmentElementTypeEntity}>
	 */
	List<AVALONEquipmentElementTypeEntity> findByValue(Long value);
}
