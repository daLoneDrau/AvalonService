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

import com.osrapi.models.avalon.AVALONArmorProtectionEntity;

import com.osrapi.repositories.avalon.AVALONArmorProtectionRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/armor_protections")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONArmorProtectionController {
    /** the static instance of {@link AVALONArmorProtectionController}. */
    private static AVALONArmorProtectionController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONArmorProtectionController}
     */
    public static AVALONArmorProtectionController getInstance() {
        if (instance == null) {
            new AVALONArmorProtectionController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONArmorProtectionRepository repository;
    /** Creates a new instance of {@link AVALONArmorProtectionController}. */
    public AVALONArmorProtectionController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONArmorProtectionEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONArmorProtectionEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONArmorProtectionEntity>> getAll() {
        Iterator<AVALONArmorProtectionEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONArmorProtectionEntity>> resources =
                new ArrayList<Resource<AVALONArmorProtectionEntity>>();
        while (iter.hasNext()) {
            resources.add(getArmorProtectionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONArmorProtectionEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONArmorProtectionEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONArmorProtectionEntity>> getById(
            @PathVariable final Long id) {
        AVALONArmorProtectionEntity entity = repository.findOne(id);
        List<Resource<AVALONArmorProtectionEntity>> resources =
                new ArrayList<Resource<AVALONArmorProtectionEntity>>();
        resources.add(getArmorProtectionResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONArmorProtectionEntity}.
     * @param entity the {@link AVALONArmorProtectionEntity}
     * @return {@link Resource}<{@link AVALONArmorProtectionEntity}>
     */
    private Resource<AVALONArmorProtectionEntity> getArmorProtectionResource(
            final AVALONArmorProtectionEntity entity) {
        Resource<AVALONArmorProtectionEntity> resource =
                new Resource<AVALONArmorProtectionEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONArmorProtectionEntity}s.
     * @param entities the list of {@link AVALONArmorProtectionEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONArmorProtectionEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONArmorProtectionEntity>> save(
            @RequestBody final List<AVALONArmorProtectionEntity> entities) {
        List<Resource<AVALONArmorProtectionEntity>> resources =
                new ArrayList<Resource<AVALONArmorProtectionEntity>>();
        Iterator<AVALONArmorProtectionEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONArmorProtectionEntity}.
     * @param entity the {@link AVALONArmorProtectionEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONArmorProtectionEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONArmorProtectionEntity>> save(
            @RequestBody final AVALONArmorProtectionEntity entity) {
    
    
        AVALONArmorProtectionEntity savedEntity = repository.save(entity);
        List<Resource<AVALONArmorProtectionEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONArmorProtectionEntity} instance
     */
    private void setIdFromRepository(final AVALONArmorProtectionEntity entity) {
        List<AVALONArmorProtectionEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONArmorProtectionEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONArmorProtectionEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONArmorProtectionEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONArmorProtectionEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONArmorProtectionEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONArmorProtectionEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONArmorProtectionEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONArmorProtectionEntity}s.
     * @param entities the list of {@link AVALONArmorProtectionEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONArmorProtectionEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONArmorProtectionEntity>> update(
            @RequestBody final List<AVALONArmorProtectionEntity> entities) {
        List<Resource<AVALONArmorProtectionEntity>> resources = new ArrayList<Resource<AVALONArmorProtectionEntity>>();
        Iterator<AVALONArmorProtectionEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONArmorProtectionEntity}.
     * @param entity the {@link AVALONArmorProtectionEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONArmorProtectionEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONArmorProtectionEntity>> update(
            @RequestBody final AVALONArmorProtectionEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONArmorProtectionEntity savedEntity = repository.save(entity);
        List<Resource<AVALONArmorProtectionEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONArmorProtectionEntity}s that share a name.
     * @param name the armor_protection' name
     * @return {@link List}<{@link Resource}<{@link AVALONArmorProtectionEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<AVALONArmorProtectionEntity>> getByName(
            @PathVariable final String name) {
        Iterator<AVALONArmorProtectionEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<AVALONArmorProtectionEntity>> resources =
                new ArrayList<Resource<AVALONArmorProtectionEntity>>();
        while (iter.hasNext()) {
            resources.add(getArmorProtectionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
