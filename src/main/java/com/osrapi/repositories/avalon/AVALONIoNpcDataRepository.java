package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONIoNpcDataEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONIoNpcDataRepository
extends CrudRepository<AVALONIoNpcDataEntity, Long> {
	/**
	 * Retrieves a list of io npc datas by their alertedAttackSpeed.
	 * @param alertedAttackSpeed the alertedAttackSpeed
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByAlertedAttackSpeed(Long alertedAttackSpeed);
	/**
	 * Retrieves a list of io npc datas by their alertedAttackStars.
	 * @param alertedAttackStars the alertedAttackStars
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByAlertedAttackStars(Long alertedAttackStars);
	/**
	 * Retrieves a list of io npc datas by their alertedMove.
	 * @param alertedMove the alertedMove
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByAlertedMove(Long alertedMove);
	/**
	 * Retrieves a list of io npc datas by their fameBounty.
	 * @param fameBounty the fameBounty
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByFameBounty(Long fameBounty);
	/**
	 * Retrieves a list of io npc datas by their goldBounty.
	 * @param goldBounty the goldBounty
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByGoldBounty(Long goldBounty);
	/**
	 * Retrieves a list of io npc datas by their internalScript.
	 * @param internalScript the internalScript
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByInternalScript(String internalScript);
	/**
	 * Retrieves a list of io npc datas by their name.
	 * @param name the name
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByName(String name);
	/**
	 * Retrieves a list of io npc datas by their naturalWeaponLength.
	 * @param naturalWeaponLength the naturalWeaponLength
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByNaturalWeaponLength(Long naturalWeaponLength);
	/**
	 * Retrieves a list of io npc datas by their notoriety.
	 * @param notoriety the notoriety
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByNotoriety(Long notoriety);
	/**
	 * Retrieves a list of io npc datas by their npcFlags.
	 * @param npcFlags the npcFlags
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByNpcFlags(Long npcFlags);
	/**
	 * Retrieves a list of io npc datas by their title.
	 * @param title the title
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByTitle(String title);
	/**
	 * Retrieves a list of io npc datas by their unalertedAttackSpeed.
	 * @param unalertedAttackSpeed the unalertedAttackSpeed
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByUnalertedAttackSpeed(Long unalertedAttackSpeed);
	/**
	 * Retrieves a list of io npc datas by their unalertedAttackStars.
	 * @param unalertedAttackStars the unalertedAttackStars
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByUnalertedAttackStars(Long unalertedAttackStars);
	/**
	 * Retrieves a list of io npc datas by their unalertedMove.
	 * @param unalertedMove the unalertedMove
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByUnalertedMove(Long unalertedMove);
	/**
	 * Retrieves a list of io npc datas by their wage.
	 * @param wage the wage
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByWage(Long wage);
}
