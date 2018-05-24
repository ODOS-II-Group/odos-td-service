package gov.dhs.uscis.odos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Building.
 */
@Entity
@Table(name = "bldg")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Building implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bldg_id")
    private Long buildingId;

    @Column(name = "bldg_nm")
    private String buildingName;

    @Column(name = "bldg_dsc")
    private String buildingDesc;
    
    @OneToMany(mappedBy = "building", fetch = FetchType.LAZY ,cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ConferenceRoom> ConferenceRoom = new ArrayList<>();

    public Long getBuildingId() {
        return buildingId;
    }

    public Building buildingId(Long buildingId) {
        this.buildingId = buildingId;
        return this;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public Building buildingName(String buildingName) {
        this.buildingName = buildingName;
        return this;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingDesc() {
        return buildingDesc;
    }

    public Building buildingDesc(String buildingDesc) {
        this.buildingDesc = buildingDesc;
        return this;
    }

    public void setBuildingDesc(String buildingDesc) {
        this.buildingDesc = buildingDesc;
    }
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public List<ConferenceRoom> getConferenceRoom() {
		return ConferenceRoom;
	}

	public void setConferenceRoom(List<ConferenceRoom> conferenceRoom) {
		ConferenceRoom = conferenceRoom;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Building building = (Building) o;
        if (building.getBuildingId() == null || getBuildingId() == null) {
            return false;
        }
        return Objects.equals(getBuildingId(), building.getBuildingId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getBuildingId());
    }

    @Override
    public String toString() {
        return "Building{" +
            "buildId=" + getBuildingId() +
            ", buildingName='" + getBuildingName() + "'" +
            ", buildingDesc='" + getBuildingDesc() + "'" +
            "}";
    }
}
