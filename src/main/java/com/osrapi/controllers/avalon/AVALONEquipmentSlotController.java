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

import com.osrapi.models.avalon.AVALONEquipmentSlotEntity;

import com.osrapi.repositories.avalon.AVALONEquipmentSlotRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/equipment_slots")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONEquipmentSlotController {
    /** the static instance of {@link AVALONEquipmentSlotController}. */
    private static AVALONEquipmentSlotController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONEquipmentSlotController}
     */
    public static AVALONEquipmentSlotController getInstance() {
        if (instance == null) {
            new AVALONEquipmentSlotController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONEquipmentSlotRepository repository;
    /** Creates a new instance of {@link AVALONEquipmentSlotController}. */
    public AVALONEquipmentSlotController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONEquipmentSlotEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentSlotEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONEquipmentSlotEntity>> getAll() {
        Iterator<AVALONEquipmentSlotEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONEquipmentSlotEntity>> resources =
                new ArrayList<Resource<AVALONEquipmentSlotEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentSlotResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONEquipmentSlotEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentSlotEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONEquipmentSlotEntity>> getById(
            @PathVariable final Long id) {
        AVALONEquipmentSlotEntity entity = repository.findOne(id);
        List<Resource<AVALONEquipmentSlotEntity>> resources =
                new ArrayList<Resource<AVALONEquipmentSlotEntity>>();
        resources.add(getEquipmentSlotResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONEquipmentSlotEntity}.
     * @param entity the {@link AVALONEquipmentSlotEntity}
     * @return {@link Resource}<{@link AVALONEquipmentSlotEntity}>
     */
    private Resource<AVALONEquipmentSlotEntity> getEquipmentSlotResource(
            final AVALONEquipmentSlotEntity entity) {
        Resource<AVALONEquipmentSlotEntity> resource =
                new Resource<AVALONEquipmentSlotEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONEquipmentSlotEntity}s.
     * @param entities the list of {@link AVALONEquipmentSlotEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentSlotEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONEquipmentSlotEntity>> save(
            @RequestBody final List<AVALONEquipmentSlotEntity> entities) {
        List<Resource<AVALONEquipmentSlotEntity>> resources =
                new ArrayList<Resource<AVALONEquipmentSlotEntity>>();
        Iterator<AVALONEquipmentSlotEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONEquipmentSlotEntity}.
     * @param entity the {@link AVALONEquipmentSlotEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentSlotEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONEquipmentSlotEntity>> save(
            @RequestBody final AVALONEquipmentSlotEntity entity) {
    
    
        AVALONEquipmentSlotEntity savedEntity = repository.save(entity);
        List<Resource<AVALONEquipmentSlotEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONEquipmentSlotEntity} instance
     */
    private void setIdFromRepository(final AVALONEquipmentSlotEntity entity) {
        List<AVALONEquipmentSlotEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONEquipmentSlotEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONEquipmentSlotEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONEquipmentSlotEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONEquipmentSlotEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONEquipmentSlotEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONEquipmentSlotEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONEquipmentSlotEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONEquipmentSlotEntity}s.
     * @param entities the list of {@link AVALONEquipmentSlotEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentSlotEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONEquipmentSlotEntity>> update(
            @RequestBody final List<AVALONEquipmentSlotEntity> entities) {
        List<Resource<AVALONEquipmentSlotEntity>> resources = new ArrayList<Resource<AVALONEquipmentSlotEntity>>();
        Iterator<AVALONEquipmentSlotEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONEquipmentSlotEntity}.
     * @param entity the {@link AVALONEquipmentSlotEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentSlotEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONEquipmentSlotEntity>> update(
            @RequestBody final AVALONEquipmentSlotEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONEquipmentSlotEntity savedEntity = repository.save(entity);
        List<Resource<AVALONEquipmentSlotEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONEquipmentSlotEntity}s that share a code.
     * @param code the equipment_slot' code
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentSlotEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<AVALONEquipmentSlotEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<AVALONEquipmentSlotEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<AVALONEquipmentSlotEntity>> resources =
                new ArrayList<Resource<AVALONEquipmentSlotEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentSlotResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONEquipmentSlotEntity}s that share a value.
     * @param value the equipment_slot' value
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentSlotEntity}>>
     */
    @RequestMapping(path = "value/{value}",
            method = RequestMethod.GET)
    public List<Resource<AVALONEquipmentSlotEntity>> getByValue(
            @PathVariable final Long value) {
        Iterator<AVALONEquipmentSlotEntity> iter = repository.findByValue(value)
                .iterator();
        List<Resource<AVALONEquipmentSlotEntity>> resources =
                new ArrayList<Resource<AVALONEquipmentSlotEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentSlotResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
