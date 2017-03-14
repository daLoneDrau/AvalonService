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

import com.osrapi.models.avalon.AVALONHexNodeEntity;
import com.osrapi.models.avalon.AVALONVector3Entity;
import com.osrapi.models.avalon.AVALONHexTerrainTypeEntity;

import com.osrapi.repositories.avalon.AVALONHexNodeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/hex_nodes")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONHexNodeController {
    /** the static instance of {@link AVALONHexNodeController}. */
    private static AVALONHexNodeController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONHexNodeController}
     */
    public static AVALONHexNodeController getInstance() {
        if (instance == null) {
            new AVALONHexNodeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONHexNodeRepository repository;
    /** Creates a new instance of {@link AVALONHexNodeController}. */
    public AVALONHexNodeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONHexNodeEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONHexNodeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONHexNodeEntity>> getAll() {
        Iterator<AVALONHexNodeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONHexNodeEntity>> resources =
                new ArrayList<Resource<AVALONHexNodeEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexNodeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONHexNodeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONHexNodeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONHexNodeEntity>> getById(
            @PathVariable final Long id) {
        AVALONHexNodeEntity entity = repository.findOne(id);
        List<Resource<AVALONHexNodeEntity>> resources =
                new ArrayList<Resource<AVALONHexNodeEntity>>();
        resources.add(getHexNodeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONHexNodeEntity}.
     * @param entity the {@link AVALONHexNodeEntity}
     * @return {@link Resource}<{@link AVALONHexNodeEntity}>
     */
    private Resource<AVALONHexNodeEntity> getHexNodeResource(
            final AVALONHexNodeEntity entity) {
        Resource<AVALONHexNodeEntity> resource =
                new Resource<AVALONHexNodeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONHexNodeEntity}s.
     * @param entities the list of {@link AVALONHexNodeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexNodeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONHexNodeEntity>> save(
            @RequestBody final List<AVALONHexNodeEntity> entities) {
        List<Resource<AVALONHexNodeEntity>> resources =
                new ArrayList<Resource<AVALONHexNodeEntity>>();
        Iterator<AVALONHexNodeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONHexNodeEntity}.
     * @param entity the {@link AVALONHexNodeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexNodeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONHexNodeEntity>> save(
            @RequestBody final AVALONHexNodeEntity entity) {
            if (entity.getLocation() != null
        && entity.getLocation().getId() == null) {
      setLocationIdFromRepository(entity);
        }

        if (entity.getType() != null
        && entity.getType().getId() == null) {
      setTypeIdFromRepository(entity);
        }


    
        AVALONHexNodeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexNodeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONHexNodeEntity} instance
     */
    private void setIdFromRepository(final AVALONHexNodeEntity entity) {
        List<AVALONHexNodeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONHexNodeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONHexNodeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONHexNodeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONHexNodeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONHexNodeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONHexNodeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONHexNodeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONHexNodeEntity}s.
     * @param entities the list of {@link AVALONHexNodeEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexNodeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONHexNodeEntity>> update(
            @RequestBody final List<AVALONHexNodeEntity> entities) {
        List<Resource<AVALONHexNodeEntity>> resources = new ArrayList<Resource<AVALONHexNodeEntity>>();
        Iterator<AVALONHexNodeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONHexNodeEntity}.
     * @param entity the {@link AVALONHexNodeEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexNodeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONHexNodeEntity>> update(
            @RequestBody final AVALONHexNodeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
            if (entity.getLocation() != null
        && entity.getLocation().getId() == null) {
      setLocationIdFromRepository(entity);
        }

        if (entity.getType() != null
        && entity.getType().getId() == null) {
      setTypeIdFromRepository(entity);
        }


    
        AVALONHexNodeEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexNodeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setLocationIdFromRepository(
      final AVALONHexNodeEntity entity) {
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

  private void setTypeIdFromRepository(
      final AVALONHexNodeEntity entity) {
    AVALONHexTerrainTypeEntity memberEntity = null;
    List<Resource<AVALONHexTerrainTypeEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = AVALONHexTerrainTypeController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = AVALONHexTerrainTypeEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getType()) != null) {
          list = (List<Resource<AVALONHexTerrainTypeEntity>>) method
              .invoke(
                  AVALONHexTerrainTypeController.getInstance(),
                  (String) field
                      .get(entity.getType()));
        }
      }
      if (list == null) {
        try {
          method = AVALONHexTerrainTypeController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = AVALONHexTerrainTypeEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getType()) != null) {
            list = (List<Resource<AVALONHexTerrainTypeEntity>>)
                method.invoke(AVALONHexTerrainTypeController
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
      memberEntity = (AVALONHexTerrainTypeEntity)
          ((Resource) AVALONHexTerrainTypeController.getInstance().save(
              entity.getType()).get(0)).getContent();
    }
    entity.setType(memberEntity);
    list = null;
    }


    /**
     * Gets a list of {@link AVALONHexNodeEntity}s that share a code.
     * @param code the hex_node' code
     * @return {@link List}<{@link Resource}<{@link AVALONHexNodeEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexNodeEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<AVALONHexNodeEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<AVALONHexNodeEntity>> resources =
                new ArrayList<Resource<AVALONHexNodeEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexNodeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
