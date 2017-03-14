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

import com.osrapi.models.avalon.AVALONHexSideEdgeEntity;
import com.osrapi.models.avalon.AVALONHexClearingEntity;
import com.osrapi.models.avalon.AVALONHexPathEntity;

import com.osrapi.repositories.avalon.AVALONHexSideEdgeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/hex_side_edges")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONHexSideEdgeController {
    /** the static instance of {@link AVALONHexSideEdgeController}. */
    private static AVALONHexSideEdgeController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONHexSideEdgeController}
     */
    public static AVALONHexSideEdgeController getInstance() {
        if (instance == null) {
            new AVALONHexSideEdgeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONHexSideEdgeRepository repository;
    /** Creates a new instance of {@link AVALONHexSideEdgeController}. */
    public AVALONHexSideEdgeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONHexSideEdgeEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONHexSideEdgeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONHexSideEdgeEntity>> getAll() {
        Iterator<AVALONHexSideEdgeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONHexSideEdgeEntity>> resources =
                new ArrayList<Resource<AVALONHexSideEdgeEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexSideEdgeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONHexSideEdgeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONHexSideEdgeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONHexSideEdgeEntity>> getById(
            @PathVariable final Long id) {
        AVALONHexSideEdgeEntity entity = repository.findOne(id);
        List<Resource<AVALONHexSideEdgeEntity>> resources =
                new ArrayList<Resource<AVALONHexSideEdgeEntity>>();
        resources.add(getHexSideEdgeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONHexSideEdgeEntity}.
     * @param entity the {@link AVALONHexSideEdgeEntity}
     * @return {@link Resource}<{@link AVALONHexSideEdgeEntity}>
     */
    private Resource<AVALONHexSideEdgeEntity> getHexSideEdgeResource(
            final AVALONHexSideEdgeEntity entity) {
        Resource<AVALONHexSideEdgeEntity> resource =
                new Resource<AVALONHexSideEdgeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONHexSideEdgeEntity}s.
     * @param entities the list of {@link AVALONHexSideEdgeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexSideEdgeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONHexSideEdgeEntity>> save(
            @RequestBody final List<AVALONHexSideEdgeEntity> entities) {
        List<Resource<AVALONHexSideEdgeEntity>> resources =
                new ArrayList<Resource<AVALONHexSideEdgeEntity>>();
        Iterator<AVALONHexSideEdgeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONHexSideEdgeEntity}.
     * @param entity the {@link AVALONHexSideEdgeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexSideEdgeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONHexSideEdgeEntity>> save(
            @RequestBody final AVALONHexSideEdgeEntity entity) {
            if (entity.getClearingFrom() != null
        && entity.getClearingFrom().getId() == null) {
      setClearingFromIdFromRepository(entity);
        }

        if (entity.getPath() != null
        && entity.getPath().getId() == null) {
      setPathIdFromRepository(entity);
        }


    
        AVALONHexSideEdgeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexSideEdgeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONHexSideEdgeEntity} instance
     */
    private void setIdFromRepository(final AVALONHexSideEdgeEntity entity) {
        List<AVALONHexSideEdgeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONHexSideEdgeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONHexSideEdgeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONHexSideEdgeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONHexSideEdgeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONHexSideEdgeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONHexSideEdgeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONHexSideEdgeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONHexSideEdgeEntity}s.
     * @param entities the list of {@link AVALONHexSideEdgeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexSideEdgeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONHexSideEdgeEntity>> update(
            @RequestBody final List<AVALONHexSideEdgeEntity> entities) {
        List<Resource<AVALONHexSideEdgeEntity>> resources = new ArrayList<Resource<AVALONHexSideEdgeEntity>>();
        Iterator<AVALONHexSideEdgeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONHexSideEdgeEntity}.
     * @param entity the {@link AVALONHexSideEdgeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexSideEdgeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONHexSideEdgeEntity>> update(
            @RequestBody final AVALONHexSideEdgeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
            if (entity.getClearingFrom() != null
        && entity.getClearingFrom().getId() == null) {
      setClearingFromIdFromRepository(entity);
        }

        if (entity.getPath() != null
        && entity.getPath().getId() == null) {
      setPathIdFromRepository(entity);
        }


    
        AVALONHexSideEdgeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexSideEdgeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setClearingFromIdFromRepository(
      final AVALONHexSideEdgeEntity entity) {
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

  private void setPathIdFromRepository(
      final AVALONHexSideEdgeEntity entity) {
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


    /**
     * Gets a list of {@link AVALONHexSideEdgeEntity}s that share a side.
     * @param side the hex_side_edge' side
     * @return {@link List}<{@link Resource}<{@link AVALONHexSideEdgeEntity}>>
     */
    @RequestMapping(path = "side/{side}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexSideEdgeEntity>> getBySide(
            @PathVariable final Long side) {
        Iterator<AVALONHexSideEdgeEntity> iter = repository.findBySide(side)
                .iterator();
        List<Resource<AVALONHexSideEdgeEntity>> resources =
                new ArrayList<Resource<AVALONHexSideEdgeEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexSideEdgeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
