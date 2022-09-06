import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChattyChatChatServer {

	private static ArrayList<ClientHandler> clients = new ArrayList<>();
	
	public static void main(String[] args) {
		
		int port = Integer.parseInt(args[0]);
		int clientNumber = 0;
		ServerSocket listener = null;
		boolean runServer = true;
		
		try {
			listener = new ServerSocket(port);
			while (runServer) {
				try {
					ClientHandler clientThread = new ClientHandler( listener.accept(), clients, clientNumber);
					clients.add(clientThread);
					clientThread.start();
					clientNumber++;
				} catch (IOException e) {
					System.out.println("Error connecting to client " + clientNumber++);
				}
			}	
			listener.close();
		} catch (Exception e) {
			System.out.println("Error establishing listener");
			runServer = false;
		}

	}

}
