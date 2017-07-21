package ObjectServer;

import ServerServiceCode.AccountManager;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 *
 * @author Stephen Hoey
 */
public class BankingServer {

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        String portNum, registryURL;

        try {
            System.out.println("Enter the RMIregistry port number:");
            int RMIPortNum = input.nextInt();
            // Turn on the registry to allow storage of remote objects
            startRegistry(RMIPortNum);

            // Create the object to be used remotely
            AccountManager exportedObj = new AccountManager();
            exportedObj.register("Stephen", "Hoey", "Afton Drive.", "password");
            // Create a location and label for the object to be stored at
            registryURL = "rmi://localhost:" + RMIPortNum + "/accountManager";
            // "Bind" / "Export" the remote object (make it available in the registry)
            Naming.rebind(registryURL, exportedObj);

            System.out.println("Server registered.  Registry currently contains:");
            // list names of objects currently in the registry
            listRegistry(registryURL);

            System.out.println("BankingServer ready.");
        }// end try
        catch (Exception re) {
            System.out.println("Exception in BankingServer.main: " + re);
        } // end catch
    } // end main

    // This method starts a RMI registry on the local host, if it
    // does not already exists at the specified port number.
    private static void startRegistry(int RMIPortNum) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list();  // This call will throw an exception
            // if the registry does not already exist
        } catch (RemoteException e) {
            // No valid registry at that port.
            System.out.println("RMI registry cannot be located at port " + RMIPortNum);
            Registry registry = LocateRegistry.createRegistry(RMIPortNum);
            System.out.println("RMI registry created at port " + RMIPortNum);
        }
    } // end startRegistry

    // This method lists the names registered with a Registry object
    private static void listRegistry(String registryURL) throws RemoteException, MalformedURLException {
        System.out.println("Registry " + registryURL + " contains: ");
        String[] names = Naming.list(registryURL);
        for (int i = 0; i < names.length; i++) {
            System.out.println(names[i]);
        }
    } //end listRegistry

} // end class
