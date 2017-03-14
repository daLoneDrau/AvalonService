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

import com.osrapi.models.avalon.AVALONMagicTypeEntity;

import com.osrapi.repositories.avalon.AVALONMagicTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/magic_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONMagicTypeController {
    /** the static instance of {@link AVALONMagicTypeController}. */
    private static AVALONMagicTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONMagicTypeController}
     */
    public static AVALONMagicTypeController getInstance() {
        if (instance == null) {
            new AVALONMagicTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONMagicTypeRepository repository;
    /** Creates a new instance of {@link AVALONMagicTypeController}. */
    public AVALONMagicTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONMagicTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONMagicTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONMagicTypeEntity>> getAll() {
        Iterator<AVALONMagicTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONMagicTypeEntity>> resources =
                new ArrayList<Resource<AVALONMagicTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getMagicTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONMagicTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONMagicTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONMagicTypeEntity>> getById(
            @PathVariable final Long id) {
        AVALONMagicTypeEntity entity = repository.findOne(id);
        List<Resource<AVALONMagicTypeEntity>> resources =
                new ArrayList<Resource<AVALONMagicTypeEntity>>();
        resources.add(getMagicTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONMagicTypeEntity}.
     * @param entity the {@link AVALONMagicTypeEntity}
     * @return {@link Resource}<{@link AVALONMagicTypeEntity}>
     */
    private Resource<AVALONMagicTypeEntity> getMagicTypeResource(
            final AVALONMagicTypeEntity entity) {
        Resource<AVALONMagicTypeEntity> resource =
                new Resource<AVALONMagicTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONMagicTypeEntity}s.
     * @param entities the list of {@link AVALONMagicTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONMagicTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONMagicTypeEntity>> save(
            @RequestBody final List<AVALONMagicTypeEntity> entities) {
        List<Resource<AVALONMagicTypeEntity>> resources =
                new ArrayList<Resource<AVALONMagicTypeEntity>>();
        Iterator<AVALONMagicTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONMagicTypeEntity}.
     * @param entity the {@link AVALONMagicTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONMagicTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONMagicTypeEntity>> save(
            @RequestBody final AVALONMagicTypeEntity entity) {
    
    
        AVALONMagicTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONMagicTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONMagicTypeEntity} instance
     */
    private void setIdFromRepository(final AVALONMagicTypeEntity entity) {
        List<AVALONMagicTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONMagicTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONMagicTypeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONMagicTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONMagicTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONMagicTypeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONMagicTypeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONMagicTypeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONMagicTypeEntity}s.
     * @param entities the list of {@link AVALONMagicTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONMagicTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONMagicTypeEntity>> update(
            @RequestBody final List<AVALONMagicTypeEntity> entities) {
        List<Resource<AVALONMagicTypeEntity>> resources = new ArrayList<Resource<AVALONMagicTypeEntity>>();
        Iterator<AVALONMagicTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONMagicTypeEntity}.
     * @param entity the {@link AVALONMagicTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONMagicTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONMagicTypeEntity>> update(
            @RequestBody final AVALONMagicTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONMagicTypeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONMagicTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONMagicTypeEntity}s that share a code.
     * @param code the magic_type' code
     * @return {@link List}<{@link Resource}<{@link AVALONMagicTypeEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<AVALONMagicTypeEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<AVALONMagicTypeEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<AVALONMagicTypeEntity>> resources =
                new ArrayList<Resource<AVALONMagicTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getMagicTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONMagicTypeEntity}s that share a spellName.
     * @param spellName the magic_type' spellName
     * @return {@link List}<{@link Resource}<{@link AVALONMagicTypeEntity}>>
     */
    @RequestMapping(path = "spell_name/{spellName}",
            method = RequestMethod.GET)
    public List<Resource<AVALONMagicTypeEntity>> getBySpellName(
            @PathVariable final String spellName) {
        Iterator<AVALONMagicTypeEntity> iter = repository.findBySpellName(spellName)
                .iterator();
        List<Resource<AVALONMagicTypeEntity>> resources =
                new ArrayList<Resource<AVALONMagicTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getMagicTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONMagicTypeEntity}s that share a title.
     * @param title the magic_type' title
     * @return {@link List}<{@link Resource}<{@link AVALONMagicTypeEntity}>>
     */
    @RequestMapping(path = "title/{title}",
            method = RequestMethod.GET)
    public List<Resource<AVALONMagicTypeEntity>> getByTitle(
            @PathVariable final String title) {
        Iterator<AVALONMagicTypeEntity> iter = repository.findByTitle(title)
                .iterator();
        List<Resource<AVALONMagicTypeEntity>> resources =
                new ArrayList<Resource<AVALONMagicTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getMagicTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
