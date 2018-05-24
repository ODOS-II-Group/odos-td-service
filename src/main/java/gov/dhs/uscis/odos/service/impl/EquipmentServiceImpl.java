package gov.dhs.uscis.odos.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dhs.uscis.odos.domain.Equipment;
import gov.dhs.uscis.odos.repository.EquipmentRepository;
import gov.dhs.uscis.odos.service.EquipmentService;
import gov.dhs.uscis.odos.service.dto.EquipmentDTO;
import gov.dhs.uscis.odos.service.mapper.EquipmentMapper;

/**
 * Service Implementation for managing Equipment.
 */
@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {

    private final Logger log = LoggerFactory.getLogger(EquipmentServiceImpl.class);

    @Inject
    private  EquipmentRepository equipmentRepository;
    
    @Inject
    private EquipmentMapper equipmentMapper;


    /**
     * Save a equipment.
     *
     * @param equipmentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EquipmentDTO save(EquipmentDTO equipmentDTO) {
    	log.debug("Request to save Equipment : {}", equipmentDTO);
    	Equipment equipment = equipmentRepository.save(equipmentMapper.toEntity(equipmentDTO));
        return equipmentMapper.toDto(equipment);
    }

    /**
     * Get all the buildings.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EquipmentDTO> findAll() {
        log.debug("Request to get all Equipment");
        return equipmentRepository.findAll().stream()
            .map(equipmentMapper::toDto)
           .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one equipment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EquipmentDTO findOne(Long id) {
        log.debug("Request to get Equipment : {}", id);
        Equipment equipment = equipmentRepository.findOne(id);
        return equipmentMapper.toDto(equipment);
    }

    /**
     * Delete the equipment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Equipment : {}", id);
        equipmentRepository.delete(id);
    }
}
