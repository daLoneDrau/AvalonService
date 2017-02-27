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

import com.osrapi.models.avalon.AVALONEquipmentElementTypeEntity;

import com.osrapi.repositories.avalon.AVALONEquipmentElementTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/equipment_element_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONEquipmentElementTypeController {
    /** the static instance of {@link AVALONEquipmentElementTypeController}. */
    private static AVALONEquipmentElementTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONEquipmentElementTypeController}
     */
    public static AVALONEquipmentElementTypeController getInstance() {
        if (instance == null) {
            new AVALONEquipmentElementTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONEquipmentElementTypeRepository repository;
    /** Creates a new instance of {@link AVALONEquipmentElementTypeController}. */
    public AVALONEquipmentElementTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONEquipmentElementTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentElementTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONEquipmentElementTypeEntity>> getAll() {
        Iterator<AVALONEquipmentElementTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONEquipmentElementTypeEntity>> resources =
                new ArrayList<Resource<AVALONEquipmentElementTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentElementTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONEquipmentElementTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentElementTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONEquipmentElementTypeEntity>> getById(
            @PathVariable final Long id) {
        AVALONEquipmentElementTypeEntity entity = repository.findOne(id);
        List<Resource<AVALONEquipmentElementTypeEntity>> resources =
                new ArrayList<Resource<AVALONEquipmentElementTypeEntity>>();
        resources.add(getEquipmentElementTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONEquipmentElementTypeEntity}.
     * @param entity the {@link AVALONEquipmentElementTypeEntity}
     * @return {@link Resource}<{@link AVALONEquipmentElementTypeEntity}>
     */
    private Resource<AVALONEquipmentElementTypeEntity> getEquipmentElementTypeResource(
            final AVALONEquipmentElementTypeEntity entity) {
        Resource<AVALONEquipmentElementTypeEntity> resource =
                new Resource<AVALONEquipmentElementTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONEquipmentElementTypeEntity}s.
     * @param entities the list of {@link AVALONEquipmentElementTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentElementTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONEquipmentElementTypeEntity>> save(
            @RequestBody final List<AVALONEquipmentElementTypeEntity> entities) {
        List<Resource<AVALONEquipmentElementTypeEntity>> resources =
                new ArrayList<Resource<AVALONEquipmentElementTypeEntity>>();
        Iterator<AVALONEquipmentElementTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONEquipmentElementTypeEntity}.
     * @param entity the {@link AVALONEquipmentElementTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentElementTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONEquipmentElementTypeEntity>> save(
            @RequestBody final AVALONEquipmentElementTypeEntity entity) {
    
    
        AVALONEquipmentElementTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONEquipmentElementTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONEquipmentElementTypeEntity} instance
     */
    private void setIdFromRepository(final AVALONEquipmentElementTypeEntity entity) {
        List<AVALONEquipmentElementTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONEquipmentElementTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONEquipmentElementTypeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONEquipmentElementTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONEquipmentElementTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONEquipmentElementTypeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONEquipmentElementTypeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONEquipmentElementTypeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONEquipmentElementTypeEntity}s.
     * @param entities the list of {@link AVALONEquipmentElementTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentElementTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONEquipmentElementTypeEntity>> update(
            @RequestBody final List<AVALONEquipmentElementTypeEntity> entities) {
        List<Resource<AVALONEquipmentElementTypeEntity>> resources = new ArrayList<Resource<AVALONEquipmentElementTypeEntity>>();
        Iterator<AVALONEquipmentElementTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONEquipmentElementTypeEntity}.
     * @param entity the {@link AVALONEquipmentElementTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentElementTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONEquipmentElementTypeEntity>> update(
            @RequestBody final AVALONEquipmentElementTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONEquipmentElementTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONEquipmentElementTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONEquipmentElementTypeEntity}s that share a code.
     * @param code the equipment_element_type' code
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentElementTypeEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<AVALONEquipmentElementTypeEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<AVALONEquipmentElementTypeEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<AVALONEquipmentElementTypeEntity>> resources =
                new ArrayList<Resource<AVALONEquipmentElementTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentElementTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONEquipmentElementTypeEntity}s that share a value.
     * @param value the equipment_element_type' value
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentElementTypeEntity}>>
     */
    @RequestMapping(path = "value/{value}",
            method = RequestMethod.GET)
    public List<Resource<AVALONEquipmentElementTypeEntity>> getByValue(
            @PathVariable final Long value) {
        Iterator<AVALONEquipmentElementTypeEntity> iter = repository.findByValue(value)
                .iterator();
        List<Resource<AVALONEquipmentElementTypeEntity>> resources =
                new ArrayList<Resource<AVALONEquipmentElementTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentElementTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
