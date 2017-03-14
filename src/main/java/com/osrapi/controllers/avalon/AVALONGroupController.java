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

import com.osrapi.models.avalon.AVALONGroupEntity;

import com.osrapi.repositories.avalon.AVALONGroupRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/groups")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONGroupController {
    /** the static instance of {@link AVALONGroupController}. */
    private static AVALONGroupController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONGroupController}
     */
    public static AVALONGroupController getInstance() {
        if (instance == null) {
            new AVALONGroupController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONGroupRepository repository;
    /** Creates a new instance of {@link AVALONGroupController}. */
    public AVALONGroupController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONGroupEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONGroupEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONGroupEntity>> getAll() {
        Iterator<AVALONGroupEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONGroupEntity>> resources =
                new ArrayList<Resource<AVALONGroupEntity>>();
        while (iter.hasNext()) {
            resources.add(getGroupResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONGroupEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONGroupEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONGroupEntity>> getById(
            @PathVariable final Long id) {
        AVALONGroupEntity entity = repository.findOne(id);
        List<Resource<AVALONGroupEntity>> resources =
                new ArrayList<Resource<AVALONGroupEntity>>();
        resources.add(getGroupResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONGroupEntity}.
     * @param entity the {@link AVALONGroupEntity}
     * @return {@link Resource}<{@link AVALONGroupEntity}>
     */
    private Resource<AVALONGroupEntity> getGroupResource(
            final AVALONGroupEntity entity) {
        Resource<AVALONGroupEntity> resource =
                new Resource<AVALONGroupEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONGroupEntity}s.
     * @param entities the list of {@link AVALONGroupEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONGroupEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONGroupEntity>> save(
            @RequestBody final List<AVALONGroupEntity> entities) {
        List<Resource<AVALONGroupEntity>> resources =
                new ArrayList<Resource<AVALONGroupEntity>>();
        Iterator<AVALONGroupEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONGroupEntity}.
     * @param entity the {@link AVALONGroupEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONGroupEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONGroupEntity>> save(
            @RequestBody final AVALONGroupEntity entity) {
    
    
        AVALONGroupEntity savedEntity = repository.save(entity);
        List<Resource<AVALONGroupEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONGroupEntity} instance
     */
    private void setIdFromRepository(final AVALONGroupEntity entity) {
        List<AVALONGroupEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONGroupEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONGroupEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONGroupEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONGroupEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONGroupEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONGroupEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONGroupEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONGroupEntity}s.
     * @param entities the list of {@link AVALONGroupEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONGroupEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONGroupEntity>> update(
            @RequestBody final List<AVALONGroupEntity> entities) {
        List<Resource<AVALONGroupEntity>> resources = new ArrayList<Resource<AVALONGroupEntity>>();
        Iterator<AVALONGroupEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONGroupEntity}.
     * @param entity the {@link AVALONGroupEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONGroupEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONGroupEntity>> update(
            @RequestBody final AVALONGroupEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONGroupEntity savedEntity = repository.save(entity);
        List<Resource<AVALONGroupEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONGroupEntity}s that share a name.
     * @param name the group' name
     * @return {@link List}<{@link Resource}<{@link AVALONGroupEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<AVALONGroupEntity>> getByName(
            @PathVariable final String name) {
        Iterator<AVALONGroupEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<AVALONGroupEntity>> resources =
                new ArrayList<Resource<AVALONGroupEntity>>();
        while (iter.hasNext()) {
            resources.add(getGroupResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
