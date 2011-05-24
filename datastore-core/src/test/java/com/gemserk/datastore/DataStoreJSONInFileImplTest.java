package com.gemserk.datastore;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.datastore.DataStoreJSONInFileImpl.DataFile;
import com.google.common.collect.Sets;

@RunWith(JMock.class)
public class DataStoreJSONInFileImplTest {
	
	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	
	File dataStoreFile;
	
	@Before
	public void init() throws URISyntaxException, IOException {
		File srcFile = new File(getClass().getClassLoader().getResource("datastore.data").toURI());
		dataStoreFile = File.createTempFile("datastore-temp", ".data");
		FileUtils.copyFile(srcFile, dataStoreFile);
	}
	
	@Test
	public void shouldGetDataFromFile() throws URISyntaxException, IOException {
		DataStoreJSONInFileImpl dataStore = new DataStoreJSONInFileImpl(dataStoreFile, new DataSerializerJSONImpl());
		Collection<Data> data = dataStore.get(Sets.<String>newHashSet());
		assertTrue(data.size() == 2);
	}
	
	@Test
	public void shouldRemoveItemsWithGivenTags() {
		
		final File storageFile = new File("");

		final String fileContent = "";
		final String serializedData = "";
		
		final Data elementWithTagA = new Data(Sets.newHashSet("A"), new HashMap<String, Object>());
		final Data elementWithTagB = new Data(Sets.newHashSet("B"), new HashMap<String, Object>());
		
		final List<Data> dataList = Arrays.asList(elementWithTagA, elementWithTagB);
		final List<Data> expectedDataList = Arrays.asList(elementWithTagB);
		
		final DataSerializer dataSerializer = mockery.mock(DataSerializer.class);
		final DataFile dataFile = mockery.mock(DataFile.class);
		
		mockery.checking(new Expectations() {
			{
				oneOf(dataFile).getFileContent(storageFile);
				will(returnValue(fileContent));
				
				oneOf(dataSerializer).parseData(fileContent);
				will(returnValue(dataList));
				oneOf(dataSerializer).serializeData(with(equal(expectedDataList)));
				// oneOf(dataSerializer).serializeData((Collection<Data>) with(Matchers.hasItem(elementWithTagB)));
				will(returnValue(serializedData));

				oneOf(dataFile).writeFileContent(storageFile, serializedData);
			}
		});
		
		DataStoreJSONInFileImpl dataStore = new DataStoreJSONInFileImpl(storageFile, dataSerializer, dataFile);
		
		dataStore.remove(Sets.newHashSet("A"));
	}
	
}
