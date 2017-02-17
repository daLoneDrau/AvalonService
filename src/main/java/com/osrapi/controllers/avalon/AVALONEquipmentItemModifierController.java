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

import com.osrapi.models.avalon.AVALONEquipmentItemModifierEntity;

import com.osrapi.repositories.avalon.AVALONEquipmentItemModifierRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/equipment_item_modifiers")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONEquipmentItemModifierController {
    /** the static instance of {@link AVALONEquipmentItemModifierController}. */
    private static AVALONEquipmentItemModifierController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONEquipmentItemModifierController}
     */
    public static AVALONEquipmentItemModifierController getInstance() {
        if (instance == null) {
            new AVALONEquipmentItemModifierController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONEquipmentItemModifierRepository repository;
    /** Creates a new instance of {@link AVALONEquipmentItemModifierController}. */
    public AVALONEquipmentItemModifierController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONEquipmentItemModifierEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentItemModifierEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONEquipmentItemModifierEntity>> getAll() {
        Iterator<AVALONEquipmentItemModifierEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<AVALONEquipmentItemModifierEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentItemModifierResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONEquipmentItemModifierEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONEquipmentItemModifierEntity>> getById(
            @PathVariable final Long id) {
        AVALONEquipmentItemModifierEntity entity = repository.findOne(id);
        List<Resource<AVALONEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<AVALONEquipmentItemModifierEntity>>();
        resources.add(getEquipmentItemModifierResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONEquipmentItemModifierEntity}.
     * @param entity the {@link AVALONEquipmentItemModifierEntity}
     * @return {@link Resource}<{@link AVALONEquipmentItemModifierEntity}>
     */
    private Resource<AVALONEquipmentItemModifierEntity> getEquipmentItemModifierResource(
            final AVALONEquipmentItemModifierEntity entity) {
        Resource<AVALONEquipmentItemModifierEntity> resource =
                new Resource<AVALONEquipmentItemModifierEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONEquipmentItemModifierEntity}s.
     * @param entities the list of {@link AVALONEquipmentItemModifierEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONEquipmentItemModifierEntity>> save(
            @RequestBody final List<AVALONEquipmentItemModifierEntity> entities) {
        List<Resource<AVALONEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<AVALONEquipmentItemModifierEntity>>();
        Iterator<AVALONEquipmentItemModifierEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONEquipmentItemModifierEntity}.
     * @param entity the {@link AVALONEquipmentItemModifierEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentItemModifierEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONEquipmentItemModifierEntity>> save(
            @RequestBody final AVALONEquipmentItemModifierEntity entity) {
    
    
        AVALONEquipmentItemModifierEntity savedEntity = repository.save(entity);
        List<Resource<AVALONEquipmentItemModifierEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONEquipmentItemModifierEntity} instance
     */
    private void setIdFromRepository(final AVALONEquipmentItemModifierEntity entity) {
        List<AVALONEquipmentItemModifierEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONEquipmentItemModifierEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONEquipmentItemModifierEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONEquipmentItemModifierEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONEquipmentItemModifierEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONEquipmentItemModifierEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONEquipmentItemModifierEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONEquipmentItemModifierEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONEquipmentItemModifierEntity}s.
     * @param entities the list of {@link AVALONEquipmentItemModifierEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONEquipmentItemModifierEntity>> update(
            @RequestBody final List<AVALONEquipmentItemModifierEntity> entities) {
        List<Resource<AVALONEquipmentItemModifierEntity>> resources = new ArrayList<Resource<AVALONEquipmentItemModifierEntity>>();
        Iterator<AVALONEquipmentItemModifierEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONEquipmentItemModifierEntity}.
     * @param entity the {@link AVALONEquipmentItemModifierEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentItemModifierEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONEquipmentItemModifierEntity>> update(
            @RequestBody final AVALONEquipmentItemModifierEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONEquipmentItemModifierEntity savedEntity = repository.save(entity);
        List<Resource<AVALONEquipmentItemModifierEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONEquipmentItemModifierEntity}s that share a code.
     * @param code the equipment_item_modifier' code
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<AVALONEquipmentItemModifierEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<AVALONEquipmentItemModifierEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<AVALONEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<AVALONEquipmentItemModifierEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentItemModifierResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONEquipmentItemModifierEntity}s that share a percent.
     * @param percent the equipment_item_modifier' percent
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "percent/{percent}",
            method = RequestMethod.GET)
    public List<Resource<AVALONEquipmentItemModifierEntity>> getByPercent(
            @PathVariable final Boolean percent) {
        Iterator<AVALONEquipmentItemModifierEntity> iter = repository.findByPercent(percent)
                .iterator();
        List<Resource<AVALONEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<AVALONEquipmentItemModifierEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentItemModifierResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONEquipmentItemModifierEntity}s that share a special.
     * @param special the equipment_item_modifier' special
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "special/{special}",
            method = RequestMethod.GET)
    public List<Resource<AVALONEquipmentItemModifierEntity>> getBySpecial(
            @PathVariable final Long special) {
        Iterator<AVALONEquipmentItemModifierEntity> iter = repository.findBySpecial(special)
                .iterator();
        List<Resource<AVALONEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<AVALONEquipmentItemModifierEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentItemModifierResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONEquipmentItemModifierEntity}s that share a value.
     * @param value the equipment_item_modifier' value
     * @return {@link List}<{@link Resource}<{@link AVALONEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "value/{value}",
            method = RequestMethod.GET)
    public List<Resource<AVALONEquipmentItemModifierEntity>> getByValue(
            @PathVariable final float value) {
        Iterator<AVALONEquipmentItemModifierEntity> iter = repository.findByValue(value)
                .iterator();
        List<Resource<AVALONEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<AVALONEquipmentItemModifierEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentItemModifierResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
