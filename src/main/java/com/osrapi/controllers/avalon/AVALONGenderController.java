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

import com.osrapi.models.avalon.AVALONGenderEntity;

import com.osrapi.repositories.avalon.AVALONGenderRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/genders")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONGenderController {
    /** the static instance of {@link AVALONGenderController}. */
    private static AVALONGenderController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONGenderController}
     */
    public static AVALONGenderController getInstance() {
        if (instance == null) {
            new AVALONGenderController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONGenderRepository repository;
    /** Creates a new instance of {@link AVALONGenderController}. */
    public AVALONGenderController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONGenderEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONGenderEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONGenderEntity>> getAll() {
        Iterator<AVALONGenderEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONGenderEntity>> resources =
                new ArrayList<Resource<AVALONGenderEntity>>();
        while (iter.hasNext()) {
            resources.add(getGenderResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONGenderEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONGenderEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONGenderEntity>> getById(
            @PathVariable final Long id) {
        AVALONGenderEntity entity = repository.findOne(id);
        List<Resource<AVALONGenderEntity>> resources =
                new ArrayList<Resource<AVALONGenderEntity>>();
        resources.add(getGenderResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONGenderEntity}.
     * @param entity the {@link AVALONGenderEntity}
     * @return {@link Resource}<{@link AVALONGenderEntity}>
     */
    private Resource<AVALONGenderEntity> getGenderResource(
            final AVALONGenderEntity entity) {
        Resource<AVALONGenderEntity> resource =
                new Resource<AVALONGenderEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONGenderEntity}s.
     * @param entities the list of {@link AVALONGenderEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONGenderEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONGenderEntity>> save(
            @RequestBody final List<AVALONGenderEntity> entities) {
        List<Resource<AVALONGenderEntity>> resources =
                new ArrayList<Resource<AVALONGenderEntity>>();
        Iterator<AVALONGenderEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONGenderEntity}.
     * @param entity the {@link AVALONGenderEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONGenderEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONGenderEntity>> save(
            @RequestBody final AVALONGenderEntity entity) {
    
    
        AVALONGenderEntity savedEntity = repository.save(entity);
        List<Resource<AVALONGenderEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONGenderEntity} instance
     */
    private void setIdFromRepository(final AVALONGenderEntity entity) {
        List<AVALONGenderEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONGenderEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONGenderEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONGenderEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONGenderEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONGenderEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONGenderEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONGenderEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONGenderEntity}s.
     * @param entities the list of {@link AVALONGenderEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONGenderEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONGenderEntity>> update(
            @RequestBody final List<AVALONGenderEntity> entities) {
        List<Resource<AVALONGenderEntity>> resources = new ArrayList<Resource<AVALONGenderEntity>>();
        Iterator<AVALONGenderEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONGenderEntity}.
     * @param entity the {@link AVALONGenderEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONGenderEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONGenderEntity>> update(
            @RequestBody final AVALONGenderEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONGenderEntity savedEntity = repository.save(entity);
        List<Resource<AVALONGenderEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONGenderEntity}s that share a description.
     * @param description the gender' description
     * @return {@link List}<{@link Resource}<{@link AVALONGenderEntity}>>
     */
    @RequestMapping(path = "description/{description}",
            method = RequestMethod.GET)
    public List<Resource<AVALONGenderEntity>> getByDescription(
            @PathVariable final String description) {
        Iterator<AVALONGenderEntity> iter = repository.findByDescription(description)
                .iterator();
        List<Resource<AVALONGenderEntity>> resources =
                new ArrayList<Resource<AVALONGenderEntity>>();
        while (iter.hasNext()) {
            resources.add(getGenderResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONGenderEntity}s that share a name.
     * @param name the gender' name
     * @return {@link List}<{@link Resource}<{@link AVALONGenderEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<AVALONGenderEntity>> getByName(
            @PathVariable final String name) {
        Iterator<AVALONGenderEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<AVALONGenderEntity>> resources =
                new ArrayList<Resource<AVALONGenderEntity>>();
        while (iter.hasNext()) {
            resources.add(getGenderResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
