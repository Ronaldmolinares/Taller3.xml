package model;

import java.util.ArrayList;

public class Sql {
	
	private ArrayList<Room> listRoom = new ArrayList<Room>();
	
	public ArrayList<Room> getListRoom() {
		return listRoom;
	}
	public void setListRoom(ArrayList<Room> listRoom) {
		this.listRoom = listRoom;
	}
	
	public void addRoom(Room room) {
		listRoom.add(room);
	}
	
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
