import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
	
	private Socket socket;
	private int clientNumber;
	private BufferedReader in;
	private PrintWriter out;
	private String nickname;
	private ArrayList<ClientHandler> clients;
	
	public ClientHandler(Socket socket, ArrayList<ClientHandler> clients, int clientNumber) throws IOException{
		this.socket = socket;
		this.clients = clients;
		this.clientNumber = clientNumber;
		in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		nickname = "";
	}
	
	@Override
	public void run() {
		try {			
			this.out.println("Hello client " + clientNumber);
			this.out.println("Enter /quit to quit");
			
			boolean done = false;
			while (!done) {
				String input = this.in.readLine();
				if (input == null || input.startsWith("/quit")) {
					done = true;
				}
				if (input.startsWith("/nick")) {
					String[] s = input.split(" ");
					this.setNickname(s[1]);
				} else if (input.startsWith("/dm")) {
					String[] t = input.split(" ");
					String n = t[1];
					String msg = "";
					for (int i = 2; i < t.length; i++) {
						msg = msg + t[i] + " ";
					}
					directMessage(n, msg);
				} else {
					publicMessage(input);
				}

				System.out.println("Sent " + input + " to client " + clientNumber);
			}
		} catch (IOException e) {
			System.out.println("Error while talking to client " + clientNumber);
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println("Error closing socket with client " + clientNumber);
			} finally {
				System.out.println("Connection to client " + clientNumber + " closed");
			}
	    }
	}
	
	private String getNickname() {
		return nickname;
	}
	
	private void setNickname(String name) {
		this.nickname = name;
	}
	
	private void publicMessage(String message) {
		for (int i = 0; i < clients.size(); i++) {
			clients.get(i).out.println(message);
		}
	}
	
	private void directMessage(String nick, String message) {
		for (int i = 0; i < clients.size(); i++) {
			if ( clients.get(i).getNickname().equals(nick)){
				clients.get(i).out.println(message);
			}
		}
	}
	
}


