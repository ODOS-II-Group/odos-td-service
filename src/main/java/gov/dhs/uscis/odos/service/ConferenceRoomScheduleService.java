package gov.dhs.uscis.odos.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import gov.dhs.uscis.odos.service.dto.ConferenceRoomScheduleDTO;

/**
 * Service Interface for managing ConferenceRoomSchedule.
 */
public interface ConferenceRoomScheduleService {

	/**
	 * Save a conferenceRoomSchedule.
	 *
	 * @param conferenceRoomScheduleDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	ConferenceRoomScheduleDTO save(ConferenceRoomScheduleDTO conferenceRoomScheduleDTO);

	/**
	 * Get all the conferenceRoomSchedules.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	Page<ConferenceRoomScheduleDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" conferenceRoomSchedule.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	ConferenceRoomScheduleDTO findOne(Long id);

	/**
	 * Delete the "id" conferenceRoomSchedule.
	 *
	 * @param id
	 *            the id of the entity
	 */
	void delete(Long id);

	List<ConferenceRoomScheduleDTO> findByRequestId(String requestorId);

	/**
	 * Get all scheduled room information
	 * 
	 * @param conferenceRoomId
	 * @return List<ConferenceRoomScheduleDTO>
	 */
	List<ConferenceRoomScheduleDTO> findAllByConferenceRoom(long conferenceRoomId);

	/**
	 * Get all scheduled room today
	 * 
	 * @param conferenceRoomId
	 * @return List<ConferenceRoomScheduleDTO>
	 */

	int findAllScheduledRoomTodayById(Long id);

	/**
	 * Get all room BookedTimeSlotscheduled room today
	 * 
	 * @param conferenceRoomId
	 * @return List<ConferenceRoomScheduleDTO>
	 */

	List<Integer> getRoomBookedTimeSlot(Long id, String date);

	List<ConferenceRoomScheduleDTO> findAllByConferenceRoomIdAndDate(Long conferenceRoomId,
			String roomScheduleStartTime);

}
