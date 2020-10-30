package Control;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import Entity.Player;

public class ModeOnline implements Runnable{
	private Player self = new Player("Player");
	private Player enemy = new Player("Enemy");
	private DatagramSocket socket;
	private boolean running;
	
	private OnSocketListener onSocketListener;
	
	public void setOnSocketListener(OnSocketListener onSocketListener)
	{
		this.onSocketListener = onSocketListener;
	}
	
	public void bind(int port) throws SocketException
	{
		socket = new DatagramSocket(port);
	}
	
	public void start()
	{
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void stop()
	{
		running = false;
		socket.close();
	}

	@Override
	public void run()
	{
		byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		
		running = true;
		while(running)
		{
			try
			{
				socket.receive(packet);
				
				String msg = new String(buffer, 0, packet.getLength());
				
				if(null != onSocketListener)
				{
					onSocketListener.onReceived(this, msg);
				}
			} 
			catch (IOException e)
			{
				break;
			}
		}
	}

	public void sendTo(InetSocketAddress address, String msg) throws IOException
	{
		byte[] buffer = msg.getBytes();
		
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		packet.setSocketAddress(address);
		
		socket.send(packet);
	}

	public Player getSelf() {
		return self;
	}

	public Player getEnemy() {
		return enemy;
	}

}
