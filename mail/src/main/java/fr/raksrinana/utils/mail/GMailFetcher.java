package fr.raksrinana.utils.mail;

import com.sun.mail.imap.IMAPFolder;
import fr.raksrinana.utils.javafx.ThreadLoop;
import jakarta.mail.*;
import jakarta.mail.event.MessageCountEvent;
import jakarta.mail.event.MessageCountListener;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Slf4j
public class GMailFetcher implements AutoCloseable{
	@Getter
	private final IMAPFolder folder;
	private final ThreadKeepAlive keepAlive;
	private final ExecutorService executorService;
	private final ThreadFetch threadFetch;
	private final boolean customExecutor;
	@Getter
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
						log.error("Error listening mails", e);
						GMailFetcher.this.close();
					}
				}
			}
			catch(MessagingException e){
				log.error("Error listening mails", e);
				GMailFetcher.this.close();
			}
		}
	}
	
	public GMailFetcher(@NonNull Store store, @NonNull String folder, @NonNull Consumer<MessageCountEvent> callback) throws MessagingException, IllegalStateException{
		this(store, folder, null, callback);
	}
	
	public GMailFetcher(@NonNull Store store, @NonNull String folder, ExecutorService executorService, @NonNull Consumer<MessageCountEvent> callback) throws IllegalStateException, MessagingException{
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
		log.info("GMailFetcher started");
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
			log.warn("Error closing GMail folder", e);
		}
		try{
			this.store.close();
		}
		catch(MessagingException e){
			log.warn("Failed to close store", e);
		}
		if(this.customExecutor){
			this.executorService.shutdownNow();
		}
		log.info("GMailFetcher closed");
	}
	
	@NonNull
	public Message[] getMails() throws MessagingException{
		if(this.folder.isOpen()){
			return this.folder.getMessages();
		}
		return new Message[0];
	}
}
