
public class Hotel {
	
	private String name;
	private Room[] rooms;
	
	public Hotel(String name, Room[] roomsCopy) {
		
		Room[] newRoom = new Room[roomsCopy.length];
		this.name = name;
		for (int i = 0; i < newRoom.length; i++) {
			
			newRoom[i] = new Room(roomsCopy[i]);
		}
		this.rooms = newRoom;
	}
	
	public int reserveRoom(String roomType) {
		
		if(Room.findAvailableRoom(this.rooms, roomType) != null) {
			
			Room availableRoom = Room.findAvailableRoom(this.rooms, roomType);
			availableRoom.changeAvailability();
			return availableRoom.getPrice();
		}	
		else {
			
			throw new IllegalArgumentException();
		}
	}
	
	public boolean cancelRoom(String roomType) {
		
		if(Room.makeRoomAvailable(this.rooms, roomType) == true) {
			
			Room.makeRoomAvailable(this.rooms, roomType);
			return true;
		}
		
		else {
			
			return false;
		}
	}
}
