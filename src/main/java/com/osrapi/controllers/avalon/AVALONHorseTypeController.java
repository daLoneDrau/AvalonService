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

import com.osrapi.models.avalon.AVALONHorseTypeEntity;

import com.osrapi.repositories.avalon.AVALONHorseTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/horse_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONHorseTypeController {
    /** the static instance of {@link AVALONHorseTypeController}. */
    private static AVALONHorseTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONHorseTypeController}
     */
    public static AVALONHorseTypeController getInstance() {
        if (instance == null) {
            new AVALONHorseTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONHorseTypeRepository repository;
    /** Creates a new instance of {@link AVALONHorseTypeController}. */
    public AVALONHorseTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONHorseTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONHorseTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONHorseTypeEntity>> getAll() {
        Iterator<AVALONHorseTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONHorseTypeEntity>> resources =
                new ArrayList<Resource<AVALONHorseTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getHorseTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONHorseTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONHorseTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONHorseTypeEntity>> getById(
            @PathVariable final Long id) {
        AVALONHorseTypeEntity entity = repository.findOne(id);
        List<Resource<AVALONHorseTypeEntity>> resources =
                new ArrayList<Resource<AVALONHorseTypeEntity>>();
        resources.add(getHorseTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONHorseTypeEntity}.
     * @param entity the {@link AVALONHorseTypeEntity}
     * @return {@link Resource}<{@link AVALONHorseTypeEntity}>
     */
    private Resource<AVALONHorseTypeEntity> getHorseTypeResource(
            final AVALONHorseTypeEntity entity) {
        Resource<AVALONHorseTypeEntity> resource =
                new Resource<AVALONHorseTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONHorseTypeEntity}s.
     * @param entities the list of {@link AVALONHorseTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHorseTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONHorseTypeEntity>> save(
            @RequestBody final List<AVALONHorseTypeEntity> entities) {
        List<Resource<AVALONHorseTypeEntity>> resources =
                new ArrayList<Resource<AVALONHorseTypeEntity>>();
        Iterator<AVALONHorseTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONHorseTypeEntity}.
     * @param entity the {@link AVALONHorseTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHorseTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONHorseTypeEntity>> save(
            @RequestBody final AVALONHorseTypeEntity entity) {
    
    
        AVALONHorseTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHorseTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONHorseTypeEntity} instance
     */
    private void setIdFromRepository(final AVALONHorseTypeEntity entity) {
        List<AVALONHorseTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONHorseTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONHorseTypeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONHorseTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONHorseTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONHorseTypeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONHorseTypeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONHorseTypeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONHorseTypeEntity}s.
     * @param entities the list of {@link AVALONHorseTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHorseTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONHorseTypeEntity>> update(
            @RequestBody final List<AVALONHorseTypeEntity> entities) {
        List<Resource<AVALONHorseTypeEntity>> resources = new ArrayList<Resource<AVALONHorseTypeEntity>>();
        Iterator<AVALONHorseTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONHorseTypeEntity}.
     * @param entity the {@link AVALONHorseTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHorseTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONHorseTypeEntity>> update(
            @RequestBody final AVALONHorseTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONHorseTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHorseTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONHorseTypeEntity}s that share a name.
     * @param name the horse_type' name
     * @return {@link List}<{@link Resource}<{@link AVALONHorseTypeEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHorseTypeEntity>> getByName(
            @PathVariable final String name) {
        Iterator<AVALONHorseTypeEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<AVALONHorseTypeEntity>> resources =
                new ArrayList<Resource<AVALONHorseTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getHorseTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
