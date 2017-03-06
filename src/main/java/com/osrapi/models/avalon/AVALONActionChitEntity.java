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
@Table(name = "action_chit", schema = "avalon")
public final class AVALONActionChitEntity {
    /**
     * the primary key - an autogenerated id (unique for each user in the db).
     */
    @Id
    @Column(name = "action_chit_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
  generator = "action_chit_seq")
    @SequenceGenerator(
        name = "action_chit_seq",
        sequenceName = "avalon.action_chit_id_seq",
        allocationSize = 1
    )
    private Long                    id;
    /** Creates a new instance of {@link AVALONActionChitEntity}. */
    public AVALONActionChitEntity() {
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

    /** the fatigueAsterisk. */
    @Column(name = "fatigue_asterisk")
    @JsonProperty("fatigue_asterisk")
    
    private Long                    fatigueAsterisk;
    /**
     * Gets the fatigueAsterisk.
     * @return {@link Long}
     */
    public Long getFatigueAsterisk() {
        return fatigueAsterisk;
    }
    /**
     * Sets the fatigueAsterisk.
     * @param val the new value
     */
    public void setFatigueAsterisk(final Long val) {
        fatigueAsterisk = val;
    }

    /** the speed. */
    @Column(name = "speed")
    @JsonProperty("speed")
    @NotNull
    private long                    speed;
    /**
     * Gets the speed.
     * @return {@link long}
     */
    public long getSpeed() {
        return speed;
    }
    /**
     * Sets the speed.
     * @param val the new value
     */
    public void setSpeed(final long val) {
        speed = val;
    }

    /** the magicType. */
    @ManyToOne(targetEntity = AVALONMagicColorEntity.class, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "magicType")
    @JsonProperty("magic_type")
  
    private AVALONMagicColorEntity    magicType;
    /**
     * Gets the magicType.
     * @return {@link AVALONMagicColorEntity}
     */
    public AVALONMagicColorEntity getMagicType() {
        return magicType;
    }
    /**
     * Sets the magicType.
     * @param val the new value
     */
    public void setMagicType(final AVALONMagicColorEntity val) {
        magicType = val;
    }

    /** the strength. */
    @ManyToOne(targetEntity = AVALONVulnerabilityEntity.class, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "strength")
    @JsonProperty("strength")
  
    private AVALONVulnerabilityEntity    strength;
    /**
     * Gets the strength.
     * @return {@link AVALONVulnerabilityEntity}
     */
    public AVALONVulnerabilityEntity getStrength() {
        return strength;
    }
    /**
     * Sets the strength.
     * @param val the new value
     */
    public void setStrength(final AVALONVulnerabilityEntity val) {
        strength = val;
    }

    /** the type. */
    @ManyToOne(targetEntity = AVALONActionTypeEntity.class, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "type")
    @JsonProperty("type")
  @NotNull
    private AVALONActionTypeEntity    type;
    /**
     * Gets the type.
     * @return {@link AVALONActionTypeEntity}
     */
    public AVALONActionTypeEntity getType() {
        return type;
    }
    /**
     * Sets the type.
     * @param val the new value
     */
    public void setType(final AVALONActionTypeEntity val) {
        type = val;
    }

}

