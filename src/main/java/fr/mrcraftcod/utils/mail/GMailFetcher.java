package fr.mrcraftcod.utils.mail;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.protocol.IMAPProtocol;
import fr.mrcraftcod.utils.Log;
import fr.mrcraftcod.utils.threads.ThreadLoop;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class GMailFetcher
{
	private final IMAPFolder folder;
	private final ThreadKeepAlive keepAlive;
	private final ExecutorService executorService;
	private final ThreadFetch threadFetch;
	private final boolean customExecutor;

	public GMailFetcher(Store store, String folder, Consumer<MessageCountEvent> callback) throws MessagingException, InvalidArgumentException
	{
		this(store, folder, null, callback);
	}

	public GMailFetcher(Store store, String folder, ExecutorService executorService, Consumer<MessageCountEvent> callback) throws MessagingException, InvalidArgumentException
	{
		if(executorService == null)
		{
			this.executorService = Executors.newFixedThreadPool(2);
			this.customExecutor = true;
		}
		else
		{
			this.executorService = executorService;
			this.customExecutor = false;
		}
		Folder tFolder = store.getFolder(folder);
		if(!(tFolder instanceof IMAPFolder))
			throw new InvalidArgumentException(new String[]{"Not IMAP folder"});
		this.folder = (IMAPFolder) tFolder;
		this.folder.open(Folder.READ_WRITE);
		this.folder.addMessageCountListener(new MessageCountListener()
		{
			@Override
			public void messagesAdded(MessageCountEvent e)
			{
				callback.accept(e);
			}

			@Override
			public void messagesRemoved(MessageCountEvent e)
			{
				callback.accept(e);
			}
		});
		this.threadFetch = new ThreadFetch();
		this.keepAlive = new ThreadKeepAlive();
		this.executorService.submit(threadFetch);
		this.executorService.submit(keepAlive);
		Log.info("GMailFetcher started");
	}

	public void close()
	{
		this.keepAlive.close();
		this.threadFetch.close();
		if(this.customExecutor)
			this.executorService.shutdownNow();
		Log.info("GMailFetcher closed");
	}

	private class ThreadFetch extends ThreadLoop
	{
		@Override
		public void loop()
		{
			try
			{
				GMailFetcher.this.folder.idle(false);
			}
			catch(MessagingException e)
			{
				Log.error("Error listening mails", e);
				GMailFetcher.this.close();
			}
		}

		@Override
		public void onClosed()
		{
			GMailFetcher.this.keepAlive.close();
		}
	}

	private class ThreadKeepAlive extends ThreadLoop
	{
		private static final long KEEP_ALIVE_FREQ = 300000;

		@Override
		public void loop()
		{
			try
			{
				Thread.sleep(KEEP_ALIVE_FREQ);
				GMailFetcher.this.folder.doCommand(new IMAPFolder.ProtocolCommand()
				{
					public Object doCommand(IMAPProtocol p) throws ProtocolException
					{
						p.simpleCommand("NOOP", null);
						return null;
					}
				});
			}
			catch (InterruptedException | MessagingException e){}
		}
	}
}
