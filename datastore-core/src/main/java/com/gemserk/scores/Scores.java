package com.gemserk.scores;

import java.util.Collection;
import java.util.Set;

public interface Scores {

	/**
	 * Submits a new score, returns the id of the score.
	 * 
	 * @param score
	 * @return the id of the entry
	 */
	String submit(Score score);
	
	/**
	 * Submits a new score, returns the id of the score.
	 * 
	 * @param score
	 * @return the id of the entry
	 */
	String submit(String profilePrivateKey, Score score);

	/**
	 * Returns scores tagged with the given tags ordered by points descending.
	 * 
	 * @param tags
	 * @param limit
	 *            the number of results
	 * @return
	 */
	Collection<Score> getOrderedByPoints(Set<String> tags, int limit, boolean ascending);

}
