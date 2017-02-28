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
import com.osrapi.models.avalon.AVALONVulnerabilityEntity;
import com.osrapi.models.avalon.AVALONGenderEntity;
import com.osrapi.models.avalon.AVALONVulnerabilityEntity;
import com.osrapi.models.avalon.AVALONAttackTypeEntity;
import com.osrapi.models.avalon.AVALONMagicTypeEntity;
import com.osrapi.models.avalon.AVALONVulnerabilityEntity;
import com.osrapi.models.avalon.AVALONVulnerabilityEntity;
import com.osrapi.models.avalon.AVALONVulnerabilityEntity;
import com.osrapi.models.avalon.AVALONGroupEntity;
import com.osrapi.models.avalon.AVALONIoItemDataEntity;

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
        if (entity.getGroups() != null
                && !entity.getGroups().isEmpty()) {
            for (int i = entity.getGroups().size() - 1; i >= 0; i--) {
                AVALONGroupEntity groups = null;
                List<Resource<AVALONGroupEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONGroupController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONGroupEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONGroupEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONGroupEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getGroups().get(i)) != null) {
                            list = (List<Resource<AVALONGroupEntity>>) method
                                    .invoke(
                                            AVALONGroupController.getInstance(),
                                            (String) field.get(entity.getGroups().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONGroupController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONGroupEntity from Controller by code");
            }
            try {
              field = AVALONGroupEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONGroupEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getGroups().get(i)) != null) {
                                list = (List<Resource<AVALONGroupEntity>>) method
                                        .invoke(
                                                AVALONGroupController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getGroups().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONGroupEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    groups = list.get(0).getContent();
                }
                if (groups == null) {
                    groups = (AVALONGroupEntity) ((Resource) AVALONGroupController
                            .getInstance()
                            .save(entity.getGroups().get(i)).get(0)).getContent();
                }
                entity.getGroups().set(i, groups);
                list = null;
            }
        }

    if (entity.getInventoryItems() != null
                && !entity.getInventoryItems().isEmpty()) {
            for (int i = entity.getInventoryItems().size() - 1; i >= 0; i--) {
                AVALONIoItemDataEntity inventoryItems = null;
                List<Resource<AVALONIoItemDataEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONIoItemDataController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONIoItemDataEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONIoItemDataEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONIoItemDataEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getInventoryItems().get(i)) != null) {
                            list = (List<Resource<AVALONIoItemDataEntity>>) method
                                    .invoke(
                                            AVALONIoItemDataController.getInstance(),
                                            (String) field.get(entity.getInventoryItems().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONIoItemDataController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONIoItemDataEntity from Controller by code");
            }
            try {
              field = AVALONIoItemDataEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONIoItemDataEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getInventoryItems().get(i)) != null) {
                                list = (List<Resource<AVALONIoItemDataEntity>>) method
                                        .invoke(
                                                AVALONIoItemDataController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getInventoryItems().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONIoItemDataEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    inventoryItems = list.get(0).getContent();
                }
                if (inventoryItems == null) {
                    inventoryItems = (AVALONIoItemDataEntity) ((Resource) AVALONIoItemDataController
                            .getInstance()
                            .save(entity.getInventoryItems().get(i)).get(0)).getContent();
                }
                entity.getInventoryItems().set(i, inventoryItems);
                list = null;
            }
        }

        if (entity.getAlertedAttackWeight() != null
        && entity.getAlertedAttackWeight().getId() == null) {
      setAlertedAttackWeightIdFromRepository(entity);
        }

        if (entity.getGender() != null
        && entity.getGender().getId() == null) {
      setGenderIdFromRepository(entity);
        }

        if (entity.getMoveStrength() != null
        && entity.getMoveStrength().getId() == null) {
      setMoveStrengthIdFromRepository(entity);
        }

        if (entity.getNaturalWeaponType() != null
        && entity.getNaturalWeaponType().getId() == null) {
      setNaturalWeaponTypeIdFromRepository(entity);
        }

        if (entity.getUnalertedAttackSpell() != null
        && entity.getUnalertedAttackSpell().getId() == null) {
      setUnalertedAttackSpellIdFromRepository(entity);
        }

        if (entity.getUnalertedAttackWeight() != null
        && entity.getUnalertedAttackWeight().getId() == null) {
      setUnalertedAttackWeightIdFromRepository(entity);
        }

        if (entity.getVulnerability() != null
        && entity.getVulnerability().getId() == null) {
      setVulnerabilityIdFromRepository(entity);
        }

        if (entity.getWeight() != null
        && entity.getWeight().getId() == null) {
      setWeightIdFromRepository(entity);
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
        if (entity.getGroups() != null
                && !entity.getGroups().isEmpty()) {
            for (int i = entity.getGroups().size() - 1; i >= 0; i--) {
                AVALONGroupEntity groups = null;
                List<Resource<AVALONGroupEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONGroupController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONGroupEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONGroupEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONGroupEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getGroups().get(i)) != null) {
                            list = (List<Resource<AVALONGroupEntity>>) method
                                    .invoke(
                                            AVALONGroupController.getInstance(),
                                            (String) field.get(entity.getGroups().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONGroupController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONGroupEntity from Controller by code");
            }
            try {
              field = AVALONGroupEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONGroupEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getGroups().get(i)) != null) {
                                list = (List<Resource<AVALONGroupEntity>>) method
                                        .invoke(
                                                AVALONGroupController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getGroups().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONGroupEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    groups = list.get(0).getContent();
                }
                if (groups == null) {
                    groups = (AVALONGroupEntity) ((Resource) AVALONGroupController
                            .getInstance()
                            .save(entity.getGroups().get(i)).get(0)).getContent();
                }
                entity.getGroups().set(i, groups);
                list = null;
            }
        }

    if (entity.getInventoryItems() != null
                && !entity.getInventoryItems().isEmpty()) {
            for (int i = entity.getInventoryItems().size() - 1; i >= 0; i--) {
                AVALONIoItemDataEntity inventoryItems = null;
                List<Resource<AVALONIoItemDataEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONIoItemDataController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONIoItemDataEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONIoItemDataEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONIoItemDataEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getInventoryItems().get(i)) != null) {
                            list = (List<Resource<AVALONIoItemDataEntity>>) method
                                    .invoke(
                                            AVALONIoItemDataController.getInstance(),
                                            (String) field.get(entity.getInventoryItems().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONIoItemDataController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONIoItemDataEntity from Controller by code");
            }
            try {
              field = AVALONIoItemDataEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONIoItemDataEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getInventoryItems().get(i)) != null) {
                                list = (List<Resource<AVALONIoItemDataEntity>>) method
                                        .invoke(
                                                AVALONIoItemDataController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getInventoryItems().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONIoItemDataEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    inventoryItems = list.get(0).getContent();
                }
                if (inventoryItems == null) {
                    inventoryItems = (AVALONIoItemDataEntity) ((Resource) AVALONIoItemDataController
                            .getInstance()
                            .save(entity.getInventoryItems().get(i)).get(0)).getContent();
                }
                entity.getInventoryItems().set(i, inventoryItems);
                list = null;
            }
        }

        if (entity.getAlertedAttackWeight() != null
        && entity.getAlertedAttackWeight().getId() == null) {
      setAlertedAttackWeightIdFromRepository(entity);
        }

        if (entity.getGender() != null
        && entity.getGender().getId() == null) {
      setGenderIdFromRepository(entity);
        }

        if (entity.getMoveStrength() != null
        && entity.getMoveStrength().getId() == null) {
      setMoveStrengthIdFromRepository(entity);
        }

        if (entity.getNaturalWeaponType() != null
        && entity.getNaturalWeaponType().getId() == null) {
      setNaturalWeaponTypeIdFromRepository(entity);
        }

        if (entity.getUnalertedAttackSpell() != null
        && entity.getUnalertedAttackSpell().getId() == null) {
      setUnalertedAttackSpellIdFromRepository(entity);
        }

        if (entity.getUnalertedAttackWeight() != null
        && entity.getUnalertedAttackWeight().getId() == null) {
      setUnalertedAttackWeightIdFromRepository(entity);
        }

        if (entity.getVulnerability() != null
        && entity.getVulnerability().getId() == null) {
      setVulnerabilityIdFromRepository(entity);
        }

        if (entity.getWeight() != null
        && entity.getWeight().getId() == null) {
      setWeightIdFromRepository(entity);
        }


    
        AVALONIoNpcDataEntity savedEntity = repository.save(entity);
        List<Resource<AVALONIoNpcDataEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setAlertedAttackWeightIdFromRepository(
      final AVALONIoNpcDataEntity entity) {
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
        if (field.get(entity.getAlertedAttackWeight()) != null) {
          list = (List<Resource<AVALONVulnerabilityEntity>>) method
              .invoke(
                  AVALONVulnerabilityController.getInstance(),
                  (String) field
                      .get(entity.getAlertedAttackWeight()));
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
          if (field.get(entity.getAlertedAttackWeight()) != null) {
            list = (List<Resource<AVALONVulnerabilityEntity>>)
                method.invoke(AVALONVulnerabilityController
                    .getInstance(),(String) field.get(
                        entity.getAlertedAttackWeight()));
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
              entity.getAlertedAttackWeight()).get(0)).getContent();
    }
    entity.setAlertedAttackWeight(memberEntity);
    list = null;
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

  private void setMoveStrengthIdFromRepository(
      final AVALONIoNpcDataEntity entity) {
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
        if (field.get(entity.getMoveStrength()) != null) {
          list = (List<Resource<AVALONVulnerabilityEntity>>) method
              .invoke(
                  AVALONVulnerabilityController.getInstance(),
                  (String) field
                      .get(entity.getMoveStrength()));
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
          if (field.get(entity.getMoveStrength()) != null) {
            list = (List<Resource<AVALONVulnerabilityEntity>>)
                method.invoke(AVALONVulnerabilityController
                    .getInstance(),(String) field.get(
                        entity.getMoveStrength()));
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
              entity.getMoveStrength()).get(0)).getContent();
    }
    entity.setMoveStrength(memberEntity);
    list = null;
    }

  private void setNaturalWeaponTypeIdFromRepository(
      final AVALONIoNpcDataEntity entity) {
    AVALONAttackTypeEntity memberEntity = null;
    List<Resource<AVALONAttackTypeEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = AVALONAttackTypeController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = AVALONAttackTypeEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getNaturalWeaponType()) != null) {
          list = (List<Resource<AVALONAttackTypeEntity>>) method
              .invoke(
                  AVALONAttackTypeController.getInstance(),
                  (String) field
                      .get(entity.getNaturalWeaponType()));
        }
      }
      if (list == null) {
        try {
          method = AVALONAttackTypeController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = AVALONAttackTypeEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getNaturalWeaponType()) != null) {
            list = (List<Resource<AVALONAttackTypeEntity>>)
                method.invoke(AVALONAttackTypeController
                    .getInstance(),(String) field.get(
                        entity.getNaturalWeaponType()));
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
      memberEntity = (AVALONAttackTypeEntity)
          ((Resource) AVALONAttackTypeController.getInstance().save(
              entity.getNaturalWeaponType()).get(0)).getContent();
    }
    entity.setNaturalWeaponType(memberEntity);
    list = null;
    }

  private void setUnalertedAttackSpellIdFromRepository(
      final AVALONIoNpcDataEntity entity) {
    AVALONMagicTypeEntity memberEntity = null;
    List<Resource<AVALONMagicTypeEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = AVALONMagicTypeController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = AVALONMagicTypeEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getUnalertedAttackSpell()) != null) {
          list = (List<Resource<AVALONMagicTypeEntity>>) method
              .invoke(
                  AVALONMagicTypeController.getInstance(),
                  (String) field
                      .get(entity.getUnalertedAttackSpell()));
        }
      }
      if (list == null) {
        try {
          method = AVALONMagicTypeController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = AVALONMagicTypeEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getUnalertedAttackSpell()) != null) {
            list = (List<Resource<AVALONMagicTypeEntity>>)
                method.invoke(AVALONMagicTypeController
                    .getInstance(),(String) field.get(
                        entity.getUnalertedAttackSpell()));
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
      memberEntity = (AVALONMagicTypeEntity)
          ((Resource) AVALONMagicTypeController.getInstance().save(
              entity.getUnalertedAttackSpell()).get(0)).getContent();
    }
    entity.setUnalertedAttackSpell(memberEntity);
    list = null;
    }

  private void setUnalertedAttackWeightIdFromRepository(
      final AVALONIoNpcDataEntity entity) {
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
        if (field.get(entity.getUnalertedAttackWeight()) != null) {
          list = (List<Resource<AVALONVulnerabilityEntity>>) method
              .invoke(
                  AVALONVulnerabilityController.getInstance(),
                  (String) field
                      .get(entity.getUnalertedAttackWeight()));
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
          if (field.get(entity.getUnalertedAttackWeight()) != null) {
            list = (List<Resource<AVALONVulnerabilityEntity>>)
                method.invoke(AVALONVulnerabilityController
                    .getInstance(),(String) field.get(
                        entity.getUnalertedAttackWeight()));
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
              entity.getUnalertedAttackWeight()).get(0)).getContent();
    }
    entity.setUnalertedAttackWeight(memberEntity);
    list = null;
    }

  private void setVulnerabilityIdFromRepository(
      final AVALONIoNpcDataEntity entity) {
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
        if (field.get(entity.getVulnerability()) != null) {
          list = (List<Resource<AVALONVulnerabilityEntity>>) method
              .invoke(
                  AVALONVulnerabilityController.getInstance(),
                  (String) field
                      .get(entity.getVulnerability()));
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
          if (field.get(entity.getVulnerability()) != null) {
            list = (List<Resource<AVALONVulnerabilityEntity>>)
                method.invoke(AVALONVulnerabilityController
                    .getInstance(),(String) field.get(
                        entity.getVulnerability()));
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
              entity.getVulnerability()).get(0)).getContent();
    }
    entity.setVulnerability(memberEntity);
    list = null;
    }

  private void setWeightIdFromRepository(
      final AVALONIoNpcDataEntity entity) {
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
        if (field.get(entity.getWeight()) != null) {
          list = (List<Resource<AVALONVulnerabilityEntity>>) method
              .invoke(
                  AVALONVulnerabilityController.getInstance(),
                  (String) field
                      .get(entity.getWeight()));
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
          if (field.get(entity.getWeight()) != null) {
            list = (List<Resource<AVALONVulnerabilityEntity>>)
                method.invoke(AVALONVulnerabilityController
                    .getInstance(),(String) field.get(
                        entity.getWeight()));
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
              entity.getWeight()).get(0)).getContent();
    }
    entity.setWeight(memberEntity);
    list = null;
    }


    /**
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a alertedAttackSpeed.
     * @param alertedAttackSpeed the io_npc_data' alertedAttackSpeed
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "alerted_attack_speed/{alertedAttackSpeed}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByAlertedAttackSpeed(
            @PathVariable final Long alertedAttackSpeed) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByAlertedAttackSpeed(alertedAttackSpeed)
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
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a alertedAttackStars.
     * @param alertedAttackStars the io_npc_data' alertedAttackStars
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "alerted_attack_stars/{alertedAttackStars}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByAlertedAttackStars(
            @PathVariable final Long alertedAttackStars) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByAlertedAttackStars(alertedAttackStars)
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
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a alertedMove.
     * @param alertedMove the io_npc_data' alertedMove
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "alerted_move/{alertedMove}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByAlertedMove(
            @PathVariable final Long alertedMove) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByAlertedMove(alertedMove)
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
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a fameBounty.
     * @param fameBounty the io_npc_data' fameBounty
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "fame_bounty/{fameBounty}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByFameBounty(
            @PathVariable final Long fameBounty) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByFameBounty(fameBounty)
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
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a goldBounty.
     * @param goldBounty the io_npc_data' goldBounty
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "gold_bounty/{goldBounty}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByGoldBounty(
            @PathVariable final Long goldBounty) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByGoldBounty(goldBounty)
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
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a naturalWeaponLength.
     * @param naturalWeaponLength the io_npc_data' naturalWeaponLength
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "natural_weapon_length/{naturalWeaponLength}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByNaturalWeaponLength(
            @PathVariable final Long naturalWeaponLength) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByNaturalWeaponLength(naturalWeaponLength)
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
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a notoriety.
     * @param notoriety the io_npc_data' notoriety
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "notoriety/{notoriety}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByNotoriety(
            @PathVariable final Long notoriety) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByNotoriety(notoriety)
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
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a unalertedAttackSpeed.
     * @param unalertedAttackSpeed the io_npc_data' unalertedAttackSpeed
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "unalerted_attack_speed/{unalertedAttackSpeed}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByUnalertedAttackSpeed(
            @PathVariable final Long unalertedAttackSpeed) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByUnalertedAttackSpeed(unalertedAttackSpeed)
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
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a unalertedAttackStars.
     * @param unalertedAttackStars the io_npc_data' unalertedAttackStars
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "unalerted_attack_stars/{unalertedAttackStars}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByUnalertedAttackStars(
            @PathVariable final Long unalertedAttackStars) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByUnalertedAttackStars(unalertedAttackStars)
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
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a unalertedMove.
     * @param unalertedMove the io_npc_data' unalertedMove
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "unalerted_move/{unalertedMove}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByUnalertedMove(
            @PathVariable final Long unalertedMove) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByUnalertedMove(unalertedMove)
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
     * Gets a list of {@link AVALONIoNpcDataEntity}s that share a wage.
     * @param wage the io_npc_data' wage
     * @return {@link List}<{@link Resource}<{@link AVALONIoNpcDataEntity}>>
     */
    @RequestMapping(path = "wage/{wage}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoNpcDataEntity>> getByWage(
            @PathVariable final Long wage) {
        Iterator<AVALONIoNpcDataEntity> iter = repository.findByWage(wage)
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
