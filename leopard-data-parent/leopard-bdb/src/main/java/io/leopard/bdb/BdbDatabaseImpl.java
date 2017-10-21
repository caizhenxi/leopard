package io.leopard.bdb;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.EnvironmentLockedException;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.Transaction;

public class BdbDatabaseImpl implements Bdb {

	private Database database;

	private Transaction transaction;

	public BdbDatabaseImpl(Environment environment, String databaseName) throws DatabaseException {
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setAllowCreate(true);
		dbConfig.setSortedDuplicates(true);
		database = environment.openDatabase(transaction, "BDB", dbConfig);
	}

	@Override
	public boolean add(String key, String value) throws DatabaseException {
		database.put(transaction, new DatabaseEntry(key.getBytes()), new DatabaseEntry(value.getBytes()));
		return true;
	}

	@Override
	public String getString(String key) throws DatabaseException {
		DatabaseEntry data = new DatabaseEntry();
		database.get(transaction, new DatabaseEntry(key.getBytes()), data, LockMode.DEFAULT);
		byte[] bytes = data.getData();
		if (bytes == null) {
			return null;
		}
		return new String(bytes);
	}

	@Override
	public boolean delete(String key) throws DatabaseException {
		database.delete(transaction, new DatabaseEntry(key.getBytes()));
		return true;
	}

	public void destroy() throws DatabaseException {
		if (database != null) {
			database.close();
		}
	}
}
