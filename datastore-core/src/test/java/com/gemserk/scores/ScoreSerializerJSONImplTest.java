package com.gemserk.scores;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.google.common.collect.Sets;

public class ScoreSerializerJSONImplTest {
	
	@Test
	public void shouldReturnEmptyCollectionForEmptyString() {
		ScoreSerializerJSONImpl scoreSerializer = new ScoreSerializerJSONImpl();
		Collection<Score> scores = scoreSerializer.parse("");
		assertThat(scores.size(), IsEqual.equalTo(0));
	}
	
	@Test
	public void shouldReturnEmptyCollectionForNullString() {
		ScoreSerializerJSONImpl scoreSerializer = new ScoreSerializerJSONImpl();
		Collection<Score> scores = scoreSerializer.parse(null);
		assertThat(scores.size(), IsEqual.equalTo(0));
	}

	@Test
	public void test() {
		ScoreSerializerJSONImpl scoreSerializer = new ScoreSerializerJSONImpl();
		Collection<Score> scores = scoreSerializer.parse("[{\"name\": \"Drakulo\", \"tags\": [\"level05\"], \"timestamp\": 1296734973000, \"points\": 14580, \"data\": {}, \"id\": \"ag1nZW1zZXJrc2NvcmVzcg0LEgVTY29yZRiyzwoM\"}, {\"name\": \"Dasgard\", \"tags\": [\"level05\"], \"timestamp\": 1293843263000, \"points\": 14100, \"data\": {}, \"id\": \"ag1nZW1zZXJrc2NvcmVzcg0LEgVTY29yZRiprggM\"}, {\"name\": \"sdf\", \"tags\": [\"level05\"], \"timestamp\": 1297237331000, \"points\": 13940, \"data\": {}, \"id\": \"ag1nZW1zZXJrc2NvcmVzcg0LEgVTY29yZRjBnQsM\"}, {\"name\": \"t\", \"tags\": [\"level05\"], \"timestamp\": 1293722595000, \"points\": 12940, \"data\": {}, \"id\": \"ag1nZW1zZXJrc2NvcmVzcg0LEgVTY29yZRiB6AcM\"}, {\"name\": \"silvi\", \"tags\": [\"level05\"], \"timestamp\": 1301270585000, \"points\": 12150, \"data\": {}, \"id\": \"ag1nZW1zZXJrc2NvcmVzcg0LEgVTY29yZRj6rg0M\"}, {\"name\": \"Dude\", \"tags\": [\"level05\"], \"timestamp\": 1293755570000, \"points\": 11880, \"data\": {}, \"id\": \"ag1nZW1zZXJrc2NvcmVzcg0LEgVTY29yZRiJjwgM\"}, {\"name\": \"mik\", \"tags\": [\"level05\"], \"timestamp\": 1294770474000, \"points\": 10850, \"data\": {}, \"id\": \"ag1nZW1zZXJrc2NvcmVzcg0LEgVTY29yZRihhAkM\"}, {\"name\": \"silvi\", \"tags\": [\"level05\"], \"timestamp\": 1301269918000, \"points\": 10350, \"data\": {}, \"id\": \"ag1nZW1zZXJrc2NvcmVzcg0LEgVTY29yZRi88AwM\"}]");
		for (Score score : scores) {
			System.out.println(score);
		}
	}
	
	@Test
	public void test2() {
		ScoreSerializerJSONImpl scoreSerializer = new ScoreSerializerJSONImpl();
		
		ArrayList<Score> scores = new ArrayList<Score>();
		
		HashMap<String, Object> data = new HashMap<String, Object>() {{
			put("DATA1", "value1");
			put("DATA2", 500);
		}};
		
		scores.add(new Score("arielsan", 15000l, Sets.newHashSet("b", "c"), data));
		
		String serializedScores = scoreSerializer.serialize(scores);
		
		System.out.println(serializedScores);
	}

}
