package com.gemserk.scores;

import java.util.Collection;
import java.util.Set;

public interface Scores {

	static enum Range {
		Day, Week, Month, All
	}

	/**
	 * Submits a new score, returns the id of the score.
	 * 
	 * @param score
	 * @return the id of the entry
	 */
	String submit(Score score);

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

	/**
	 * Returns scores tagged with the given tags, ordered by points ascending/descending by the specified range.
	 * 
	 * @param tags
	 *            The tags of the score.
	 * @param limit
	 *            How many scores we want.
	 * @param ascending
	 *            If the scores should be ordered ascending or not.
	 * @param range
	 *            The range of the scores, day for today scores, week for scores of this week, etc.
	 * @return
	 */
	Collection<Score> getOrderedByPoints(Set<String> tags, int limit, boolean ascending, Range range);

}
