package com.msx7.db;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public final class DataBaseAgent {
	public ReentrantReadWriteLock mLock = new ReentrantReadWriteLock();
	public Lock mWLock = mLock.writeLock();
	public Lock mRLock = mLock.readLock();
	private static DataBaseAgent mAgent;
	protected SQLiteDatabase db;

	protected DataBaseAgent(String db_path) {
		db = SQLiteDatabase.openOrCreateDatabase(db_path, null);
	}

	protected DataBaseAgent(SQLiteOpenHelper helper) {
		db = helper.getWritableDatabase();
	}

	public String getDatabasePath() {
		return db.getPath();
	}

	public static synchronized DataBaseAgent getInstance(String db_path) {
		if (mAgent == null) {
			mAgent = new DataBaseAgent(db_path);
		}
		return mAgent;
	}

	public int delete(String table, String whereClause, String[] whereArgs) {
		int id = 0;
		try {
			mWLock.lock();
			id = db.delete(table, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mWLock.unlock();
		}

		return id;
	}

	public int update(String table, ContentValues values, String whereClause,
			String[] whereArgs) {
		int id = 0;
		try {
			mWLock.lock();
			id = db.update(table, values, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mWLock.unlock();
		}
		return id;
	}

	public int updateWithOnConflict(String table, ContentValues values,
			String whereClause, String[] whereArgs, int conflictAlgorithm) {
		int id = 0;
		try {
			mWLock.lock();
			id = db.updateWithOnConflict(table, values, whereClause, whereArgs,
					conflictAlgorithm);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mWLock.unlock();
		}
		return id;
	}

	public void execSQL(String sql) {
		try {
			mWLock.lock();
			db.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mWLock.unlock();
		}
	}

	public void execSQL(String sql, Object[] bindArgs) {
		try {
			mWLock.lock();
			db.execSQL(sql, bindArgs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mWLock.unlock();
		}
	}

	public Cursor query(boolean distinct, String table, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy, String limit) {
		Cursor cursor = null;
		try {
			mRLock.lock();
			cursor = db.query(distinct, table, columns, selection,
					selectionArgs, groupBy, having, orderBy, limit);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mRLock.unlock();
		}
		return cursor;
	}

	public Cursor queryWithFactory(CursorFactory cursorFactory,
			boolean distinct, String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, String limit) {
		Cursor cursor = null;
		try {
			mRLock.lock();
			cursor = db.queryWithFactory(cursorFactory, distinct, table,
					columns, selection, selectionArgs, groupBy, having,
					orderBy, limit);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mRLock.unlock();
		}
		return cursor;
	}

	public Cursor query(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		Cursor cursor = null;
		try {
			mRLock.lock();
			cursor = db.query(table, columns, selection, selectionArgs,
					groupBy, having, orderBy);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mRLock.unlock();
		}
		return cursor;
	}

	public Cursor query(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, String limit) {
		Cursor cursor = null;
		try {
			mRLock.lock();
			cursor = db.query(table, columns, selection, selectionArgs,
					groupBy, having, orderBy, limit);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mRLock.unlock();
		}
		return cursor;
	}

	public long insert(String table, String nullColumnHack, ContentValues values) {
		long id = 0;
		try {
			mWLock.lock();
			id = db.insert(table, nullColumnHack, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mWLock.unlock();
		}
		return id;
	}

	public long insertOrThrow(String table, String nullColumnHack,
			ContentValues values) throws SQLException {
		long id = 0;
		try {
			mWLock.lock();
			id = db.insertOrThrow(table, nullColumnHack, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mWLock.unlock();
		}
		return id;
	}

	public long replace(String table, String nullColumnHack,
			ContentValues initialValues) {
		long id = 0;
		try {
			mWLock.lock();
			id = db.replace(table, nullColumnHack, initialValues);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mWLock.unlock();
		}
		return id;
	}

	public long replaceOrThrow(String table, String nullColumnHack,
			ContentValues initialValues) throws SQLException {
		long id = 0;
		try {
			mWLock.lock();
			id = db.replaceOrThrow(table, nullColumnHack, initialValues);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mWLock.unlock();
		}
		return id;
	}

	public long insertWithOnConflict(String table, String nullColumnHack,
			ContentValues initialValues, int conflictAlgorithm) {
		long id = 0;
		try {
			mWLock.lock();
			id = db.insertWithOnConflict(table, nullColumnHack, initialValues,
					conflictAlgorithm);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mWLock.unlock();
		}
		return id;
	}

	public void destroy() {
		try {
			mWLock.lock();
			mRLock.lock();
			if (db != null && db.isOpen())
				db.close();
			db = null;
			mAgent = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mWLock.unlock();
			mRLock.lock();
		}

	}
}
