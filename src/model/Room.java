package model;

import java.util.ArrayList;

public class Room {
	private int id;
	private int bedNumbers;
	private int floorNumber;
	private short roomNumber;
	private ArrayList<Patient> listPatients = new ArrayList<Patient>();
	
	public Room () {
		
	}
	
	public Room(int id, int floorNumber, short roomNumber, int bedNumbers) {
		this.id = id;
		this.floorNumber = floorNumber;
		this.roomNumber = roomNumber;
		this.bedNumbers = bedNumbers;
	}

	public Room(int id, int bedNumbers, int floorNumber, short roomNumber, ArrayList<Patient> listPatients) {
		super();
		this.id = id;
		this.bedNumbers = bedNumbers;
		this.floorNumber = floorNumber;
		this.roomNumber = roomNumber;
		this.listPatients = listPatients;
	}

	public Room(int id, int bedNumbers, int floorNumber, short roomNumber) {
		this.id = id;
		this.floorNumber = floorNumber;
		this.roomNumber = roomNumber;
		this.bedNumbers = bedNumbers;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public short getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(short roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public ArrayList<Patient> getListPatients() {
		return listPatients;
	}
	
	public void addPatient(Patient patient) {
		listPatients.add(patient);
	}
	
	public int getBedNumbers() {
		return bedNumbers;
	}

	public void setBedNumbers(int bedNumbers) {
		this.bedNumbers = bedNumbers;
	}

	public Patient patient(int position) {
		return listPatients.get(position);
	}

	@Override
	public String toString() {
		return "Room {Id: " + id + ", Floor Number: " + floorNumber + ", Room Number: " + roomNumber + ", List Patients: "+ getListPatients() + "}" + "\n";
	}
	
}