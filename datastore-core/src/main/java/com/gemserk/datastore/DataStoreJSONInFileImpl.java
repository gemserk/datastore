package com.gemserk.datastore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class DataStoreJSONInFileImpl implements DataStore {

	File storage;

	DataSerializer dataSerializer;

	DataFile dataFile;

	public DataStoreJSONInFileImpl(File storage) {
		this(storage, new DataSerializerJSonImpl());
	}

	public DataStoreJSONInFileImpl(File storage, DataSerializer dataSerializer) {
		this(storage, dataSerializer, new DataFile());
	}

	public DataStoreJSONInFileImpl(File storage, DataSerializer dataSerializer, DataFile dataFile) {
		this.storage = storage;
		this.dataSerializer = dataSerializer;
		this.dataFile = dataFile;
	}

	@Override
	public Collection<Data> get(final Set<String> tags) {

		try {
			ensureFileExists();
			String readData = getFileContent();

			Collection<Data> dataCollection = dataSerializer.parseData(readData);
			return Collections2.filter(dataCollection, new Predicate<Data>() {
				@Override
				public boolean apply(Data data) {
					return data.getTags().containsAll(tags);
				}
			});
		} catch (IOException e) {
			throw new RuntimeException("couldnt read storage: " + storage, e);
		}
	}

	@Override
	public String submit(Data data) {
		data.setId(Integer.toHexString(System.identityHashCode(data)));

		Collection<Data> previousData = get(new HashSet<String>());
		previousData.add(data);

		String dataToStore = dataSerializer.serializeData(previousData);
		writeFileContent(dataToStore);

		return data.getId();
	}

	static class DataFile {

		String getFileContent(File storage) {
			try {
				return FileUtils.readFileToString(storage);
			} catch (IOException e) {
				throw new RuntimeException("failed to get file contents from " + storage.getAbsolutePath(), e);
			}
		}

		void writeFileContent(File storage, String value) {
			try {
				FileUtils.writeStringToFile(storage, value);
			} catch (IOException e) {
				throw new RuntimeException("failed to write file contents to " + storage.getAbsolutePath(), e);
			}
		}

	}

	String getFileContent() {
		return dataFile.getFileContent(storage);
	}

	void writeFileContent(String value) {
		dataFile.writeFileContent(storage, value);
	}

	void ensureFileExists() throws IOException {
		if (!storage.exists())
			writeFileContent("[]");
	}

	static interface DataSerializer {

		Collection<Data> parseData(String data);

		String serializeData(Collection<Data> dataCollection);

	}

	static class DataSerializerJSonImpl implements DataSerializer {

		@SuppressWarnings("unchecked")
		public Collection<Data> parseData(String data) {
			return JSONArray.toCollection(JSONArray.fromObject(data), Data.class);
		}

		public String serializeData(Collection<Data> dataCollection) {
			JSONArray jobject = JSONArray.fromObject(dataCollection);
			String jsonData = jobject.toString(1);
			return jsonData;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void remove(final Set<String> tags) {

		String readData = getFileContent();
		Collection<Data> dataCollection = dataSerializer.parseData(readData);

		ArrayList<Data> previousCollection = new ArrayList<Data>(dataCollection);

		ArrayList<Data> filteredCollection = new ArrayList(Collections2.filter(dataCollection, new Predicate<Data>() {
			@Override
			public boolean apply(Data data) {
				return data.getTags().containsAll(tags);
			}
		}));

		previousCollection.removeAll(filteredCollection);

		String dataToStore = dataSerializer.serializeData(previousCollection);
		writeFileContent(dataToStore);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(final Data data) {
		
		String readData = getFileContent();
		Collection<Data> dataCollection = dataSerializer.parseData(readData);
		
		ArrayList<Data> filteredCollection = new ArrayList(Collections2.filter(dataCollection, new Predicate<Data>() {
			@Override
			public boolean apply(Data internalData) {
				return internalData.getId().equals(data.getId());
			}
		}));
		
		dataCollection.removeAll(filteredCollection);
		dataCollection.add(data);
		
		String dataToStore = dataSerializer.serializeData(dataCollection);
		writeFileContent(dataToStore);
	}

}
