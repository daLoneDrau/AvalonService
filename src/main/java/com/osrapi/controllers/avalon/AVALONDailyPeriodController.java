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

import com.osrapi.models.avalon.AVALONDailyPeriodEntity;

import com.osrapi.repositories.avalon.AVALONDailyPeriodRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/daily_periods")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONDailyPeriodController {
    /** the static instance of {@link AVALONDailyPeriodController}. */
    private static AVALONDailyPeriodController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONDailyPeriodController}
     */
    public static AVALONDailyPeriodController getInstance() {
        if (instance == null) {
            new AVALONDailyPeriodController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONDailyPeriodRepository repository;
    /** Creates a new instance of {@link AVALONDailyPeriodController}. */
    public AVALONDailyPeriodController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONDailyPeriodEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONDailyPeriodEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONDailyPeriodEntity>> getAll() {
        Iterator<AVALONDailyPeriodEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONDailyPeriodEntity>> resources =
                new ArrayList<Resource<AVALONDailyPeriodEntity>>();
        while (iter.hasNext()) {
            resources.add(getDailyPeriodResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONDailyPeriodEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONDailyPeriodEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONDailyPeriodEntity>> getById(
            @PathVariable final Long id) {
        AVALONDailyPeriodEntity entity = repository.findOne(id);
        List<Resource<AVALONDailyPeriodEntity>> resources =
                new ArrayList<Resource<AVALONDailyPeriodEntity>>();
        resources.add(getDailyPeriodResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONDailyPeriodEntity}.
     * @param entity the {@link AVALONDailyPeriodEntity}
     * @return {@link Resource}<{@link AVALONDailyPeriodEntity}>
     */
    private Resource<AVALONDailyPeriodEntity> getDailyPeriodResource(
            final AVALONDailyPeriodEntity entity) {
        Resource<AVALONDailyPeriodEntity> resource =
                new Resource<AVALONDailyPeriodEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONDailyPeriodEntity}s.
     * @param entities the list of {@link AVALONDailyPeriodEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONDailyPeriodEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONDailyPeriodEntity>> save(
            @RequestBody final List<AVALONDailyPeriodEntity> entities) {
        List<Resource<AVALONDailyPeriodEntity>> resources =
                new ArrayList<Resource<AVALONDailyPeriodEntity>>();
        Iterator<AVALONDailyPeriodEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONDailyPeriodEntity}.
     * @param entity the {@link AVALONDailyPeriodEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONDailyPeriodEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONDailyPeriodEntity>> save(
            @RequestBody final AVALONDailyPeriodEntity entity) {
    
    
        AVALONDailyPeriodEntity savedEntity = repository.save(entity);
        List<Resource<AVALONDailyPeriodEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONDailyPeriodEntity} instance
     */
    private void setIdFromRepository(final AVALONDailyPeriodEntity entity) {
        List<AVALONDailyPeriodEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONDailyPeriodEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONDailyPeriodEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONDailyPeriodEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONDailyPeriodEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONDailyPeriodEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONDailyPeriodEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONDailyPeriodEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONDailyPeriodEntity}s.
     * @param entities the list of {@link AVALONDailyPeriodEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONDailyPeriodEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONDailyPeriodEntity>> update(
            @RequestBody final List<AVALONDailyPeriodEntity> entities) {
        List<Resource<AVALONDailyPeriodEntity>> resources = new ArrayList<Resource<AVALONDailyPeriodEntity>>();
        Iterator<AVALONDailyPeriodEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONDailyPeriodEntity}.
     * @param entity the {@link AVALONDailyPeriodEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONDailyPeriodEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONDailyPeriodEntity>> update(
            @RequestBody final AVALONDailyPeriodEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONDailyPeriodEntity savedEntity = repository.save(entity);
        List<Resource<AVALONDailyPeriodEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONDailyPeriodEntity}s that share a name.
     * @param name the daily_period' name
     * @return {@link List}<{@link Resource}<{@link AVALONDailyPeriodEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<AVALONDailyPeriodEntity>> getByName(
            @PathVariable final String name) {
        Iterator<AVALONDailyPeriodEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<AVALONDailyPeriodEntity>> resources =
                new ArrayList<Resource<AVALONDailyPeriodEntity>>();
        while (iter.hasNext()) {
            resources.add(getDailyPeriodResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
