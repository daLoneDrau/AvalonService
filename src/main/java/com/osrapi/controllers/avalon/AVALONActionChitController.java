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

import com.osrapi.models.avalon.AVALONActionChitEntity;
import com.osrapi.models.avalon.AVALONActionTypeEntity;
import com.osrapi.models.avalon.AVALONVulnerabilityEntity;
import com.osrapi.models.avalon.AVALONMagicColorEntity;

import com.osrapi.repositories.avalon.AVALONActionChitRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/action_chits")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONActionChitController {
    /** the static instance of {@link AVALONActionChitController}. */
    private static AVALONActionChitController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONActionChitController}
     */
    public static AVALONActionChitController getInstance() {
        if (instance == null) {
            new AVALONActionChitController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONActionChitRepository repository;
    /** Creates a new instance of {@link AVALONActionChitController}. */
    public AVALONActionChitController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONActionChitEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONActionChitEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONActionChitEntity>> getAll() {
        Iterator<AVALONActionChitEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONActionChitEntity>> resources =
                new ArrayList<Resource<AVALONActionChitEntity>>();
        while (iter.hasNext()) {
            resources.add(getActionChitResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONActionChitEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONActionChitEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONActionChitEntity>> getById(
            @PathVariable final Long id) {
        AVALONActionChitEntity entity = repository.findOne(id);
        List<Resource<AVALONActionChitEntity>> resources =
                new ArrayList<Resource<AVALONActionChitEntity>>();
        resources.add(getActionChitResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONActionChitEntity}.
     * @param entity the {@link AVALONActionChitEntity}
     * @return {@link Resource}<{@link AVALONActionChitEntity}>
     */
    private Resource<AVALONActionChitEntity> getActionChitResource(
            final AVALONActionChitEntity entity) {
        Resource<AVALONActionChitEntity> resource =
                new Resource<AVALONActionChitEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONActionChitEntity}s.
     * @param entities the list of {@link AVALONActionChitEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONActionChitEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONActionChitEntity>> save(
            @RequestBody final List<AVALONActionChitEntity> entities) {
        List<Resource<AVALONActionChitEntity>> resources =
                new ArrayList<Resource<AVALONActionChitEntity>>();
        Iterator<AVALONActionChitEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONActionChitEntity}.
     * @param entity the {@link AVALONActionChitEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONActionChitEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONActionChitEntity>> save(
            @RequestBody final AVALONActionChitEntity entity) {
            if (entity.getType() != null
        && entity.getType().getId() == null) {
      setTypeIdFromRepository(entity);
        }

        if (entity.getStrength() != null
        && entity.getStrength().getId() == null) {
      setStrengthIdFromRepository(entity);
        }

        if (entity.getMagicType() != null
        && entity.getMagicType().getId() == null) {
      setMagicTypeIdFromRepository(entity);
        }


    
        AVALONActionChitEntity savedEntity = repository.save(entity);
        List<Resource<AVALONActionChitEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONActionChitEntity} instance
     */
    private void setIdFromRepository(final AVALONActionChitEntity entity) {
        List<AVALONActionChitEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONActionChitEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONActionChitEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONActionChitEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONActionChitEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONActionChitEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONActionChitEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONActionChitEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONActionChitEntity}s.
     * @param entities the list of {@link AVALONActionChitEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONActionChitEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONActionChitEntity>> update(
            @RequestBody final List<AVALONActionChitEntity> entities) {
        List<Resource<AVALONActionChitEntity>> resources = new ArrayList<Resource<AVALONActionChitEntity>>();
        Iterator<AVALONActionChitEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONActionChitEntity}.
     * @param entity the {@link AVALONActionChitEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONActionChitEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONActionChitEntity>> update(
            @RequestBody final AVALONActionChitEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
            if (entity.getType() != null
        && entity.getType().getId() == null) {
      setTypeIdFromRepository(entity);
        }

        if (entity.getStrength() != null
        && entity.getStrength().getId() == null) {
      setStrengthIdFromRepository(entity);
        }

        if (entity.getMagicType() != null
        && entity.getMagicType().getId() == null) {
      setMagicTypeIdFromRepository(entity);
        }


    
        AVALONActionChitEntity savedEntity = repository.save(entity);
        List<Resource<AVALONActionChitEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setTypeIdFromRepository(
      final AVALONActionChitEntity entity) {
    AVALONActionTypeEntity memberEntity = null;
    List<Resource<AVALONActionTypeEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = AVALONActionTypeController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = AVALONActionTypeEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getType()) != null) {
          list = (List<Resource<AVALONActionTypeEntity>>) method
              .invoke(
                  AVALONActionTypeController.getInstance(),
                  (String) field
                      .get(entity.getType()));
        }
      }
      if (list == null) {
        try {
          method = AVALONActionTypeController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = AVALONActionTypeEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getType()) != null) {
            list = (List<Resource<AVALONActionTypeEntity>>)
                method.invoke(AVALONActionTypeController
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
      memberEntity = (AVALONActionTypeEntity)
          ((Resource) AVALONActionTypeController.getInstance().save(
              entity.getType()).get(0)).getContent();
    }
    entity.setType(memberEntity);
    list = null;
    }

  private void setStrengthIdFromRepository(
      final AVALONActionChitEntity entity) {
    AVALONVulnerabilityEntity memberEntity = null;
    List<Resource<AVALONVulnerabilityEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = AVALONVulnerabilityController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = AVALONVulnerabilityEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getStrength()) != null) {
          list = (List<Resource<AVALONVulnerabilityEntity>>) method
              .invoke(
                  AVALONVulnerabilityController.getInstance(),
                  (String) field
                      .get(entity.getStrength()));
        }
      }
      if (list == null) {
        try {
          method = AVALONVulnerabilityController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = AVALONVulnerabilityEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getStrength()) != null) {
            list = (List<Resource<AVALONVulnerabilityEntity>>)
                method.invoke(AVALONVulnerabilityController
                    .getInstance(),(String) field.get(
                        entity.getStrength()));
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
      memberEntity = (AVALONVulnerabilityEntity)
          ((Resource) AVALONVulnerabilityController.getInstance().save(
              entity.getStrength()).get(0)).getContent();
    }
    entity.setStrength(memberEntity);
    list = null;
    }

  private void setMagicTypeIdFromRepository(
      final AVALONActionChitEntity entity) {
    AVALONMagicColorEntity memberEntity = null;
    List<Resource<AVALONMagicColorEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = AVALONMagicColorController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = AVALONMagicColorEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getMagicType()) != null) {
          list = (List<Resource<AVALONMagicColorEntity>>) method
              .invoke(
                  AVALONMagicColorController.getInstance(),
                  (String) field
                      .get(entity.getMagicType()));
        }
      }
      if (list == null) {
        try {
          method = AVALONMagicColorController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = AVALONMagicColorEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getMagicType()) != null) {
            list = (List<Resource<AVALONMagicColorEntity>>)
                method.invoke(AVALONMagicColorController
                    .getInstance(),(String) field.get(
                        entity.getMagicType()));
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
      memberEntity = (AVALONMagicColorEntity)
          ((Resource) AVALONMagicColorController.getInstance().save(
              entity.getMagicType()).get(0)).getContent();
    }
    entity.setMagicType(memberEntity);
    list = null;
    }


    /**
     * Gets a list of {@link AVALONActionChitEntity}s that share a speed.
     * @param speed the action_chit' speed
     * @return {@link List}<{@link Resource}<{@link AVALONActionChitEntity}>>
     */
    @RequestMapping(path = "speed/{speed}",
            method = RequestMethod.GET)
    public List<Resource<AVALONActionChitEntity>> getBySpeed(
            @PathVariable final Long speed) {
        Iterator<AVALONActionChitEntity> iter = repository.findBySpeed(speed)
                .iterator();
        List<Resource<AVALONActionChitEntity>> resources =
                new ArrayList<Resource<AVALONActionChitEntity>>();
        while (iter.hasNext()) {
            resources.add(getActionChitResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONActionChitEntity}s that share a fatigueAsterisk.
     * @param fatigueAsterisk the action_chit' fatigueAsterisk
     * @return {@link List}<{@link Resource}<{@link AVALONActionChitEntity}>>
     */
    @RequestMapping(path = "fatigue_asterisk/{fatigueAsterisk}",
            method = RequestMethod.GET)
    public List<Resource<AVALONActionChitEntity>> getByFatigueAsterisk(
            @PathVariable final Long fatigueAsterisk) {
        Iterator<AVALONActionChitEntity> iter = repository.findByFatigueAsterisk(fatigueAsterisk)
                .iterator();
        List<Resource<AVALONActionChitEntity>> resources =
                new ArrayList<Resource<AVALONActionChitEntity>>();
        while (iter.hasNext()) {
            resources.add(getActionChitResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONActionChitEntity}s that share a code.
     * @param code the action_chit' code
     * @return {@link List}<{@link Resource}<{@link AVALONActionChitEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<AVALONActionChitEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<AVALONActionChitEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<AVALONActionChitEntity>> resources =
                new ArrayList<Resource<AVALONActionChitEntity>>();
        while (iter.hasNext()) {
            resources.add(getActionChitResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
