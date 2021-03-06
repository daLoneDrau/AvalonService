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
@Table(name = "node_type", schema = "avalon")
public final class AVALONNodeTypeEntity {
    /**
     * the primary key - an autogenerated id (unique for each user in the db).
     */
    @Id
    @Column(name = "node_type_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
  generator = "node_type_seq")
    @SequenceGenerator(
        name = "node_type_seq",
        sequenceName = "avalon.node_type_id_seq",
        allocationSize = 1
    )
    private Long                    id;
    /** Creates a new instance of {@link AVALONNodeTypeEntity}. */
    public AVALONNodeTypeEntity() {
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

    /** the description. */
    @Column(name = "description")
    @JsonProperty("description")
    @NotNull
    private String                    description;
    /**
     * Gets the description.
     * @return {@link String}
     */
    public String getDescription() {
        return description;
    }
    /**
     * Sets the description.
     * @param val the new value
     */
    public void setDescription(final String val) {
        description = val;
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

}

