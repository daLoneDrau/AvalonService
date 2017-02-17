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

import com.osrapi.models.avalon.AVALONIoNpcDataEntity;
import com.osrapi.models.avalon.AVALONGenderEntity;

import com.osrapi.repositories.avalon.AVALONIoNpcDataRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/io_npc_data")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONIoNpcDataController {
    /** the static instance of {@link AVALONIoNpcDataController}. */
    private static AVALONIoNpcDataController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONIoNpcDataController}
     */
    public static AVALONIoNpcDataController getInstance() {
        if (instance == null) {
            new AVALONIoNpcDataController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONIoNpcDataRepository repository;
    /** Creates a new instance of {@link AVALONIoNpcDataController}. */
    public AVALONIoNpcDataController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getAll() {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONIoNpcDataEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getById(
            @PathVariable final Long id) {
        AVALONIoNpcDataEntity entity = repository.findOne(id);
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        resources.add(getIoNpcDataResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONIoNpcDataEntity}.
     * @param entity the {@link AVALONIoNpcDataEntity}
     * @return {@link Resource}<{@link AVALONIoNpcDataEntity}>
     */
    private Resource<AVALONIoNpcDataEntity> getIoNpcDataResource(
            final AVALONIoNpcDataEntity entity) {
        Resource<AVALONIoNpcDataEntity> resource =
                new Resource<AVALONIoNpcDataEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONIoNpcDataEntity}s.
     * @param entities the list of {@link AVALONIoNpcDataEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONIoNpcDataEntity>> save(
            @RequestBody final List<AVALONIoNpcDataEntity> entities) {
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        Iterator<AVALONIoNpcDataEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONIoNpcDataEntity}.
     * @param entity the {@link AVALONIoNpcDataEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONIoNpcDataEntity>> save(
            @RequestBody final AVALONIoNpcDataEntity entity) {
            if (entity.getGender() != null
        && entity.getGender().getId() == null) {
      setGenderIdFromRepository(entity);
        }


    
        AVALONIoNpcDataEntity savedEntity = repository.save(entity);
        List<Resource<AVALONIoNpcDataEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONIoNpcDataEntity} instance
     */
    private void setIdFromRepository(final AVALONIoNpcDataEntity entity) {
        List<AVALONIoNpcDataEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONIoNpcDataEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONIoNpcDataEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONIoNpcDataEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONIoNpcDataEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONIoNpcDataEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONIoNpcDataEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONIoNpcDataEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONIoNpcDataEntity}s.
     * @param entities the list of {@link AVALONIoNpcDataEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONIoNpcDataEntity>> update(
            @RequestBody final List<AVALONIoNpcDataEntity> entities) {
        List<Resource<AVALONIoNpcDataEntity>> resources = new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        Iterator<AVALONIoNpcDataEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONIoNpcDataEntity}.
     * @param entity the {@link AVALONIoNpcDataEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONIoNpcDataEntity>> update(
            @RequestBody final AVALONIoNpcDataEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
            if (entity.getGender() != null
        && entity.getGender().getId() == null) {
      setGenderIdFromRepository(entity);
        }


    
        AVALONIoNpcDataEntity savedEntity = repository.save(entity);
        List<Resource<AVALONIoNpcDataEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setGenderIdFromRepository(
      final AVALONIoNpcDataEntity entity) {
    AVALONGenderEntity memberEntity = null;
    List<Resource<AVALONGenderEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = AVALONGenderController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = AVALONGenderEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getGender()) != null) {
          list = (List<Resource<AVALONGenderEntity>>) method
              .invoke(
                  AVALONGenderController.getInstance(),
                  (String) field
                      .get(entity.getGender()));
        }
      }
      if (list == null) {
        try {
          method = AVALONGenderController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = AVALONGenderEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getGender()) != null) {
            list = (List<Resource<AVALONGenderEntity>>)
                method.invoke(AVALONGenderController
                    .getInstance(),(String) field.get(
                        entity.getGender()));
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
      memberEntity = (AVALONGenderEntity)
          ((Resource) AVALONGenderController.getInstance().save(
              entity.getGender()).get(0)).getContent();
    }
    entity.setGender(memberEntity);
    list = null;
    }


    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a behavior.
     * @param behavior the io_npc_data' behavior
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "behavior/{behavior}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByBehavior(
            @PathVariable final Long behavior) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByBehavior(behavior)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a behaviorParam.
     * @param behaviorParam the io_npc_data' behaviorParam
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "behavior_param/{behaviorParam}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByBehaviorParam(
            @PathVariable final Float behaviorParam) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByBehaviorParam(behaviorParam)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a climbCount.
     * @param climbCount the io_npc_data' climbCount
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "climb_count/{climbCount}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByClimbCount(
            @PathVariable final Float climbCount) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByClimbCount(climbCount)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a collidState.
     * @param collidState the io_npc_data' collidState
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "collid_state/{collidState}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByCollidState(
            @PathVariable final Long collidState) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByCollidState(collidState)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a collidTime.
     * @param collidTime the io_npc_data' collidTime
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "collid_time/{collidTime}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByCollidTime(
            @PathVariable final Long collidTime) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByCollidTime(collidTime)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a critical.
     * @param critical the io_npc_data' critical
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "critical/{critical}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByCritical(
            @PathVariable final Float critical) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByCritical(critical)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a cut.
     * @param cut the io_npc_data' cut
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "cut/{cut}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByCut(
            @PathVariable final Boolean cut) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByCut(cut)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a cuts.
     * @param cuts the io_npc_data' cuts
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "cuts/{cuts}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByCuts(
            @PathVariable final Long cuts) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByCuts(cuts)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a damages.
     * @param damages the io_npc_data' damages
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "damages/{damages}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByDamages(
            @PathVariable final Float damages) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByDamages(damages)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a internalScript.
     * @param internalScript the io_npc_data' internalScript
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "internal_script/{internalScript}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByInternalScript(
            @PathVariable final String internalScript) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByInternalScript(internalScript)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a life.
     * @param life the io_npc_data' life
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "life/{life}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByLife(
            @PathVariable final Float life) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByLife(life)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a mana.
     * @param mana the io_npc_data' mana
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "mana/{mana}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByMana(
            @PathVariable final Float mana) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByMana(mana)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a maxlife.
     * @param maxlife the io_npc_data' maxlife
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "maxlife/{maxlife}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByMaxlife(
            @PathVariable final Float maxlife) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByMaxlife(maxlife)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a maxmana.
     * @param maxmana the io_npc_data' maxmana
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "maxmana/{maxmana}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByMaxmana(
            @PathVariable final Float maxmana) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByMaxmana(maxmana)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a name.
     * @param name the io_npc_data' name
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByName(
            @PathVariable final String name) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a npcFlags.
     * @param npcFlags the io_npc_data' npcFlags
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "npc_flags/{npcFlags}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByNpcFlags(
            @PathVariable final Long npcFlags) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByNpcFlags(npcFlags)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a title.
     * @param title the io_npc_data' title
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "title/{title}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByTitle(
            @PathVariable final String title) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByTitle(title)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a weapon.
     * @param weapon the io_npc_data' weapon
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "weapon/{weapon}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByWeapon(
            @PathVariable final String weapon) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByWeapon(weapon)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a xpvalue.
     * @param xpvalue the io_npc_data' xpvalue
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "xpvalue/{xpvalue}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByXpvalue(
            @PathVariable final Long xpvalue) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByXpvalue(xpvalue)
                .iterator();
        List<Resource<AVALONIoNpcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
