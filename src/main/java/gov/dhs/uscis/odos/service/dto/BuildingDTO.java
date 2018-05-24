package gov.dhs.uscis.odos.service.dto;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the Building entity.
 */
public class BuildingDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6613433690413368185L;

	private Long buildingId;

    private String buildingName;

    private String buildingDesc;
    
    private List<ConferenceRoomDTO> conferenceRooms;

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingDesc() {
        return buildingDesc;
    }

    public void setBuildingDesc(String buildingDesc) {
        this.buildingDesc = buildingDesc;
    }

    public List<ConferenceRoomDTO> getConferenceRooms() {
		return conferenceRooms;
	}

	public void setConferenceRooms(List<ConferenceRoomDTO> conferenceRooms) {
		this.conferenceRooms = conferenceRooms;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuildingDTO buildingDTO = (BuildingDTO) o;
        if(buildingDTO.getBuildingId() == null || getBuildingId() == null) {
            return false;
        }
        return Objects.equals(getBuildingId(), buildingDTO.getBuildingId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getBuildingId());
    }

    @Override
    public String toString() {
        return "BuildingDTO{" +
            "buildId=" + getBuildingId() +
            ", buildingName='" + getBuildingName() + "'" +
            ", buildingDesc='" + getBuildingDesc() + "'" +
            "}";
    }
}
