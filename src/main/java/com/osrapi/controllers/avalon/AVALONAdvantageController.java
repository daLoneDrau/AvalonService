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

import com.osrapi.models.avalon.AVALONAdvantageEntity;

import com.osrapi.repositories.avalon.AVALONAdvantageRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/advantages")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONAdvantageController {
    /** the static instance of {@link AVALONAdvantageController}. */
    private static AVALONAdvantageController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONAdvantageController}
     */
    public static AVALONAdvantageController getInstance() {
        if (instance == null) {
            new AVALONAdvantageController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONAdvantageRepository repository;
    /** Creates a new instance of {@link AVALONAdvantageController}. */
    public AVALONAdvantageController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONAdvantageEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONAdvantageEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONAdvantageEntity>> getAll() {
        Iterator<AVALONAdvantageEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONAdvantageEntity>> resources =
                new ArrayList<Resource<AVALONAdvantageEntity>>();
        while (iter.hasNext()) {
            resources.add(getAdvantageResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONAdvantageEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONAdvantageEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONAdvantageEntity>> getById(
            @PathVariable final Long id) {
        AVALONAdvantageEntity entity = repository.findOne(id);
        List<Resource<AVALONAdvantageEntity>> resources =
                new ArrayList<Resource<AVALONAdvantageEntity>>();
        resources.add(getAdvantageResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONAdvantageEntity}.
     * @param entity the {@link AVALONAdvantageEntity}
     * @return {@link Resource}<{@link AVALONAdvantageEntity}>
     */
    private Resource<AVALONAdvantageEntity> getAdvantageResource(
            final AVALONAdvantageEntity entity) {
        Resource<AVALONAdvantageEntity> resource =
                new Resource<AVALONAdvantageEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONAdvantageEntity}s.
     * @param entities the list of {@link AVALONAdvantageEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONAdvantageEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONAdvantageEntity>> save(
            @RequestBody final List<AVALONAdvantageEntity> entities) {
        List<Resource<AVALONAdvantageEntity>> resources =
                new ArrayList<Resource<AVALONAdvantageEntity>>();
        Iterator<AVALONAdvantageEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONAdvantageEntity}.
     * @param entity the {@link AVALONAdvantageEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONAdvantageEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONAdvantageEntity>> save(
            @RequestBody final AVALONAdvantageEntity entity) {
    
    
        AVALONAdvantageEntity savedEntity = repository.save(entity);
        List<Resource<AVALONAdvantageEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONAdvantageEntity} instance
     */
    private void setIdFromRepository(final AVALONAdvantageEntity entity) {
        List<AVALONAdvantageEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONAdvantageEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONAdvantageEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONAdvantageEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONAdvantageEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONAdvantageEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONAdvantageEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONAdvantageEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONAdvantageEntity}s.
     * @param entities the list of {@link AVALONAdvantageEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONAdvantageEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONAdvantageEntity>> update(
            @RequestBody final List<AVALONAdvantageEntity> entities) {
        List<Resource<AVALONAdvantageEntity>> resources = new ArrayList<Resource<AVALONAdvantageEntity>>();
        Iterator<AVALONAdvantageEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONAdvantageEntity}.
     * @param entity the {@link AVALONAdvantageEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONAdvantageEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONAdvantageEntity>> update(
            @RequestBody final AVALONAdvantageEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONAdvantageEntity savedEntity = repository.save(entity);
        List<Resource<AVALONAdvantageEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONAdvantageEntity}s that share a description.
     * @param description the advantage' description
     * @return {@link List}<{@link Resource}<{@link AVALONAdvantageEntity}>>
     */
    @RequestMapping(path = "description/{description}",
            method = RequestMethod.GET)
    public List<Resource<AVALONAdvantageEntity>> getByDescription(
            @PathVariable final String description) {
        Iterator<AVALONAdvantageEntity> iter = repository.findByDescription(description)
                .iterator();
        List<Resource<AVALONAdvantageEntity>> resources =
                new ArrayList<Resource<AVALONAdvantageEntity>>();
        while (iter.hasNext()) {
            resources.add(getAdvantageResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONAdvantageEntity}s that share a flag.
     * @param flag the advantage' flag
     * @return {@link List}<{@link Resource}<{@link AVALONAdvantageEntity}>>
     */
    @RequestMapping(path = "flag/{flag}",
            method = RequestMethod.GET)
    public List<Resource<AVALONAdvantageEntity>> getByFlag(
            @PathVariable final Long flag) {
        Iterator<AVALONAdvantageEntity> iter = repository.findByFlag(flag)
                .iterator();
        List<Resource<AVALONAdvantageEntity>> resources =
                new ArrayList<Resource<AVALONAdvantageEntity>>();
        while (iter.hasNext()) {
            resources.add(getAdvantageResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONAdvantageEntity}s that share a name.
     * @param name the advantage' name
     * @return {@link List}<{@link Resource}<{@link AVALONAdvantageEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<AVALONAdvantageEntity>> getByName(
            @PathVariable final String name) {
        Iterator<AVALONAdvantageEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<AVALONAdvantageEntity>> resources =
                new ArrayList<Resource<AVALONAdvantageEntity>>();
        while (iter.hasNext()) {
            resources.add(getAdvantageResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
