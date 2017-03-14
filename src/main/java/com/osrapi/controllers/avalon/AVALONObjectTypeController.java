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

import com.osrapi.models.avalon.AVALONObjectTypeEntity;

import com.osrapi.repositories.avalon.AVALONObjectTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/object_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONObjectTypeController {
    /** the static instance of {@link AVALONObjectTypeController}. */
    private static AVALONObjectTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONObjectTypeController}
     */
    public static AVALONObjectTypeController getInstance() {
        if (instance == null) {
            new AVALONObjectTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONObjectTypeRepository repository;
    /** Creates a new instance of {@link AVALONObjectTypeController}. */
    public AVALONObjectTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONObjectTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONObjectTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONObjectTypeEntity>> getAll() {
        Iterator<AVALONObjectTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONObjectTypeEntity>> resources =
                new ArrayList<Resource<AVALONObjectTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getObjectTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONObjectTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONObjectTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONObjectTypeEntity>> getById(
            @PathVariable final Long id) {
        AVALONObjectTypeEntity entity = repository.findOne(id);
        List<Resource<AVALONObjectTypeEntity>> resources =
                new ArrayList<Resource<AVALONObjectTypeEntity>>();
        resources.add(getObjectTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONObjectTypeEntity}.
     * @param entity the {@link AVALONObjectTypeEntity}
     * @return {@link Resource}<{@link AVALONObjectTypeEntity}>
     */
    private Resource<AVALONObjectTypeEntity> getObjectTypeResource(
            final AVALONObjectTypeEntity entity) {
        Resource<AVALONObjectTypeEntity> resource =
                new Resource<AVALONObjectTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONObjectTypeEntity}s.
     * @param entities the list of {@link AVALONObjectTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONObjectTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONObjectTypeEntity>> save(
            @RequestBody final List<AVALONObjectTypeEntity> entities) {
        List<Resource<AVALONObjectTypeEntity>> resources =
                new ArrayList<Resource<AVALONObjectTypeEntity>>();
        Iterator<AVALONObjectTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONObjectTypeEntity}.
     * @param entity the {@link AVALONObjectTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONObjectTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONObjectTypeEntity>> save(
            @RequestBody final AVALONObjectTypeEntity entity) {
    
    
        AVALONObjectTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONObjectTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONObjectTypeEntity} instance
     */
    private void setIdFromRepository(final AVALONObjectTypeEntity entity) {
        List<AVALONObjectTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONObjectTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONObjectTypeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONObjectTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONObjectTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONObjectTypeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONObjectTypeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONObjectTypeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONObjectTypeEntity}s.
     * @param entities the list of {@link AVALONObjectTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONObjectTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONObjectTypeEntity>> update(
            @RequestBody final List<AVALONObjectTypeEntity> entities) {
        List<Resource<AVALONObjectTypeEntity>> resources = new ArrayList<Resource<AVALONObjectTypeEntity>>();
        Iterator<AVALONObjectTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONObjectTypeEntity}.
     * @param entity the {@link AVALONObjectTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONObjectTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONObjectTypeEntity>> update(
            @RequestBody final AVALONObjectTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONObjectTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONObjectTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONObjectTypeEntity}s that share a code.
     * @param code the object_type' code
     * @return {@link List}<{@link Resource}<{@link AVALONObjectTypeEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<AVALONObjectTypeEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<AVALONObjectTypeEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<AVALONObjectTypeEntity>> resources =
                new ArrayList<Resource<AVALONObjectTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getObjectTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONObjectTypeEntity}s that share a flag.
     * @param flag the object_type' flag
     * @return {@link List}<{@link Resource}<{@link AVALONObjectTypeEntity}>>
     */
    @RequestMapping(path = "flag/{flag}",
            method = RequestMethod.GET)
    public List<Resource<AVALONObjectTypeEntity>> getByFlag(
            @PathVariable final Long flag) {
        Iterator<AVALONObjectTypeEntity> iter = repository.findByFlag(flag)
                .iterator();
        List<Resource<AVALONObjectTypeEntity>> resources =
                new ArrayList<Resource<AVALONObjectTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getObjectTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
