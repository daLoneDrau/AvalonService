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

import com.osrapi.models.avalon.AVALONNodeTypeEntity;

import com.osrapi.repositories.avalon.AVALONNodeTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/node_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONNodeTypeController {
    /** the static instance of {@link AVALONNodeTypeController}. */
    private static AVALONNodeTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONNodeTypeController}
     */
    public static AVALONNodeTypeController getInstance() {
        if (instance == null) {
            new AVALONNodeTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONNodeTypeRepository repository;
    /** Creates a new instance of {@link AVALONNodeTypeController}. */
    public AVALONNodeTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONNodeTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONNodeTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONNodeTypeEntity>> getAll() {
        Iterator<AVALONNodeTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONNodeTypeEntity>> resources =
                new ArrayList<Resource<AVALONNodeTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getNodeTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONNodeTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONNodeTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONNodeTypeEntity>> getById(
            @PathVariable final Long id) {
        AVALONNodeTypeEntity entity = repository.findOne(id);
        List<Resource<AVALONNodeTypeEntity>> resources =
                new ArrayList<Resource<AVALONNodeTypeEntity>>();
        resources.add(getNodeTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONNodeTypeEntity}.
     * @param entity the {@link AVALONNodeTypeEntity}
     * @return {@link Resource}<{@link AVALONNodeTypeEntity}>
     */
    private Resource<AVALONNodeTypeEntity> getNodeTypeResource(
            final AVALONNodeTypeEntity entity) {
        Resource<AVALONNodeTypeEntity> resource =
                new Resource<AVALONNodeTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONNodeTypeEntity}s.
     * @param entities the list of {@link AVALONNodeTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONNodeTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONNodeTypeEntity>> save(
            @RequestBody final List<AVALONNodeTypeEntity> entities) {
        List<Resource<AVALONNodeTypeEntity>> resources =
                new ArrayList<Resource<AVALONNodeTypeEntity>>();
        Iterator<AVALONNodeTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONNodeTypeEntity}.
     * @param entity the {@link AVALONNodeTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONNodeTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONNodeTypeEntity>> save(
            @RequestBody final AVALONNodeTypeEntity entity) {
    
    
        AVALONNodeTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONNodeTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONNodeTypeEntity} instance
     */
    private void setIdFromRepository(final AVALONNodeTypeEntity entity) {
        List<AVALONNodeTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONNodeTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONNodeTypeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONNodeTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONNodeTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONNodeTypeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONNodeTypeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONNodeTypeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONNodeTypeEntity}s.
     * @param entities the list of {@link AVALONNodeTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONNodeTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONNodeTypeEntity>> update(
            @RequestBody final List<AVALONNodeTypeEntity> entities) {
        List<Resource<AVALONNodeTypeEntity>> resources = new ArrayList<Resource<AVALONNodeTypeEntity>>();
        Iterator<AVALONNodeTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONNodeTypeEntity}.
     * @param entity the {@link AVALONNodeTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONNodeTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONNodeTypeEntity>> update(
            @RequestBody final AVALONNodeTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONNodeTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONNodeTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONNodeTypeEntity}s that share a code.
     * @param code the node_type' code
     * @return {@link List}<{@link Resource}<{@link AVALONNodeTypeEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<AVALONNodeTypeEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<AVALONNodeTypeEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<AVALONNodeTypeEntity>> resources =
                new ArrayList<Resource<AVALONNodeTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getNodeTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONNodeTypeEntity}s that share a description.
     * @param description the node_type' description
     * @return {@link List}<{@link Resource}<{@link AVALONNodeTypeEntity}>>
     */
    @RequestMapping(path = "description/{description}",
            method = RequestMethod.GET)
    public List<Resource<AVALONNodeTypeEntity>> getByDescription(
            @PathVariable final String description) {
        Iterator<AVALONNodeTypeEntity> iter = repository.findByDescription(description)
                .iterator();
        List<Resource<AVALONNodeTypeEntity>> resources =
                new ArrayList<Resource<AVALONNodeTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getNodeTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
