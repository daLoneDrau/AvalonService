package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONVector3Entity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONVector3Repository
extends CrudRepository<AVALONVector3Entity, Long> {
	/**
	 * Retrieves a list of vector3s by their x.
	 * @param x the x
	 * @return {@link List}<{@link AVALONVector3Entity}>
	 */
	List<AVALONVector3Entity> findByX(Long x);
	/**
	 * Retrieves a list of vector3s by their y.
	 * @param y the y
	 * @return {@link List}<{@link AVALONVector3Entity}>
	 */
	List<AVALONVector3Entity> findByY(Long y);
	/**
	 * Retrieves a list of vector3s by their z.
	 * @param z the z
	 * @return {@link List}<{@link AVALONVector3Entity}>
	 */
	List<AVALONVector3Entity> findByZ(Long z);
	/**
	 * Retrieves a list of vector3s by their code.
	 * @param code the code
	 * @return {@link List}<{@link AVALONVector3Entity}>
	 */
	List<AVALONVector3Entity> findByCode(String code);
}
