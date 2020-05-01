
public class BnBReservation extends HotelReservation {
	
	public BnBReservation(String name, Hotel hotel,
						  String type, int nightsSpent) {
		
		super(name, hotel, type, nightsSpent);
	}
	
	public int getCost() {
		
		return ((super.getCost()+(super.getNumOfNights()*1000)));
	}
}
