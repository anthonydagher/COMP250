

import java.util.Arrays;
import java.util.Random;

public class TrainLine {

	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	public static Random rand;

	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
	/*
	 * Constructor for TrainStation input: stationList - An array of TrainStation
	 * containing the stations to be placed in the line name - Name of the line
	 * goingRight - boolean indicating the direction of travel
	 */
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 1; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]);
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name,
			boolean goingRight) {/*
									 * Constructor for TrainStation. input: stationNames - An array of String
									 * containing the name of the stations to be placed in the line name - Name of
									 * the line goingRight - boolean indicating the direction of travel
									 */
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i]));
		}

		this.lineMap = this.getLineArray();

	}

	// adds a station at the last position before the right terminus
	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft();
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}

	public String getName() {
		return this.lineName;
	}

	public int getSize() {
		
		TrainStation station = this.rightTerminus;
		int size = 2;
		while(!station.getLeft().isLeftTerminal()) {
		
			size ++;
			station = station.getLeft();
		}
		return size;
	}

	public void reverseDirection() {
		this.goingRight = !this.goingRight;
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation travelOneStation(TrainStation current, TrainStation previous) throws StationNotFoundException{
		
		try{
			findStation(current.getName());
		}
		catch(StationNotFoundException s){
			throw new StationNotFoundException(current.getName());
		}
		if(current.hasConnection == true) {
			if(current.getTransferStation().equals(previous) == false){
				
				TrainStation station = current.getTransferStation();
				return station;
			
			}else {
				return getNext(current);
			}
		}else {
			return getNext(current);
		}
	}
		

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation getNext(TrainStation station) throws StationNotFoundException {

		TrainLine line = station.getLine();
		
		if(line != this) {
			
			throw new StationNotFoundException(station.getName());
		}
		if(line.goingRight == true) {
			
			if(station.isRightTerminal() == false){
				
				return station.getRight();		
			}
			
			if(station.isRightTerminal() == true) {
				
				this.reverseDirection();
				return station.getLeft();
			}
		}
		
		if(line.goingRight == false) {
			
			if(station.isLeftTerminal() == false){
				
				return station.getLeft();		
			}
			
			if(station.isLeftTerminal() == true) {
				
				this.reverseDirection();
				return station.getRight();
			}
		}
		return null;
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation findStation(String name) throws StationNotFoundException {
		
		TrainStation station = this.rightTerminus;
		while(station.getName() != name) {
			
			if(station.isLeftTerminal()) {
				if(station.getName() != name) {
					return station;
				}
				else {
					throw new StationNotFoundException(name);
				}
			}
			station = station.getLeft();
		}
		return station;
	}

	public void sortLine() {
		
		TrainStation[] array = this.getLineArray();
		int size = array.length;
		
		for(int i=0; i<size; i++) {
			for(int j=0; j<size-i-1; j++) {
				
				if(array[j].getName().compareTo(array[j+1].getName())>0) {
					
						TrainStation tmp = array[j];
						array[j] = array[j+1];
						array[j+1] = tmp;
				}
			}
		}
		array[size-1].setLeft(array[0]);
		array[size-1].setNonTerminal();
		array[0].setRight(array[size-1]);
		array[0].setNonTerminal();
		this.leftTerminus = array[0];
		this.rightTerminus = array[size-1];
		
		for(int i=0; i<size; i++) {
			
			if(i==0) {
				array[i].setRight(array[i+1]);
				array[i].setLeftTerminal();
				this.leftTerminus = array[i];
			}
			if(i==size-1) {
				array[i].setLeft(array[i-1]);
				array[i].setRightTerminal();
				this.rightTerminus = array[i];
			}
			if(i>0 && i<size-1) {
				
				array[i].setNonTerminal();
				array[i].setLeft(array[i-1]);
				array[i].setRight(array[i+1]);
				addStation(array[i]);
			}
		}
		this.lineMap = this.getLineArray();
	}

	public TrainStation[] getLineArray() {

		TrainStation[] array = new TrainStation[this.getSize()];
		TrainStation station = this.leftTerminus;
		for(int i=0; i<array.length; i++) {
			
			array[i] = station;
			if(station.isRightTerminal()) {
				array[i] = this.rightTerminus;
				continue;
			}
			station = station.getRight();
		}
		return array;
	}

	private TrainStation[] shuffleArray(TrainStation[] array) {
		Random rand = new Random();
		rand.setSeed(11);
		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}

	public void shuffleLine() {

		// you are given a shuffled array of trainStations to start with
		TrainStation[] lineArray = this.getLineArray();
		TrainStation[] shuffledArray = shuffleArray(lineArray);
		int size = shuffledArray.length;
		
		shuffledArray[size-1].setLeft(shuffledArray[0]);
		shuffledArray[size-1].setNonTerminal();
		shuffledArray[0].setRight(shuffledArray[size-1]);
		shuffledArray[0].setNonTerminal();
		this.leftTerminus = shuffledArray[0];
		this.rightTerminus = shuffledArray[size-1];
		
		for(int i=0; i<size; i++) {
			
			if(i==0){
				
				shuffledArray[i].setLeftTerminal();
			}
			if(i==size-1){
				
				shuffledArray[i].setRightTerminal();
			}
			if(i>0 && i<size-1) {
				
				shuffledArray[i].setNonTerminal();
				shuffledArray[i].setLeft(shuffledArray[i-1]);
				shuffledArray[i].setRight(shuffledArray[i+1]);
				addStation(shuffledArray[i]);
			}	
		}
		this.lineMap = this.getLineArray();
	}

	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2))
					return false;
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus;
	}
}

//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}
}
