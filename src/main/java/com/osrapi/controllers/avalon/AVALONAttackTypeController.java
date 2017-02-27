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

import com.osrapi.models.avalon.AVALONAttackTypeEntity;

import com.osrapi.repositories.avalon.AVALONAttackTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/attack_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONAttackTypeController {
    /** the static instance of {@link AVALONAttackTypeController}. */
    private static AVALONAttackTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONAttackTypeController}
     */
    public static AVALONAttackTypeController getInstance() {
        if (instance == null) {
            new AVALONAttackTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONAttackTypeRepository repository;
    /** Creates a new instance of {@link AVALONAttackTypeController}. */
    public AVALONAttackTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONAttackTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONAttackTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONAttackTypeEntity>> getAll() {
        Iterator<AVALONAttackTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONAttackTypeEntity>> resources =
                new ArrayList<Resource<AVALONAttackTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getAttackTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONAttackTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONAttackTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONAttackTypeEntity>> getById(
            @PathVariable final Long id) {
        AVALONAttackTypeEntity entity = repository.findOne(id);
        List<Resource<AVALONAttackTypeEntity>> resources =
                new ArrayList<Resource<AVALONAttackTypeEntity>>();
        resources.add(getAttackTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONAttackTypeEntity}.
     * @param entity the {@link AVALONAttackTypeEntity}
     * @return {@link Resource}<{@link AVALONAttackTypeEntity}>
     */
    private Resource<AVALONAttackTypeEntity> getAttackTypeResource(
            final AVALONAttackTypeEntity entity) {
        Resource<AVALONAttackTypeEntity> resource =
                new Resource<AVALONAttackTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONAttackTypeEntity}s.
     * @param entities the list of {@link AVALONAttackTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONAttackTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONAttackTypeEntity>> save(
            @RequestBody final List<AVALONAttackTypeEntity> entities) {
        List<Resource<AVALONAttackTypeEntity>> resources =
                new ArrayList<Resource<AVALONAttackTypeEntity>>();
        Iterator<AVALONAttackTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONAttackTypeEntity}.
     * @param entity the {@link AVALONAttackTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONAttackTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONAttackTypeEntity>> save(
            @RequestBody final AVALONAttackTypeEntity entity) {
    
    
        AVALONAttackTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONAttackTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONAttackTypeEntity} instance
     */
    private void setIdFromRepository(final AVALONAttackTypeEntity entity) {
        List<AVALONAttackTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONAttackTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONAttackTypeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONAttackTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONAttackTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONAttackTypeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONAttackTypeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONAttackTypeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONAttackTypeEntity}s.
     * @param entities the list of {@link AVALONAttackTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONAttackTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONAttackTypeEntity>> update(
            @RequestBody final List<AVALONAttackTypeEntity> entities) {
        List<Resource<AVALONAttackTypeEntity>> resources = new ArrayList<Resource<AVALONAttackTypeEntity>>();
        Iterator<AVALONAttackTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONAttackTypeEntity}.
     * @param entity the {@link AVALONAttackTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONAttackTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONAttackTypeEntity>> update(
            @RequestBody final AVALONAttackTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONAttackTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONAttackTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONAttackTypeEntity}s that share a name.
     * @param name the attack_type' name
     * @return {@link List}<{@link Resource}<{@link AVALONAttackTypeEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<AVALONAttackTypeEntity>> getByName(
            @PathVariable final String name) {
        Iterator<AVALONAttackTypeEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<AVALONAttackTypeEntity>> resources =
                new ArrayList<Resource<AVALONAttackTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getAttackTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
