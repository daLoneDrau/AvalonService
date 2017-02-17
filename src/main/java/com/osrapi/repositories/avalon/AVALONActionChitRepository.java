package com.osrapi.repositories.avalon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.avalon.AVALONActionChitEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface AVALONActionChitRepository
extends CrudRepository<AVALONActionChitEntity, Long> {
}
