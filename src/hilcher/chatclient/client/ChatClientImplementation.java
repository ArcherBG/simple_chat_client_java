package hilcher.chatclient.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import hilcher.chatclient.interfaces.ChatClient;

public final class ChatClientImplementation implements ChatClient {
	private static final String IDENTIFICATION_COMMAND = ":meet";
	private static final String GET_ALL_CLIENTS_COMMAND = ":who";
	private static final String QUIT_COMMAND = ":quit";
	private static final String WHISPER_COMMAND = ":whisper";

	private String serverIp;
	private int serverPortNum;
	private Socket socket;
	private PrintWriter out;

	private boolean listening;

	public ChatClientImplementation(String serverIp, int serverPortNum) {

		if (serverIp != null && serverIp.length() > 0) {
			this.serverIp = serverIp;
		} else {
			printMessageOnConsole("Server Ip can not be null or empty");
			System.exit(1);
		}
		if (serverPortNum > 0 && serverPortNum < 65545) {
			this.serverPortNum = serverPortNum;
		} else {
			printMessageOnConsole("Invalid server port number");
			System.exit(1);
		}

		listening = true;
	}

	public void startClient() throws IOException {

		// Open socket and open output stream
		socket = new Socket(serverIp, serverPortNum);
		out = new PrintWriter(socket.getOutputStream(), true);

		// Start listening on new thread so it does not get interrupted
		new Thread(new Runnable() {

			@Override
			public void run() {
				// Open input stream
				try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

					String msgFromServer = null;
					try {
						while (listening) {

							// Read
							if ((msgFromServer = in.readLine()) != null) {
								printMessageOnConsole(msgFromServer);
							}
						}
					} catch (IOException e) {
					}
				} catch (IOException e) {
					printMessageOnConsole("Error creating in stream");
					e.printStackTrace();
				}
			}
		}).start();

	}

	private void sendMessageToServer(String message) {
		out.println(message);
	}

	private synchronized void printMessageOnConsole(String message) {
		System.out.println(message);
	}

	@Override
	public void setName(String name) {
		String message = IDENTIFICATION_COMMAND + name;
		sendMessageToServer(message);
	}

	@Override
	public void getAllActiveClients() {
		String message = GET_ALL_CLIENTS_COMMAND;
		sendMessageToServer(message);
	}

	@Override
	public void whisper(String whisperToName, String message) {
		/*- Format is the following
		 * :whisper <client name>, <message> -
		 */
		String composedMessage = WHISPER_COMMAND + whisperToName + "," + message;
		sendMessageToServer(composedMessage);
	}

	@Override
	public void quit() {
		sendMessageToServer(QUIT_COMMAND);

		listening = false;
		out.close();

		try {
			socket.close();
		} catch (IOException e) {
			printMessageOnConsole("Error closing socket");
			e.printStackTrace();
		}
	}

	@Override
	public void send(String message) {
		sendMessageToServer(message);
	}

}
