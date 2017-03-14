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

import com.osrapi.models.avalon.AVALONHexTileEntity;
import com.osrapi.models.avalon.AVALONHexTileTypeEntity;
import com.osrapi.models.avalon.AVALONHexClearingEntity;
import com.osrapi.models.avalon.AVALONHexNodeEdgeEntity;
import com.osrapi.models.avalon.AVALONHexNodeEntity;
import com.osrapi.models.avalon.AVALONHexSideEdgeEntity;

import com.osrapi.repositories.avalon.AVALONHexTileRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/hex_tiles")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONHexTileController {
    /** the static instance of {@link AVALONHexTileController}. */
    private static AVALONHexTileController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONHexTileController}
     */
    public static AVALONHexTileController getInstance() {
        if (instance == null) {
            new AVALONHexTileController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONHexTileRepository repository;
    /** Creates a new instance of {@link AVALONHexTileController}. */
    public AVALONHexTileController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONHexTileEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONHexTileEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONHexTileEntity>> getAll() {
        Iterator<AVALONHexTileEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONHexTileEntity>> resources =
                new ArrayList<Resource<AVALONHexTileEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexTileResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONHexTileEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONHexTileEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONHexTileEntity>> getById(
            @PathVariable final Long id) {
        AVALONHexTileEntity entity = repository.findOne(id);
        List<Resource<AVALONHexTileEntity>> resources =
                new ArrayList<Resource<AVALONHexTileEntity>>();
        resources.add(getHexTileResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONHexTileEntity}.
     * @param entity the {@link AVALONHexTileEntity}
     * @return {@link Resource}<{@link AVALONHexTileEntity}>
     */
    private Resource<AVALONHexTileEntity> getHexTileResource(
            final AVALONHexTileEntity entity) {
        Resource<AVALONHexTileEntity> resource =
                new Resource<AVALONHexTileEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONHexTileEntity}s.
     * @param entities the list of {@link AVALONHexTileEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexTileEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONHexTileEntity>> save(
            @RequestBody final List<AVALONHexTileEntity> entities) {
        List<Resource<AVALONHexTileEntity>> resources =
                new ArrayList<Resource<AVALONHexTileEntity>>();
        Iterator<AVALONHexTileEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONHexTileEntity}.
     * @param entity the {@link AVALONHexTileEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexTileEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONHexTileEntity>> save(
            @RequestBody final AVALONHexTileEntity entity) {
        if (entity.getClearings() != null
                && !entity.getClearings().isEmpty()) {
            for (int i = entity.getClearings().size() - 1; i >= 0; i--) {
                AVALONHexClearingEntity clearings = null;
                List<Resource<AVALONHexClearingEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexClearingController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexClearingEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexClearingEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexClearingEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getClearings().get(i)) != null) {
                            list = (List<Resource<AVALONHexClearingEntity>>) method
                                    .invoke(
                                            AVALONHexClearingController.getInstance(),
                                            (String) field.get(entity.getClearings().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexClearingController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexClearingEntity from Controller by code");
            }
            try {
              field = AVALONHexClearingEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexClearingEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getClearings().get(i)) != null) {
                                list = (List<Resource<AVALONHexClearingEntity>>) method
                                        .invoke(
                                                AVALONHexClearingController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getClearings().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexClearingEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    clearings = list.get(0).getContent();
                }
                if (clearings == null) {
                    clearings = (AVALONHexClearingEntity) ((Resource) AVALONHexClearingController
                            .getInstance()
                            .save(entity.getClearings().get(i)).get(0)).getContent();
                }
                entity.getClearings().set(i, clearings);
                list = null;
            }
        }

    if (entity.getEdges() != null
                && !entity.getEdges().isEmpty()) {
            for (int i = entity.getEdges().size() - 1; i >= 0; i--) {
                AVALONHexNodeEdgeEntity edges = null;
                List<Resource<AVALONHexNodeEdgeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexNodeEdgeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexNodeEdgeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getEdges().get(i)) != null) {
                            list = (List<Resource<AVALONHexNodeEdgeEntity>>) method
                                    .invoke(
                                            AVALONHexNodeEdgeController.getInstance(),
                                            (String) field.get(entity.getEdges().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexNodeEdgeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from Controller by code");
            }
            try {
              field = AVALONHexNodeEdgeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getEdges().get(i)) != null) {
                                list = (List<Resource<AVALONHexNodeEdgeEntity>>) method
                                        .invoke(
                                                AVALONHexNodeEdgeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getEdges().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexNodeEdgeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    edges = list.get(0).getContent();
                }
                if (edges == null) {
                    edges = (AVALONHexNodeEdgeEntity) ((Resource) AVALONHexNodeEdgeController
                            .getInstance()
                            .save(entity.getEdges().get(i)).get(0)).getContent();
                }
                entity.getEdges().set(i, edges);
                list = null;
            }
        }

    if (entity.getEnchantedEdges() != null
                && !entity.getEnchantedEdges().isEmpty()) {
            for (int i = entity.getEnchantedEdges().size() - 1; i >= 0; i--) {
                AVALONHexNodeEdgeEntity enchantedEdges = null;
                List<Resource<AVALONHexNodeEdgeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexNodeEdgeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexNodeEdgeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getEnchantedEdges().get(i)) != null) {
                            list = (List<Resource<AVALONHexNodeEdgeEntity>>) method
                                    .invoke(
                                            AVALONHexNodeEdgeController.getInstance(),
                                            (String) field.get(entity.getEnchantedEdges().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexNodeEdgeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from Controller by code");
            }
            try {
              field = AVALONHexNodeEdgeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getEnchantedEdges().get(i)) != null) {
                                list = (List<Resource<AVALONHexNodeEdgeEntity>>) method
                                        .invoke(
                                                AVALONHexNodeEdgeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getEnchantedEdges().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexNodeEdgeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    enchantedEdges = list.get(0).getContent();
                }
                if (enchantedEdges == null) {
                    enchantedEdges = (AVALONHexNodeEdgeEntity) ((Resource) AVALONHexNodeEdgeController
                            .getInstance()
                            .save(entity.getEnchantedEdges().get(i)).get(0)).getContent();
                }
                entity.getEnchantedEdges().set(i, enchantedEdges);
                list = null;
            }
        }

    if (entity.getEnchantedSecretEdges() != null
                && !entity.getEnchantedSecretEdges().isEmpty()) {
            for (int i = entity.getEnchantedSecretEdges().size() - 1; i >= 0; i--) {
                AVALONHexNodeEdgeEntity enchantedSecretEdges = null;
                List<Resource<AVALONHexNodeEdgeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexNodeEdgeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexNodeEdgeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getEnchantedSecretEdges().get(i)) != null) {
                            list = (List<Resource<AVALONHexNodeEdgeEntity>>) method
                                    .invoke(
                                            AVALONHexNodeEdgeController.getInstance(),
                                            (String) field.get(entity.getEnchantedSecretEdges().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexNodeEdgeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from Controller by code");
            }
            try {
              field = AVALONHexNodeEdgeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getEnchantedSecretEdges().get(i)) != null) {
                                list = (List<Resource<AVALONHexNodeEdgeEntity>>) method
                                        .invoke(
                                                AVALONHexNodeEdgeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getEnchantedSecretEdges().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexNodeEdgeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    enchantedSecretEdges = list.get(0).getContent();
                }
                if (enchantedSecretEdges == null) {
                    enchantedSecretEdges = (AVALONHexNodeEdgeEntity) ((Resource) AVALONHexNodeEdgeController
                            .getInstance()
                            .save(entity.getEnchantedSecretEdges().get(i)).get(0)).getContent();
                }
                entity.getEnchantedSecretEdges().set(i, enchantedSecretEdges);
                list = null;
            }
        }

    if (entity.getEnchantedSideEdges() != null
                && !entity.getEnchantedSideEdges().isEmpty()) {
            for (int i = entity.getEnchantedSideEdges().size() - 1; i >= 0; i--) {
                AVALONHexSideEdgeEntity enchantedSideEdges = null;
                List<Resource<AVALONHexSideEdgeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexSideEdgeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexSideEdgeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexSideEdgeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexSideEdgeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getEnchantedSideEdges().get(i)) != null) {
                            list = (List<Resource<AVALONHexSideEdgeEntity>>) method
                                    .invoke(
                                            AVALONHexSideEdgeController.getInstance(),
                                            (String) field.get(entity.getEnchantedSideEdges().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexSideEdgeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexSideEdgeEntity from Controller by code");
            }
            try {
              field = AVALONHexSideEdgeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexSideEdgeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getEnchantedSideEdges().get(i)) != null) {
                                list = (List<Resource<AVALONHexSideEdgeEntity>>) method
                                        .invoke(
                                                AVALONHexSideEdgeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getEnchantedSideEdges().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexSideEdgeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    enchantedSideEdges = list.get(0).getContent();
                }
                if (enchantedSideEdges == null) {
                    enchantedSideEdges = (AVALONHexSideEdgeEntity) ((Resource) AVALONHexSideEdgeController
                            .getInstance()
                            .save(entity.getEnchantedSideEdges().get(i)).get(0)).getContent();
                }
                entity.getEnchantedSideEdges().set(i, enchantedSideEdges);
                list = null;
            }
        }

    if (entity.getEnchantedTerrain() != null
                && !entity.getEnchantedTerrain().isEmpty()) {
            for (int i = entity.getEnchantedTerrain().size() - 1; i >= 0; i--) {
                AVALONHexNodeEntity enchantedTerrain = null;
                List<Resource<AVALONHexNodeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexNodeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexNodeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getEnchantedTerrain().get(i)) != null) {
                            list = (List<Resource<AVALONHexNodeEntity>>) method
                                    .invoke(
                                            AVALONHexNodeController.getInstance(),
                                            (String) field.get(entity.getEnchantedTerrain().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexNodeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEntity from Controller by code");
            }
            try {
              field = AVALONHexNodeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getEnchantedTerrain().get(i)) != null) {
                                list = (List<Resource<AVALONHexNodeEntity>>) method
                                        .invoke(
                                                AVALONHexNodeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getEnchantedTerrain().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexNodeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    enchantedTerrain = list.get(0).getContent();
                }
                if (enchantedTerrain == null) {
                    enchantedTerrain = (AVALONHexNodeEntity) ((Resource) AVALONHexNodeController
                            .getInstance()
                            .save(entity.getEnchantedTerrain().get(i)).get(0)).getContent();
                }
                entity.getEnchantedTerrain().set(i, enchantedTerrain);
                list = null;
            }
        }

    if (entity.getSecretEdges() != null
                && !entity.getSecretEdges().isEmpty()) {
            for (int i = entity.getSecretEdges().size() - 1; i >= 0; i--) {
                AVALONHexNodeEdgeEntity secretEdges = null;
                List<Resource<AVALONHexNodeEdgeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexNodeEdgeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexNodeEdgeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getSecretEdges().get(i)) != null) {
                            list = (List<Resource<AVALONHexNodeEdgeEntity>>) method
                                    .invoke(
                                            AVALONHexNodeEdgeController.getInstance(),
                                            (String) field.get(entity.getSecretEdges().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexNodeEdgeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from Controller by code");
            }
            try {
              field = AVALONHexNodeEdgeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getSecretEdges().get(i)) != null) {
                                list = (List<Resource<AVALONHexNodeEdgeEntity>>) method
                                        .invoke(
                                                AVALONHexNodeEdgeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getSecretEdges().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexNodeEdgeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    secretEdges = list.get(0).getContent();
                }
                if (secretEdges == null) {
                    secretEdges = (AVALONHexNodeEdgeEntity) ((Resource) AVALONHexNodeEdgeController
                            .getInstance()
                            .save(entity.getSecretEdges().get(i)).get(0)).getContent();
                }
                entity.getSecretEdges().set(i, secretEdges);
                list = null;
            }
        }

    if (entity.getSideEdges() != null
                && !entity.getSideEdges().isEmpty()) {
            for (int i = entity.getSideEdges().size() - 1; i >= 0; i--) {
                AVALONHexSideEdgeEntity sideEdges = null;
                List<Resource<AVALONHexSideEdgeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexSideEdgeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexSideEdgeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexSideEdgeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexSideEdgeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getSideEdges().get(i)) != null) {
                            list = (List<Resource<AVALONHexSideEdgeEntity>>) method
                                    .invoke(
                                            AVALONHexSideEdgeController.getInstance(),
                                            (String) field.get(entity.getSideEdges().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexSideEdgeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexSideEdgeEntity from Controller by code");
            }
            try {
              field = AVALONHexSideEdgeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexSideEdgeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getSideEdges().get(i)) != null) {
                                list = (List<Resource<AVALONHexSideEdgeEntity>>) method
                                        .invoke(
                                                AVALONHexSideEdgeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getSideEdges().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexSideEdgeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    sideEdges = list.get(0).getContent();
                }
                if (sideEdges == null) {
                    sideEdges = (AVALONHexSideEdgeEntity) ((Resource) AVALONHexSideEdgeController
                            .getInstance()
                            .save(entity.getSideEdges().get(i)).get(0)).getContent();
                }
                entity.getSideEdges().set(i, sideEdges);
                list = null;
            }
        }

    if (entity.getTerrain() != null
                && !entity.getTerrain().isEmpty()) {
            for (int i = entity.getTerrain().size() - 1; i >= 0; i--) {
                AVALONHexNodeEntity terrain = null;
                List<Resource<AVALONHexNodeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexNodeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexNodeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getTerrain().get(i)) != null) {
                            list = (List<Resource<AVALONHexNodeEntity>>) method
                                    .invoke(
                                            AVALONHexNodeController.getInstance(),
                                            (String) field.get(entity.getTerrain().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexNodeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEntity from Controller by code");
            }
            try {
              field = AVALONHexNodeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getTerrain().get(i)) != null) {
                                list = (List<Resource<AVALONHexNodeEntity>>) method
                                        .invoke(
                                                AVALONHexNodeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getTerrain().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexNodeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    terrain = list.get(0).getContent();
                }
                if (terrain == null) {
                    terrain = (AVALONHexNodeEntity) ((Resource) AVALONHexNodeController
                            .getInstance()
                            .save(entity.getTerrain().get(i)).get(0)).getContent();
                }
                entity.getTerrain().set(i, terrain);
                list = null;
            }
        }

        if (entity.getType() != null
        && entity.getType().getId() == null) {
      setTypeIdFromRepository(entity);
        }


    
        AVALONHexTileEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexTileEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONHexTileEntity} instance
     */
    private void setIdFromRepository(final AVALONHexTileEntity entity) {
        List<AVALONHexTileEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONHexTileEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONHexTileEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONHexTileEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONHexTileEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONHexTileEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONHexTileEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONHexTileEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONHexTileEntity}s.
     * @param entities the list of {@link AVALONHexTileEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexTileEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONHexTileEntity>> update(
            @RequestBody final List<AVALONHexTileEntity> entities) {
        List<Resource<AVALONHexTileEntity>> resources = new ArrayList<Resource<AVALONHexTileEntity>>();
        Iterator<AVALONHexTileEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONHexTileEntity}.
     * @param entity the {@link AVALONHexTileEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexTileEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONHexTileEntity>> update(
            @RequestBody final AVALONHexTileEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
        if (entity.getClearings() != null
                && !entity.getClearings().isEmpty()) {
            for (int i = entity.getClearings().size() - 1; i >= 0; i--) {
                AVALONHexClearingEntity clearings = null;
                List<Resource<AVALONHexClearingEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexClearingController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexClearingEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexClearingEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexClearingEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getClearings().get(i)) != null) {
                            list = (List<Resource<AVALONHexClearingEntity>>) method
                                    .invoke(
                                            AVALONHexClearingController.getInstance(),
                                            (String) field.get(entity.getClearings().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexClearingController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexClearingEntity from Controller by code");
            }
            try {
              field = AVALONHexClearingEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexClearingEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getClearings().get(i)) != null) {
                                list = (List<Resource<AVALONHexClearingEntity>>) method
                                        .invoke(
                                                AVALONHexClearingController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getClearings().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexClearingEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    clearings = list.get(0).getContent();
                }
                if (clearings == null) {
                    clearings = (AVALONHexClearingEntity) ((Resource) AVALONHexClearingController
                            .getInstance()
                            .save(entity.getClearings().get(i)).get(0)).getContent();
                }
                entity.getClearings().set(i, clearings);
                list = null;
            }
        }

    if (entity.getEdges() != null
                && !entity.getEdges().isEmpty()) {
            for (int i = entity.getEdges().size() - 1; i >= 0; i--) {
                AVALONHexNodeEdgeEntity edges = null;
                List<Resource<AVALONHexNodeEdgeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexNodeEdgeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexNodeEdgeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getEdges().get(i)) != null) {
                            list = (List<Resource<AVALONHexNodeEdgeEntity>>) method
                                    .invoke(
                                            AVALONHexNodeEdgeController.getInstance(),
                                            (String) field.get(entity.getEdges().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexNodeEdgeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from Controller by code");
            }
            try {
              field = AVALONHexNodeEdgeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getEdges().get(i)) != null) {
                                list = (List<Resource<AVALONHexNodeEdgeEntity>>) method
                                        .invoke(
                                                AVALONHexNodeEdgeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getEdges().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexNodeEdgeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    edges = list.get(0).getContent();
                }
                if (edges == null) {
                    edges = (AVALONHexNodeEdgeEntity) ((Resource) AVALONHexNodeEdgeController
                            .getInstance()
                            .save(entity.getEdges().get(i)).get(0)).getContent();
                }
                entity.getEdges().set(i, edges);
                list = null;
            }
        }

    if (entity.getEnchantedEdges() != null
                && !entity.getEnchantedEdges().isEmpty()) {
            for (int i = entity.getEnchantedEdges().size() - 1; i >= 0; i--) {
                AVALONHexNodeEdgeEntity enchantedEdges = null;
                List<Resource<AVALONHexNodeEdgeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexNodeEdgeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexNodeEdgeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getEnchantedEdges().get(i)) != null) {
                            list = (List<Resource<AVALONHexNodeEdgeEntity>>) method
                                    .invoke(
                                            AVALONHexNodeEdgeController.getInstance(),
                                            (String) field.get(entity.getEnchantedEdges().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexNodeEdgeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from Controller by code");
            }
            try {
              field = AVALONHexNodeEdgeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getEnchantedEdges().get(i)) != null) {
                                list = (List<Resource<AVALONHexNodeEdgeEntity>>) method
                                        .invoke(
                                                AVALONHexNodeEdgeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getEnchantedEdges().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexNodeEdgeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    enchantedEdges = list.get(0).getContent();
                }
                if (enchantedEdges == null) {
                    enchantedEdges = (AVALONHexNodeEdgeEntity) ((Resource) AVALONHexNodeEdgeController
                            .getInstance()
                            .save(entity.getEnchantedEdges().get(i)).get(0)).getContent();
                }
                entity.getEnchantedEdges().set(i, enchantedEdges);
                list = null;
            }
        }

    if (entity.getEnchantedSecretEdges() != null
                && !entity.getEnchantedSecretEdges().isEmpty()) {
            for (int i = entity.getEnchantedSecretEdges().size() - 1; i >= 0; i--) {
                AVALONHexNodeEdgeEntity enchantedSecretEdges = null;
                List<Resource<AVALONHexNodeEdgeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexNodeEdgeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexNodeEdgeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getEnchantedSecretEdges().get(i)) != null) {
                            list = (List<Resource<AVALONHexNodeEdgeEntity>>) method
                                    .invoke(
                                            AVALONHexNodeEdgeController.getInstance(),
                                            (String) field.get(entity.getEnchantedSecretEdges().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexNodeEdgeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from Controller by code");
            }
            try {
              field = AVALONHexNodeEdgeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getEnchantedSecretEdges().get(i)) != null) {
                                list = (List<Resource<AVALONHexNodeEdgeEntity>>) method
                                        .invoke(
                                                AVALONHexNodeEdgeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getEnchantedSecretEdges().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexNodeEdgeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    enchantedSecretEdges = list.get(0).getContent();
                }
                if (enchantedSecretEdges == null) {
                    enchantedSecretEdges = (AVALONHexNodeEdgeEntity) ((Resource) AVALONHexNodeEdgeController
                            .getInstance()
                            .save(entity.getEnchantedSecretEdges().get(i)).get(0)).getContent();
                }
                entity.getEnchantedSecretEdges().set(i, enchantedSecretEdges);
                list = null;
            }
        }

    if (entity.getEnchantedSideEdges() != null
                && !entity.getEnchantedSideEdges().isEmpty()) {
            for (int i = entity.getEnchantedSideEdges().size() - 1; i >= 0; i--) {
                AVALONHexSideEdgeEntity enchantedSideEdges = null;
                List<Resource<AVALONHexSideEdgeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexSideEdgeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexSideEdgeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexSideEdgeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexSideEdgeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getEnchantedSideEdges().get(i)) != null) {
                            list = (List<Resource<AVALONHexSideEdgeEntity>>) method
                                    .invoke(
                                            AVALONHexSideEdgeController.getInstance(),
                                            (String) field.get(entity.getEnchantedSideEdges().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexSideEdgeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexSideEdgeEntity from Controller by code");
            }
            try {
              field = AVALONHexSideEdgeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexSideEdgeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getEnchantedSideEdges().get(i)) != null) {
                                list = (List<Resource<AVALONHexSideEdgeEntity>>) method
                                        .invoke(
                                                AVALONHexSideEdgeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getEnchantedSideEdges().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexSideEdgeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    enchantedSideEdges = list.get(0).getContent();
                }
                if (enchantedSideEdges == null) {
                    enchantedSideEdges = (AVALONHexSideEdgeEntity) ((Resource) AVALONHexSideEdgeController
                            .getInstance()
                            .save(entity.getEnchantedSideEdges().get(i)).get(0)).getContent();
                }
                entity.getEnchantedSideEdges().set(i, enchantedSideEdges);
                list = null;
            }
        }

    if (entity.getEnchantedTerrain() != null
                && !entity.getEnchantedTerrain().isEmpty()) {
            for (int i = entity.getEnchantedTerrain().size() - 1; i >= 0; i--) {
                AVALONHexNodeEntity enchantedTerrain = null;
                List<Resource<AVALONHexNodeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexNodeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexNodeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getEnchantedTerrain().get(i)) != null) {
                            list = (List<Resource<AVALONHexNodeEntity>>) method
                                    .invoke(
                                            AVALONHexNodeController.getInstance(),
                                            (String) field.get(entity.getEnchantedTerrain().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexNodeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEntity from Controller by code");
            }
            try {
              field = AVALONHexNodeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getEnchantedTerrain().get(i)) != null) {
                                list = (List<Resource<AVALONHexNodeEntity>>) method
                                        .invoke(
                                                AVALONHexNodeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getEnchantedTerrain().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexNodeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    enchantedTerrain = list.get(0).getContent();
                }
                if (enchantedTerrain == null) {
                    enchantedTerrain = (AVALONHexNodeEntity) ((Resource) AVALONHexNodeController
                            .getInstance()
                            .save(entity.getEnchantedTerrain().get(i)).get(0)).getContent();
                }
                entity.getEnchantedTerrain().set(i, enchantedTerrain);
                list = null;
            }
        }

    if (entity.getSecretEdges() != null
                && !entity.getSecretEdges().isEmpty()) {
            for (int i = entity.getSecretEdges().size() - 1; i >= 0; i--) {
                AVALONHexNodeEdgeEntity secretEdges = null;
                List<Resource<AVALONHexNodeEdgeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexNodeEdgeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexNodeEdgeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getSecretEdges().get(i)) != null) {
                            list = (List<Resource<AVALONHexNodeEdgeEntity>>) method
                                    .invoke(
                                            AVALONHexNodeEdgeController.getInstance(),
                                            (String) field.get(entity.getSecretEdges().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexNodeEdgeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from Controller by code");
            }
            try {
              field = AVALONHexNodeEdgeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEdgeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getSecretEdges().get(i)) != null) {
                                list = (List<Resource<AVALONHexNodeEdgeEntity>>) method
                                        .invoke(
                                                AVALONHexNodeEdgeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getSecretEdges().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexNodeEdgeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    secretEdges = list.get(0).getContent();
                }
                if (secretEdges == null) {
                    secretEdges = (AVALONHexNodeEdgeEntity) ((Resource) AVALONHexNodeEdgeController
                            .getInstance()
                            .save(entity.getSecretEdges().get(i)).get(0)).getContent();
                }
                entity.getSecretEdges().set(i, secretEdges);
                list = null;
            }
        }

    if (entity.getSideEdges() != null
                && !entity.getSideEdges().isEmpty()) {
            for (int i = entity.getSideEdges().size() - 1; i >= 0; i--) {
                AVALONHexSideEdgeEntity sideEdges = null;
                List<Resource<AVALONHexSideEdgeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexSideEdgeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexSideEdgeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexSideEdgeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexSideEdgeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getSideEdges().get(i)) != null) {
                            list = (List<Resource<AVALONHexSideEdgeEntity>>) method
                                    .invoke(
                                            AVALONHexSideEdgeController.getInstance(),
                                            (String) field.get(entity.getSideEdges().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexSideEdgeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexSideEdgeEntity from Controller by code");
            }
            try {
              field = AVALONHexSideEdgeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexSideEdgeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getSideEdges().get(i)) != null) {
                                list = (List<Resource<AVALONHexSideEdgeEntity>>) method
                                        .invoke(
                                                AVALONHexSideEdgeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getSideEdges().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexSideEdgeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    sideEdges = list.get(0).getContent();
                }
                if (sideEdges == null) {
                    sideEdges = (AVALONHexSideEdgeEntity) ((Resource) AVALONHexSideEdgeController
                            .getInstance()
                            .save(entity.getSideEdges().get(i)).get(0)).getContent();
                }
                entity.getSideEdges().set(i, sideEdges);
                list = null;
            }
        }

    if (entity.getTerrain() != null
                && !entity.getTerrain().isEmpty()) {
            for (int i = entity.getTerrain().size() - 1; i >= 0; i--) {
                AVALONHexNodeEntity terrain = null;
                List<Resource<AVALONHexNodeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = AVALONHexNodeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = AVALONHexNodeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getTerrain().get(i)) != null) {
                            list = (List<Resource<AVALONHexNodeEntity>>) method
                                    .invoke(
                                            AVALONHexNodeController.getInstance(),
                                            (String) field.get(entity.getTerrain().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = AVALONHexNodeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEntity from Controller by code");
            }
            try {
              field = AVALONHexNodeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity AVALONHexNodeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getTerrain().get(i)) != null) {
                                list = (List<Resource<AVALONHexNodeEntity>>) method
                                        .invoke(
                                                AVALONHexNodeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getTerrain().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity AVALONHexNodeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    terrain = list.get(0).getContent();
                }
                if (terrain == null) {
                    terrain = (AVALONHexNodeEntity) ((Resource) AVALONHexNodeController
                            .getInstance()
                            .save(entity.getTerrain().get(i)).get(0)).getContent();
                }
                entity.getTerrain().set(i, terrain);
                list = null;
            }
        }

        if (entity.getType() != null
        && entity.getType().getId() == null) {
      setTypeIdFromRepository(entity);
        }


    
        AVALONHexTileEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexTileEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setTypeIdFromRepository(
      final AVALONHexTileEntity entity) {
    AVALONHexTileTypeEntity memberEntity = null;
    List<Resource<AVALONHexTileTypeEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = AVALONHexTileTypeController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = AVALONHexTileTypeEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getType()) != null) {
          list = (List<Resource<AVALONHexTileTypeEntity>>) method
              .invoke(
                  AVALONHexTileTypeController.getInstance(),
                  (String) field
                      .get(entity.getType()));
        }
      }
      if (list == null) {
        try {
          method = AVALONHexTileTypeController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = AVALONHexTileTypeEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getType()) != null) {
            list = (List<Resource<AVALONHexTileTypeEntity>>)
                method.invoke(AVALONHexTileTypeController
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
      memberEntity = (AVALONHexTileTypeEntity)
          ((Resource) AVALONHexTileTypeController.getInstance().save(
              entity.getType()).get(0)).getContent();
    }
    entity.setType(memberEntity);
    list = null;
    }


    /**
     * Gets a list of {@link AVALONHexTileEntity}s that share a abbreviation.
     * @param abbreviation the hex_tile' abbreviation
     * @return {@link List}<{@link Resource}<{@link AVALONHexTileEntity}>>
     */
    @RequestMapping(path = "abbreviation/{abbreviation}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexTileEntity>> getByAbbreviation(
            @PathVariable final String abbreviation) {
        Iterator<AVALONHexTileEntity> iter = repository.findByAbbreviation(abbreviation)
                .iterator();
        List<Resource<AVALONHexTileEntity>> resources =
                new ArrayList<Resource<AVALONHexTileEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexTileResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONHexTileEntity}s that share a name.
     * @param name the hex_tile' name
     * @return {@link List}<{@link Resource}<{@link AVALONHexTileEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexTileEntity>> getByName(
            @PathVariable final String name) {
        Iterator<AVALONHexTileEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<AVALONHexTileEntity>> resources =
                new ArrayList<Resource<AVALONHexTileEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexTileResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
