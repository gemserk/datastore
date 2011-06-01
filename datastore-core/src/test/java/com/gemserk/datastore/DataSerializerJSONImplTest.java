package com.gemserk.datastore;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Test;


public class DataSerializerJSONImplTest {
	
	@Test
	public void test() {
		
		DataSerializerJSONImpl dataSerializerJSONImpl = new DataSerializerJSONImpl();
		
		String dataCollectionJson = "[{\"id\": \"14b5f4a\", \"tags\":  [ \"selected\",  \"profile\" ], \"values\": {\"name\": \"naruto\"} }]";
		
		Collection<Data> dataCollection = dataSerializerJSONImpl.parse(dataCollectionJson);
		
		for (Data data : dataCollection) {
			System.out.println(MessageFormat.format("{0},{1}", data.getId(), data.getTags()));
			Map<String, Object> values = data.getValues();
			for (Object object : values.values()) {
				System.out.println(object.toString());
			}
		}
		
	}

	@Test
	public void test2() {
		
		DataSerializerJSONImpl dataSerializerJSONImpl = new DataSerializerJSONImpl();

		ArrayList<Data> dataCollection = new ArrayList<Data>();
		
		HashSet<String> tags = new HashSet<String>();
		tags.add("A");
		tags.add("B");
		
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("V1", "string");
		
		Data data = new Data(tags, values);
		
		data.setId("ID");
		
		dataCollection.add(data);
		
		String dataCollectionJson = dataSerializerJSONImpl.serialize(dataCollection);
		System.out.println(dataCollectionJson);
		
		Collection<Data> newDataCollection = dataSerializerJSONImpl.parse(dataCollectionJson);
		
		for (Data newData : newDataCollection) {
			System.out.println(MessageFormat.format("{0},{1},{2}", newData.getId(), newData.getTags(), newData.getValues()));
			System.out.println(newData.getValues().get("V1").toString());
		}
		
	}
}
