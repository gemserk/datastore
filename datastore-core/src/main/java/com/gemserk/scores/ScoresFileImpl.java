package com.gemserk.scores;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class ScoresFileImpl implements Scores {
	
	protected static final Logger logger = LoggerFactory.getLogger(ScoresFileImpl.class);

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

	File storage;
	
	ScoreSerializer scoreSerializer;

	public ScoresFileImpl(File storage, ScoreSerializer scoreSerializer) {
		this.storage = storage;
		this.scoreSerializer = scoreSerializer ;
	}

	@Override
	public Collection<Score> getOrderedByPoints(final Set<String> tags, int quantity, boolean ascending) {
		Collection<Score> filteredScores = get(tags);
		ArrayList<Score> scores = new ArrayList<Score>(filteredScores);
		Collections.sort(scores, ascending ? new AscendingScoreComparator() : new DescendingScoreComparator());
		return scores.subList(0, Math.min(quantity, scores.size()));
	}

	public String submit(Score score) {
		try {
			score.setId(Integer.toHexString(System.identityHashCode(score)));
			score.setTimestamp(System.currentTimeMillis());

			Collection<Score> previousData = get(new HashSet<String>());
			previousData.add(score);

			String dataToStore = scoreSerializer.serialize(previousData);
			writeFileContent(dataToStore);

			return score.getId();
		} catch (IOException e) {
			throw new RuntimeException("couldnt write to  storage: " + storage, e);
		}
	}

	private Collection<Score> get(final Set<String> tags) {
		try {
			ensureFileExists();
			String readData = getFileContent();
			Collection<Score> filteredScores = Collections2.filter(scoreSerializer.parse(readData), new Predicate<Score>() {
				@Override
				public boolean apply(Score data) {
					return data.getTags().containsAll(tags);
				}
			});
			return filteredScores;
		} catch (IOException e) {
			throw new RuntimeException("couldnt read storage: " + storage, e);
		}
	}

	String getFileContent() throws IOException {
		return FileUtils.readFileToString(storage);
	}

	void writeFileContent(String value) throws IOException {
		FileUtils.writeStringToFile(storage, value);
	}

	void ensureFileExists() throws IOException {
		if (!storage.exists())
			writeFileContent("[]");
	}

	@Override
	public String submit(String privateKey, Score score) {
		logger.warn("submit score specifying profile private key is not implemented yet, calling the other submit score for now.");
		return submit(score);
	}

	@Override
	public Collection<Score> getOrderedByPoints(Set<String> tags, int limit, boolean ascending, Range range) {
		logger.warn("getOrderedByPoints specifying scores range is not implemented yet, calling the other implementation for now.");
		return getOrderedByPoints(tags, limit, ascending);
	}

}
