package gov.dhs.uscis.odos.service.dto;


import java.io.Serializable;

/**
 * A DTO for the Equipment entity.
 */
public class EquipmentDTO implements Serializable {

	private static final long serialVersionUID = 8218354466207328515L;
	
	private Long equipmentId;
	
	private String equipmentName;
	
	private String equipmentDesc;

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getEquipmentDesc() {
		return equipmentDesc;
	}

	public void setEquipmentDesc(String equipmentDesc) {
		this.equipmentDesc = equipmentDesc;
	}
}
