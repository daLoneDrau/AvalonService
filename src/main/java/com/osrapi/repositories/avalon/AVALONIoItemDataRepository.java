package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONIoItemDataEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONIoItemDataRepository
extends CrudRepository<AVALONIoItemDataEntity, Long> {
	/**
	 * Retrieves a list of io item datas by their alertedSpeed.
	 * @param alertedSpeed the alertedSpeed
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByAlertedSpeed(Long alertedSpeed);
	/**
	 * Retrieves a list of io item datas by their alertedSharpness.
	 * @param alertedSharpness the alertedSharpness
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByAlertedSharpness(Long alertedSharpness);
	/**
	 * Retrieves a list of io item datas by their count.
	 * @param count the count
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByCount(Long count);
	/**
	 * Retrieves a list of io item datas by their description.
	 * @param description the description
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByDescription(String description);
	/**
	 * Retrieves a list of io item datas by their fame.
	 * @param fame the fame
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByFame(Long fame);
	/**
	 * Retrieves a list of io item datas by their foodValue.
	 * @param foodValue the foodValue
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByFoodValue(Long foodValue);
	/**
	 * Retrieves a list of io item datas by their internalScript.
	 * @param internalScript the internalScript
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByInternalScript(String internalScript);
	/**
	 * Retrieves a list of io item datas by their leftRing.
	 * @param leftRing the leftRing
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByLeftRing(Boolean leftRing);
	/**
	 * Retrieves a list of io item datas by their length.
	 * @param length the length
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByLength(Long length);
	/**
	 * Retrieves a list of io item datas by their lightValue.
	 * @param lightValue the lightValue
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByLightValue(Long lightValue);
	/**
	 * Retrieves a list of io item datas by their maxOwned.
	 * @param maxOwned the maxOwned
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByMaxOwned(Long maxOwned);
	/**
	 * Retrieves a list of io item datas by their name.
	 * @param name the name
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByName(String name);
	/**
	 * Retrieves a list of io item datas by their notoriety.
	 * @param notoriety the notoriety
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByNotoriety(Long notoriety);
	/**
	 * Retrieves a list of io item datas by their price.
	 * @param price the price
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByPrice(Float price);
	/**
	 * Retrieves a list of io item datas by their priceDamaged.
	 * @param priceDamaged the priceDamaged
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByPriceDamaged(Float priceDamaged);
	/**
	 * Retrieves a list of io item datas by their priceDestroyed.
	 * @param priceDestroyed the priceDestroyed
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByPriceDestroyed(Float priceDestroyed);
	/**
	 * Retrieves a list of io item datas by their ringType.
	 * @param ringType the ringType
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByRingType(Long ringType);
	/**
	 * Retrieves a list of io item datas by their stackSize.
	 * @param stackSize the stackSize
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByStackSize(Long stackSize);
	/**
	 * Retrieves a list of io item datas by their stealValue.
	 * @param stealValue the stealValue
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByStealValue(Long stealValue);
	/**
	 * Retrieves a list of io item datas by their title.
	 * @param title the title
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByTitle(String title);
	/**
	 * Retrieves a list of io item datas by their unalertedSpeed.
	 * @param unalertedSpeed the unalertedSpeed
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByUnalertedSpeed(Long unalertedSpeed);
	/**
	 * Retrieves a list of io item datas by their unalertedSharpness.
	 * @param unalertedSharpness the unalertedSharpness
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByUnalertedSharpness(Long unalertedSharpness);
	/**
	 * Retrieves a list of io item datas by their weaponLength.
	 * @param weaponLength the weaponLength
	 * @return {@link List}<{@link AVALONIoItemDataEntity}>
	 */
	List<AVALONIoItemDataEntity> findByWeaponLength(Long weaponLength);
}
