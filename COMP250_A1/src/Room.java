
public class Room {
	
	private String roomType;
	private int price;
	private boolean availability;
	
	public Room(String type) { // constructor
		
		// Throws an exception if the room type is not equal to king, queen or double
		if((type.equalsIgnoreCase("queen") || type.equalsIgnoreCase("king") || type.equalsIgnoreCase("double")) == false) {
	
			throw new IllegalArgumentException("Sorry, there is no room of such type.");
		}
		
		if(type.equalsIgnoreCase("queen")) { // initializes the remaining fields for queen
			
			this.roomType = "queen";
			this.price = 11000;
			this.availability = true;
		}
		
		if(type.equalsIgnoreCase("king")) { // initializes the remaining fields for king
			
			this.roomType = "king";
			this.price = 15000;
			this.availability = true;
		}
		
		if(type.equalsIgnoreCase("double")) { // initializes the remaining fields for double
	
			this.roomType = "double";
			this.price = 9000;
			this.availability = true;
		}
	}
	
	public Room(Room room) {
		
		this.roomType = room.roomType;
		this.price = room.price;
		this.availability = room.availability;
	}
	
	public String getType() { // method to get the type of a room
		
		return this.roomType;
	}
	
	public int getPrice() { // method to get the price of a room
		
		return this.price;
	}
	
	public void changeAvailability() {
		
		// Returns false if the availability is true
		if(this.availability == true) {
			this.availability = false;
			
		}
		// Returns true if the availability is false
		else {
			this.availability = true;	
		}
	}
	
	public static Room findAvailableRoom(Room[] rooms, String type) {
		
		for(int i=0; i<=(rooms.length-1); i++) {
			 
			if(rooms[i].availability == true) {
				
				if(rooms[i].getType() == type) {
					
					return rooms[i];
				}	
			}
			
			if(i == rooms.length-1) {
				
				if(rooms[i].availability == false) {
					
					return null;
				}
			}
		}
		return null;
	}
	public static boolean makeRoomAvailable(Room[] rooms, String type) {
		
		for(int i=0; i<=(rooms.length-1); i++) {
			
			if(rooms[i].availability == false) {
				
				rooms[i].availability = true;
				i = rooms.length-1;
				return rooms[i].availability;
			}
			 
			if(i == rooms.length-1) {
				 
				if(rooms[i].availability == true) {
					 
					return false;
				}
			}
		}
		return false;
	}
}
