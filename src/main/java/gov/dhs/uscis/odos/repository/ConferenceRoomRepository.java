package gov.dhs.uscis.odos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dhs.uscis.odos.domain.ConferenceRoom;


/**
 * Spring Data JPA repository for the ConferenceRoom entity.
 */
@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, Long> {

}
