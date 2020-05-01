
import java.util.ArrayList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CatTree implements Iterable<CatInfo>{
    public CatNode root;
    
    public CatTree(CatInfo c) {
        this.root = new CatNode(c);
    }
    
    private CatTree(CatNode c) {
        this.root = c;
    }
    
    public void addCat(CatInfo c)
    {
        this.root = root.addCat(new CatNode(c));
    }
    
    public void removeCat(CatInfo c)
    {
        this.root = root.removeCat(c);
    }
    
    public int mostSenior()
    {
        return root.mostSenior();
    }
    
    public int fluffiest() {
        return root.fluffiest();
    }
    
    public CatInfo fluffiestFromMonth(int month) {
        return root.fluffiestFromMonth(month);
    }
    
    public int hiredFromMonths(int monthMin, int monthMax) {
        return root.hiredFromMonths(monthMin, monthMax);
    }
    
    public int[] costPlanning(int nbMonths) {
        return root.costPlanning(nbMonths);
    }
    
    
    
    public Iterator<CatInfo> iterator()
    {
        return new CatTreeIterator();
    }
    
    
    class CatNode {
        
        CatInfo data;
        CatNode senior;
        CatNode same;
        CatNode junior;
        
        public CatNode(CatInfo data) {
            this.data = data;
            this.senior = null;
            this.same = null;
            this.junior = null;
        }
        
        public String toString() {
            String result = this.data.toString() + "\n";
            if (this.senior != null) {
                result += "more senior " + this.data.toString() + " :\n";
                result += this.senior.toString();
            }
            if (this.same != null) {
                result += "same seniority " + this.data.toString() + " :\n";
                result += this.same.toString();
            }
            if (this.junior != null) {
                result += "more junior " + this.data.toString() + " :\n";
                result += this.junior.toString();
            }
            return result;
        }
        
        
        public CatNode addCat(CatNode c) {
            int rootMonth = this.data.monthHired;
            int nodeMonth = c.data.monthHired;
            int rootFur = this.data.furThickness;
            int nodeFur = c.data.furThickness;
            
            // If the seniority of the root is more (senior)
        	if(rootMonth > nodeMonth) {
        		// If the senior node is empty, add it
        		if(this.senior == null) {
        			this.senior = new CatNode(c.data);
        		}
        		// Recursive continuation
        		else {
        			this.senior.addCat(c);
        		}
        	}
        	// If they have equal seniority
        	if(rootMonth == nodeMonth) {
        		// If the new cat has thicker fur, store at the root node and this is the tree node
        		if(rootFur <= nodeFur) {
        			// Basic info swap after creation of a new same node
        			// Create node with c.data info
        			CatNode junior = this.junior;
        			CatNode senior = this.senior;
        			CatNode newNode = new CatNode(c.data);
        			// Set branches to the new node
        			newNode.senior = senior;
        			newNode.junior = junior;
        			// Remove old branches
        			this.junior = this.senior = null;
        			// Set new root same branch to previous node 
        			newNode.same = this;
        			// return the new root
        			return newNode;
        		}
        		// If the same node is empty, add it
        		if(this.same == null) {
        			this.same = new CatNode(c.data);
        		}
        		// Recursive continuation
        		else {
        			this.same.addCat(c);
        		}
        	}
        	// If the seniority of the root is less (junior)
        	if(rootMonth < nodeMonth) {
        		// If the junior node is empty, add it
        		if(this.junior == null) {
        			this.junior = new CatNode(c.data);
        		}
        		// Recursive continuation
        		else {
        			this.junior.addCat(c);
        		}
        	}
        	// return root
            return this;
        }
        
        
        public CatNode removeCat(CatInfo c) {
        	
        	// Edge case test for evaluation tester
        	if(c == null) {
        		return this;
        	}	
        	// The three cases where the root is the desired node to remove
        	if(this.data.equals(c) == true) {
        	
        		// Update the root and subtrees if not null
        		// same moves to the root, adjusts subtrees to new root
        		if(this.same != null) {
        			CatNode rootNode = this;
        			CatNode newRoot = rootNode.same;
        			// Basic info swap of branches
        			newRoot.junior = rootNode.junior;
        			newRoot.senior = rootNode.senior;
        			rootNode = newRoot;
        			// Return updated root
        			return rootNode;
        		}
        		// Senior becomes root and adjusts tree
        		if(this.senior != null && this.same == null) {
        			CatNode rootNode = this;
        			CatNode newRoot = rootNode.senior;
        			// Basic info swap of branches
        			newRoot.junior = rootNode.junior;
        			rootNode = newRoot;
        			// Return updated root
        			return rootNode;
        		}
        		// Junior becomes root
        		if(this.senior == null && this.same == null) {
        			CatInfo newRoot = this.junior.data;
        			this.data = newRoot;
        			return this.junior;
        		}
        		// If we are at the root, and if none
        		// of these are the case, I assume it's all removed basically, so null
        		// (edge case)
        		else {
        			return null;
        		}
        	}	
        	// The cases where the root is not the desired node to remove
        	// Basically need to recursively navigate tree to find the correct node
        	else if(this.data.equals(c) == false) {
        		
        		int rootMonths = this.data.monthHired;
        		
        		// If same isn't null and they both have 
        		// identical months, continue on tree because
        		if(this.same != null) {
        			if(rootMonths == c.monthHired) {
        				this.same = this.same.removeCat(c);
        			}
        		}
        		// If senior isn't null and the root's 
        		// months are more, continue on tree because
        		// there are older cats probably 
        		if(this.senior != null) {
        			if(rootMonths > c.monthHired) {
        				this.senior = this.senior.removeCat(c);
        			}
        		}
        		// If junior isn't null and the root's 
        		// months are less, continue on tree because
        		// there are younger cats probably 
        		if(this.junior != null) {
        			if(rootMonths < c.monthHired) {
        				this.junior = this.junior.removeCat(c);
        			}
        		}
        	}
        	return this;
        }
        
        public int mostSenior() {
        	
        	// Months of "this"
        	int month = this.data.monthHired;
        	CatNode senior = this.senior;
        	// If the senior branch is empty, it means we are the 
        	// oldest cat (farthest to the right of the tree)
        	if(senior == null) {
        		return month;
        	}
        	// If the senior branch isn't empty, recursively continue down tree.
        	return senior.mostSenior();
        }
        
        public int fluffiest() {
        	
        	// Store the nodes fur thickness for recursion
        	int fluff = this.data.furThickness;
        	CatNode senior = this.senior;
        	CatNode junior = this.junior;
        	CatNode same = this.same;
        	// Check if senior branch exists
        	if(senior != null) { 
        		// If it does, assign senior fur thickness 
        		// (gives NullPointer if we did this before checking if the branch exists)
        		int seFluff = senior.data.furThickness;
        		if(seFluff > fluff) {
        			//Continue recursion if the senior thickness if larger
        			fluff = senior.fluffiest();
        		}
        	}
        	// Check if same branch exists
        	if(same != null) {
        		// If it does, assign same fur thickness 
        		// (gives NullPointer if we did this before checking if the branch exists)
        		int saFluff = same.data.furThickness;
        		if(saFluff > fluff) {
        			//Continue recursion if the same thickness if larger
        			fluff = same.fluffiest();
        		}	
        	}
        	// Check if junior branch exists
        	if(junior != null) {
        		// If it does, assign junior fur thickness
        		// (gives NullPointer if we did this before checking if the branch exists)
        		int juFluff = junior.data.furThickness;
        		if(juFluff > fluff) {
        			//Continue recursion if the junior thickness if larger
        			fluff = junior.fluffiest();
        		}		
        	}
        	// Recursively continue, and returns the fluffiest fur once all recursion ends
        	return fluff;
        }
        
        public int hiredFromMonths(int monthMin, int monthMax) {
        	
        	int cats = 0; // Set number of cats initially to 0 
        	int months = this.data.monthHired;
        	CatNode senior = this.senior;
        	CatNode junior = this.junior;
        	CatNode same = this.same;
        	if(monthMax<monthMin) { // If illegal input, return 0
        		return cats;
        	}
        	// If the months hired is less than the maximum but more than minimum, add cats
        	// Do this for recursion purposes
        	else {
        		if(months >= monthMin) {
        			if(months <= monthMax) {
            			cats = 1+cats;
            		}
            	}
                // Get the number of months of the senior cat and add to cats
            	if(senior != null) {
            		cats = cats + senior.hiredFromMonths(monthMin, monthMax);
            	}
            	// Get the number of months of the same cat and add to cats
            	if(same != null) {
            		cats = cats + same.hiredFromMonths(monthMin, monthMax);
            	}
            	// Get the number of months of the junior cat and add to cats
            	if(junior != null) {
            		cats = cats + junior.hiredFromMonths(monthMin, monthMax);
        		}
        	}
        	return cats;
        }
        
        public CatInfo fluffiestFromMonth(int month) {
        	
        	CatInfo info = this.data;
        	int months = this.data.monthHired;
        	CatNode senior = this.senior;
        	CatNode junior = this.junior;
        	// Returns the fluffiest from month (Junior) only if the monthHired is smaller
        	// It will continue recursively down the "senior" branch
        	if(months < month) {
        		if(junior!=null) {
        			return junior.fluffiestFromMonth(month);
        		}
        	}
        	// Returns the fluffiest from month (Senior) only if the monthHired is larger
        	// It will continue recursively down the "junior" branch
        	if(months > month) {
        		if(senior!=null) {
        			return senior.fluffiestFromMonth(month);
        		}
        	}
        	// Cat has been found 
        	// Assume same search is useless since they have the same months anyway?
        	if(months == month) {
        		return info; // If the same number of months, return the CatInfo
        	}
        	return null; // If cat is never found, return null;
        }
        
        public int[] costPlanning(int nbMonths) {
            
        	int[] newCost = new int[nbMonths];
        	int present = 243;
        	int next = this.data.nextGroomingAppointment;
        	int i = next - present;
        	CatNode senior = this.senior;
        	CatNode junior = this.junior;
        	CatNode same = this.same;
        	// edge case
        	if(nbMonths < 0) {
        		return null;
        	}
        	// Set the already known cost of *this* date if the root is acceptable
        	if(nbMonths > i) {
        		newCost[i] = newCost[i] + this.data.expectedGroomingCost;
        	}
        	// Get the costs for same branch and add to array
        	int[] sameCost = new int[nbMonths];
        	if(same != null) {
        		sameCost = same.costPlanning(nbMonths);
        	}
        	// Get the costs for junior branch and add to array
        	int[] juniorCost = new int[nbMonths];
        	if(junior != null) {
        		juniorCost = junior.costPlanning(nbMonths);
        	}
        	// Get the costs for senior branch and add to array
        	int[] seniorCost = new int[nbMonths];
        	if(senior != null) {
        		seniorCost = senior.costPlanning(nbMonths);
        	}
        	// Add values for each branch into total cost
        	for(int j = 0; j<nbMonths; j++) {
        		int total = sameCost[i]+juniorCost[i]+seniorCost[i];
        		newCost[i] = newCost[i] + total;
        	}
        	return newCost;
        }
    }
    
    private class CatTreeIterator implements Iterator<CatInfo> {
    	
    	ArrayList<CatInfo> tree;
    	
        public CatTreeIterator() {
        	// Sets the tree with this root, and puts it in desired order
        	tree = new ArrayList<CatInfo>();
        	inorder(root);
        }
        //helper method to do in order traversal
        private void inorder(CatNode node) {
        	// basic in order method, slight changes to incorporate .same
        	if(node == null) {
        		return;
        	}
        	// replace the typical "System.out.println" with add to tree
        	if(node != null) {
        		if(node.junior != null) {
        			inorder(node.junior);
        		}
            	if(node.same != null) {
        			inorder(node.same);
        		}
            	tree.add(node.data);
            	if(node.senior != null) {
        			inorder(node.senior);
        		}
        	}
        }
        int iterate = 0;
        public CatInfo next(){
        	iterate++;
        	CatInfo next;
        	try {
            next = tree.get(iterate);
        	}catch(Exception e) {
        		throw new NoSuchElementException("No more cats in the tree!");
        	}
        	return next;
        }
        public boolean hasNext() {
            if(tree.size() > iterate) {
            	return true;
            }
        	return false;
        }
    }
}

