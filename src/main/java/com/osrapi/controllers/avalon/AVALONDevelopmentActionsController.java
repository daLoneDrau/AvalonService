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

import com.osrapi.models.avalon.AVALONDevelopmentActionsEntity;
import com.osrapi.models.avalon.AVALONActionChitEntity;

import com.osrapi.repositories.avalon.AVALONDevelopmentActionsRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/development_actionss")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONDevelopmentActionsController {
    /** the static instance of {@link AVALONDevelopmentActionsController}. */
    private static AVALONDevelopmentActionsController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONDevelopmentActionsController}
     */
    public static AVALONDevelopmentActionsController getInstance() {
        if (instance == null) {
            new AVALONDevelopmentActionsController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONDevelopmentActionsRepository repository;
    /** Creates a new instance of {@link AVALONDevelopmentActionsController}. */
    public AVALONDevelopmentActionsController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONDevelopmentActionsEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONDevelopmentActionsEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONDevelopmentActionsEntity>> getAll() {
        Iterator<AVALONDevelopmentActionsEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONDevelopmentActionsEntity>> resources =
                new ArrayList<Resource<AVALONDevelopmentActionsEntity>>();
        while (iter.hasNext()) {
            resources.add(getDevelopmentActionsResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONDevelopmentActionsEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONDevelopmentActionsEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONDevelopmentActionsEntity>> getById(
            @PathVariable final Long id) {
        AVALONDevelopmentActionsEntity entity = repository.findOne(id);
        List<Resource<AVALONDevelopmentActionsEntity>> resources =
                new ArrayList<Resource<AVALONDevelopmentActionsEntity>>();
        resources.add(getDevelopmentActionsResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONDevelopmentActionsEntity}.
     * @param entity the {@link AVALONDevelopmentActionsEntity}
     * @return {@link Resource}<{@link AVALONDevelopmentActionsEntity}>
     */
    private Resource<AVALONDevelopmentActionsEntity> getDevelopmentActionsResource(
            final AVALONDevelopmentActionsEntity entity) {
        Resource<AVALONDevelopmentActionsEntity> resource =
                new Resource<AVALONDevelopmentActionsEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONDevelopmentActionsEntity}s.
     * @param entities the list of {@link AVALONDevelopmentActionsEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONDevelopmentActionsEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONDevelopmentActionsEntity>> save(
            @RequestBody final List<AVALONDevelopmentActionsEntity> entities) {
        List<Resource<AVALONDevelopmentActionsEntity>> resources =
                new ArrayList<Resource<AVALONDevelopmentActionsEntity>>();
        Iterator<AVALONDevelopmentActionsEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONDevelopmentActionsEntity}.
     * @param entity the {@link AVALONDevelopmentActionsEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONDevelopmentActionsEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONDevelopmentActionsEntity>> save(
            @RequestBody final AVALONDevelopmentActionsEntity entity) {
            if (entity.getAction() != null
        && entity.getAction().getId() == null) {
      setActionIdFromRepository(entity);
        }


    
        AVALONDevelopmentActionsEntity savedEntity = repository.save(entity);
        List<Resource<AVALONDevelopmentActionsEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONDevelopmentActionsEntity} instance
     */
    private void setIdFromRepository(final AVALONDevelopmentActionsEntity entity) {
        List<AVALONDevelopmentActionsEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONDevelopmentActionsEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONDevelopmentActionsEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONDevelopmentActionsEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONDevelopmentActionsEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONDevelopmentActionsEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONDevelopmentActionsEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONDevelopmentActionsEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONDevelopmentActionsEntity}s.
     * @param entities the list of {@link AVALONDevelopmentActionsEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONDevelopmentActionsEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONDevelopmentActionsEntity>> update(
            @RequestBody final List<AVALONDevelopmentActionsEntity> entities) {
        List<Resource<AVALONDevelopmentActionsEntity>> resources = new ArrayList<Resource<AVALONDevelopmentActionsEntity>>();
        Iterator<AVALONDevelopmentActionsEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONDevelopmentActionsEntity}.
     * @param entity the {@link AVALONDevelopmentActionsEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONDevelopmentActionsEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONDevelopmentActionsEntity>> update(
            @RequestBody final AVALONDevelopmentActionsEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
            if (entity.getAction() != null
        && entity.getAction().getId() == null) {
      setActionIdFromRepository(entity);
        }


    
        AVALONDevelopmentActionsEntity savedEntity = repository.save(entity);
        List<Resource<AVALONDevelopmentActionsEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setActionIdFromRepository(
      final AVALONDevelopmentActionsEntity entity) {
    AVALONActionChitEntity memberEntity = null;
    List<Resource<AVALONActionChitEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = AVALONActionChitController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = AVALONActionChitEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getAction()) != null) {
          list = (List<Resource<AVALONActionChitEntity>>) method
              .invoke(
                  AVALONActionChitController.getInstance(),
                  (String) field
                      .get(entity.getAction()));
        }
      }
      if (list == null) {
        try {
          method = AVALONActionChitController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = AVALONActionChitEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getAction()) != null) {
            list = (List<Resource<AVALONActionChitEntity>>)
                method.invoke(AVALONActionChitController
                    .getInstance(),(String) field.get(
                        entity.getAction()));
          }
        }
      }
      method = null;
      field = null;
    } catch (SecurityException | IllegalArgumentException
        | IllegalAccessException
        | InvocationTargetException e) {
    }
    if (list != null
        && !list.isEmpty()) {
      memberEntity = list.get(0).getContent();
    }
    if (memberEntity == null) {
      memberEntity = (AVALONActionChitEntity)
          ((Resource) AVALONActionChitController.getInstance().save(
              entity.getAction()).get(0)).getContent();
    }
    entity.setAction(memberEntity);
    list = null;
    }


    /**
     * Gets a list of {@link AVALONDevelopmentActionsEntity}s that share a quantity.
     * @param quantity the development_actions's quantity
     * @return {@link List}<{@link Resource}<{@link AVALONDevelopmentActionsEntity}>>
     */
    @RequestMapping(path = "quantity/{quantity}",
            method = RequestMethod.GET)
    public List<Resource<AVALONDevelopmentActionsEntity>> getByQuantity(
            @PathVariable final Long quantity) {
        Iterator<AVALONDevelopmentActionsEntity> iter = repository.findByQuantity(quantity)
                .iterator();
        List<Resource<AVALONDevelopmentActionsEntity>> resources =
                new ArrayList<Resource<AVALONDevelopmentActionsEntity>>();
        while (iter.hasNext()) {
            resources.add(getDevelopmentActionsResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONDevelopmentActionsEntity}s that share a code.
     * @param code the development_actions's code
     * @return {@link List}<{@link Resource}<{@link AVALONDevelopmentActionsEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<AVALONDevelopmentActionsEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<AVALONDevelopmentActionsEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<AVALONDevelopmentActionsEntity>> resources =
                new ArrayList<Resource<AVALONDevelopmentActionsEntity>>();
        while (iter.hasNext()) {
            resources.add(getDevelopmentActionsResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
