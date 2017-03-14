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

import com.osrapi.models.avalon.AVALONHexTileTypeEntity;

import com.osrapi.repositories.avalon.AVALONHexTileTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/hex_tile_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONHexTileTypeController {
    /** the static instance of {@link AVALONHexTileTypeController}. */
    private static AVALONHexTileTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONHexTileTypeController}
     */
    public static AVALONHexTileTypeController getInstance() {
        if (instance == null) {
            new AVALONHexTileTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONHexTileTypeRepository repository;
    /** Creates a new instance of {@link AVALONHexTileTypeController}. */
    public AVALONHexTileTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONHexTileTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONHexTileTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONHexTileTypeEntity>> getAll() {
        Iterator<AVALONHexTileTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONHexTileTypeEntity>> resources =
                new ArrayList<Resource<AVALONHexTileTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexTileTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONHexTileTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONHexTileTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONHexTileTypeEntity>> getById(
            @PathVariable final Long id) {
        AVALONHexTileTypeEntity entity = repository.findOne(id);
        List<Resource<AVALONHexTileTypeEntity>> resources =
                new ArrayList<Resource<AVALONHexTileTypeEntity>>();
        resources.add(getHexTileTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONHexTileTypeEntity}.
     * @param entity the {@link AVALONHexTileTypeEntity}
     * @return {@link Resource}<{@link AVALONHexTileTypeEntity}>
     */
    private Resource<AVALONHexTileTypeEntity> getHexTileTypeResource(
            final AVALONHexTileTypeEntity entity) {
        Resource<AVALONHexTileTypeEntity> resource =
                new Resource<AVALONHexTileTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONHexTileTypeEntity}s.
     * @param entities the list of {@link AVALONHexTileTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexTileTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONHexTileTypeEntity>> save(
            @RequestBody final List<AVALONHexTileTypeEntity> entities) {
        List<Resource<AVALONHexTileTypeEntity>> resources =
                new ArrayList<Resource<AVALONHexTileTypeEntity>>();
        Iterator<AVALONHexTileTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONHexTileTypeEntity}.
     * @param entity the {@link AVALONHexTileTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexTileTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONHexTileTypeEntity>> save(
            @RequestBody final AVALONHexTileTypeEntity entity) {
    
    
        AVALONHexTileTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexTileTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONHexTileTypeEntity} instance
     */
    private void setIdFromRepository(final AVALONHexTileTypeEntity entity) {
        List<AVALONHexTileTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONHexTileTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONHexTileTypeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONHexTileTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONHexTileTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONHexTileTypeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONHexTileTypeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONHexTileTypeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONHexTileTypeEntity}s.
     * @param entities the list of {@link AVALONHexTileTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexTileTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONHexTileTypeEntity>> update(
            @RequestBody final List<AVALONHexTileTypeEntity> entities) {
        List<Resource<AVALONHexTileTypeEntity>> resources = new ArrayList<Resource<AVALONHexTileTypeEntity>>();
        Iterator<AVALONHexTileTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONHexTileTypeEntity}.
     * @param entity the {@link AVALONHexTileTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexTileTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONHexTileTypeEntity>> update(
            @RequestBody final AVALONHexTileTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONHexTileTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexTileTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONHexTileTypeEntity}s that share a code.
     * @param code the hex_tile_type' code
     * @return {@link List}<{@link Resource}<{@link AVALONHexTileTypeEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexTileTypeEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<AVALONHexTileTypeEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<AVALONHexTileTypeEntity>> resources =
                new ArrayList<Resource<AVALONHexTileTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexTileTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
