import java.util.ArrayList;

import java.util.ArrayList;

public class Twitter {
	
	//ADD YOUR CODE BELOW HERE
	// initialize (same as the ones in constructor & for the methods after)
	MyHashTable<String, ArrayList<Tweet>> dateAdded; // this one is an array since there can be many tweets from the same day
	MyHashTable<String, ArrayList<Tweet>> tweetAuthor;// this one is an array since there can be many tweets from the same author
	ArrayList<Tweet> list;
	ArrayList<String> words;
	//ADD CODE ABOVE HERE 
	
	// O(n+m) where n is the number of tweets, and m the number of stopWords
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		//ADD YOUR CODE BELOW HERE
		// making them extra big for all possible rehash cases, just in case the tester
		// is really really big
		this.list = new ArrayList<Tweet>();
		this.dateAdded = new MyHashTable<String, ArrayList<Tweet>>(50);
		this.tweetAuthor = new MyHashTable<String, ArrayList<Tweet>>(50);
		this.words = stopWords;
		// fill array with the tweets
		for(Tweet tweet: tweets) {
			addTweet(tweet);
		}
		//ADD CODE ABOVE HERE 
	}
	
	
    /**
     * Add Tweet t to this Twitter
     * O(1)
     */
	public void addTweet(Tweet t) {
		//ADD CODE BELOW HERE
		// get information
		String date = t.getDateAndTime();
		String author = t.getAuthor();
		ArrayList<Tweet> test;
		// update the twitter
		if(this.dateAdded.get(date) != (null)) this.dateAdded.get(date).add(t);
		// add a new list for this date, possibility of multiple tweets now possible
		else {
			test = new ArrayList<Tweet>();
			test.add(t);
			this.dateAdded.put(date, test);
		}
		if(this.tweetAuthor.get(author) != (null)) this.tweetAuthor.get(author).add(t);
		// add a new list for this author, possibility of multiple tweets now possible
		else {
			test = new ArrayList<Tweet>();
			test.add(t);
			this.tweetAuthor.put(author, test);
		}
		this.list.add(t);
		//ADD CODE ABOVE HERE 
	}
	

    /**
     * Search this Twitter for the latest Tweet of a given author.
     * If there are no tweets from the given author, then the 
     * method returns null. 
     * O(1)  
     */
    public Tweet latestTweetByAuthor(String author) {
        //ADD CODE BELOW HERE
    	if(author == null) return null;
    	if(this.tweetAuthor.get(author) == (null)) return null;
    	else {
    	// return the element at the first position (can be multiple tweets)
    	return this.tweetAuthor.get(author).get(0);
    	}
        //ADD CODE ABOVE HERE 
    }


    /**
     * Search this Twitter for Tweets by `date' and return an 
     * ArrayList of all such Tweets. If there are no tweets on 
     * the given date, then the method returns null.
     * O(1)
     */
    public ArrayList<Tweet> tweetsByDate(String date) {
        //ADD CODE BELOW HERE
    	if(date == null) return null;
    	if(this.dateAdded.get(date) == (null)) return new ArrayList<Tweet>();
    	else {
    	//return tweets;
    	return this.dateAdded.get(date);
    	}
        //ADD CODE ABOVE HERE
    }
    
	/**
	 * Returns an ArrayList of words (that are not stop words!) that
	 * appear in the tweets. The words should be ordered from most 
	 * frequent to least frequent by counting in how many tweet messages
	 * the words appear. Note that if a word appears more than once
	 * in the same tweet, it should be counted only once. 
	 */
    public ArrayList<String> trendingTopics() {
        //ADD CODE BELOW HERE
    	ArrayList<String> words;
    	for(int i=0; i<this.list.size(); i++) {
    		Tweet t= this.list.get(i);
    		words = new ArrayList<String>();
    		words = Twitter.getWords(t.getMessage());
    		for(int j=0; j< words.size(); j++) {
    			String word = words.get(i).toLowerCase();
    			this.words.add(word);
    		}
    	}
    	return this.words;
    	
        //ADD CODE ABOVE HERE    	
    }
    
    
    
    /**
     * An helper method you can use to obtain an ArrayList of words from a 
     * String, separating them based on apostrophes and space characters. 
     * All character that are not letters from the English alphabet are ignored. 
     */
    private static ArrayList<String> getWords(String msg) {
    	msg = msg.replace('\'', ' ');
    	String[] words = msg.split(" ");
    	ArrayList<String> wordsList = new ArrayList<String>(words.length);
    	for (int i=0; i<words.length; i++) {
    		String w = "";
    		for (int j=0; j< words[i].length(); j++) {
    			char c = words[i].charAt(j);
    			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
    				w += c;
    			
    		}
    		wordsList.add(w);
    	}
    	return wordsList;
    }

    

}
