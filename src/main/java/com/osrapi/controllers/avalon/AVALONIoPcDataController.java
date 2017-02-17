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

import com.osrapi.models.avalon.AVALONIoPcDataEntity;
import com.osrapi.models.avalon.AVALONGenderEntity;

import com.osrapi.repositories.avalon.AVALONIoPcDataRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/io_pc_data")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONIoPcDataController {
    /** the static instance of {@link AVALONIoPcDataController}. */
    private static AVALONIoPcDataController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONIoPcDataController}
     */
    public static AVALONIoPcDataController getInstance() {
        if (instance == null) {
            new AVALONIoPcDataController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONIoPcDataRepository repository;
    /** Creates a new instance of {@link AVALONIoPcDataController}. */
    public AVALONIoPcDataController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONIoPcDataEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONIoPcDataEntity>> getAll() {
        Iterator<AVALONIoPcDataEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONIoPcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONIoPcDataEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONIoPcDataEntity>> getById(
            @PathVariable final Long id) {
        AVALONIoPcDataEntity entity = repository.findOne(id);
        List<Resource<AVALONIoPcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoPcDataEntity>>();
        resources.add(getIoPcDataResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONIoPcDataEntity}.
     * @param entity the {@link AVALONIoPcDataEntity}
     * @return {@link Resource}<{@link AVALONIoPcDataEntity}>
     */
    private Resource<AVALONIoPcDataEntity> getIoPcDataResource(
            final AVALONIoPcDataEntity entity) {
        Resource<AVALONIoPcDataEntity> resource =
                new Resource<AVALONIoPcDataEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONIoPcDataEntity}s.
     * @param entities the list of {@link AVALONIoPcDataEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONIoPcDataEntity>> save(
            @RequestBody final List<AVALONIoPcDataEntity> entities) {
        List<Resource<AVALONIoPcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoPcDataEntity>>();
        Iterator<AVALONIoPcDataEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONIoPcDataEntity}.
     * @param entity the {@link AVALONIoPcDataEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONIoPcDataEntity>> save(
            @RequestBody final AVALONIoPcDataEntity entity) {
            if (entity.getGender() != null
        && entity.getGender().getId() == null) {
      setGenderIdFromRepository(entity);
        }


    
        AVALONIoPcDataEntity savedEntity = repository.save(entity);
        List<Resource<AVALONIoPcDataEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONIoPcDataEntity} instance
     */
    private void setIdFromRepository(final AVALONIoPcDataEntity entity) {
        List<AVALONIoPcDataEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONIoPcDataEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONIoPcDataEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONIoPcDataEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONIoPcDataEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONIoPcDataEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONIoPcDataEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONIoPcDataEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONIoPcDataEntity}s.
     * @param entities the list of {@link AVALONIoPcDataEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONIoPcDataEntity>> update(
            @RequestBody final List<AVALONIoPcDataEntity> entities) {
        List<Resource<AVALONIoPcDataEntity>> resources = new ArrayList<Resource<AVALONIoPcDataEntity>>();
        Iterator<AVALONIoPcDataEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONIoPcDataEntity}.
     * @param entity the {@link AVALONIoPcDataEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONIoPcDataEntity>> update(
            @RequestBody final AVALONIoPcDataEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
            if (entity.getGender() != null
        && entity.getGender().getId() == null) {
      setGenderIdFromRepository(entity);
        }


    
        AVALONIoPcDataEntity savedEntity = repository.save(entity);
        List<Resource<AVALONIoPcDataEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setGenderIdFromRepository(
      final AVALONIoPcDataEntity entity) {
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
     * Gets a list of {@link AVALONIoPcDataEntity}s that share a bags.
     * @param bags the io_pc_data' bags
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "bags/{bags}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoPcDataEntity>> getByBags(
            @PathVariable final Long bags) {
        Iterator<AVALONIoPcDataEntity> iter = repository.findByBags(bags)
                .iterator();
        List<Resource<AVALONIoPcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoPcDataEntity}s that share a gold.
     * @param gold the io_pc_data' gold
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "gold/{gold}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoPcDataEntity>> getByGold(
            @PathVariable final float gold) {
        Iterator<AVALONIoPcDataEntity> iter = repository.findByGold(gold)
                .iterator();
        List<Resource<AVALONIoPcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoPcDataEntity}s that share a interfaceFlags.
     * @param interfaceFlags the io_pc_data' interfaceFlags
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "interface_flags/{interfaceFlags}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoPcDataEntity>> getByInterfaceFlags(
            @PathVariable final Long interfaceFlags) {
        Iterator<AVALONIoPcDataEntity> iter = repository.findByInterfaceFlags(interfaceFlags)
                .iterator();
        List<Resource<AVALONIoPcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoPcDataEntity}s that share a level.
     * @param level the io_pc_data' level
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "level/{level}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoPcDataEntity>> getByLevel(
            @PathVariable final Long level) {
        Iterator<AVALONIoPcDataEntity> iter = repository.findByLevel(level)
                .iterator();
        List<Resource<AVALONIoPcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoPcDataEntity}s that share a name.
     * @param name the io_pc_data' name
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoPcDataEntity>> getByName(
            @PathVariable final String name) {
        Iterator<AVALONIoPcDataEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<AVALONIoPcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONIoPcDataEntity}s that share a xp.
     * @param xp the io_pc_data' xp
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "xp/{xp}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoPcDataEntity>> getByXp(
            @PathVariable final Long xp) {
        Iterator<AVALONIoPcDataEntity> iter = repository.findByXp(xp)
                .iterator();
        List<Resource<AVALONIoPcDataEntity>> resources =
                new ArrayList<Resource<AVALONIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
