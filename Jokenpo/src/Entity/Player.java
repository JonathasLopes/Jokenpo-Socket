package Entity;

public class Player {
	private String Player;
	private int PlacarPlayer;
	private int Empates;
	private int Escolha;
	
	public Player(String Player) {
		this.Player = Player;
	}

	public int getPlacarPlayer() {
		return PlacarPlayer;
	}
	public void setPlacarPlayer(int placarPlayer) {
		this.PlacarPlayer = placarPlayer;
	}
	public int getEscolha() {
		return Escolha;
	}
	public void setEscolha(int escolha) {
		this.Escolha = escolha;
	}
	public int getEmpates() {
		return Empates;
	}
	public void setEmpates(int empates) {
		this.Empates = empates;
	}
	public String getPlayer() {
		return Player;
	}
	public void setPlayer(String player) {
		this.Player = player;
	}
	
}
