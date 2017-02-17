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

import com.osrapi.models.avalon.AVALONAttributeEntity;

import com.osrapi.repositories.avalon.AVALONAttributeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/attributes")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONAttributeController {
    /** the static instance of {@link AVALONAttributeController}. */
    private static AVALONAttributeController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONAttributeController}
     */
    public static AVALONAttributeController getInstance() {
        if (instance == null) {
            new AVALONAttributeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONAttributeRepository repository;
    /** Creates a new instance of {@link AVALONAttributeController}. */
    public AVALONAttributeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONAttributeEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONAttributeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONAttributeEntity>> getAll() {
        Iterator<AVALONAttributeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONAttributeEntity>> resources =
                new ArrayList<Resource<AVALONAttributeEntity>>();
        while (iter.hasNext()) {
            resources.add(getAttributeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONAttributeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONAttributeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONAttributeEntity>> getById(
            @PathVariable final Long id) {
        AVALONAttributeEntity entity = repository.findOne(id);
        List<Resource<AVALONAttributeEntity>> resources =
                new ArrayList<Resource<AVALONAttributeEntity>>();
        resources.add(getAttributeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONAttributeEntity}.
     * @param entity the {@link AVALONAttributeEntity}
     * @return {@link Resource}<{@link AVALONAttributeEntity}>
     */
    private Resource<AVALONAttributeEntity> getAttributeResource(
            final AVALONAttributeEntity entity) {
        Resource<AVALONAttributeEntity> resource =
                new Resource<AVALONAttributeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONAttributeEntity}s.
     * @param entities the list of {@link AVALONAttributeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONAttributeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONAttributeEntity>> save(
            @RequestBody final List<AVALONAttributeEntity> entities) {
        List<Resource<AVALONAttributeEntity>> resources =
                new ArrayList<Resource<AVALONAttributeEntity>>();
        Iterator<AVALONAttributeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONAttributeEntity}.
     * @param entity the {@link AVALONAttributeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONAttributeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONAttributeEntity>> save(
            @RequestBody final AVALONAttributeEntity entity) {
    
    
        AVALONAttributeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONAttributeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONAttributeEntity} instance
     */
    private void setIdFromRepository(final AVALONAttributeEntity entity) {
        List<AVALONAttributeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONAttributeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONAttributeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONAttributeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONAttributeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONAttributeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONAttributeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONAttributeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONAttributeEntity}s.
     * @param entities the list of {@link AVALONAttributeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONAttributeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONAttributeEntity>> update(
            @RequestBody final List<AVALONAttributeEntity> entities) {
        List<Resource<AVALONAttributeEntity>> resources = new ArrayList<Resource<AVALONAttributeEntity>>();
        Iterator<AVALONAttributeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONAttributeEntity}.
     * @param entity the {@link AVALONAttributeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONAttributeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONAttributeEntity>> update(
            @RequestBody final AVALONAttributeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONAttributeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONAttributeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONAttributeEntity}s that share a code.
     * @param code the attribute' code
     * @return {@link List}<{@link Resource}<{@link AVALONAttributeEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<AVALONAttributeEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<AVALONAttributeEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<AVALONAttributeEntity>> resources =
                new ArrayList<Resource<AVALONAttributeEntity>>();
        while (iter.hasNext()) {
            resources.add(getAttributeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONAttributeEntity}s that share a description.
     * @param description the attribute' description
     * @return {@link List}<{@link Resource}<{@link AVALONAttributeEntity}>>
     */
    @RequestMapping(path = "description/{description}",
            method = RequestMethod.GET)
    public List<Resource<AVALONAttributeEntity>> getByDescription(
            @PathVariable final String description) {
        Iterator<AVALONAttributeEntity> iter = repository.findByDescription(description)
                .iterator();
        List<Resource<AVALONAttributeEntity>> resources =
                new ArrayList<Resource<AVALONAttributeEntity>>();
        while (iter.hasNext()) {
            resources.add(getAttributeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONAttributeEntity}s that share a name.
     * @param name the attribute' name
     * @return {@link List}<{@link Resource}<{@link AVALONAttributeEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<AVALONAttributeEntity>> getByName(
            @PathVariable final String name) {
        Iterator<AVALONAttributeEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<AVALONAttributeEntity>> resources =
                new ArrayList<Resource<AVALONAttributeEntity>>();
        while (iter.hasNext()) {
            resources.add(getAttributeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
