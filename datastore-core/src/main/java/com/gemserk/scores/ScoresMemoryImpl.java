package com.gemserk.scores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import com.gemserk.datastore.profiles.Profile;
import com.gemserk.datastore.profiles.Profiles;
import com.gemserk.datastore.profiles.ProfilesMemoryImpl;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class ScoresMemoryImpl implements Scores {

	class DescendingScoreComparator implements Comparator<Score> {
		@Override
		public int compare(Score score1, Score score2) {
			return (int) (score2.getPoints() - score1.getPoints());
		}
	}

	class AscendingScoreComparator implements Comparator<Score> {
		@Override
		public int compare(Score score1, Score score2) {
			return (int) (score1.getPoints() - score2.getPoints());
		}
	}

	private Collection<Score> scores;
	private final Profiles profiles;

	public ScoresMemoryImpl() {
		scores = new ArrayList<Score>();
		profiles = new ProfilesMemoryImpl();
	}
	
	public ScoresMemoryImpl(Profiles profiles) {
		this.profiles = profiles;
		scores = new ArrayList<Score>();
	}

	@Override
	public Collection<Score> getOrderedByPoints(final Set<String> tags, int quantity, boolean ascending) {
		Collection<Score> filteredScores = get(tags);
		ArrayList<Score> scores = new ArrayList<Score>(filteredScores);
		Collections.sort(scores, ascending ? new AscendingScoreComparator() : new DescendingScoreComparator());
		return scores.subList(0, Math.min(quantity, scores.size()));
	}

	public String submit(Score score) {
		String id = "" + scores.size() + 1;
		scores.add(score);
		return id;
	}

	private Collection<Score> get(final Set<String> tags) {
		Collection<Score> filteredScores = Collections2.filter(scores, new Predicate<Score>() {
			@Override
			public boolean apply(Score data) {
				return data.getTags().containsAll(tags);
			}
		});
		return filteredScores;
	}

	@Override
	public String submit(String privateKey, Score score) {
		Profile profile = profiles.getProfile(privateKey);
		if (profile != null)
			score.setProfilePublicKey(profile.getPublicKey());
		return submit(score);
	}

	@Override
	public Collection<Score> getOrderedByPoints(Set<String> tags, int limit, boolean ascending, Range range) {
		return getOrderedByPoints(tags, limit, ascending);
	}

}
