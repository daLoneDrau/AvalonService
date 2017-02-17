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
	 * Retrieves a list of io npc datas by their behavior.
	 * @param behavior the behavior
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByBehavior(Long behavior);
	/**
	 * Retrieves a list of io npc datas by their behaviorParam.
	 * @param behaviorParam the behaviorParam
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByBehaviorParam(Float behaviorParam);
	/**
	 * Retrieves a list of io npc datas by their climbCount.
	 * @param climbCount the climbCount
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByClimbCount(Float climbCount);
	/**
	 * Retrieves a list of io npc datas by their collidState.
	 * @param collidState the collidState
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByCollidState(Long collidState);
	/**
	 * Retrieves a list of io npc datas by their collidTime.
	 * @param collidTime the collidTime
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByCollidTime(Long collidTime);
	/**
	 * Retrieves a list of io npc datas by their critical.
	 * @param critical the critical
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByCritical(Float critical);
	/**
	 * Retrieves a list of io npc datas by their cut.
	 * @param cut the cut
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByCut(Boolean cut);
	/**
	 * Retrieves a list of io npc datas by their cuts.
	 * @param cuts the cuts
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByCuts(Long cuts);
	/**
	 * Retrieves a list of io npc datas by their damages.
	 * @param damages the damages
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByDamages(Float damages);
	/**
	 * Retrieves a list of io npc datas by their internalScript.
	 * @param internalScript the internalScript
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByInternalScript(String internalScript);
	/**
	 * Retrieves a list of io npc datas by their life.
	 * @param life the life
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByLife(Float life);
	/**
	 * Retrieves a list of io npc datas by their mana.
	 * @param mana the mana
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByMana(Float mana);
	/**
	 * Retrieves a list of io npc datas by their maxlife.
	 * @param maxlife the maxlife
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByMaxlife(Float maxlife);
	/**
	 * Retrieves a list of io npc datas by their maxmana.
	 * @param maxmana the maxmana
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByMaxmana(Float maxmana);
	/**
	 * Retrieves a list of io npc datas by their name.
	 * @param name the name
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByName(String name);
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
	 * Retrieves a list of io npc datas by their weapon.
	 * @param weapon the weapon
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByWeapon(String weapon);
	/**
	 * Retrieves a list of io npc datas by their xpvalue.
	 * @param xpvalue the xpvalue
	 * @return {@link List}<{@link AVALONIoNpcDataEntity}>
	 */
	List<AVALONIoNpcDataEntity> findByXpvalue(Long xpvalue);
}
