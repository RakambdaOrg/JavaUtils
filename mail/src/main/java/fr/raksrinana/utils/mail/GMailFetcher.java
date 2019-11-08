package fr.raksrinana.utils.mail;

import com.sun.mail.imap.IMAPFolder;
import fr.raksrinana.utils.javafx.ThreadLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.mail.*;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class GMailFetcher implements AutoCloseable{
	private static final Logger LOGGER = LoggerFactory.getLogger(MailUtils.class);
	private final IMAPFolder folder;
	private final ThreadKeepAlive keepAlive;
	private final ExecutorService executorService;
	private final ThreadFetch threadFetch;
	private final boolean customExecutor;
	private final Store store;
	
	private class ThreadKeepAlive extends ThreadLoop{
		private static final long KEEP_ALIVE_FREQ = 300000;
		
		@Override
		public void loop(){
			try{
				Thread.sleep(KEEP_ALIVE_FREQ);
				GMailFetcher.this.folder.doCommand(p -> {
					p.simpleCommand("NOOP", null);
					return null;
				});
			}
			catch(InterruptedException | MessagingException | NullPointerException ignored){
			}
		}
	}
	
	private class ThreadFetch extends ThreadLoop{
		@Override
		public void loop(){
			try{
				GMailFetcher.this.folder.idle(false);
			}
			catch(FolderClosedException ex){
				if(!folder.isOpen()){
					try{
						folder.open(Folder.READ_ONLY);
					}
					catch(MessagingException e){
						LOGGER.error("Error listening mails", e);
						GMailFetcher.this.close();
					}
				}
			}
			catch(MessagingException e){
				LOGGER.error("Error listening mails", e);
				GMailFetcher.this.close();
			}
		}
	}
	
	public GMailFetcher(@Nonnull Store store, @Nonnull String folder, @Nonnull Consumer<MessageCountEvent> callback) throws MessagingException, IllegalStateException{
		this(store, folder, null, callback);
	}
	
	public GMailFetcher(@Nonnull Store store, @Nonnull String folder, @Nullable ExecutorService executorService, @Nonnull Consumer<MessageCountEvent> callback) throws IllegalStateException, MessagingException{
		this.store = store;
		if(executorService == null){
			this.executorService = Executors.newFixedThreadPool(2);
			this.customExecutor = true;
		}
		else{
			this.executorService = executorService;
			this.customExecutor = false;
		}
		Folder tFolder = store.getFolder(folder);
		this.folder = (IMAPFolder) tFolder;
		this.folder.open(Folder.READ_WRITE);
		this.folder.addMessageCountListener(new MessageCountListener(){
			@Override
			public void messagesAdded(MessageCountEvent e){
				callback.accept(e);
			}
			
			@Override
			public void messagesRemoved(MessageCountEvent e){
				callback.accept(e);
			}
		});
		this.threadFetch = new ThreadFetch();
		this.keepAlive = new ThreadKeepAlive();
		this.executorService.submit(threadFetch);
		this.executorService.submit(keepAlive);
		LOGGER.info("GMailFetcher started");
	}
	
	@Override
	public void close(){
		this.keepAlive.close();
		this.threadFetch.close();
		try{
			if(this.folder.isOpen()){
				this.folder.close(true);
			}
		}
		catch(MessagingException | IllegalStateException e){
			LOGGER.warn("Error closing GMail folder", e);
		}
		try{
			this.store.close();
		}
		catch(MessagingException e){
			LOGGER.warn("Failed to close store", e);
		}
		if(this.customExecutor){
			this.executorService.shutdownNow();
		}
		LOGGER.info("GMailFetcher closed");
	}
	
	@Nonnull
	public Folder getFolder(){
		return this.folder;
	}
	
	@Nonnull
	public Message[] getMails() throws MessagingException{
		if(this.folder.isOpen()){
			return this.folder.getMessages();
		}
		return new Message[0];
	}
	
	@Nonnull
	public Store getStore(){
		return this.store;
	}
}
