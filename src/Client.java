import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class Client {
	public Client() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String args[]) {
		//SELF-NOTE:///////
		//Classes that are shared, such as the remote interfaces, <--- What I was worried about 
		//must be in the same package at both ends.
		//////////////
		if(args.length != 1 && args.length != 4){
			System.err.println("[+]Usage: Java Client [List | Reserve <Class> <Passenger_name> <SeatNumber>]");
		}else if(args[0].equalsIgnoreCase("list")){
		       	try {
		       		//LocateRegistry makes a reference to a bootstrap remote object registry on a particular host
		       		//getRegistry returns a reference (a stub) to the remote objects registry on a specified host (host)
		    	    Registry registry = LocateRegistry.getRegistry(); 
		    	    
		    	    //Requires 'Hello' interface in Client package
		    	    //Returns a remote reference bound to a specified name in registry
		    	    System.out.println("[+]Client: Fetching Client List...\n");
		    	    System.out.println("-----------------------------------------------\n");
					
		    	    Hello stub = (Hello) registry.lookup("Hello");
		    	    String response  = stub.list();
		    	    System.out.println("[D]: \n" + String.valueOf(response));
		    	    //Using the stub to invoke the remote method on the remote object
		    	} catch (NotBoundException e) {
		    	    System.err.println("[-]Error: Could not reference bound to name...");
		    	    e.printStackTrace();
		    	} catch (RemoteException e) {
					// TODO Auto-generated catch block
		    		System.err.println("[-]Error: Reference could not be created");
					e.printStackTrace();	
		    	}
			}else if(args[0].equalsIgnoreCase("reserve")) {
				try{
	    	    Registry registry = LocateRegistry.getRegistry();
	    	    Hello stub = (Hello) registry.lookup("Hello");
	    	    String response = stub.reserve(args[1], args[2], Integer.valueOf(args[3]));
	    	    System.out.println(response);
	    	
			}catch (Exception e){
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}else if (args[0].equals("passenger List")|| args[0].equals("passenger list") || args[0].equals("list passengers") || args[0].equals("passengers")){
			System.out.println();
			try{
				Registry registry = LocateRegistry.getRegistry();
				Hello stub = (Hello) registry.lookup("Hello");
				String response = stub.listPassengers();
				System.out.println(response);
			}catch (Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
