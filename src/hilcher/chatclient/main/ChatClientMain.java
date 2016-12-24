package hilcher.chatclient.main;

import java.io.IOException;

import hilcher.chatclient.client.ChatClientImplementation;
import hilcher.chatclient.interfaces.ChatClient;

public class ChatClientMain {

	public static void main(String[] args) {

		String serverIp = "192.168.0.101";
		int serverPortNum = 3456;

		// First Chat
		Thread firstClientThread = new Thread(new Runnable() {
			@Override
			public void run() {

				ChatClient whisperChat = new ChatClientImplementation(serverIp, serverPortNum);
				try {
					// Slow down the thread in order the other threads to create
					for (int i = 0; i < 1000000; i++) {
					}
					
					// Whisper only so one person
					whisperChat.startClient();
					whisperChat.setName("Yoda");
					whisperChat.whisper("Vazov", "Darth Vader");
					whisperChat.whisper("Vazov", "is here");
					
					// Slow down the thread in order to live longer
					for (int i = 0; i < 1000000; i++) {
						double rand = Math.random();
					}
					whisperChat.quit();
				} catch (IOException e) {
					printMessageOnConsole("Unable to start Chat Client");
					e.printStackTrace();
				}
			}
		});
		firstClientThread.start();

		// Second Chat
		Thread secondClientThread = new Thread(new Runnable() {
			@Override
			public void run() {

				ChatClient secondChat = new ChatClientImplementation(serverIp, serverPortNum);
				try {
					// Slow down the thread in order the other threads to create
					for (int i = 0; i < 1000000; i++) {
					}

					secondChat.startClient();
					secondChat.setName("Vazov");
					secondChat.send("Ide li");
					
					// Slow down the thread in order to live longer
					for (int i = 0; i < 1000000; i++) {
						double rand = Math.random();
					}
					secondChat.quit();
				} catch (IOException e) {
					printMessageOnConsole("Unable to start Chat Client");
					e.printStackTrace();
				}
			}
		});
		secondClientThread.start();

		// Third Chat 
		Thread thirdClientThread = new Thread(new Runnable() {
			@Override
			public void run() {

				ChatClient thirdChat = new ChatClientImplementation(serverIp, serverPortNum);
				try {
					// Slow down the thread in order the other threads to create
					for (int i = 0; i < 1000000; i++) {
					}
					thirdChat.startClient();
					thirdChat.setName("Henry Ford");
					thirdChat.send("Model T");
					
					// Slow down the thread in order to live longer
					for (int i = 0; i < 1000000; i++) {
						double rand = Math.random();
					}
					 thirdChat.quit();
				} catch (IOException e) {
					printMessageOnConsole("Unable to start Chat Client");
					e.printStackTrace();
				}
			}
		});
		thirdClientThread.start();

		// Fourth chat
		Thread whoClientThread = new Thread(new Runnable() {
			@Override
			public void run() {

				ChatClient secondChat = new ChatClientImplementation(serverIp, serverPortNum);
				try {
					// Slow down the thread in order the other threads to create
					for (int i = 0; i < 1000000; i++) {
					}
					secondChat.startClient();
					secondChat.setName("Anon");
					secondChat.getAllActiveClients();
					
					// Slow down the thread in order to live longer
					for (int i = 0; i < 1000000; i++) {
						double rand = Math.random();
					}
					 secondChat.quit();						
				} catch (IOException e) {
					printMessageOnConsole("Unable to start Chat Client");
					e.printStackTrace();
				}
			}
		});
		whoClientThread.start();

	}

	private synchronized static void printMessageOnConsole(String message) {
		System.out.println(message);
	}

}
