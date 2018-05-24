package gov.dhs.uscis.odos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dhs.uscis.odos.domain.ConferenceRoomSchedule;


/**
 * Spring Data JPA repository for the ConferenceRoomSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConferenceRoomScheduleRepository extends JpaRepository<ConferenceRoomSchedule, Long> {
	public List<ConferenceRoomSchedule> findByRequestorId(String requestorId);
}
