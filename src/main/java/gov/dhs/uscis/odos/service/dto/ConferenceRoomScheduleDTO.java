package gov.dhs.uscis.odos.service.dto;


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the ConferenceRoomSchedule entity.
 */
public class ConferenceRoomScheduleDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -737083741204310273L;

	private Long id;
	
	private Long conferenceRoomId;

    @NotNull
    private String requestorId;

    @NotNull
    private String roomScheduleStartTime;

    @NotNull
    private String roomScheduleEndTime;

    private String conferenceTitle;
    
    private String buildingName;
    
    private String conferenceRoomName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConferenceRoomId() {
		return conferenceRoomId;
	}

	public void setConferenceRoomId(Long conferenceRoomId) {
		this.conferenceRoomId = conferenceRoomId;
	}

	public String getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }

    public String getRoomScheduleStartTime() {
        return roomScheduleStartTime;
    }

    public void setRoomScheduleStartTime(String roomScheduleStartTime) {
        this.roomScheduleStartTime = roomScheduleStartTime;
    }

    public String getRoomScheduleEndTime() {
        return roomScheduleEndTime;
    }

    public void setRoomScheduleEndTime(String roomScheduleEndTime) {
        this.roomScheduleEndTime = roomScheduleEndTime;
    }

    public String getConferenceTitle() {
        return conferenceTitle;
    }

    public void setConferenceTitle(String conferenceTitle) {
        this.conferenceTitle = conferenceTitle;
    }

    public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getConferenceRoomName() {
		return conferenceRoomName;
	}

	public void setConferenceRoomName(String conferenceRoomName) {
		this.conferenceRoomName = conferenceRoomName;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConferenceRoomScheduleDTO conferenceRoomScheduleDTO = (ConferenceRoomScheduleDTO) o;
        if(conferenceRoomScheduleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conferenceRoomScheduleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConferenceRoomScheduleDTO{" +
            "id=" + getId() +
            ", requestorId='" + getRequestorId() + "'" +
            ", roomScheduleStartTime='" + getRoomScheduleStartTime() + "'" +
            ", roomScheduleEndTime='" + getRoomScheduleEndTime() + "'" +
            ", conferenceTitle='" + getConferenceTitle() + "'" +
            "}";
    }
}
