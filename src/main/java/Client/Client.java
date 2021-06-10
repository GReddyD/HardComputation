package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private static final int SERVER_PORT = 44444;
	private static final String HOST = "127.0.0.1";

	protected void startClient() {
		try {
			Socket socket = new Socket(HOST, SERVER_PORT);
			System.out.println("Клиент стартовал");
			System.out.println("\nConnection to IP: " + HOST + ":" + SERVER_PORT);
			createConnection(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void createConnection(Socket socket) {
		try (BufferedReader inClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		     PrintWriter outClient = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		     Scanner scanner = new Scanner(System.in)) {
			String msg;
			while (true) {
				System.out.print("\nВведите число для расчета на сервере или end для выхода: ");
				msg = scanner.nextLine();
				outClient.println(msg);
				if ("end".equals(msg)) {
					break;
				}
				System.out.println("\nОтвет от сервера: " +
								"\nЧисло Фибоначи = " + inClient.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
