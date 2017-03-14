package com.osrapi.controllers.avalon;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.osrapi.models.avalon.AVALONHexTerrainTypeEntity;

import com.osrapi.repositories.avalon.AVALONHexTerrainTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/hex_terrain_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONHexTerrainTypeController {
    /** the static instance of {@link AVALONHexTerrainTypeController}. */
    private static AVALONHexTerrainTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONHexTerrainTypeController}
     */
    public static AVALONHexTerrainTypeController getInstance() {
        if (instance == null) {
            new AVALONHexTerrainTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONHexTerrainTypeRepository repository;
    /** Creates a new instance of {@link AVALONHexTerrainTypeController}. */
    public AVALONHexTerrainTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONHexTerrainTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONHexTerrainTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONHexTerrainTypeEntity>> getAll() {
        Iterator<AVALONHexTerrainTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONHexTerrainTypeEntity>> resources =
                new ArrayList<Resource<AVALONHexTerrainTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexTerrainTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONHexTerrainTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONHexTerrainTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONHexTerrainTypeEntity>> getById(
            @PathVariable final Long id) {
        AVALONHexTerrainTypeEntity entity = repository.findOne(id);
        List<Resource<AVALONHexTerrainTypeEntity>> resources =
                new ArrayList<Resource<AVALONHexTerrainTypeEntity>>();
        resources.add(getHexTerrainTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONHexTerrainTypeEntity}.
     * @param entity the {@link AVALONHexTerrainTypeEntity}
     * @return {@link Resource}<{@link AVALONHexTerrainTypeEntity}>
     */
    private Resource<AVALONHexTerrainTypeEntity> getHexTerrainTypeResource(
            final AVALONHexTerrainTypeEntity entity) {
        Resource<AVALONHexTerrainTypeEntity> resource =
                new Resource<AVALONHexTerrainTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONHexTerrainTypeEntity}s.
     * @param entities the list of {@link AVALONHexTerrainTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexTerrainTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONHexTerrainTypeEntity>> save(
            @RequestBody final List<AVALONHexTerrainTypeEntity> entities) {
        List<Resource<AVALONHexTerrainTypeEntity>> resources =
                new ArrayList<Resource<AVALONHexTerrainTypeEntity>>();
        Iterator<AVALONHexTerrainTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONHexTerrainTypeEntity}.
     * @param entity the {@link AVALONHexTerrainTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexTerrainTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONHexTerrainTypeEntity>> save(
            @RequestBody final AVALONHexTerrainTypeEntity entity) {
    
    
        AVALONHexTerrainTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexTerrainTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONHexTerrainTypeEntity} instance
     */
    private void setIdFromRepository(final AVALONHexTerrainTypeEntity entity) {
        List<AVALONHexTerrainTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONHexTerrainTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONHexTerrainTypeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONHexTerrainTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONHexTerrainTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONHexTerrainTypeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONHexTerrainTypeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONHexTerrainTypeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONHexTerrainTypeEntity}s.
     * @param entities the list of {@link AVALONHexTerrainTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexTerrainTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONHexTerrainTypeEntity>> update(
            @RequestBody final List<AVALONHexTerrainTypeEntity> entities) {
        List<Resource<AVALONHexTerrainTypeEntity>> resources = new ArrayList<Resource<AVALONHexTerrainTypeEntity>>();
        Iterator<AVALONHexTerrainTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONHexTerrainTypeEntity}.
     * @param entity the {@link AVALONHexTerrainTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexTerrainTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONHexTerrainTypeEntity>> update(
            @RequestBody final AVALONHexTerrainTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONHexTerrainTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexTerrainTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONHexTerrainTypeEntity}s that share a code.
     * @param code the hex_terrain_type' code
     * @return {@link List}<{@link Resource}<{@link AVALONHexTerrainTypeEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexTerrainTypeEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<AVALONHexTerrainTypeEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<AVALONHexTerrainTypeEntity>> resources =
                new ArrayList<Resource<AVALONHexTerrainTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexTerrainTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
