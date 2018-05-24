package gov.dhs.uscis.odos.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ConferenceRoomSchedule.
 */
@Entity
@Table(name = "conf_sched")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConferenceRoomSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "conf_sch_id")
    private Long id;

    @NotNull
    @Column(name = "req_id", nullable = false)
    private String requestorId;

    @NotNull
    @Column(name = "start_dttm", nullable = false)
    private Date roomScheduleStartTime;

    @NotNull
    @Column(name = "end_dttm", nullable = false)
    private Date roomScheduleEndTime;

    @Column(name = "conf_title")
    private String conferenceTitle;
    
    @ManyToOne(targetEntity = ConferenceRoom.class,cascade = CascadeType.ALL)
	@JoinColumn(name = "conf_rm_id")
	private ConferenceRoom conferenceRoom;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestorId() {
        return requestorId;
    }

    public ConferenceRoomSchedule requestorId(String requestorId) {
        this.requestorId = requestorId;
        return this;
    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }

    public Date getRoomScheduleStartTime() {
        return roomScheduleStartTime;
    }

    public ConferenceRoomSchedule roomScheduleStartTime(Date roomScheduleStartTime) {
        this.roomScheduleStartTime = roomScheduleStartTime;
        return this;
    }

    public void setRoomScheduleStartTime(Date roomScheduleStartTime) {
        this.roomScheduleStartTime = roomScheduleStartTime;
    }

    public Date getRoomScheduleEndTime() {
        return roomScheduleEndTime;
    }

    public ConferenceRoomSchedule roomScheduleEndTime(Date roomScheduleEndTime) {
        this.roomScheduleEndTime = roomScheduleEndTime;
        return this;
    }

    public void setRoomScheduleEndTime(Date roomScheduleEndTime) {
        this.roomScheduleEndTime = roomScheduleEndTime;
    }

    public String getConferenceTitle() {
        return conferenceTitle;
    }

    public ConferenceRoomSchedule conferenceTitle(String conferenceTitle) {
        this.conferenceTitle = conferenceTitle;
        return this;
    }

    public void setConferenceTitle(String conferenceTitle) {
        this.conferenceTitle = conferenceTitle;
    }
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public ConferenceRoom getConferenceRoom() {
		return conferenceRoom;
	}

	public void setConferenceRoom(ConferenceRoom conferenceRoom) {
		this.conferenceRoom = conferenceRoom;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConferenceRoomSchedule conferenceRoomSchedule = (ConferenceRoomSchedule) o;
        if (conferenceRoomSchedule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conferenceRoomSchedule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConferenceRoomSchedule{" +
            "id=" + getId() +
            ", requestorId='" + getRequestorId() + "'" +
            ", roomScheduleStartTime='" + getRoomScheduleStartTime() + "'" +
            ", roomScheduleEndTime='" + getRoomScheduleEndTime() + "'" +
            ", conferenceTitle='" + getConferenceTitle() + "'" +
            "}";
    }
}
