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

import com.osrapi.models.avalon.AVALONHexagonEntity;

import com.osrapi.repositories.avalon.AVALONHexagonRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/avalon/hexagons")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AVALONHexagonController {
    /** the static instance of {@link AVALONHexagonController}. */
    private static AVALONHexagonController instance;
    /**
     * Gets the static instance.
     * @return {@link AVALONHexagonController}
     */
    public static AVALONHexagonController getInstance() {
        if (instance == null) {
            new AVALONHexagonController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private AVALONHexagonRepository repository;
    /** Creates a new instance of {@link AVALONHexagonController}. */
    public AVALONHexagonController() {
        instance = this;
    }
    /**
     * Gets a list of {@link AVALONHexagonEntity}s.
     * @return {@link List}<{@link Resource}<{@link AVALONHexagonEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<AVALONHexagonEntity>> getAll() {
        Iterator<AVALONHexagonEntity> iter = repository.findAll()
                .iterator();
        List<Resource<AVALONHexagonEntity>> resources =
                new ArrayList<Resource<AVALONHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link AVALONHexagonEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link AVALONHexagonEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<AVALONHexagonEntity>> getById(
            @PathVariable final Long id) {
        AVALONHexagonEntity entity = repository.findOne(id);
        List<Resource<AVALONHexagonEntity>> resources =
                new ArrayList<Resource<AVALONHexagonEntity>>();
        resources.add(getHexagonResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link AVALONHexagonEntity}.
     * @param entity the {@link AVALONHexagonEntity}
     * @return {@link Resource}<{@link AVALONHexagonEntity}>
     */
    private Resource<AVALONHexagonEntity> getHexagonResource(
            final AVALONHexagonEntity entity) {
        Resource<AVALONHexagonEntity> resource =
                new Resource<AVALONHexagonEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link AVALONHexagonEntity}s.
     * @param entities the list of {@link AVALONHexagonEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexagonEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<AVALONHexagonEntity>> save(
            @RequestBody final List<AVALONHexagonEntity> entities) {
        List<Resource<AVALONHexagonEntity>> resources =
                new ArrayList<Resource<AVALONHexagonEntity>>();
        Iterator<AVALONHexagonEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link AVALONHexagonEntity}.
     * @param entity the {@link AVALONHexagonEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexagonEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<AVALONHexagonEntity>> save(
            @RequestBody final AVALONHexagonEntity entity) {
    
    
        AVALONHexagonEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexagonEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link AVALONHexagonEntity} instance
     */
    private void setIdFromRepository(final AVALONHexagonEntity entity) {
        List<AVALONHexagonEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = AVALONHexagonEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity AVALONHexagonEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<AVALONHexagonEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = AVALONHexagonEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity AVALONHexagonEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<AVALONHexagonEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity AVALONHexagonEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link AVALONHexagonEntity}s.
     * @param entities the list of {@link AVALONHexagonEntity} instances
     * @return {@link List}<{@link Resource}<{@link AVALONHexagonEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<AVALONHexagonEntity>> update(
            @RequestBody final List<AVALONHexagonEntity> entities) {
        List<Resource<AVALONHexagonEntity>> resources = new ArrayList<Resource<AVALONHexagonEntity>>();
        Iterator<AVALONHexagonEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link AVALONHexagonEntity}.
     * @param entity the {@link AVALONHexagonEntity} instance
     * @return {@link List}<{@link Resource}<{@link AVALONHexagonEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<AVALONHexagonEntity>> update(
            @RequestBody final AVALONHexagonEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        AVALONHexagonEntity savedEntity = repository.save(entity);
        List<Resource<AVALONHexagonEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link AVALONHexagonEntity}s that share a flat.
     * @param flat the hexagon' flat
     * @return {@link List}<{@link Resource}<{@link AVALONHexagonEntity}>>
     */
    @RequestMapping(path = "flat/{flat}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexagonEntity>> getByFlat(
            @PathVariable final Boolean flat) {
        Iterator<AVALONHexagonEntity> iter = repository.findByFlat(flat)
                .iterator();
        List<Resource<AVALONHexagonEntity>> resources =
                new ArrayList<Resource<AVALONHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONHexagonEntity}s that share a height.
     * @param height the hexagon' height
     * @return {@link List}<{@link Resource}<{@link AVALONHexagonEntity}>>
     */
    @RequestMapping(path = "height/{height}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexagonEntity>> getByHeight(
            @PathVariable final float height) {
        Iterator<AVALONHexagonEntity> iter = repository.findByHeight(height)
                .iterator();
        List<Resource<AVALONHexagonEntity>> resources =
                new ArrayList<Resource<AVALONHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONHexagonEntity}s that share a horizontalDistance.
     * @param horizontalDistance the hexagon' horizontalDistance
     * @return {@link List}<{@link Resource}<{@link AVALONHexagonEntity}>>
     */
    @RequestMapping(path = "horizontal_distance/{horizontalDistance}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexagonEntity>> getByHorizontalDistance(
            @PathVariable final float horizontalDistance) {
        Iterator<AVALONHexagonEntity> iter = repository.findByHorizontalDistance(horizontalDistance)
                .iterator();
        List<Resource<AVALONHexagonEntity>> resources =
                new ArrayList<Resource<AVALONHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONHexagonEntity}s that share a size.
     * @param size the hexagon' size
     * @return {@link List}<{@link Resource}<{@link AVALONHexagonEntity}>>
     */
    @RequestMapping(path = "size/{size}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexagonEntity>> getBySize(
            @PathVariable final float size) {
        Iterator<AVALONHexagonEntity> iter = repository.findBySize(size)
                .iterator();
        List<Resource<AVALONHexagonEntity>> resources =
                new ArrayList<Resource<AVALONHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONHexagonEntity}s that share a verticalDistance.
     * @param verticalDistance the hexagon' verticalDistance
     * @return {@link List}<{@link Resource}<{@link AVALONHexagonEntity}>>
     */
    @RequestMapping(path = "vertical_distance/{verticalDistance}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexagonEntity>> getByVerticalDistance(
            @PathVariable final float verticalDistance) {
        Iterator<AVALONHexagonEntity> iter = repository.findByVerticalDistance(verticalDistance)
                .iterator();
        List<Resource<AVALONHexagonEntity>> resources =
                new ArrayList<Resource<AVALONHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONHexagonEntity}s that share a width.
     * @param width the hexagon' width
     * @return {@link List}<{@link Resource}<{@link AVALONHexagonEntity}>>
     */
    @RequestMapping(path = "width/{width}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexagonEntity>> getByWidth(
            @PathVariable final float width) {
        Iterator<AVALONHexagonEntity> iter = repository.findByWidth(width)
                .iterator();
        List<Resource<AVALONHexagonEntity>> resources =
                new ArrayList<Resource<AVALONHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONHexagonEntity}s that share a x.
     * @param x the hexagon' x
     * @return {@link List}<{@link Resource}<{@link AVALONHexagonEntity}>>
     */
    @RequestMapping(path = "x/{x}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexagonEntity>> getByX(
            @PathVariable final Long x) {
        Iterator<AVALONHexagonEntity> iter = repository.findByX(x)
                .iterator();
        List<Resource<AVALONHexagonEntity>> resources =
                new ArrayList<Resource<AVALONHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONHexagonEntity}s that share a y.
     * @param y the hexagon' y
     * @return {@link List}<{@link Resource}<{@link AVALONHexagonEntity}>>
     */
    @RequestMapping(path = "y/{y}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexagonEntity>> getByY(
            @PathVariable final Long y) {
        Iterator<AVALONHexagonEntity> iter = repository.findByY(y)
                .iterator();
        List<Resource<AVALONHexagonEntity>> resources =
                new ArrayList<Resource<AVALONHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link AVALONHexagonEntity}s that share a z.
     * @param z the hexagon' z
     * @return {@link List}<{@link Resource}<{@link AVALONHexagonEntity}>>
     */
    @RequestMapping(path = "z/{z}",
            method = RequestMethod.GET)
    public List<Resource<AVALONHexagonEntity>> getByZ(
            @PathVariable final Long z) {
        Iterator<AVALONHexagonEntity> iter = repository.findByZ(z)
                .iterator();
        List<Resource<AVALONHexagonEntity>> resources =
                new ArrayList<Resource<AVALONHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
