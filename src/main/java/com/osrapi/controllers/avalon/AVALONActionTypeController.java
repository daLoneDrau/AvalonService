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

import com.osrapi.models.avalon.AVALONActionTypeEntity;

import com.osrapi.repositories.avalon.AVALONActionTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/action_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONActionTypeController {
    /** the static instance of {@link AVALONActionTypeController}. */
    private static AVALONActionTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONActionTypeController}
     */
    public static AVALONActionTypeController getInstance() {
        if (instance == null) {
            new AVALONActionTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONActionTypeRepository repository;
    /** Creates a new instance of {@link AVALONActionTypeController}. */
    public AVALONActionTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONActionTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONActionTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONActionTypeEntity>> getAll() {
        Iterator<AVALONActionTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONActionTypeEntity>> resources =
                new ArrayList<Resource<AVALONActionTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getActionTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONActionTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONActionTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONActionTypeEntity>> getById(
            @PathVariable final Long id) {
        AVALONActionTypeEntity entity = repository.findOne(id);
        List<Resource<AVALONActionTypeEntity>> resources =
                new ArrayList<Resource<AVALONActionTypeEntity>>();
        resources.add(getActionTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONActionTypeEntity}.
     * @param entity the {@link AVALONActionTypeEntity}
     * @return {@link Resource}<{@link AVALONActionTypeEntity}>
     */
    private Resource<AVALONActionTypeEntity> getActionTypeResource(
            final AVALONActionTypeEntity entity) {
        Resource<AVALONActionTypeEntity> resource =
                new Resource<AVALONActionTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONActionTypeEntity}s.
     * @param entities the list of {@link AVALONActionTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONActionTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONActionTypeEntity>> save(
            @RequestBody final List<AVALONActionTypeEntity> entities) {
        List<Resource<AVALONActionTypeEntity>> resources =
                new ArrayList<Resource<AVALONActionTypeEntity>>();
        Iterator<AVALONActionTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONActionTypeEntity}.
     * @param entity the {@link AVALONActionTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONActionTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONActionTypeEntity>> save(
            @RequestBody final AVALONActionTypeEntity entity) {
    
    
        AVALONActionTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONActionTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONActionTypeEntity} instance
     */
    private void setIdFromRepository(final AVALONActionTypeEntity entity) {
        List<AVALONActionTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONActionTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONActionTypeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONActionTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONActionTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONActionTypeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONActionTypeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONActionTypeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONActionTypeEntity}s.
     * @param entities the list of {@link AVALONActionTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONActionTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONActionTypeEntity>> update(
            @RequestBody final List<AVALONActionTypeEntity> entities) {
        List<Resource<AVALONActionTypeEntity>> resources = new ArrayList<Resource<AVALONActionTypeEntity>>();
        Iterator<AVALONActionTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONActionTypeEntity}.
     * @param entity the {@link AVALONActionTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONActionTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONActionTypeEntity>> update(
            @RequestBody final AVALONActionTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONActionTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONActionTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONActionTypeEntity}s that share a name.
     * @param name the action_type' name
     * @return {@link List}<{@link Resource}<{@link AVALONActionTypeEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<AVALONActionTypeEntity>> getByName(
            @PathVariable final String name) {
        Iterator<AVALONActionTypeEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<AVALONActionTypeEntity>> resources =
                new ArrayList<Resource<AVALONActionTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getActionTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
