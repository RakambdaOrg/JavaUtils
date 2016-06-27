package fr.mrcraftcod.utils.config;

import fr.mrcraftcod.utils.Log;
import org.jdeferred.DeferredManager;
import org.jdeferred.Promise;
import org.jdeferred.impl.DefaultDeferredManager;
import java.sql.*;
import java.util.ArrayList;

public abstract class JDBCBase
{
	protected final DeferredManager dm;
	private final String NAME;
	private final boolean log;
	protected Connection connection;
	private final ArrayList<Promise> promises;

	protected JDBCBase(String name, boolean log)
	{
		dm = new DefaultDeferredManager();
		this.log = log;
		this.NAME = name;
		this.promises = new ArrayList<>();
	}

	protected abstract void login();

	public synchronized Promise<ResultSet, Throwable, Void> sendQueryRequest(String request)
	{
		Promise<ResultSet, Throwable, Void> promise = dm.when(() -> sendQueryRequest(request, true)).fail(event -> Log.warning(log, "SQL Query request on " + NAME + " failed!", event));
		promises.add(promise);
		return promise;
	}

	public synchronized Promise<Integer, Throwable, Void> sendUpdateRequest(String request)
	{
		Promise<Integer, Throwable, Void> promise = dm.when(() -> sendUpdateRequest(request, true)).fail(event -> Log.warning(log, "SQL Update request on " + NAME + " failed!", event));
		promises.add(promise);
		return promise;
	}

	public void close()
	{
		for(Promise promise : promises)
			try
			{
				promise.waitSafely();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		if(connection != null)
			try
			{
				this.connection.close();
			}
			catch(SQLException ignored)
			{
			}
		Log.info("SQL Connection closed");
	}

	private ResultSet sendQueryRequest(String request, boolean retry) throws SQLException
	{
		if(this.connection == null)
			return null;
		if(retry)
			Log.info(log, "Sending SQL request to " + NAME + "...: " + request);
		ResultSet result;
		try
		{
			Statement stmt = this.connection.createStatement();
			result = stmt.executeQuery(request);
		}
		catch(SQLTimeoutException e)
		{
			if(!retry)
				throw e;
			login();
			return sendQueryRequest(request, false);
		}
		return result;
	}

	private int sendUpdateRequest(String request, boolean retry) throws SQLException
	{
		if(this.connection == null)
			return 0;
		Log.info(log, "Sending SQL update to " + NAME + "...: " + request);
		int result = 0;
		try
		{
			for(String req : request.split(";"))
			{
				Statement stmt = this.connection.createStatement();
				result += stmt.executeUpdate(req + ";");
			}
		}
		catch(SQLException e)
		{
			if(!retry)
				throw e;
			login();
			return sendUpdateRequest(request, false);
		}
		return result;
	}
}
