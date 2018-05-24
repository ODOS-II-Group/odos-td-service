package gov.dhs.uscis.odos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dhs.uscis.odos.domain.ConferenceRoomEquipment;


/**
 * Spring Data JPA repository for the Building entity.
 */
@Repository
public interface ConferenceRoomEquipmentRepository extends JpaRepository<ConferenceRoomEquipment, Long> {

}
