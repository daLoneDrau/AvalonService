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

import com.osrapi.models.avalon.AVALONVector3Entity;

import com.osrapi.repositories.avalon.AVALONVector3Repository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/vector3s")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONVector3Controller {
    /** the static instance of {@link AVALONVector3Controller}. */
    private static AVALONVector3Controller instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONVector3Controller}
     */
    public static AVALONVector3Controller getInstance() {
        if (instance == null) {
            new AVALONVector3Controller();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONVector3Repository repository;
    /** Creates a new instance of {@link AVALONVector3Controller}. */
    public AVALONVector3Controller() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONVector3Entity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONVector3Entity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONVector3Entity>> getAll() {
        Iterator<AVALONVector3Entity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONVector3Entity>> resources =
                new ArrayList<Resource<AVALONVector3Entity>>();
        while (iter.hasNext()) {
            resources.add(getVector3Resource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONVector3Entity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONVector3Entity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONVector3Entity>> getById(
            @PathVariable final Long id) {
        AVALONVector3Entity entity = repository.findOne(id);
        List<Resource<AVALONVector3Entity>> resources =
                new ArrayList<Resource<AVALONVector3Entity>>();
        resources.add(getVector3Resource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONVector3Entity}.
     * @param entity the {@link AVALONVector3Entity}
     * @return {@link Resource}<{@link AVALONVector3Entity}>
     */
    private Resource<AVALONVector3Entity> getVector3Resource(
            final AVALONVector3Entity entity) {
        Resource<AVALONVector3Entity> resource =
                new Resource<AVALONVector3Entity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONVector3Entity}s.
     * @param entities the list of {@link AVALONVector3Entity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONVector3Entity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONVector3Entity>> save(
            @RequestBody final List<AVALONVector3Entity> entities) {
        List<Resource<AVALONVector3Entity>> resources =
                new ArrayList<Resource<AVALONVector3Entity>>();
        Iterator<AVALONVector3Entity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONVector3Entity}.
     * @param entity the {@link AVALONVector3Entity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONVector3Entity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONVector3Entity>> save(
            @RequestBody final AVALONVector3Entity entity) {
    
    
        AVALONVector3Entity savedEntity = repository.save(entity);
        List<Resource<AVALONVector3Entity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONVector3Entity} instance
     */
    private void setIdFromRepository(final AVALONVector3Entity entity) {
        List<AVALONVector3Entity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONVector3Entity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONVector3Entity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONVector3Entity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONVector3Entity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONVector3Entity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONVector3Entity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONVector3Entity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONVector3Entity}s.
     * @param entities the list of {@link AVALONVector3Entity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONVector3Entity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONVector3Entity>> update(
            @RequestBody final List<AVALONVector3Entity> entities) {
        List<Resource<AVALONVector3Entity>> resources = new ArrayList<Resource<AVALONVector3Entity>>();
        Iterator<AVALONVector3Entity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONVector3Entity}.
     * @param entity the {@link AVALONVector3Entity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONVector3Entity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONVector3Entity>> update(
            @RequestBody final AVALONVector3Entity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONVector3Entity savedEntity = repository.save(entity);
        List<Resource<AVALONVector3Entity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONVector3Entity}s that share a x.
     * @param x the vector3' x
     * @return {@link List}<{@link Resource}<{@link AVALONVector3Entity}>>
     */
    @RequestMapping(path = "x/{x}",
            method = RequestMethod.GET)
    public List<Resource<AVALONVector3Entity>> getByX(
            @PathVariable final Long x) {
        Iterator<AVALONVector3Entity> iter = repository.findByX(x)
                .iterator();
        List<Resource<AVALONVector3Entity>> resources =
                new ArrayList<Resource<AVALONVector3Entity>>();
        while (iter.hasNext()) {
            resources.add(getVector3Resource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONVector3Entity}s that share a y.
     * @param y the vector3' y
     * @return {@link List}<{@link Resource}<{@link AVALONVector3Entity}>>
     */
    @RequestMapping(path = "y/{y}",
            method = RequestMethod.GET)
    public List<Resource<AVALONVector3Entity>> getByY(
            @PathVariable final Long y) {
        Iterator<AVALONVector3Entity> iter = repository.findByY(y)
                .iterator();
        List<Resource<AVALONVector3Entity>> resources =
                new ArrayList<Resource<AVALONVector3Entity>>();
        while (iter.hasNext()) {
            resources.add(getVector3Resource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONVector3Entity}s that share a z.
     * @param z the vector3' z
     * @return {@link List}<{@link Resource}<{@link AVALONVector3Entity}>>
     */
    @RequestMapping(path = "z/{z}",
            method = RequestMethod.GET)
    public List<Resource<AVALONVector3Entity>> getByZ(
            @PathVariable final Long z) {
        Iterator<AVALONVector3Entity> iter = repository.findByZ(z)
                .iterator();
        List<Resource<AVALONVector3Entity>> resources =
                new ArrayList<Resource<AVALONVector3Entity>>();
        while (iter.hasNext()) {
            resources.add(getVector3Resource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONVector3Entity}s that share a code.
     * @param code the vector3' code
     * @return {@link List}<{@link Resource}<{@link AVALONVector3Entity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<AVALONVector3Entity>> getByCode(
            @PathVariable final String code) {
        Iterator<AVALONVector3Entity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<AVALONVector3Entity>> resources =
                new ArrayList<Resource<AVALONVector3Entity>>();
        while (iter.hasNext()) {
            resources.add(getVector3Resource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
