
public abstract class Reservation {
	
	private String name;
	
	public Reservation(String clientName) {
		
		this.name = clientName; // initializes the name
	}
	
	public final String reservationName() {
		
		return this.name;
	}
	
	public abstract int getCost();
	
	public abstract boolean equals(Object anObject);
}
