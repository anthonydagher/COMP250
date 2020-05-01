public class Basket {
	
	private Reservation[] reservations;
	
	public Basket() {
		
		this.reservations = new Reservation[0];
	}
	
	public Reservation[] getProducts() {
		
		int i;
		Reservation[] newReservation = new Reservation[reservations.length];
		for(i=0; i < reservations.length; i++) {
			
			newReservation[i] = reservations[i];
		}
		return newReservation;
	}
	
	public int add(Reservation reservation) {
		
		int i;
		int size = this.reservations.length;
		Reservation[] newReservation = new Reservation[size+1];
		
		for(i=0; i < size; i++) {
			
			newReservation[i] = this.reservations[i];
		}
		newReservation[size] = reservation;
		this.reservations = newReservation;
		return reservations.length;
	}
	
	public boolean remove(Reservation reservation) {
		
		int i;
		int k;
		int l;
		int size = this.reservations.length;
		for(i=0; i< size; i++) {
			
			if(reservations[i].equals(reservation)) {
				
				Reservation[] newReservation = new Reservation[size-1];
				
				for(k=i; k<size-1;k++) {
					
					reservations[k]=reservations[k+1];
				}
				for(l=0; l<size-1;l++) {
					
					newReservation[l] = reservations[l];
				}
				reservations[this.reservations.length-1]= null;
				reservations = newReservation;
				return true;
			} 
		}
		return false;
	}
	
	public void clear() {
		
		Reservation[] newReservation = new Reservation[0];
		this.reservations = newReservation;
	}
	
	public int getNumOfReservations() {
		
		int i;
		int reservation =0;
		for(i=0; i<reservations.length; i++) {
			if(reservations[i] == null) {
				continue;
			}
			else reservation += 1;
		}
		return reservation;
	}
	
	public int getTotalCost() {
		
		int i;
		int totalCost = 0;
		int size = this.reservations.length;
		for(i=0; i<size; i++) {
			totalCost += this.reservations[i].getCost();
		}
		return totalCost;
	}
}
