package com.gemserk.scores;

import java.util.Collection;
import java.util.Set;

/**
 * Provides a way to submit and retrieve scores.
 */
public interface Scores {

	static enum Range {
		Day, Week, Month, All
	}

	/**
	 * Submits a new score, returns the id of the score.
	 * 
	 * @param score
	 *            The score to submit
	 * @return the id of the score
	 */
	String submit(Score score);

	/**
	 * Submits a new score using the profilePrivateKey to identify the score's player profile.
	 * 
	 * @param profilePrivateKey
	 *            The private key of the profile of the player.
	 * @param score
	 *            The score to submit.
	 * @return The id of the score
	 */
	String submit(String profilePrivateKey, Score score);

	/**
	 * Returns scores tagged with the given tags ordered by points descending.
	 * 
	 * @param tags
	 *            A collection of tags used to filter the scores.
	 * @param limit
	 *            the number of results
	 * @return A collection of scores matching the search criteria.
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
	 * @return A collection of scores matching the search criteria.
	 */
	Collection<Score> getOrderedByPoints(Set<String> tags, int limit, boolean ascending, Range range);

}
