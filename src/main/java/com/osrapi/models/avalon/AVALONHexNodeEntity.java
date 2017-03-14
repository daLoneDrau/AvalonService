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
@Table(name = "hex_node", schema = "avalon")
public final class AVALONHexNodeEntity {
    /**
     * the primary key - an autogenerated id (unique for each user in the db).
     */
    @Id
    @Column(name = "hex_node_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
  generator = "hex_node_seq")
    @SequenceGenerator(
        name = "hex_node_seq",
        sequenceName = "avalon.hex_node_id_seq",
        allocationSize = 1
    )
    private Long                    id;
    /** Creates a new instance of {@link AVALONHexNodeEntity}. */
    public AVALONHexNodeEntity() {
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

    /** the code. */
    @Column(name = "code")
    @JsonProperty("code")
    @NotNull
    private String                    code;
    /**
     * Gets the code.
     * @return {@link String}
     */
    public String getCode() {
        return code;
    }
    /**
     * Sets the code.
     * @param val the new value
     */
    public void setCode(final String val) {
        code = val;
    }

    /** the type. */
    @ManyToOne(targetEntity = AVALONHexTerrainTypeEntity.class, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "type")
    @JsonProperty("type")
  @NotNull
    private AVALONHexTerrainTypeEntity    type;
    /**
     * Gets the type.
     * @return {@link AVALONHexTerrainTypeEntity}
     */
    public AVALONHexTerrainTypeEntity getType() {
        return type;
    }
    /**
     * Sets the type.
     * @param val the new value
     */
    public void setType(final AVALONHexTerrainTypeEntity val) {
        type = val;
    }

    /** the location. */
    @ManyToOne(targetEntity = AVALONVector3Entity.class, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "location")
    @JsonProperty("location")
  @NotNull
    private AVALONVector3Entity    location;
    /**
     * Gets the location.
     * @return {@link AVALONVector3Entity}
     */
    public AVALONVector3Entity getLocation() {
        return location;
    }
    /**
     * Sets the location.
     * @param val the new value
     */
    public void setLocation(final AVALONVector3Entity val) {
        location = val;
    }

}

