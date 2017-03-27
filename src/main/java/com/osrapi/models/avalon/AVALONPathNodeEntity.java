package com.osrapi.models.avalon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author drau
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity
@Table(name = "path_node", schema = "avalon")
public final class AVALONPathNodeEntity {
    /**
     * the primary key - an autogenerated id (unique for each user in the db).
     */
    @Id
    @Column(name = "path_node_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
  generator = "path_node_seq")
    @SequenceGenerator(
        name = "path_node_seq",
        sequenceName = "avalon.path_node_id_seq",
        allocationSize = 1
    )
    private Long                    id;
    /** Creates a new instance of {@link AVALONPathNodeEntity}. */
    public AVALONPathNodeEntity() {
        super();
    }
    /**
     * Gets the id.
     * @return {@link Long}
     */
    public Long getId() {
        return id;
    }
    /**
     * Sets the id.
     * @param val the new value
     */
    public void setId(final Long val) {
        id = val;
    }

    /** the sortOrder. */
    @Column(name = "sort_order")
    @JsonProperty("sort_order")
    @NotNull
    private long                    sortOrder;
    /**
     * Gets the sortOrder.
     * @return {@link long}
     */
    public long getSortOrder() {
        return sortOrder;
    }
    /**
     * Sets the sortOrder.
     * @param val the new value
     */
    public void setSortOrder(final long val) {
        sortOrder = val;
    }

    /** the node. */
    @ManyToOne(targetEntity = AVALONVector3Entity.class, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "node")
    @JsonProperty("node")
  @NotNull
    private AVALONVector3Entity    node;
    /**
     * Gets the node.
     * @return {@link AVALONVector3Entity}
     */
    public AVALONVector3Entity getNode() {
        return node;
    }
    /**
     * Sets the node.
     * @param val the new value
     */
    public void setNode(final AVALONVector3Entity val) {
        node = val;
    }

}

