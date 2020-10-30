package Control;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import Entity.Player;

public class ModeOffline {
	private Player pl = new Player("Player");
	private Player bot = new Player("bot");
	
	public void send(Socket socket, String message) {
		DataOutputStream out;
		try {
			out = new DataOutputStream(socket.getOutputStream());
			out.write(message.getBytes());
			out.flush();
		} catch (Exception e) {
			System.err.println("Excecao no OutputStream");
		}
	}

	public String receive(Socket socket) {
		InputStream in;
        int bt;
        byte btxt[];
        String txt = "";
        btxt = new byte[127];
        try {
            in = socket.getInputStream();
            bt = in.read(btxt);
            if (bt > 0) {
                txt = new String(btxt);
            }
        } catch (Exception e) {
            System.err.println("Excecao no InputStream: " + e);
        }
        return txt.trim();
	}
	
	public int VerificaGanhador(int player, int pc) {
		int resultado = 0;

		switch (player) {
		case 1:
			switch (pc) {
			case 1:
				resultado = 0;
				break;
			case 2:
				resultado = 2;
				break;
			case 3:
				resultado = 1;
				break;
			}
			break;
		case 2:
			switch (pc) {
			case 1:
				resultado = 1;
				break;
			case 2:
				resultado = 0;
				break;
			case 3:
				resultado = 2;
				break;
			}
			break;
		case 3:
			switch (pc) {
			case 1:
				resultado = 2;
				break;
			case 2:
				resultado = 1;
				break;
			case 3:
				resultado = 0;
				break;
			}
			break;
		}
		
		return resultado;
	}
	
	public void WinnerOffline(int w, String escolhaPlayer, String escolhaPc) {
		
		if (w == 1) {
			JOptionPane.showMessageDialog(null,
					"You choose: " + escolhaPlayer + " \nPC choose: " + escolhaPc + " \nYou Win!");
			pl.setPlacarPlayer(pl.getPlacarPlayer() + 1);
		} else if (w == 2) {
			JOptionPane.showMessageDialog(null,
					"You choose: " + escolhaPlayer + " \nPC choose: " + escolhaPc + " \nPC Win!");
			bot.setPlacarPlayer(bot.getPlacarPlayer() + 1);
		} else {
			JOptionPane.showMessageDialog(null,
					"You choose: " + escolhaPlayer + " \nPC choose: " + escolhaPc + " \nDraw!");
			pl.setEmpates(pl.getEmpates() + 1);
			bot.setEmpates(bot.getEmpates() + 1);
		}
	}
	
	public String Winner(int player, int pc) {
		if(player > pc) {
			return "You Win!";
		}
		if(player < pc){
			return "Pc Win!";
		}else {
			return "Draw!";
		}
	}
	
	public Player getPl() {
		return pl;
	}
	
	public Player getBot() {
		return bot;
	}
}
