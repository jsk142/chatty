import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChattyChatChatClient {

	public static void main(String[] args) {
		
		String hostName = args[0];
		int port = Integer.parseInt(args[1]);
		Socket socket = null;
		
		try {
			socket = new Socket(hostName, port);
			BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader userIn = new BufferedReader( new InputStreamReader(System.in));
			
			for (int i = 0; i < 2; i++) {
				System.out.println(in.readLine());
			}
			
			new ClientListener(socket).start();
			
			boolean done = false;
			while (!done) {
				String userInput = userIn.readLine();
				out.println(userInput);
				if ( userInput.startsWith("/quit")) {
					done = true;
				}
			}
			socket.close();
		} catch (IOException e) {
			System.out.println("Error connecting to server");
		}

	}

}
