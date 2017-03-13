package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONIoPcDataEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONIoPcDataRepository
extends CrudRepository<AVALONIoPcDataEntity, Long> {
	/**
	 * Retrieves a list of io pc datas by their evaluation.
	 * @param evaluation the evaluation
	 * @return {@link List}<{@link AVALONIoPcDataEntity}>
	 */
	List<AVALONIoPcDataEntity> findByEvaluation(String evaluation);
	/**
	 * Retrieves a list of io pc datas by their glyph.
	 * @param glyph the glyph
	 * @return {@link List}<{@link AVALONIoPcDataEntity}>
	 */
	List<AVALONIoPcDataEntity> findByGlyph(String glyph);
	/**
	 * Retrieves a list of io pc datas by their gold.
	 * @param gold the gold
	 * @return {@link List}<{@link AVALONIoPcDataEntity}>
	 */
	List<AVALONIoPcDataEntity> findByGold(Float gold);
	/**
	 * Retrieves a list of io pc datas by their interfaceFlags.
	 * @param interfaceFlags the interfaceFlags
	 * @return {@link List}<{@link AVALONIoPcDataEntity}>
	 */
	List<AVALONIoPcDataEntity> findByInterfaceFlags(Long interfaceFlags);
	/**
	 * Retrieves a list of io pc datas by their name.
	 * @param name the name
	 * @return {@link List}<{@link AVALONIoPcDataEntity}>
	 */
	List<AVALONIoPcDataEntity> findByName(String name);
	/**
	 * Retrieves a list of io pc datas by their stageOneName.
	 * @param stageOneName the stageOneName
	 * @return {@link List}<{@link AVALONIoPcDataEntity}>
	 */
	List<AVALONIoPcDataEntity> findByStageOneName(String stageOneName);
	/**
	 * Retrieves a list of io pc datas by their stageOneSpells.
	 * @param stageOneSpells the stageOneSpells
	 * @return {@link List}<{@link AVALONIoPcDataEntity}>
	 */
	List<AVALONIoPcDataEntity> findByStageOneSpells(Long stageOneSpells);
	/**
	 * Retrieves a list of io pc datas by their stageTwoName.
	 * @param stageTwoName the stageTwoName
	 * @return {@link List}<{@link AVALONIoPcDataEntity}>
	 */
	List<AVALONIoPcDataEntity> findByStageTwoName(String stageTwoName);
	/**
	 * Retrieves a list of io pc datas by their stageTwoSpells.
	 * @param stageTwoSpells the stageTwoSpells
	 * @return {@link List}<{@link AVALONIoPcDataEntity}>
	 */
	List<AVALONIoPcDataEntity> findByStageTwoSpells(Long stageTwoSpells);
	/**
	 * Retrieves a list of io pc datas by their stageThreeName.
	 * @param stageThreeName the stageThreeName
	 * @return {@link List}<{@link AVALONIoPcDataEntity}>
	 */
	List<AVALONIoPcDataEntity> findByStageThreeName(String stageThreeName);
	/**
	 * Retrieves a list of io pc datas by their stageThreeSpells.
	 * @param stageThreeSpells the stageThreeSpells
	 * @return {@link List}<{@link AVALONIoPcDataEntity}>
	 */
	List<AVALONIoPcDataEntity> findByStageThreeSpells(Long stageThreeSpells);
	/**
	 * Retrieves a list of io pc datas by their stageFourSpells.
	 * @param stageFourSpells the stageFourSpells
	 * @return {@link List}<{@link AVALONIoPcDataEntity}>
	 */
	List<AVALONIoPcDataEntity> findByStageFourSpells(Long stageFourSpells);
}
