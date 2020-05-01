
public class FlightReservation extends Reservation {
	
	private Airport departure;
	private Airport arrival;
	
	public FlightReservation(String reservationName, Airport departurePlace, 
							 Airport arrivalPlace) {
		
		// initializes the locations
		super(reservationName);
		this.departure = departurePlace;
		this.arrival = arrivalPlace;
		
		// Throws an exception if both airports are identical
		if(departurePlace.equals(arrivalPlace)){
			
			throw new IllegalArgumentException("Airports cannot be identical.");
		}
	}
	
	public int getCost() {
		
		double fuel = ((Airport.getDistance(departure, arrival))/167.52);
		double fuelCost = fuel*124;
		double cost = (fuelCost + departure.getFees()+ arrival.getFees() + 5375);
		int roundedCost = (int) Math.ceil(cost);
		return roundedCost;
	}
	
	public boolean equals(Object anObject) {
		
		if(anObject instanceof FlightReservation) {
			if(((FlightReservation)anObject).reservationName() == this.reservationName()) {
				if(((FlightReservation)anObject).departure == this.departure) {
					if(((FlightReservation)anObject).arrival == this.arrival) {
						return true;
					}else return false;
				}else return false;
			}else return false;
		}else return false;
	}
}

