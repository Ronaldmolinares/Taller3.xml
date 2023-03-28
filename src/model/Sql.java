package model;

import java.util.ArrayList;

public class Sql {
	
	private ArrayList<Room> listRoom = new ArrayList<Room>();
	//private ArrayList<Patient> listPatient = new ArrayList<Patient>();
	
	public ArrayList<Room> getListRoom() {
		return listRoom;
	}
	public void setListRoom(ArrayList<Room> listRoom) {
		this.listRoom = listRoom;
	}
//	public ArrayList<Patient> getListpatient() {
//		return listPatient;
//	}
//	public void setListpatient(ArrayList<Patient> listpatient) {
//		this.listPatient = listpatient;
//	}
	
	public void addRoom(Room room) {
		listRoom.add(room);
	}
	
//	public void addPatient(Patient patient) {
//		listPatient.add(patient);
//	}
	
	public int findRoom(int id) {
		int position = -1;
		for (Room room : listRoom) {
			if (id == room.getId()) {
				position = listRoom.indexOf(room);
			}
		}
		return position;
	}
}
