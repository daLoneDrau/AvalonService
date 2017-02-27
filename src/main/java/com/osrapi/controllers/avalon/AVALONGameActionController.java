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

import com.osrapi.models.avalon.AVALONGameActionEntity;

import com.osrapi.repositories.avalon.AVALONGameActionRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/game_actions")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONGameActionController {
    /** the static instance of {@link AVALONGameActionController}. */
    private static AVALONGameActionController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONGameActionController}
     */
    public static AVALONGameActionController getInstance() {
        if (instance == null) {
            new AVALONGameActionController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONGameActionRepository repository;
    /** Creates a new instance of {@link AVALONGameActionController}. */
    public AVALONGameActionController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONGameActionEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONGameActionEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONGameActionEntity>> getAll() {
        Iterator<AVALONGameActionEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONGameActionEntity>> resources =
                new ArrayList<Resource<AVALONGameActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getGameActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONGameActionEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONGameActionEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONGameActionEntity>> getById(
            @PathVariable final Long id) {
        AVALONGameActionEntity entity = repository.findOne(id);
        List<Resource<AVALONGameActionEntity>> resources =
                new ArrayList<Resource<AVALONGameActionEntity>>();
        resources.add(getGameActionResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONGameActionEntity}.
     * @param entity the {@link AVALONGameActionEntity}
     * @return {@link Resource}<{@link AVALONGameActionEntity}>
     */
    private Resource<AVALONGameActionEntity> getGameActionResource(
            final AVALONGameActionEntity entity) {
        Resource<AVALONGameActionEntity> resource =
                new Resource<AVALONGameActionEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONGameActionEntity}s.
     * @param entities the list of {@link AVALONGameActionEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONGameActionEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONGameActionEntity>> save(
            @RequestBody final List<AVALONGameActionEntity> entities) {
        List<Resource<AVALONGameActionEntity>> resources =
                new ArrayList<Resource<AVALONGameActionEntity>>();
        Iterator<AVALONGameActionEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONGameActionEntity}.
     * @param entity the {@link AVALONGameActionEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONGameActionEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONGameActionEntity>> save(
            @RequestBody final AVALONGameActionEntity entity) {
    
    
        AVALONGameActionEntity savedEntity = repository.save(entity);
        List<Resource<AVALONGameActionEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONGameActionEntity} instance
     */
    private void setIdFromRepository(final AVALONGameActionEntity entity) {
        List<AVALONGameActionEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONGameActionEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONGameActionEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONGameActionEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONGameActionEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONGameActionEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONGameActionEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONGameActionEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONGameActionEntity}s.
     * @param entities the list of {@link AVALONGameActionEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONGameActionEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONGameActionEntity>> update(
            @RequestBody final List<AVALONGameActionEntity> entities) {
        List<Resource<AVALONGameActionEntity>> resources = new ArrayList<Resource<AVALONGameActionEntity>>();
        Iterator<AVALONGameActionEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONGameActionEntity}.
     * @param entity the {@link AVALONGameActionEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONGameActionEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONGameActionEntity>> update(
            @RequestBody final AVALONGameActionEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONGameActionEntity savedEntity = repository.save(entity);
        List<Resource<AVALONGameActionEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONGameActionEntity}s that share a name.
     * @param name the game_action' name
     * @return {@link List}<{@link Resource}<{@link AVALONGameActionEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<AVALONGameActionEntity>> getByName(
            @PathVariable final String name) {
        Iterator<AVALONGameActionEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<AVALONGameActionEntity>> resources =
                new ArrayList<Resource<AVALONGameActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getGameActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
