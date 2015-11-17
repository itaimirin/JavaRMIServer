
import java.rmi.registry.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Server implements Hello{
	static List<Integer> seatList = new ArrayList<Integer>();
	static List<String> passengerList = new ArrayList<String>();
	
	public Server(){}
	//This method is responsible for listing the available seats
	public String list(){


		// TODO Auto-generated method stub

		System.out.println("...Client is requesting Seating List...");
		
		String List = null;
		String busSeats = "\nSeat Numbers: \n";
		String econSeats = "\nSeat Numbers: \n";
		
		if(seatList.size() == 0){
			System.out.println("[-]Server: There aren't any available seats");
			return "..There aren't any available seats..";
		}//END OF IF (SEATLIST) STATEMENT
		else{
			//Creating seat output
			String bString = null,eString = null; 
			int bTckt = 0, highB = 0, lowB = 0;	//Business Ticket Count, High and Low Price
			int eTckt = 0, highE = 0, midE = 0, lowE = 0;	//Economy Ticket Count, High, Mid and Low Price
			for(int i = 0; i <seatList.size();i++){
				if(seatList.get(i) <= 5){ // Business Tickets
					bTckt ++;
					busSeats = busSeats+ seatList.get(i) + ",";
				}//END OF NESTED IF
				else if(seatList.get(i) <= 30){ //Economy Tickets
					eTckt ++;
					econSeats = econSeats + seatList.get(i) + ",";
				}//END OF ELSE IF
			}//END OF FORLOOP
			if(bTckt > 0 ){
				if (bTckt > 3){
					lowB = bTckt - 2;
					highB = 2;
					bString = lowB + "Seat(s) at $500 each \n" + highB + " Seat(s) at $800 each";
				}else{
					highB = bTckt;
					bString = highB + " Seat(s) at $800 each";
				}
			}else{ // IF NO BUSINESS TICKETS ARE AVAIABLE
				bString = "[-]Server: No More Business Seats Available";
			}
		if (eTckt > 0) {
			if (eTckt > 15) { // IF ALL SETS OF ECONOMY TICKETS ARE AVAILABLE
				lowE = eTckt - 15;
				midE = 10;
				highE = 5;

				eString = lowE + " seat(s) at $200 each\n" + midE
						+ " seat(s) at $300 each\n" + highE
						+ " seat(s) at $450 each";
			} else if (eTckt > 5) { // IF ONLY THE SECOND AND LAST SET OF ECONOMY TICKETS ARE AVAILABLE
				midE = eTckt - 5;
				highE = 5;

				eString = midE + " seat(s) at $300 each\n" + highE
						+ " seat(s) at $450 each";
			} else { // IF ONLY THE LAST SET OF ECONOMY TICKETS ARE AVAILABLE
				highE = eTckt;
				eString = highE + " seat(s) at $450 each";
			}

		} else // IF NO ECONOMY TICKETS ARE AVAILABLE
			eString = "[+]Server: No More Economy Seats Avaialble";

		// COMPLETE LIST OF AVAILABLE SEATS
		List = ("Business Class:\n" + bString + "\n" + busSeats
				+ "\n\nEconomy Class:\n" + eString + "\n" + econSeats);
	}
		return List;
	} //END OF LIST
	//This method is responsible for listing passengers occupying seats
	public String listPassengers(){
		System.out.println("...Client Requesting Passenger List...");
		String output = "Passenger List: \n";
		if(passengerList.size() > 0){
			for(int i = 0; i < passengerList.size(); i ++){
				output =  output + passengerList.get(i)+ "\n";
			}
		}else{
			return "There aren't any passengers!";
		}
		return output;
	}
	//This method is responsible for creating seat reservations
	public String reserve(String Class, String Name, int seat)  {

		System.out.println("...Client Requesting Seat Reservation...");
		String output = null;
		
		//Error Checking for invalid seats
		if ((seat < 1 || seat > 30) || (seat <= 5 && Class.toLowerCase().equals("economy")) || (seat > 5 && Class.toLowerCase().equals("business"))) {
			output = "Failed to reserve seat #" + seat + ": Invalid Seat Number";
		}else if(Class.toLowerCase().equals("business") || Class.toLowerCase().equals("economy")) {
			for(int i = 0; i < seatList.size(); i ++){
				if(seatList.get(i) == seat){	
					if(seat <= 5 && Class.toLowerCase().equals("business") || (seat > 5 && Class.toLowerCase().equals("economy"))) {
						passengerList.add(Name + " " + Class + " " + seat);
						seatList.remove(i); //remove from Array so overlapping doesn't occur
						output = "Seat #"+ seat +" reserved for passenger " + Name;
					}break;
				}else{
					output = "Seat #"+ seat +" can not be reserved!" + " Seat is unavailable";
				}
			}
		}else{
			output = "Seat #"+ seat + " can not be reserved! "+ "Invalid seat number";
		}
		return output;
	}
	
	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub
		System.setProperty("java.security.policy","file:/C:/Program Files/Java/jre1.8.0_60/lib/security/mypolicy.policy");
		//I do not need to start the RMI registry if i use this command
		//I do not need securityManager as well, apparently because I am not using codebase ( not sure what that is)
		//However, Security Manager allows me to call classes that are not local (Has to do with permissions)
		LocateRegistry.createRegistry(1099); //Solution to all my problems?? jheezuz
		try {
			for(int i = 1; i < 30; i++){
				seatList.add(i);
			}
			System.out.println("[+]RMIServer: Constructing server implementation...");
			Server obj = new Server();
			Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);
			
			System.out.println("[+]RMIServer: Binding Server implementation to registry...");
			//Fetches the Registry I implemented above?
			Registry registry = LocateRegistry.getRegistry(); 
		    // Bind the remote object's stub in the registry
			registry.rebind("Hello", stub); //
			
			System.out.println("[+]Server is Ready!\n\n");
		}catch(Exception e){
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
