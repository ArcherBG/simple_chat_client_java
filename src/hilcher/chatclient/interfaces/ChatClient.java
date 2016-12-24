package hilcher.chatclient.interfaces;

import java.io.IOException;

public interface ChatClient {

	/*
	 * Connects to server and starts listening
	 */
	public void startClient() throws IOException;
	
	/*
	 * Sends message to server with the name defined from user
	 */
	public void setName(String name);
	
	/*
	 * Return all active clients connected to the server
	 */
	public void  getAllActiveClients();

	/*
	 * Sends message to only one client with given name
	 */
	public void whisper(String whisperToName, String message);
	
	/*
	 * Disconnects from server
	 */
	public void quit();
	
	/*
	 * Sends message to server
	 */
	public void send(String message);
}
