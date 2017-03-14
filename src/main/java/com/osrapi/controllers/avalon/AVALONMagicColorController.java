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

import com.osrapi.models.avalon.AVALONMagicColorEntity;

import com.osrapi.repositories.avalon.AVALONMagicColorRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/magic_colors")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONMagicColorController {
    /** the static instance of {@link AVALONMagicColorController}. */
    private static AVALONMagicColorController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONMagicColorController}
     */
    public static AVALONMagicColorController getInstance() {
        if (instance == null) {
            new AVALONMagicColorController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONMagicColorRepository repository;
    /** Creates a new instance of {@link AVALONMagicColorController}. */
    public AVALONMagicColorController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONMagicColorEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONMagicColorEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONMagicColorEntity>> getAll() {
        Iterator<AVALONMagicColorEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONMagicColorEntity>> resources =
                new ArrayList<Resource<AVALONMagicColorEntity>>();
        while (iter.hasNext()) {
            resources.add(getMagicColorResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONMagicColorEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONMagicColorEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONMagicColorEntity>> getById(
            @PathVariable final Long id) {
        AVALONMagicColorEntity entity = repository.findOne(id);
        List<Resource<AVALONMagicColorEntity>> resources =
                new ArrayList<Resource<AVALONMagicColorEntity>>();
        resources.add(getMagicColorResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONMagicColorEntity}.
     * @param entity the {@link AVALONMagicColorEntity}
     * @return {@link Resource}<{@link AVALONMagicColorEntity}>
     */
    private Resource<AVALONMagicColorEntity> getMagicColorResource(
            final AVALONMagicColorEntity entity) {
        Resource<AVALONMagicColorEntity> resource =
                new Resource<AVALONMagicColorEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONMagicColorEntity}s.
     * @param entities the list of {@link AVALONMagicColorEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONMagicColorEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONMagicColorEntity>> save(
            @RequestBody final List<AVALONMagicColorEntity> entities) {
        List<Resource<AVALONMagicColorEntity>> resources =
                new ArrayList<Resource<AVALONMagicColorEntity>>();
        Iterator<AVALONMagicColorEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONMagicColorEntity}.
     * @param entity the {@link AVALONMagicColorEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONMagicColorEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONMagicColorEntity>> save(
            @RequestBody final AVALONMagicColorEntity entity) {
    
    
        AVALONMagicColorEntity savedEntity = repository.save(entity);
        List<Resource<AVALONMagicColorEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONMagicColorEntity} instance
     */
    private void setIdFromRepository(final AVALONMagicColorEntity entity) {
        List<AVALONMagicColorEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONMagicColorEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONMagicColorEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONMagicColorEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONMagicColorEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONMagicColorEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONMagicColorEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONMagicColorEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONMagicColorEntity}s.
     * @param entities the list of {@link AVALONMagicColorEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONMagicColorEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONMagicColorEntity>> update(
            @RequestBody final List<AVALONMagicColorEntity> entities) {
        List<Resource<AVALONMagicColorEntity>> resources = new ArrayList<Resource<AVALONMagicColorEntity>>();
        Iterator<AVALONMagicColorEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONMagicColorEntity}.
     * @param entity the {@link AVALONMagicColorEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONMagicColorEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONMagicColorEntity>> update(
            @RequestBody final AVALONMagicColorEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONMagicColorEntity savedEntity = repository.save(entity);
        List<Resource<AVALONMagicColorEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONMagicColorEntity}s that share a longName.
     * @param longName the magic_color' longName
     * @return {@link List}<{@link Resource}<{@link AVALONMagicColorEntity}>>
     */
    @RequestMapping(path = "long_name/{longName}",
            method = RequestMethod.GET)
    public List<Resource<AVALONMagicColorEntity>> getByLongName(
            @PathVariable final String longName) {
        Iterator<AVALONMagicColorEntity> iter = repository.findByLongName(longName)
                .iterator();
        List<Resource<AVALONMagicColorEntity>> resources =
                new ArrayList<Resource<AVALONMagicColorEntity>>();
        while (iter.hasNext()) {
            resources.add(getMagicColorResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONMagicColorEntity}s that share a name.
     * @param name the magic_color' name
     * @return {@link List}<{@link Resource}<{@link AVALONMagicColorEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<AVALONMagicColorEntity>> getByName(
            @PathVariable final String name) {
        Iterator<AVALONMagicColorEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<AVALONMagicColorEntity>> resources =
                new ArrayList<Resource<AVALONMagicColorEntity>>();
        while (iter.hasNext()) {
            resources.add(getMagicColorResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
