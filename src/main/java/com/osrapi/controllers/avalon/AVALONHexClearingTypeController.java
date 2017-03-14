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

import com.osrapi.models.avalon.AVALONHexClearingTypeEntity;

import com.osrapi.repositories.avalon.AVALONHexClearingTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/hex_clearing_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONHexClearingTypeController {
    /** the static instance of {@link AVALONHexClearingTypeController}. */
    private static AVALONHexClearingTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONHexClearingTypeController}
     */
    public static AVALONHexClearingTypeController getInstance() {
        if (instance == null) {
            new AVALONHexClearingTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONHexClearingTypeRepository repository;
    /** Creates a new instance of {@link AVALONHexClearingTypeController}. */
    public AVALONHexClearingTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONHexClearingTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONHexClearingTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONHexClearingTypeEntity>> getAll() {
        Iterator<AVALONHexClearingTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONHexClearingTypeEntity>> resources =
                new ArrayList<Resource<AVALONHexClearingTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexClearingTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONHexClearingTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONHexClearingTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONHexClearingTypeEntity>> getById(
            @PathVariable final Long id) {
        AVALONHexClearingTypeEntity entity = repository.findOne(id);
        List<Resource<AVALONHexClearingTypeEntity>> resources =
                new ArrayList<Resource<AVALONHexClearingTypeEntity>>();
        resources.add(getHexClearingTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONHexClearingTypeEntity}.
     * @param entity the {@link AVALONHexClearingTypeEntity}
     * @return {@link Resource}<{@link AVALONHexClearingTypeEntity}>
     */
    private Resource<AVALONHexClearingTypeEntity> getHexClearingTypeResource(
            final AVALONHexClearingTypeEntity entity) {
        Resource<AVALONHexClearingTypeEntity> resource =
                new Resource<AVALONHexClearingTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONHexClearingTypeEntity}s.
     * @param entities the list of {@link AVALONHexClearingTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexClearingTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONHexClearingTypeEntity>> save(
            @RequestBody final List<AVALONHexClearingTypeEntity> entities) {
        List<Resource<AVALONHexClearingTypeEntity>> resources =
                new ArrayList<Resource<AVALONHexClearingTypeEntity>>();
        Iterator<AVALONHexClearingTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONHexClearingTypeEntity}.
     * @param entity the {@link AVALONHexClearingTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexClearingTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONHexClearingTypeEntity>> save(
            @RequestBody final AVALONHexClearingTypeEntity entity) {
    
    
        AVALONHexClearingTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexClearingTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONHexClearingTypeEntity} instance
     */
    private void setIdFromRepository(final AVALONHexClearingTypeEntity entity) {
        List<AVALONHexClearingTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONHexClearingTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONHexClearingTypeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONHexClearingTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONHexClearingTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONHexClearingTypeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONHexClearingTypeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONHexClearingTypeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONHexClearingTypeEntity}s.
     * @param entities the list of {@link AVALONHexClearingTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexClearingTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONHexClearingTypeEntity>> update(
            @RequestBody final List<AVALONHexClearingTypeEntity> entities) {
        List<Resource<AVALONHexClearingTypeEntity>> resources = new ArrayList<Resource<AVALONHexClearingTypeEntity>>();
        Iterator<AVALONHexClearingTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONHexClearingTypeEntity}.
     * @param entity the {@link AVALONHexClearingTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexClearingTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONHexClearingTypeEntity>> update(
            @RequestBody final AVALONHexClearingTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONHexClearingTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexClearingTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONHexClearingTypeEntity}s that share a code.
     * @param code the hex_clearing_type' code
     * @return {@link List}<{@link Resource}<{@link AVALONHexClearingTypeEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexClearingTypeEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<AVALONHexClearingTypeEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<AVALONHexClearingTypeEntity>> resources =
                new ArrayList<Resource<AVALONHexClearingTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexClearingTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
