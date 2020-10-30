package Client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;
import javax.swing.JOptionPane;
import Control.ModeOffline;
import Control.ModeOnline;
import Control.OnSocketListener;
import Utils.EnumFingers;

public class Client implements OnSocketListener {
	static Socket socket;
	private int self;
	private int enemy;

	public Client() {
		try {
			socket = new Socket("localhost", 3333);
		} catch (Exception e) {
			System.err.println("Socket isn't working...");
		}
	}

	public static void main(String[] args) {
		Object[] selectionValues = { "Offline", "Online" };
		String initialSelection = "Offline";
		Object selection = JOptionPane.showInputDialog(null, "What's The Game Mode?", "Jokenpô",
				JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
		String opcao = selection.toString();

		try {
			socket = new Socket("localhost", 3333);
		} catch (Exception e) {
			System.err.println("Socket isn't working...");
		}

		if (opcao.equals("Offline")) {
			ModeOffline moff = new ModeOffline();
			moff.send(socket, opcao);
			Offline(socket, moff);
		}

		if (opcao.equals("Online")) {
			ModeOnline mon = new ModeOnline();
			Online(socket, mon);
		}

		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void Offline(Socket client, ModeOffline moff) {
		boolean on = true;
		Random gerador = new Random();

		moff.send(client, "Lets Go!");
		while (on) {
			int escolha = Integer.parseInt(
					JOptionPane.showInputDialog("Choose one hand: \n 1 - Scissors \n 2 - Rock \n 3 - Paper \n"));

			if (escolha <= 0 || escolha > 3) {
				JOptionPane.showMessageDialog(null, "Enter only one of the options!!");
				escolha = Integer.parseInt(
						JOptionPane.showInputDialog("Choose one hand: \n 1 - Scissors \n 2 - Rock \n 3 - Paper \n"));
			}

			int escolhaPC = gerador.nextInt(3) + 1;

			moff.getPl().setEscolha(escolha);
			moff.getBot().setEscolha(escolhaPC);

			String escolhaPlayer = Choose(escolha);
			String escolhaPc = Choose(escolhaPC);
			int vencedor = moff.VerificaGanhador(escolha, escolhaPC);

			moff.WinnerOffline(vencedor, escolhaPlayer, escolhaPc);

			String mensagem = "Result:\n\nPlayer: " + moff.getPl().getPlacarPlayer() + "\nBot: "
					+ moff.getBot().getPlacarPlayer() + "\n\n";

			JOptionPane.showMessageDialog(null, mensagem);

			String message = moff.receive(client);
			System.out.println("Server said: " + message);

			int resposta = JOptionPane.showConfirmDialog(null, "Play again?", "Warning", JOptionPane.YES_NO_OPTION);

			if (resposta == JOptionPane.YES_OPTION) {
				moff.send(client, "I'm gonna to play again!");
			} else {
				moff.send(client, "exit");
				message = moff.receive(client);
				System.out.println("-----------------------------------");
				System.out.println(message);
				ShowMessage(moff);
				on = false;
			}
		}

	}

	public static void Online(Socket client, ModeOnline mon) {
		boolean on = true;
		InetSocketAddress address = new InetSocketAddress(client.getInetAddress(), 3333);
		while (on) {
			int escolha = Integer.parseInt(
					JOptionPane.showInputDialog("Choose one hand: \n 1 - Scissors \n 2 - Rock \n 3 - Paper \n"));

			if (escolha <= 0 || escolha > 3) {
				JOptionPane.showMessageDialog(null, "Enter only one of the options!!");
				escolha = Integer.parseInt(
						JOptionPane.showInputDialog("Choose one hand: \n 1 - Scissors \n 2 - Rock \n 3 - Paper \n"));
			}

			String escolhaPlayer = Choose(escolha);

			try {
				mon.sendTo(address, escolhaPlayer);
				mon.getSelf().setEscolha(escolha);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			int resposta = JOptionPane.showConfirmDialog(null, "Play again?", "Warning", JOptionPane.YES_NO_OPTION);

			if (resposta == JOptionPane.YES_OPTION) {
				
			} else {
				
			}
		}
	}

	public static String Choose(int e) {
		EnumFingers val;
		switch (e) {
		case 1:
			val = EnumFingers.SCISSORS;
			break;
		case 2:
			val = EnumFingers.ROCK;
			break;
		case 3:
			val = EnumFingers.PAPER;
			break;
		default:
			return "";
		}
		return val.toString();
	}

	public static void ShowMessage(ModeOffline moff) {
		int pl = moff.getPl().getPlacarPlayer();
		int bot = moff.getBot().getPlacarPlayer();
		String winner = moff.Winner(pl, bot);
		String send = "You scored: " + pl + " points\nPc scored: " + bot + " points\nThere were: "
				+ moff.getBot().getEmpates() + " draws";

		System.out.println();
		System.out.println(send);
		System.out.println();
		System.out.println("The winner was: " + winner);
		System.out.println("-----------------------------------");
	}

	public void onReceiveEnemyInput(int input) {
		setEnemy(input);
	}

	public void onReceiveSelfInput(int input) {
		setSelf(input);
	}

	@Override
	public void onConnected(ModeOnline channel) {

	}

	@Override
	public void onDisconnected(ModeOnline channel) {

	}

	@Override
	public void onReceived(ModeOnline mon, String msg) {
		int escolha = Integer.parseInt(msg);
		mon.getEnemy().setEscolha(escolha);
	}

	public int getSelf() {
		return self;
	}

	public void setSelf(int self) {
		this.self = self;
	}

	public int getEnemy() {
		return enemy;
	}

	public void setEnemy(int enemy) {
		this.enemy = enemy;
	}

}
