
public class Customer {
	
	private String name;
	private int balance;
	private Basket reservations;
	
	public Customer(String customerName, int initialBalance) {
		
		this.name = customerName;
		this.balance = initialBalance;
		this.reservations = new Basket();
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public int getBalance() {
		
		return balance;
	}
	
	public Basket getBasket() {
		
		return this.reservations;
	}
	
	public int addFunds(int amount) {
		
		if(amount<0) {
			
			throw new IllegalArgumentException("Invalid amount of funds. Input cannot be negative.");
		}
		balance += amount;
		return balance;
	}
	
	public int addToBasket(Reservation reservation) {
		
		if((this.name).equals(reservation.reservationName()) == true) {
			reservations.add(reservation);
			return reservations.getNumOfReservations();
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	public int addToBasket(Hotel hotel, String roomType, int numNights, boolean breakfast) {
		
		HotelReservation newHotel = new HotelReservation(this.name, hotel, roomType, numNights);
		reservations.add(newHotel);
		return reservations.getNumOfReservations();
		
	}
	
	public int addToBasket(Airport a1, Airport a2) {
		
		FlightReservation newFlight = new FlightReservation(this.name, a1, a2);
		reservations.add(newFlight);
		return reservations.getNumOfReservations();
	}
	
	public boolean removeFromBasket(Reservation reservation) {
		
		if(reservations.remove(reservation) == true) {
			
			reservations.remove(reservation);
			return true;
		}
		else {
			return false;
		}
	}
	
	public int checkOut() {
		
		if(reservations.getTotalCost() > balance) {
			
			throw new IllegalStateException();
		}
		else {
			
			balance -= reservations.getTotalCost();
			reservations.clear();
			return balance;
		}
	}
}
