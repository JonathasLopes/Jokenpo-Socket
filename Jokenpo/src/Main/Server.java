package Main;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import Client.Client;
import Control.ModeOffline;
import Control.ModeOnline;

public class Server {

	public static void main(String[] args) {
		ServerSocket server;
		Socket client;
		boolean on = true;
		ModeOffline moff = new ModeOffline();

		try {
			server = new ServerSocket(3333);
			System.out.println("Server port: 3333");

			while (on) {
				client = server.accept();
				System.out.println("client entered!");

				String mode = moff.receive(client);

				if (mode.equalsIgnoreCase("Offline")) {
					PlayOffline(client, moff);
					on = false;
				} else {
					PlayOnline(client);
				}

				client.close();
				System.out.println("client left!");
				server.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void PlayOffline(Socket client, ModeOffline moff) {
		String message = "";
		String exit = "exit";
		String send = "";

		do {
			message = moff.receive(client);
			if (!message.equalsIgnoreCase(exit)) {
				System.out.println(message);
				send = "if you want to leave, click 'no'!";
				moff.send(client, send);
			} else {
				System.out.println(message);		
				send = "Result of the match:";
				moff.send(client, send);
			}
		} while (!message.equalsIgnoreCase(exit));
	}

	private static void PlayOnline(Socket client) {
		ModeOnline mon = new ModeOnline();
		Client cl = new Client();
		try {
			mon.bind(3333);
			mon.setOnSocketListener(cl);
			mon.start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		mon.stop();
	}

}
