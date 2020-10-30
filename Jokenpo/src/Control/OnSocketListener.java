package Control;

public interface OnSocketListener
{
	void onConnected(ModeOnline channel);
	void onDisconnected(ModeOnline channel);
	void onReceived(ModeOnline channel, String msg);
}
