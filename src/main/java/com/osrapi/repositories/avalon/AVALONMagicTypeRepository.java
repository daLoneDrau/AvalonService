package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONMagicTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONMagicTypeRepository
extends CrudRepository<AVALONMagicTypeEntity, Long> {
	/**
	 * Retrieves a list of magic types by their code.
	 * @param code the code
	 * @return {@link List}<{@link AVALONMagicTypeEntity}>
	 */
	List<AVALONMagicTypeEntity> findByCode(String code);
	/**
	 * Retrieves a list of magic types by their spellName.
	 * @param spellName the spellName
	 * @return {@link List}<{@link AVALONMagicTypeEntity}>
	 */
	List<AVALONMagicTypeEntity> findBySpellName(String spellName);
	/**
	 * Retrieves a list of magic types by their title.
	 * @param title the title
	 * @return {@link List}<{@link AVALONMagicTypeEntity}>
	 */
	List<AVALONMagicTypeEntity> findByTitle(String title);
}
