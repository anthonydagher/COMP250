
public class HotelReservation extends Reservation {
	
	private Hotel reservationPlace;
	private String roomType;
	private int nights;
	private int price;
	
	public HotelReservation(String reservationName, Hotel hotel, 
							String type, int nightsSpent) {
		
		super(reservationName);
		this.roomType = type;
		this.nights = nightsSpent;
		this.reservationPlace = hotel;
		this.price = this.reservationPlace.reserveRoom(this.roomType);
		
	}
	
	public int getNumOfNights() {
		
		return this.nights;
	}
	
	public int getCost() {
		
		return this.price*this.nights;
	}
	
	public boolean equals(Object anObject) {
		
		if(anObject instanceof HotelReservation) {
			if(((HotelReservation)anObject).getNumOfNights() == this.getNumOfNights()) {
				if(((HotelReservation)anObject).getCost() == this.getCost()) {
					if(((HotelReservation)anObject).reservationPlace == this.reservationPlace) {
						if(((HotelReservation)anObject).roomType == this.roomType) {
							if(((HotelReservation)anObject).reservationName() == this.reservationName()) {
								return true;
							}else return false;
						}else return false;
					}else return false;
				}else return false;
			}else return false;
		}else return false;
	}
}

