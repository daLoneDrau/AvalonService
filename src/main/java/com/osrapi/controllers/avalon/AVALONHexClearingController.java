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

import com.osrapi.models.avalon.AVALONHexClearingEntity;
import com.osrapi.models.avalon.AVALONHexClearingTypeEntity;
import com.osrapi.models.avalon.AVALONVector3Entity;

import com.osrapi.repositories.avalon.AVALONHexClearingRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/hex_clearings")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONHexClearingController {
    /** the static instance of {@link AVALONHexClearingController}. */
    private static AVALONHexClearingController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONHexClearingController}
     */
    public static AVALONHexClearingController getInstance() {
        if (instance == null) {
            new AVALONHexClearingController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONHexClearingRepository repository;
    /** Creates a new instance of {@link AVALONHexClearingController}. */
    public AVALONHexClearingController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONHexClearingEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONHexClearingEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONHexClearingEntity>> getAll() {
        Iterator<AVALONHexClearingEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONHexClearingEntity>> resources =
                new ArrayList<Resource<AVALONHexClearingEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexClearingResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONHexClearingEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONHexClearingEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONHexClearingEntity>> getById(
            @PathVariable final Long id) {
        AVALONHexClearingEntity entity = repository.findOne(id);
        List<Resource<AVALONHexClearingEntity>> resources =
                new ArrayList<Resource<AVALONHexClearingEntity>>();
        resources.add(getHexClearingResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONHexClearingEntity}.
     * @param entity the {@link AVALONHexClearingEntity}
     * @return {@link Resource}<{@link AVALONHexClearingEntity}>
     */
    private Resource<AVALONHexClearingEntity> getHexClearingResource(
            final AVALONHexClearingEntity entity) {
        Resource<AVALONHexClearingEntity> resource =
                new Resource<AVALONHexClearingEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONHexClearingEntity}s.
     * @param entities the list of {@link AVALONHexClearingEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexClearingEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONHexClearingEntity>> save(
            @RequestBody final List<AVALONHexClearingEntity> entities) {
        List<Resource<AVALONHexClearingEntity>> resources =
                new ArrayList<Resource<AVALONHexClearingEntity>>();
        Iterator<AVALONHexClearingEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONHexClearingEntity}.
     * @param entity the {@link AVALONHexClearingEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexClearingEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONHexClearingEntity>> save(
            @RequestBody final AVALONHexClearingEntity entity) {
            if (entity.getType() != null
        && entity.getType().getId() == null) {
      setTypeIdFromRepository(entity);
        }

        if (entity.getLocation() != null
        && entity.getLocation().getId() == null) {
      setLocationIdFromRepository(entity);
        }


    
        AVALONHexClearingEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexClearingEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONHexClearingEntity} instance
     */
    private void setIdFromRepository(final AVALONHexClearingEntity entity) {
        List<AVALONHexClearingEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONHexClearingEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONHexClearingEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONHexClearingEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONHexClearingEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONHexClearingEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONHexClearingEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONHexClearingEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONHexClearingEntity}s.
     * @param entities the list of {@link AVALONHexClearingEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexClearingEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONHexClearingEntity>> update(
            @RequestBody final List<AVALONHexClearingEntity> entities) {
        List<Resource<AVALONHexClearingEntity>> resources = new ArrayList<Resource<AVALONHexClearingEntity>>();
        Iterator<AVALONHexClearingEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONHexClearingEntity}.
     * @param entity the {@link AVALONHexClearingEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexClearingEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONHexClearingEntity>> update(
            @RequestBody final AVALONHexClearingEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
            if (entity.getType() != null
        && entity.getType().getId() == null) {
      setTypeIdFromRepository(entity);
        }

        if (entity.getLocation() != null
        && entity.getLocation().getId() == null) {
      setLocationIdFromRepository(entity);
        }


    
        AVALONHexClearingEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexClearingEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setTypeIdFromRepository(
      final AVALONHexClearingEntity entity) {
    AVALONHexClearingTypeEntity memberEntity = null;
    List<Resource<AVALONHexClearingTypeEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = AVALONHexClearingTypeController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = AVALONHexClearingTypeEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getType()) != null) {
          list = (List<Resource<AVALONHexClearingTypeEntity>>) method
              .invoke(
                  AVALONHexClearingTypeController.getInstance(),
                  (String) field
                      .get(entity.getType()));
        }
      }
      if (list == null) {
        try {
          method = AVALONHexClearingTypeController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = AVALONHexClearingTypeEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getType()) != null) {
            list = (List<Resource<AVALONHexClearingTypeEntity>>)
                method.invoke(AVALONHexClearingTypeController
                    .getInstance(),(String) field.get(
                        entity.getType()));
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
      memberEntity = (AVALONHexClearingTypeEntity)
          ((Resource) AVALONHexClearingTypeController.getInstance().save(
              entity.getType()).get(0)).getContent();
    }
    entity.setType(memberEntity);
    list = null;
    }

  private void setLocationIdFromRepository(
      final AVALONHexClearingEntity entity) {
    AVALONVector3Entity memberEntity = null;
    List<Resource<AVALONVector3Entity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = AVALONVector3Controller.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = AVALONVector3Entity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getLocation()) != null) {
          list = (List<Resource<AVALONVector3Entity>>) method
              .invoke(
                  AVALONVector3Controller.getInstance(),
                  (String) field
                      .get(entity.getLocation()));
        }
      }
      if (list == null) {
        try {
          method = AVALONVector3Controller.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = AVALONVector3Entity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getLocation()) != null) {
            list = (List<Resource<AVALONVector3Entity>>)
                method.invoke(AVALONVector3Controller
                    .getInstance(),(String) field.get(
                        entity.getLocation()));
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
      memberEntity = (AVALONVector3Entity)
          ((Resource) AVALONVector3Controller.getInstance().save(
              entity.getLocation()).get(0)).getContent();
    }
    entity.setLocation(memberEntity);
    list = null;
    }


    /**
     * Gets a list of {@link AVALONHexClearingEntity}s that share a number.
     * @param number the hex_clearing' number
     * @return {@link List}<{@link Resource}<{@link AVALONHexClearingEntity}>>
     */
    @RequestMapping(path = "number/{number}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexClearingEntity>> getByNumber(
            @PathVariable final Long number) {
        Iterator<AVALONHexClearingEntity> iter = repository.findByNumber(number)
                .iterator();
        List<Resource<AVALONHexClearingEntity>> resources =
                new ArrayList<Resource<AVALONHexClearingEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexClearingResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONHexClearingEntity}s that share a code.
     * @param code the hex_clearing' code
     * @return {@link List}<{@link Resource}<{@link AVALONHexClearingEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexClearingEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<AVALONHexClearingEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<AVALONHexClearingEntity>> resources =
                new ArrayList<Resource<AVALONHexClearingEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexClearingResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
