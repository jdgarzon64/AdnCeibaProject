package com.ceiba.AdnProject.model;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the vehicle database table.
 * 
 */
@Embeddable
public class VehiclePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_vehicle")
	private int idVehicle;

	@Column(name="parking_id_parking")
	private int parkingIdParking;

	@Column(name="vehicle_type_id_type")
	private int vehicleTypeIdType;

	public VehiclePK() {
	}
	public int getIdVehicle() {
		return this.idVehicle;
	}
	public void setIdVehicle(int idVehicle) {
		this.idVehicle = idVehicle;
	}
	public int getParkingIdParking() {
		return this.parkingIdParking;
	}
	public void setParkingIdParking(int parkingIdParking) {
		this.parkingIdParking = parkingIdParking;
	}
	public int getVehicleTypeIdType() {
		return this.vehicleTypeIdType;
	}
	public void setVehicleTypeIdType(int vehicleTypeIdType) {
		this.vehicleTypeIdType = vehicleTypeIdType;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VehiclePK)) {
			return false;
		}
		VehiclePK castOther = (VehiclePK)other;
		return 
			(this.idVehicle == castOther.idVehicle)
			&& (this.parkingIdParking == castOther.parkingIdParking)
			&& (this.vehicleTypeIdType == castOther.vehicleTypeIdType);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idVehicle;
		hash = hash * prime + this.parkingIdParking;
		hash = hash * prime + this.vehicleTypeIdType;
		
		return hash;
	}
}