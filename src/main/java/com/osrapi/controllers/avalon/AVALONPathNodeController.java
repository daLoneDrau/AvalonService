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

import com.osrapi.models.avalon.AVALONPathNodeEntity;
import com.osrapi.models.avalon.AVALONVector3Entity;

import com.osrapi.repositories.avalon.AVALONPathNodeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/path_nodes")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONPathNodeController {
    /** the static instance of {@link AVALONPathNodeController}. */
    private static AVALONPathNodeController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONPathNodeController}
     */
    public static AVALONPathNodeController getInstance() {
        if (instance == null) {
            new AVALONPathNodeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONPathNodeRepository repository;
    /** Creates a new instance of {@link AVALONPathNodeController}. */
    public AVALONPathNodeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONPathNodeEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONPathNodeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONPathNodeEntity>> getAll() {
        Iterator<AVALONPathNodeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONPathNodeEntity>> resources =
                new ArrayList<Resource<AVALONPathNodeEntity>>();
        while (iter.hasNext()) {
            resources.add(getPathNodeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONPathNodeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONPathNodeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONPathNodeEntity>> getById(
            @PathVariable final Long id) {
        AVALONPathNodeEntity entity = repository.findOne(id);
        List<Resource<AVALONPathNodeEntity>> resources =
                new ArrayList<Resource<AVALONPathNodeEntity>>();
        resources.add(getPathNodeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONPathNodeEntity}.
     * @param entity the {@link AVALONPathNodeEntity}
     * @return {@link Resource}<{@link AVALONPathNodeEntity}>
     */
    private Resource<AVALONPathNodeEntity> getPathNodeResource(
            final AVALONPathNodeEntity entity) {
        Resource<AVALONPathNodeEntity> resource =
                new Resource<AVALONPathNodeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONPathNodeEntity}s.
     * @param entities the list of {@link AVALONPathNodeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONPathNodeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONPathNodeEntity>> save(
            @RequestBody final List<AVALONPathNodeEntity> entities) {
        List<Resource<AVALONPathNodeEntity>> resources =
                new ArrayList<Resource<AVALONPathNodeEntity>>();
        Iterator<AVALONPathNodeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONPathNodeEntity}.
     * @param entity the {@link AVALONPathNodeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONPathNodeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONPathNodeEntity>> save(
            @RequestBody final AVALONPathNodeEntity entity) {
            if (entity.getNode() != null
        && entity.getNode().getId() == null) {
      setNodeIdFromRepository(entity);
        }


    
        AVALONPathNodeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONPathNodeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONPathNodeEntity} instance
     */
    private void setIdFromRepository(final AVALONPathNodeEntity entity) {
        List<AVALONPathNodeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONPathNodeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONPathNodeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONPathNodeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONPathNodeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONPathNodeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONPathNodeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONPathNodeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONPathNodeEntity}s.
     * @param entities the list of {@link AVALONPathNodeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONPathNodeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONPathNodeEntity>> update(
            @RequestBody final List<AVALONPathNodeEntity> entities) {
        List<Resource<AVALONPathNodeEntity>> resources = new ArrayList<Resource<AVALONPathNodeEntity>>();
        Iterator<AVALONPathNodeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONPathNodeEntity}.
     * @param entity the {@link AVALONPathNodeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONPathNodeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONPathNodeEntity>> update(
            @RequestBody final AVALONPathNodeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
            if (entity.getNode() != null
        && entity.getNode().getId() == null) {
      setNodeIdFromRepository(entity);
        }


    
        AVALONPathNodeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONPathNodeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setNodeIdFromRepository(
      final AVALONPathNodeEntity entity) {
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
        if (field.get(entity.getNode()) != null) {
          list = (List<Resource<AVALONVector3Entity>>) method
              .invoke(
                  AVALONVector3Controller.getInstance(),
                  (String) field
                      .get(entity.getNode()));
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
          if (field.get(entity.getNode()) != null) {
            list = (List<Resource<AVALONVector3Entity>>)
                method.invoke(AVALONVector3Controller
                    .getInstance(),(String) field.get(
                        entity.getNode()));
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
              entity.getNode()).get(0)).getContent();
    }
    entity.setNode(memberEntity);
    list = null;
    }


    /**
     * Gets a list of {@link AVALONPathNodeEntity}s that share a sortOrder.
     * @param sortOrder the path_node' sortOrder
     * @return {@link List}<{@link Resource}<{@link AVALONPathNodeEntity}>>
     */
    @RequestMapping(path = "sort_order/{sortOrder}",
            method = RequestMethod.GET)
    public List<Resource<AVALONPathNodeEntity>> getBySortOrder(
            @PathVariable final Long sortOrder) {
        Iterator<AVALONPathNodeEntity> iter = repository.findBySortOrder(sortOrder)
                .iterator();
        List<Resource<AVALONPathNodeEntity>> resources =
                new ArrayList<Resource<AVALONPathNodeEntity>>();
        while (iter.hasNext()) {
            resources.add(getPathNodeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
