package Server;

import java.io.*;
import java.net.*;
import java.util.stream.Stream;

public class Server {

	private static final int SERVER_PORT = 44444;

	protected void startServer() {
		try {
			ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("\nСервер стартовал");
			createSocket(serverSocket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void createSocket(ServerSocket serverSocket) {
		String msg;
		try (Socket socket = serverSocket.accept();
		     PrintWriter outServer = new PrintWriter(socket.getOutputStream(), true);
		     BufferedReader inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			while ((msg = inServer.readLine()) != null) {
				if (msg.equals("end")) {
					System.out.println("\nОтключение от сервера");
					break;
				}
				outServer.println(getFibonacciValue(msg));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int getFibonacciValue(String msg) {
		int value = Integer.parseInt(msg);
		return Stream.iterate(new int[]{0, 1}, arr -> new int[]{arr[1], arr[0] + arr[1]})
						.limit(value)
						.map(t -> t[0])
						.mapToInt(Integer::intValue)
						.sum();
	}
}
