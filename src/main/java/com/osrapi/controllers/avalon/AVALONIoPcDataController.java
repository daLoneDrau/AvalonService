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
import com.osrapi.models.avalon.AVALONAdvantageEntity;
import com.osrapi.models.avalon.AVALONAdvantageEntity;
import com.osrapi.models.avalon.AVALONGenderEntity;
import com.osrapi.models.avalon.AVALONVulnerabilityEntity;
import com.osrapi.models.avalon.AVALONGroupEntity;
import com.osrapi.models.avalon.AVALONDevelopmentActionsEntity;

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
        if (entity.getAlly() != null
                && !entity.getAlly().isEmpty()) {
            for (int i = entity.getAlly().size() - 1; i >= 0; i--) {
                AVALONGroupEntity ally = null;
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
                        if (field.get(entity.getAlly().get(i)) != null) {
                            list = (List<Resource<AVALONGroupEntity>>) method
                                    .invoke(
                                            AVALONGroupController.getInstance(),
                                            (String) field.get(entity.getAlly().get(i)));
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
                            if (field.get(entity.getAlly().get(i)) != null) {
                                list = (List<Resource<AVALONGroupEntity>>) method
                                        .invoke(
                                                AVALONGroupController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getAlly().get(i)));
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
                    ally = list.get(0).getContent();
                }
                if (ally == null) {
                    ally = (AVALONGroupEntity) ((Resource) AVALONGroupController
                            .getInstance()
                            .save(entity.getAlly().get(i)).get(0)).getContent();
                }
                entity.getAlly().set(i, ally);
                list = null;
            }
        }

    if (entity.getEnemy() != null
                && !entity.getEnemy().isEmpty()) {
            for (int i = entity.getEnemy().size() - 1; i >= 0; i--) {
                AVALONGroupEntity enemy = null;
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
                        if (field.get(entity.getEnemy().get(i)) != null) {
                            list = (List<Resource<AVALONGroupEntity>>) method
                                    .invoke(
                                            AVALONGroupController.getInstance(),
                                            (String) field.get(entity.getEnemy().get(i)));
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
                            if (field.get(entity.getEnemy().get(i)) != null) {
                                list = (List<Resource<AVALONGroupEntity>>) method
                                        .invoke(
                                                AVALONGroupController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getEnemy().get(i)));
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
                    enemy = list.get(0).getContent();
                }
                if (enemy == null) {
                    enemy = (AVALONGroupEntity) ((Resource) AVALONGroupController
                            .getInstance()
                            .save(entity.getEnemy().get(i)).get(0)).getContent();
                }
                entity.getEnemy().set(i, enemy);
                list = null;
            }
        }

    if (entity.getFriendly() != null
                && !entity.getFriendly().isEmpty()) {
            for (int i = entity.getFriendly().size() - 1; i >= 0; i--) {
                AVALONGroupEntity friendly = null;
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
                        if (field.get(entity.getFriendly().get(i)) != null) {
                            list = (List<Resource<AVALONGroupEntity>>) method
                                    .invoke(
                                            AVALONGroupController.getInstance(),
                                            (String) field.get(entity.getFriendly().get(i)));
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
                            if (field.get(entity.getFriendly().get(i)) != null) {
                                list = (List<Resource<AVALONGroupEntity>>) method
                                        .invoke(
                                                AVALONGroupController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getFriendly().get(i)));
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
                    friendly = list.get(0).getContent();
                }
                if (friendly == null) {
                    friendly = (AVALONGroupEntity) ((Resource) AVALONGroupController
                            .getInstance()
                            .save(entity.getFriendly().get(i)).get(0)).getContent();
                }
                entity.getFriendly().set(i, friendly);
                list = null;
            }
        }

    if (entity.getStageOneActions() != null
                && !entity.getStageOneActions().isEmpty()) {
            for (int i = entity.getStageOneActions().size() - 1; i >= 0; i--) {
                AVALONDevelopmentActionsEntity stageOneActions = null;
                List<Resource<AVALONDevelopmentActionsEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONDevelopmentActionsController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONDevelopmentActionsEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getStageOneActions().get(i)) != null) {
                            list = (List<Resource<AVALONDevelopmentActionsEntity>>) method
                                    .invoke(
                                            AVALONDevelopmentActionsController.getInstance(),
                                            (String) field.get(entity.getStageOneActions().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONDevelopmentActionsController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from Controller by code");
            }
            try {
              field = AVALONDevelopmentActionsEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getStageOneActions().get(i)) != null) {
                                list = (List<Resource<AVALONDevelopmentActionsEntity>>) method
                                        .invoke(
                                                AVALONDevelopmentActionsController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getStageOneActions().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONDevelopmentActionsEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    stageOneActions = list.get(0).getContent();
                }
                if (stageOneActions == null) {
                    stageOneActions = (AVALONDevelopmentActionsEntity) ((Resource) AVALONDevelopmentActionsController
                            .getInstance()
                            .save(entity.getStageOneActions().get(i)).get(0)).getContent();
                }
                entity.getStageOneActions().set(i, stageOneActions);
                list = null;
            }
        }

    if (entity.getStageTwoActions() != null
                && !entity.getStageTwoActions().isEmpty()) {
            for (int i = entity.getStageTwoActions().size() - 1; i >= 0; i--) {
                AVALONDevelopmentActionsEntity stageTwoActions = null;
                List<Resource<AVALONDevelopmentActionsEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONDevelopmentActionsController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONDevelopmentActionsEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getStageTwoActions().get(i)) != null) {
                            list = (List<Resource<AVALONDevelopmentActionsEntity>>) method
                                    .invoke(
                                            AVALONDevelopmentActionsController.getInstance(),
                                            (String) field.get(entity.getStageTwoActions().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONDevelopmentActionsController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from Controller by code");
            }
            try {
              field = AVALONDevelopmentActionsEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getStageTwoActions().get(i)) != null) {
                                list = (List<Resource<AVALONDevelopmentActionsEntity>>) method
                                        .invoke(
                                                AVALONDevelopmentActionsController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getStageTwoActions().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONDevelopmentActionsEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    stageTwoActions = list.get(0).getContent();
                }
                if (stageTwoActions == null) {
                    stageTwoActions = (AVALONDevelopmentActionsEntity) ((Resource) AVALONDevelopmentActionsController
                            .getInstance()
                            .save(entity.getStageTwoActions().get(i)).get(0)).getContent();
                }
                entity.getStageTwoActions().set(i, stageTwoActions);
                list = null;
            }
        }

    if (entity.getStageThreeActions() != null
                && !entity.getStageThreeActions().isEmpty()) {
            for (int i = entity.getStageThreeActions().size() - 1; i >= 0; i--) {
                AVALONDevelopmentActionsEntity stageThreeActions = null;
                List<Resource<AVALONDevelopmentActionsEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONDevelopmentActionsController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONDevelopmentActionsEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getStageThreeActions().get(i)) != null) {
                            list = (List<Resource<AVALONDevelopmentActionsEntity>>) method
                                    .invoke(
                                            AVALONDevelopmentActionsController.getInstance(),
                                            (String) field.get(entity.getStageThreeActions().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONDevelopmentActionsController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from Controller by code");
            }
            try {
              field = AVALONDevelopmentActionsEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getStageThreeActions().get(i)) != null) {
                                list = (List<Resource<AVALONDevelopmentActionsEntity>>) method
                                        .invoke(
                                                AVALONDevelopmentActionsController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getStageThreeActions().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONDevelopmentActionsEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    stageThreeActions = list.get(0).getContent();
                }
                if (stageThreeActions == null) {
                    stageThreeActions = (AVALONDevelopmentActionsEntity) ((Resource) AVALONDevelopmentActionsController
                            .getInstance()
                            .save(entity.getStageThreeActions().get(i)).get(0)).getContent();
                }
                entity.getStageThreeActions().set(i, stageThreeActions);
                list = null;
            }
        }

    if (entity.getStageFourActions() != null
                && !entity.getStageFourActions().isEmpty()) {
            for (int i = entity.getStageFourActions().size() - 1; i >= 0; i--) {
                AVALONDevelopmentActionsEntity stageFourActions = null;
                List<Resource<AVALONDevelopmentActionsEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONDevelopmentActionsController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONDevelopmentActionsEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getStageFourActions().get(i)) != null) {
                            list = (List<Resource<AVALONDevelopmentActionsEntity>>) method
                                    .invoke(
                                            AVALONDevelopmentActionsController.getInstance(),
                                            (String) field.get(entity.getStageFourActions().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONDevelopmentActionsController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from Controller by code");
            }
            try {
              field = AVALONDevelopmentActionsEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getStageFourActions().get(i)) != null) {
                                list = (List<Resource<AVALONDevelopmentActionsEntity>>) method
                                        .invoke(
                                                AVALONDevelopmentActionsController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getStageFourActions().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONDevelopmentActionsEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    stageFourActions = list.get(0).getContent();
                }
                if (stageFourActions == null) {
                    stageFourActions = (AVALONDevelopmentActionsEntity) ((Resource) AVALONDevelopmentActionsController
                            .getInstance()
                            .save(entity.getStageFourActions().get(i)).get(0)).getContent();
                }
                entity.getStageFourActions().set(i, stageFourActions);
                list = null;
            }
        }

    if (entity.getUnfriendly() != null
                && !entity.getUnfriendly().isEmpty()) {
            for (int i = entity.getUnfriendly().size() - 1; i >= 0; i--) {
                AVALONGroupEntity unfriendly = null;
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
                        if (field.get(entity.getUnfriendly().get(i)) != null) {
                            list = (List<Resource<AVALONGroupEntity>>) method
                                    .invoke(
                                            AVALONGroupController.getInstance(),
                                            (String) field.get(entity.getUnfriendly().get(i)));
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
                            if (field.get(entity.getUnfriendly().get(i)) != null) {
                                list = (List<Resource<AVALONGroupEntity>>) method
                                        .invoke(
                                                AVALONGroupController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getUnfriendly().get(i)));
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
                    unfriendly = list.get(0).getContent();
                }
                if (unfriendly == null) {
                    unfriendly = (AVALONGroupEntity) ((Resource) AVALONGroupController
                            .getInstance()
                            .save(entity.getUnfriendly().get(i)).get(0)).getContent();
                }
                entity.getUnfriendly().set(i, unfriendly);
                list = null;
            }
        }

        if (entity.getAdvantage1() != null
        && entity.getAdvantage1().getId() == null) {
      setAdvantage1IdFromRepository(entity);
        }

        if (entity.getAdvantage2() != null
        && entity.getAdvantage2().getId() == null) {
      setAdvantage2IdFromRepository(entity);
        }

        if (entity.getGender() != null
        && entity.getGender().getId() == null) {
      setGenderIdFromRepository(entity);
        }

        if (entity.getVulnerability() != null
        && entity.getVulnerability().getId() == null) {
      setVulnerabilityIdFromRepository(entity);
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
        if (entity.getAlly() != null
                && !entity.getAlly().isEmpty()) {
            for (int i = entity.getAlly().size() - 1; i >= 0; i--) {
                AVALONGroupEntity ally = null;
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
                        if (field.get(entity.getAlly().get(i)) != null) {
                            list = (List<Resource<AVALONGroupEntity>>) method
                                    .invoke(
                                            AVALONGroupController.getInstance(),
                                            (String) field.get(entity.getAlly().get(i)));
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
                            if (field.get(entity.getAlly().get(i)) != null) {
                                list = (List<Resource<AVALONGroupEntity>>) method
                                        .invoke(
                                                AVALONGroupController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getAlly().get(i)));
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
                    ally = list.get(0).getContent();
                }
                if (ally == null) {
                    ally = (AVALONGroupEntity) ((Resource) AVALONGroupController
                            .getInstance()
                            .save(entity.getAlly().get(i)).get(0)).getContent();
                }
                entity.getAlly().set(i, ally);
                list = null;
            }
        }

    if (entity.getEnemy() != null
                && !entity.getEnemy().isEmpty()) {
            for (int i = entity.getEnemy().size() - 1; i >= 0; i--) {
                AVALONGroupEntity enemy = null;
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
                        if (field.get(entity.getEnemy().get(i)) != null) {
                            list = (List<Resource<AVALONGroupEntity>>) method
                                    .invoke(
                                            AVALONGroupController.getInstance(),
                                            (String) field.get(entity.getEnemy().get(i)));
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
                            if (field.get(entity.getEnemy().get(i)) != null) {
                                list = (List<Resource<AVALONGroupEntity>>) method
                                        .invoke(
                                                AVALONGroupController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getEnemy().get(i)));
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
                    enemy = list.get(0).getContent();
                }
                if (enemy == null) {
                    enemy = (AVALONGroupEntity) ((Resource) AVALONGroupController
                            .getInstance()
                            .save(entity.getEnemy().get(i)).get(0)).getContent();
                }
                entity.getEnemy().set(i, enemy);
                list = null;
            }
        }

    if (entity.getFriendly() != null
                && !entity.getFriendly().isEmpty()) {
            for (int i = entity.getFriendly().size() - 1; i >= 0; i--) {
                AVALONGroupEntity friendly = null;
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
                        if (field.get(entity.getFriendly().get(i)) != null) {
                            list = (List<Resource<AVALONGroupEntity>>) method
                                    .invoke(
                                            AVALONGroupController.getInstance(),
                                            (String) field.get(entity.getFriendly().get(i)));
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
                            if (field.get(entity.getFriendly().get(i)) != null) {
                                list = (List<Resource<AVALONGroupEntity>>) method
                                        .invoke(
                                                AVALONGroupController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getFriendly().get(i)));
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
                    friendly = list.get(0).getContent();
                }
                if (friendly == null) {
                    friendly = (AVALONGroupEntity) ((Resource) AVALONGroupController
                            .getInstance()
                            .save(entity.getFriendly().get(i)).get(0)).getContent();
                }
                entity.getFriendly().set(i, friendly);
                list = null;
            }
        }

    if (entity.getStageOneActions() != null
                && !entity.getStageOneActions().isEmpty()) {
            for (int i = entity.getStageOneActions().size() - 1; i >= 0; i--) {
                AVALONDevelopmentActionsEntity stageOneActions = null;
                List<Resource<AVALONDevelopmentActionsEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONDevelopmentActionsController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONDevelopmentActionsEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getStageOneActions().get(i)) != null) {
                            list = (List<Resource<AVALONDevelopmentActionsEntity>>) method
                                    .invoke(
                                            AVALONDevelopmentActionsController.getInstance(),
                                            (String) field.get(entity.getStageOneActions().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONDevelopmentActionsController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from Controller by code");
            }
            try {
              field = AVALONDevelopmentActionsEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getStageOneActions().get(i)) != null) {
                                list = (List<Resource<AVALONDevelopmentActionsEntity>>) method
                                        .invoke(
                                                AVALONDevelopmentActionsController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getStageOneActions().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONDevelopmentActionsEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    stageOneActions = list.get(0).getContent();
                }
                if (stageOneActions == null) {
                    stageOneActions = (AVALONDevelopmentActionsEntity) ((Resource) AVALONDevelopmentActionsController
                            .getInstance()
                            .save(entity.getStageOneActions().get(i)).get(0)).getContent();
                }
                entity.getStageOneActions().set(i, stageOneActions);
                list = null;
            }
        }

    if (entity.getStageTwoActions() != null
                && !entity.getStageTwoActions().isEmpty()) {
            for (int i = entity.getStageTwoActions().size() - 1; i >= 0; i--) {
                AVALONDevelopmentActionsEntity stageTwoActions = null;
                List<Resource<AVALONDevelopmentActionsEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONDevelopmentActionsController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONDevelopmentActionsEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getStageTwoActions().get(i)) != null) {
                            list = (List<Resource<AVALONDevelopmentActionsEntity>>) method
                                    .invoke(
                                            AVALONDevelopmentActionsController.getInstance(),
                                            (String) field.get(entity.getStageTwoActions().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONDevelopmentActionsController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from Controller by code");
            }
            try {
              field = AVALONDevelopmentActionsEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getStageTwoActions().get(i)) != null) {
                                list = (List<Resource<AVALONDevelopmentActionsEntity>>) method
                                        .invoke(
                                                AVALONDevelopmentActionsController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getStageTwoActions().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONDevelopmentActionsEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    stageTwoActions = list.get(0).getContent();
                }
                if (stageTwoActions == null) {
                    stageTwoActions = (AVALONDevelopmentActionsEntity) ((Resource) AVALONDevelopmentActionsController
                            .getInstance()
                            .save(entity.getStageTwoActions().get(i)).get(0)).getContent();
                }
                entity.getStageTwoActions().set(i, stageTwoActions);
                list = null;
            }
        }

    if (entity.getStageThreeActions() != null
                && !entity.getStageThreeActions().isEmpty()) {
            for (int i = entity.getStageThreeActions().size() - 1; i >= 0; i--) {
                AVALONDevelopmentActionsEntity stageThreeActions = null;
                List<Resource<AVALONDevelopmentActionsEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONDevelopmentActionsController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONDevelopmentActionsEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getStageThreeActions().get(i)) != null) {
                            list = (List<Resource<AVALONDevelopmentActionsEntity>>) method
                                    .invoke(
                                            AVALONDevelopmentActionsController.getInstance(),
                                            (String) field.get(entity.getStageThreeActions().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONDevelopmentActionsController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from Controller by code");
            }
            try {
              field = AVALONDevelopmentActionsEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getStageThreeActions().get(i)) != null) {
                                list = (List<Resource<AVALONDevelopmentActionsEntity>>) method
                                        .invoke(
                                                AVALONDevelopmentActionsController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getStageThreeActions().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONDevelopmentActionsEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    stageThreeActions = list.get(0).getContent();
                }
                if (stageThreeActions == null) {
                    stageThreeActions = (AVALONDevelopmentActionsEntity) ((Resource) AVALONDevelopmentActionsController
                            .getInstance()
                            .save(entity.getStageThreeActions().get(i)).get(0)).getContent();
                }
                entity.getStageThreeActions().set(i, stageThreeActions);
                list = null;
            }
        }

    if (entity.getStageFourActions() != null
                && !entity.getStageFourActions().isEmpty()) {
            for (int i = entity.getStageFourActions().size() - 1; i >= 0; i--) {
                AVALONDevelopmentActionsEntity stageFourActions = null;
                List<Resource<AVALONDevelopmentActionsEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONDevelopmentActionsController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONDevelopmentActionsEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getStageFourActions().get(i)) != null) {
                            list = (List<Resource<AVALONDevelopmentActionsEntity>>) method
                                    .invoke(
                                            AVALONDevelopmentActionsController.getInstance(),
                                            (String) field.get(entity.getStageFourActions().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONDevelopmentActionsController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from Controller by code");
            }
            try {
              field = AVALONDevelopmentActionsEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONDevelopmentActionsEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getStageFourActions().get(i)) != null) {
                                list = (List<Resource<AVALONDevelopmentActionsEntity>>) method
                                        .invoke(
                                                AVALONDevelopmentActionsController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getStageFourActions().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONDevelopmentActionsEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    stageFourActions = list.get(0).getContent();
                }
                if (stageFourActions == null) {
                    stageFourActions = (AVALONDevelopmentActionsEntity) ((Resource) AVALONDevelopmentActionsController
                            .getInstance()
                            .save(entity.getStageFourActions().get(i)).get(0)).getContent();
                }
                entity.getStageFourActions().set(i, stageFourActions);
                list = null;
            }
        }

    if (entity.getUnfriendly() != null
                && !entity.getUnfriendly().isEmpty()) {
            for (int i = entity.getUnfriendly().size() - 1; i >= 0; i--) {
                AVALONGroupEntity unfriendly = null;
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
                        if (field.get(entity.getUnfriendly().get(i)) != null) {
                            list = (List<Resource<AVALONGroupEntity>>) method
                                    .invoke(
                                            AVALONGroupController.getInstance(),
                                            (String) field.get(entity.getUnfriendly().get(i)));
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
                            if (field.get(entity.getUnfriendly().get(i)) != null) {
                                list = (List<Resource<AVALONGroupEntity>>) method
                                        .invoke(
                                                AVALONGroupController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getUnfriendly().get(i)));
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
                    unfriendly = list.get(0).getContent();
                }
                if (unfriendly == null) {
                    unfriendly = (AVALONGroupEntity) ((Resource) AVALONGroupController
                            .getInstance()
                            .save(entity.getUnfriendly().get(i)).get(0)).getContent();
                }
                entity.getUnfriendly().set(i, unfriendly);
                list = null;
            }
        }

        if (entity.getAdvantage1() != null
        && entity.getAdvantage1().getId() == null) {
      setAdvantage1IdFromRepository(entity);
        }

        if (entity.getAdvantage2() != null
        && entity.getAdvantage2().getId() == null) {
      setAdvantage2IdFromRepository(entity);
        }

        if (entity.getGender() != null
        && entity.getGender().getId() == null) {
      setGenderIdFromRepository(entity);
        }

        if (entity.getVulnerability() != null
        && entity.getVulnerability().getId() == null) {
      setVulnerabilityIdFromRepository(entity);
        }


    
        AVALONIoPcDataEntity savedEntity = repository.save(entity);
        List<Resource<AVALONIoPcDataEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setAdvantage1IdFromRepository(
      final AVALONIoPcDataEntity entity) {
    AVALONAdvantageEntity memberEntity = null;
    List<Resource<AVALONAdvantageEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = AVALONAdvantageController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = AVALONAdvantageEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getAdvantage1()) != null) {
          list = (List<Resource<AVALONAdvantageEntity>>) method
              .invoke(
                  AVALONAdvantageController.getInstance(),
                  (String) field
                      .get(entity.getAdvantage1()));
        }
      }
      if (list == null) {
        try {
          method = AVALONAdvantageController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = AVALONAdvantageEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getAdvantage1()) != null) {
            list = (List<Resource<AVALONAdvantageEntity>>)
                method.invoke(AVALONAdvantageController
                    .getInstance(),(String) field.get(
                        entity.getAdvantage1()));
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
      memberEntity = (AVALONAdvantageEntity)
          ((Resource) AVALONAdvantageController.getInstance().save(
              entity.getAdvantage1()).get(0)).getContent();
    }
    entity.setAdvantage1(memberEntity);
    list = null;
    }

  private void setAdvantage2IdFromRepository(
      final AVALONIoPcDataEntity entity) {
    AVALONAdvantageEntity memberEntity = null;
    List<Resource<AVALONAdvantageEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = AVALONAdvantageController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = AVALONAdvantageEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getAdvantage2()) != null) {
          list = (List<Resource<AVALONAdvantageEntity>>) method
              .invoke(
                  AVALONAdvantageController.getInstance(),
                  (String) field
                      .get(entity.getAdvantage2()));
        }
      }
      if (list == null) {
        try {
          method = AVALONAdvantageController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = AVALONAdvantageEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getAdvantage2()) != null) {
            list = (List<Resource<AVALONAdvantageEntity>>)
                method.invoke(AVALONAdvantageController
                    .getInstance(),(String) field.get(
                        entity.getAdvantage2()));
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
      memberEntity = (AVALONAdvantageEntity)
          ((Resource) AVALONAdvantageController.getInstance().save(
              entity.getAdvantage2()).get(0)).getContent();
    }
    entity.setAdvantage2(memberEntity);
    list = null;
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

  private void setVulnerabilityIdFromRepository(
      final AVALONIoPcDataEntity entity) {
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


    /**
     * Gets a list of {@link AVALONIoPcDataEntity}s that share a evaluation.
     * @param evaluation the io_pc_data' evaluation
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "evaluation/{evaluation}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoPcDataEntity>> getByEvaluation(
            @PathVariable final String evaluation) {
        Iterator<AVALONIoPcDataEntity> iter = repository.findByEvaluation(evaluation)
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
     * Gets a list of {@link AVALONIoPcDataEntity}s that share a glyph.
     * @param glyph the io_pc_data' glyph
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "glyph/{glyph}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoPcDataEntity>> getByGlyph(
            @PathVariable final String glyph) {
        Iterator<AVALONIoPcDataEntity> iter = repository.findByGlyph(glyph)
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
            @PathVariable final Float gold) {
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
     * Gets a list of {@link AVALONIoPcDataEntity}s that share a stageOneName.
     * @param stageOneName the io_pc_data' stageOneName
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "stage_one_name/{stageOneName}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoPcDataEntity>> getByStageOneName(
            @PathVariable final String stageOneName) {
        Iterator<AVALONIoPcDataEntity> iter = repository.findByStageOneName(stageOneName)
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
     * Gets a list of {@link AVALONIoPcDataEntity}s that share a stageOneSpells.
     * @param stageOneSpells the io_pc_data' stageOneSpells
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "stage_one_spells/{stageOneSpells}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoPcDataEntity>> getByStageOneSpells(
            @PathVariable final Long stageOneSpells) {
        Iterator<AVALONIoPcDataEntity> iter = repository.findByStageOneSpells(stageOneSpells)
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
     * Gets a list of {@link AVALONIoPcDataEntity}s that share a stageTwoName.
     * @param stageTwoName the io_pc_data' stageTwoName
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "stage_two_name/{stageTwoName}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoPcDataEntity>> getByStageTwoName(
            @PathVariable final String stageTwoName) {
        Iterator<AVALONIoPcDataEntity> iter = repository.findByStageTwoName(stageTwoName)
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
     * Gets a list of {@link AVALONIoPcDataEntity}s that share a stageTwoSpells.
     * @param stageTwoSpells the io_pc_data' stageTwoSpells
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "stage_two_spells/{stageTwoSpells}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoPcDataEntity>> getByStageTwoSpells(
            @PathVariable final Long stageTwoSpells) {
        Iterator<AVALONIoPcDataEntity> iter = repository.findByStageTwoSpells(stageTwoSpells)
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
     * Gets a list of {@link AVALONIoPcDataEntity}s that share a stageThreeName.
     * @param stageThreeName the io_pc_data' stageThreeName
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "stage_three_name/{stageThreeName}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoPcDataEntity>> getByStageThreeName(
            @PathVariable final String stageThreeName) {
        Iterator<AVALONIoPcDataEntity> iter = repository.findByStageThreeName(stageThreeName)
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
     * Gets a list of {@link AVALONIoPcDataEntity}s that share a stageThreeSpells.
     * @param stageThreeSpells the io_pc_data' stageThreeSpells
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "stage_three_spells/{stageThreeSpells}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoPcDataEntity>> getByStageThreeSpells(
            @PathVariable final Long stageThreeSpells) {
        Iterator<AVALONIoPcDataEntity> iter = repository.findByStageThreeSpells(stageThreeSpells)
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
     * Gets a list of {@link AVALONIoPcDataEntity}s that share a stageFourSpells.
     * @param stageFourSpells the io_pc_data' stageFourSpells
     * @return {@link List}<{@link Resource}<{@link AVALONIoPcDataEntity}>>
     */
    @RequestMapping(path = "stage_four_spells/{stageFourSpells}",
            method = RequestMethod.GET)
    public List<Resource<AVALONIoPcDataEntity>> getByStageFourSpells(
            @PathVariable final Long stageFourSpells) {
        Iterator<AVALONIoPcDataEntity> iter = repository.findByStageFourSpells(stageFourSpells)
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
