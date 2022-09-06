import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientListener extends Thread {
	
	private Socket socket;
	private BufferedReader in;
	
	public ClientListener(Socket socket) throws IOException {
		this.socket = socket;
		in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
	}

	@Override
	public void run() {
		boolean done = false;
		while (!done) {
			String response = null;
			try {
				response = in.readLine();
				if (response == null || response.equals("")) {
					done = true;
				}
			} catch (IOException e) {
				System.out.println("Error receiving from server");
				done = true;
			}
			System.out.println(response);
		}

	}
}
