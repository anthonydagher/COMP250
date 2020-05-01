
public class Airport {
	
	private int x;
	private int y;
	private int fees;
	
	public Airport(int xCoord, int yCoord, int feesAirport) {
		
		// initializing the coordinates and fees
		this.x = xCoord;
		this.y = yCoord;
		this.fees = feesAirport;
	
	}

	public int getFees() { // method to get the fees of the airport
		
		return this.fees;
	}

	public static int getDistance(Airport airport1, Airport airport2) {
		
		// Calculate distance between two airports
		double distance = (Math.sqrt((Math.pow((airport1.x-airport2.x),2))+Math.pow((airport1.y-airport2.y),2)));
		int roundedDistance = (int) Math.ceil(distance);
		return roundedDistance;
	}
}
