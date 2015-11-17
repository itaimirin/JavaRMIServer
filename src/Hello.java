import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
	public String list() throws RemoteException;
	public String reserve(String Class, String Name, int seat) throws RemoteException;
	public String listPassengers() throws RemoteException;
}
