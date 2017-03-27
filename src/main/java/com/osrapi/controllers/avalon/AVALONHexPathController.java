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

import com.osrapi.models.avalon.AVALONHexPathEntity;
import com.osrapi.models.avalon.AVALONPathNodeEntity;
import com.osrapi.repositories.avalon.AVALONHexPathRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/hex_paths")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONHexPathController {
    /** the static instance of {@link AVALONHexPathController}. */
    private static AVALONHexPathController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONHexPathController}
     */
    public static AVALONHexPathController getInstance() {
        if (instance == null) {
            new AVALONHexPathController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONHexPathRepository repository;
    /** Creates a new instance of {@link AVALONHexPathController}. */
    public AVALONHexPathController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONHexPathEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONHexPathEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONHexPathEntity>> getAll() {
        Iterator<AVALONHexPathEntity> iter = repository.findAll()
                .iterator();
        final List<Resource<AVALONHexPathEntity>> resources =
                new ArrayList<>();
        while (iter.hasNext()) {
            resources.add(getHexPathResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONHexPathEntity}s that share a code.
     * @param code the hex_path' code
     * @return {@link List}<{@link Resource}<{@link AVALONHexPathEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexPathEntity>> getByCode(
            @PathVariable
            final String code) {
        Iterator<AVALONHexPathEntity> iter = repository.findByCode(code)
                .iterator();
        final List<Resource<AVALONHexPathEntity>> resources =
                new ArrayList<>();
        while (iter.hasNext()) {
            resources.add(getHexPathResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONHexPathEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONHexPathEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONHexPathEntity>> getById(
            @PathVariable
            final Long id) {
        AVALONHexPathEntity entity = repository.findOne(id);
        final List<Resource<AVALONHexPathEntity>> resources =
                new ArrayList<>();
        resources.add(getHexPathResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONHexPathEntity}.
     * @param entity the {@link AVALONHexPathEntity}
     * @return {@link Resource}<{@link AVALONHexPathEntity}>
     */
    private Resource<AVALONHexPathEntity> getHexPathResource(
            final AVALONHexPathEntity entity) {
        final Resource<AVALONHexPathEntity> resource =
                new Resource<>(
                        entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves a single {@link AVALONHexPathEntity}.
     * @param entity the {@link AVALONHexPathEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexPathEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONHexPathEntity>> save(
            @RequestBody
            final AVALONHexPathEntity entity) {
        System.out.println("save path "+entity.getCode());
        if (entity.getNodes() != null
                && !entity.getNodes().isEmpty()) {
            for (int i = entity.getNodes().size() - 1; i >= 0; i--) {
                AVALONPathNodeEntity nodes = null;
                List<Resource<AVALONPathNodeEntity>> list = null;
                try {
                    Method method = null;
                    try {
                        method = AVALONPathNodeController.class
                                .getDeclaredMethod(
                                        "getByName",
                                        new Class[] { String.class });
                    } catch (final NoSuchMethodException e) {
                        System.out.println(
                                "Cannot get embedded lookup Entity AVALONPathNodeEntity from Controller by name");
                    }
                    Field field = null;
                    try {
                        field = AVALONPathNodeEntity.class
                                .getDeclaredField("name");
                    } catch (final NoSuchFieldException e) {
                        System.out.println(
                                "Cannot get embedded lookup Entity AVALONPathNodeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getNodes().get(i)) != null) {
                            list = (List<Resource<AVALONPathNodeEntity>>) method
                                    .invoke(
                                            AVALONPathNodeController
                                                    .getInstance(),
                                            (String) field.get(
                                                    entity.getNodes().get(i)));
                        }
                    }
                    if (list == null) {
                        try {
                            method = AVALONPathNodeController.class
                                    .getDeclaredMethod(
                                            "getByCode",
                                            new Class[] { String.class });
                        } catch (final NoSuchMethodException e) {
                            System.out.println(
                                    "Cannot get embedded lookup Entity AVALONPathNodeEntity from Controller by code");
                        }
                        try {
                            field = AVALONPathNodeEntity.class.getDeclaredField(
                                    "code");
                        } catch (final NoSuchFieldException e) {
                            System.out.println(
                                    "Cannot get embedded lookup Entity AVALONPathNodeEntity from class by code");
                        }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getNodes().get(i)) != null) {
                                list = (List<
                                        Resource<AVALONPathNodeEntity>>) method
                                                .invoke(
                                                        AVALONPathNodeController
                                                                .getInstance(),
                                                        (String) field
                                                                .get(entity
                                                                        .getNodes()
                                                                        .get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
                    System.out.println(
                            "CANNOT get embedded lookup Entity AVALONPathNodeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    nodes = list.get(0).getContent();
                }
                if (nodes == null) {
                    System.out.println("try to save node "+entity.getNodes().get(i).getSortOrder()+"::"+entity.getNodes().get(i).getNode().toString());
                    nodes = (AVALONPathNodeEntity) ((Resource) AVALONPathNodeController
                            .getInstance()
                            .save(entity.getNodes().get(i)).get(0))
                                    .getContent();
                }
                entity.getNodes().set(i, nodes);
                list = null;
            }
        }

        AVALONHexPathEntity savedEntity = repository.save(entity);
        final List<Resource<AVALONHexPathEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Saves multiple {@link AVALONHexPathEntity}s.
     * @param entities the list of {@link AVALONHexPathEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexPathEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONHexPathEntity>> save(
            @RequestBody
            final List<AVALONHexPathEntity> entities) {
        final List<Resource<AVALONHexPathEntity>> resources =
                new ArrayList<>();
        Iterator<AVALONHexPathEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONHexPathEntity} instance
     */
    private void setIdFromRepository(final AVALONHexPathEntity entity) {
        List<AVALONHexPathEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONHexPathEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println(
                        "Cannot get Entity AVALONHexPathEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONHexPathEntity>) method.invoke(
                            repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || old != null
                            && old.size() > 1) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONHexPathEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    System.out.println(
                            "Cannot get Entity AVALONHexPathEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONHexPathEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
            System.out.println(
                    "Cannot get Entity AVALONHexPathEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;
    }
    /**
     * Updates a single {@link AVALONHexPathEntity}.
     * @param entity the {@link AVALONHexPathEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexPathEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONHexPathEntity>> update(
            @RequestBody
            final AVALONHexPathEntity entity) {
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
        if (entity.getNodes() != null
                && !entity.getNodes().isEmpty()) {
            for (int i = entity.getNodes().size() - 1; i >= 0; i--) {
                AVALONPathNodeEntity nodes = null;
                List<Resource<AVALONPathNodeEntity>> list = null;
                try {
                    Method method = null;
                    try {
                        method = AVALONPathNodeController.class
                                .getDeclaredMethod(
                                        "getByName",
                                        new Class[] { String.class });
                    } catch (final NoSuchMethodException e) {
                        System.out.println(
                                "Cannot get embedded lookup Entity AVALONPathNodeEntity from Controller by name");
                    }
                    Field field = null;
                    try {
                        field = AVALONPathNodeEntity.class
                                .getDeclaredField("name");
                    } catch (final NoSuchFieldException e) {
                        System.out.println(
                                "Cannot get embedded lookup Entity AVALONPathNodeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getNodes().get(i)) != null) {
                            list = (List<Resource<AVALONPathNodeEntity>>) method
                                    .invoke(
                                            AVALONPathNodeController
                                                    .getInstance(),
                                            (String) field.get(
                                                    entity.getNodes().get(i)));
                        }
                    }
                    if (list == null) {
                        try {
                            method = AVALONPathNodeController.class
                                    .getDeclaredMethod(
                                            "getByCode",
                                            new Class[] { String.class });
                        } catch (final NoSuchMethodException e) {
                            System.out.println(
                                    "Cannot get embedded lookup Entity AVALONPathNodeEntity from Controller by code");
                        }
                        try {
                            field = AVALONPathNodeEntity.class.getDeclaredField(
                                    "code");
                        } catch (final NoSuchFieldException e) {
                            System.out.println(
                                    "Cannot get embedded lookup Entity AVALONPathNodeEntity from class by code");
                        }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getNodes().get(i)) != null) {
                                list = (List<
                                        Resource<AVALONPathNodeEntity>>) method
                                                .invoke(
                                                        AVALONPathNodeController
                                                                .getInstance(),
                                                        (String) field
                                                                .get(entity
                                                                        .getNodes()
                                                                        .get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
                    System.out.println(
                            "CANNOT get embedded lookup Entity AVALONPathNodeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    nodes = list.get(0).getContent();
                }
                if (nodes == null) {
                    nodes = (AVALONPathNodeEntity) ((Resource) AVALONPathNodeController
                            .getInstance()
                            .save(entity.getNodes().get(i)).get(0))
                                    .getContent();
                }
                entity.getNodes().set(i, nodes);
                list = null;
            }
        }

        AVALONHexPathEntity savedEntity = repository.save(entity);
        final List<Resource<AVALONHexPathEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Updates multiple {@link AVALONHexPathEntity}s.
     * @param entities the list of {@link AVALONHexPathEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexPathEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONHexPathEntity>> update(
            @RequestBody
            final List<AVALONHexPathEntity> entities) {
        final List<Resource<AVALONHexPathEntity>> resources =
                new ArrayList<>();
        Iterator<AVALONHexPathEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
}
