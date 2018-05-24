package gov.dhs.uscis.odos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dhs.uscis.odos.domain.Building;


/**
 * Spring Data JPA repository for the Building entity.
 */
@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
	List<Building> findByBuildingName(String buildingName);
}
