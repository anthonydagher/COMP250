
public class TrainNetwork {
	final int swapFreq = 2;
	TrainLine[] networkLines;

    public TrainNetwork(int nLines) {
    	this.networkLines = new TrainLine[nLines];
    }
    
    public void addLines(TrainLine[] lines) {
    	this.networkLines = lines;
    }
    
    public TrainLine[] getLines() {
    	return this.networkLines;
    }
    
    public void dance() {
    	System.out.println("The tracks are moving!");
    	for(int i=0; i<this.networkLines.length; i++) {
    		
    		networkLines[i].shuffleLine();
    	}
    }
    
    public void undance() {
    	for(int i=0; i<this.networkLines.length; i++) {
    		
    		networkLines[i].sortLine();
    	}
    }
    
    public int travel(String startStation, String startLine, String endStation, String endLine) {
    	
    	int hoursCount = 0;
    	try {
    		getLineByName(startLine);
    	}
    	catch(LineNotFoundException l) {
    		hoursCount = 0;
    		return hoursCount;
    	}
    	TrainLine curLine = getLineByName(startLine); //use this variable to store the current line.
    	
    	try{
    		curLine.findStation(startStation);
    	}
    	catch(StationNotFoundException s) {
    		hoursCount = 168;
    	}
    	
    	TrainStation curStation= curLine.findStation(startStation); //use this variable to store the current station. 
    	TrainStation lastStation = curStation;
    	TrainStation tmpStation = null;
    	boolean arrived = false;
    	System.out.println("Departing from "+startStation);
   
    	while(true) {
    		
    		if(hoursCount == 168) {
    			System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
    			return hoursCount;
    		}
    		if(curLine.getName().equals(endLine) == true) {
    			if(curStation.getName().equals(endStation) == true) {
    				
    				arrived = true;
    				if(arrived == true) {
    					System.out.println("Arrived at destination after "+hoursCount+" hours!");
    					return hoursCount;
    				}
    			}
	    	}
    		hoursCount ++;
    		tmpStation = curLine.travelOneStation(curStation, lastStation);
    		lastStation = curStation;
    		if(hoursCount != 0) {
    			if(hoursCount % 2 == 0) {
    				dance();
    			}
    		}
    		curStation = tmpStation;
    		curLine = tmpStation.getLine();
    		//prints an update on your current location in the network.
    		System.out.println("Traveling on line "+curLine.getName()+":"+curLine.toString());
	    	System.out.println("Hour "+hoursCount+". Current station: "+curStation.getName()+" on line "+curLine.getName());
	    	System.out.println("=============================================");
    	}
    }
    
    //you can extend the method header if needed to include an exception. You cannot make any other change to the header.
    public TrainLine getLineByName(String lineName) throws LineNotFoundException {
    	
    	for(int i=0; i<this.networkLines.length; i++) {
			
			if((networkLines[i].getName()).equalsIgnoreCase(lineName) == true) {
				
				return networkLines[i];
				
			}
    	}
    	throw new LineNotFoundException(lineName);
	}
    
  //prints a plan of the network for you.
    public void printPlan() {
    	System.out.println("CURRENT TRAIN NETWORK PLAN");
    	System.out.println("----------------------------");
    	for(int i=0;i<this.networkLines.length;i++) {
    		System.out.println(this.networkLines[i].getName()+":"+this.networkLines[i].toString());
    		}
    	System.out.println("----------------------------");
    }
}

//exception when searching a network for a LineName and not finding any matching Line object.
class LineNotFoundException extends RuntimeException {
	   String name;

	   public LineNotFoundException(String n) {
	      name = n;
	   }

	   public String toString() {
	      return "LineNotFoundException[" + name + "]";
	   }
	}
