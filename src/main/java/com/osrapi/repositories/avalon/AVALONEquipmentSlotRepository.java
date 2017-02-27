package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONEquipmentSlotEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONEquipmentSlotRepository
extends CrudRepository<AVALONEquipmentSlotEntity, Long> {
	/**
	 * Retrieves a list of equipment slots by their code.
	 * @param code the code
	 * @return {@link List}<{@link AVALONEquipmentSlotEntity}>
	 */
	List<AVALONEquipmentSlotEntity> findByCode(String code);
	/**
	 * Retrieves a list of equipment slots by their value.
	 * @param value the value
	 * @return {@link List}<{@link AVALONEquipmentSlotEntity}>
	 */
	List<AVALONEquipmentSlotEntity> findByValue(Long value);
}
