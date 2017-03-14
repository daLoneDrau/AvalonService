package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONEquipmentItemModifierEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONEquipmentItemModifierRepository
extends CrudRepository<AVALONEquipmentItemModifierEntity, Long> {
	/**
	 * Retrieves a list of equipment item modifiers by their code.
	 * @param code the code
	 * @return {@link List}<{@link AVALONEquipmentItemModifierEntity}>
	 */
	List<AVALONEquipmentItemModifierEntity> findByCode(String code);
	/**
	 * Retrieves a list of equipment item modifiers by their percent.
	 * @param percent the percent
	 * @return {@link List}<{@link AVALONEquipmentItemModifierEntity}>
	 */
	List<AVALONEquipmentItemModifierEntity> findByPercent(Boolean percent);
	/**
	 * Retrieves a list of equipment item modifiers by their special.
	 * @param special the special
	 * @return {@link List}<{@link AVALONEquipmentItemModifierEntity}>
	 */
	List<AVALONEquipmentItemModifierEntity> findBySpecial(Long special);
	/**
	 * Retrieves a list of equipment item modifiers by their value.
	 * @param value the value
	 * @return {@link List}<{@link AVALONEquipmentItemModifierEntity}>
	 */
	List<AVALONEquipmentItemModifierEntity> findByValue(Float value);
}
