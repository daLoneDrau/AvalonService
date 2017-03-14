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

import com.osrapi.models.avalon.AVALONHexNodeEdgeEntity;
import com.osrapi.models.avalon.AVALONHexClearingEntity;
import com.osrapi.models.avalon.AVALONHexClearingEntity;
import com.osrapi.models.avalon.AVALONHexPathEntity;

import com.osrapi.repositories.avalon.AVALONHexNodeEdgeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/hex_node_edges")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONHexNodeEdgeController {
    /** the static instance of {@link AVALONHexNodeEdgeController}. */
    private static AVALONHexNodeEdgeController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONHexNodeEdgeController}
     */
    public static AVALONHexNodeEdgeController getInstance() {
        if (instance == null) {
            new AVALONHexNodeEdgeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONHexNodeEdgeRepository repository;
    /** Creates a new instance of {@link AVALONHexNodeEdgeController}. */
    public AVALONHexNodeEdgeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONHexNodeEdgeEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONHexNodeEdgeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONHexNodeEdgeEntity>> getAll() {
        Iterator<AVALONHexNodeEdgeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONHexNodeEdgeEntity>> resources =
                new ArrayList<Resource<AVALONHexNodeEdgeEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexNodeEdgeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONHexNodeEdgeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONHexNodeEdgeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONHexNodeEdgeEntity>> getById(
            @PathVariable final Long id) {
        AVALONHexNodeEdgeEntity entity = repository.findOne(id);
        List<Resource<AVALONHexNodeEdgeEntity>> resources =
                new ArrayList<Resource<AVALONHexNodeEdgeEntity>>();
        resources.add(getHexNodeEdgeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONHexNodeEdgeEntity}.
     * @param entity the {@link AVALONHexNodeEdgeEntity}
     * @return {@link Resource}<{@link AVALONHexNodeEdgeEntity}>
     */
    private Resource<AVALONHexNodeEdgeEntity> getHexNodeEdgeResource(
            final AVALONHexNodeEdgeEntity entity) {
        Resource<AVALONHexNodeEdgeEntity> resource =
                new Resource<AVALONHexNodeEdgeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONHexNodeEdgeEntity}s.
     * @param entities the list of {@link AVALONHexNodeEdgeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexNodeEdgeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONHexNodeEdgeEntity>> save(
            @RequestBody final List<AVALONHexNodeEdgeEntity> entities) {
        List<Resource<AVALONHexNodeEdgeEntity>> resources =
                new ArrayList<Resource<AVALONHexNodeEdgeEntity>>();
        Iterator<AVALONHexNodeEdgeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONHexNodeEdgeEntity}.
     * @param entity the {@link AVALONHexNodeEdgeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexNodeEdgeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONHexNodeEdgeEntity>> save(
            @RequestBody final AVALONHexNodeEdgeEntity entity) {
            if (entity.getClearingFrom() != null
        && entity.getClearingFrom().getId() == null) {
      setClearingFromIdFromRepository(entity);
        }

        if (entity.getClearingTo() != null
        && entity.getClearingTo().getId() == null) {
      setClearingToIdFromRepository(entity);
        }

        if (entity.getPath() != null
        && entity.getPath().getId() == null) {
      setPathIdFromRepository(entity);
        }


    
        AVALONHexNodeEdgeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexNodeEdgeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONHexNodeEdgeEntity} instance
     */
    private void setIdFromRepository(final AVALONHexNodeEdgeEntity entity) {
        List<AVALONHexNodeEdgeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONHexNodeEdgeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONHexNodeEdgeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONHexNodeEdgeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONHexNodeEdgeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONHexNodeEdgeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONHexNodeEdgeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONHexNodeEdgeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONHexNodeEdgeEntity}s.
     * @param entities the list of {@link AVALONHexNodeEdgeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexNodeEdgeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONHexNodeEdgeEntity>> update(
            @RequestBody final List<AVALONHexNodeEdgeEntity> entities) {
        List<Resource<AVALONHexNodeEdgeEntity>> resources = new ArrayList<Resource<AVALONHexNodeEdgeEntity>>();
        Iterator<AVALONHexNodeEdgeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONHexNodeEdgeEntity}.
     * @param entity the {@link AVALONHexNodeEdgeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexNodeEdgeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONHexNodeEdgeEntity>> update(
            @RequestBody final AVALONHexNodeEdgeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
            if (entity.getClearingFrom() != null
        && entity.getClearingFrom().getId() == null) {
      setClearingFromIdFromRepository(entity);
        }

        if (entity.getClearingTo() != null
        && entity.getClearingTo().getId() == null) {
      setClearingToIdFromRepository(entity);
        }

        if (entity.getPath() != null
        && entity.getPath().getId() == null) {
      setPathIdFromRepository(entity);
        }


    
        AVALONHexNodeEdgeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexNodeEdgeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setClearingFromIdFromRepository(
      final AVALONHexNodeEdgeEntity entity) {
    AVALONHexClearingEntity memberEntity = null;
    List<Resource<AVALONHexClearingEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = AVALONHexClearingController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = AVALONHexClearingEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getClearingFrom()) != null) {
          list = (List<Resource<AVALONHexClearingEntity>>) method
              .invoke(
                  AVALONHexClearingController.getInstance(),
                  (String) field
                      .get(entity.getClearingFrom()));
        }
      }
      if (list == null) {
        try {
          method = AVALONHexClearingController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = AVALONHexClearingEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getClearingFrom()) != null) {
            list = (List<Resource<AVALONHexClearingEntity>>)
                method.invoke(AVALONHexClearingController
                    .getInstance(),(String) field.get(
                        entity.getClearingFrom()));
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
      memberEntity = (AVALONHexClearingEntity)
          ((Resource) AVALONHexClearingController.getInstance().save(
              entity.getClearingFrom()).get(0)).getContent();
    }
    entity.setClearingFrom(memberEntity);
    list = null;
    }

  private void setClearingToIdFromRepository(
      final AVALONHexNodeEdgeEntity entity) {
    AVALONHexClearingEntity memberEntity = null;
    List<Resource<AVALONHexClearingEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = AVALONHexClearingController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = AVALONHexClearingEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getClearingTo()) != null) {
          list = (List<Resource<AVALONHexClearingEntity>>) method
              .invoke(
                  AVALONHexClearingController.getInstance(),
                  (String) field
                      .get(entity.getClearingTo()));
        }
      }
      if (list == null) {
        try {
          method = AVALONHexClearingController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = AVALONHexClearingEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getClearingTo()) != null) {
            list = (List<Resource<AVALONHexClearingEntity>>)
                method.invoke(AVALONHexClearingController
                    .getInstance(),(String) field.get(
                        entity.getClearingTo()));
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
      memberEntity = (AVALONHexClearingEntity)
          ((Resource) AVALONHexClearingController.getInstance().save(
              entity.getClearingTo()).get(0)).getContent();
    }
    entity.setClearingTo(memberEntity);
    list = null;
    }

  private void setPathIdFromRepository(
      final AVALONHexNodeEdgeEntity entity) {
    AVALONHexPathEntity memberEntity = null;
    List<Resource<AVALONHexPathEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = AVALONHexPathController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = AVALONHexPathEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getPath()) != null) {
          list = (List<Resource<AVALONHexPathEntity>>) method
              .invoke(
                  AVALONHexPathController.getInstance(),
                  (String) field
                      .get(entity.getPath()));
        }
      }
      if (list == null) {
        try {
          method = AVALONHexPathController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = AVALONHexPathEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getPath()) != null) {
            list = (List<Resource<AVALONHexPathEntity>>)
                method.invoke(AVALONHexPathController
                    .getInstance(),(String) field.get(
                        entity.getPath()));
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
      memberEntity = (AVALONHexPathEntity)
          ((Resource) AVALONHexPathController.getInstance().save(
              entity.getPath()).get(0)).getContent();
    }
    entity.setPath(memberEntity);
    list = null;
    }


}
