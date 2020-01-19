
import common.*;
import ocsf.server.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.String;
import javax.net.ssl.SSLException;

/**
* This class overrides some of the methods in the abstract 
* superclass in order to give more functionality to the server.
*
* @author Dr Timothy C. Lethbridge
* @author Dr Robert Lagani&egrave;re
* @author Fran&ccedil;ois B&eacute;langer
* @author Paul Holden
* @version July 2000
*/
public class EchoServer extends AbstractServer 
{
//Class variables *************************************************

static private final String DB = "wlcjCucJm8";
static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
static private final String USER = "wlcjCucJm8";
static private final String PASS = "ono3VjFc4a";
/**
* The default port to listen on.
*/
final public static int DEFAULT_PORT =5555;
static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
/**
* The interface type variable. It allows the implementation of 
* the display method in the client.
*/
ChatIF serverUI;



LinkedList<Integer> updated=new LinkedList<Integer>();

Connection conn = null;
Statement stmt = null;

//Constructors ****************************************************

/**
* Constructs an instance of the echo server.
*
* @param port The port number to connect on.
*/
public EchoServer(int port) 
{
 super(port);
 
}

/**
* Constructs an instance of the echo server.
*
* @param port The port number to connect on.
* @param serverUI The interface type variable.
*/
public EchoServer(int port, ChatIF serverUI) throws IOException
{
 super(port);
 this.serverUI = serverUI;
 //updated = new LinkedList<Integer>();
 /*
 try {
	   Class.forName(DRIVER);
	   conn = DriverManager.getConnection(DB_URL, USER, PASS);
	   stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
 }
 catch (SQLException se) {
	   se.printStackTrace();
	   System.out.println("SQLException: " + se.getMessage());
	   System.out.println("SQLState: " + se.getSQLState());
	   System.out.println("VendorError: " + se.getErrorCode());
	   System.out.println("VendorError: " + se.getErrorCode());
     //client.sendToClient("Connection to the database failed.");
	} catch (Exception e) {
	   e.printStackTrace();
		//client.sendToClient("Connection to the database failed.");
	}*/
}


//Instance methods ************************************************

/**
* This method handles any messages received from the client.
*
* @param msg The message received from the client.
* @param client The connection from which the message originated.
 * @throws SQLException 
 * @throws IOException 
*/
public void handleMessageFromClient
 (Object msg, ConnectionToClient client) throws SQLException, IOException
{
	
 if (msg.toString().startsWith("login "))
 {
   if (client.getInfo("loginID") != null)
   {
     try
     {
       client.sendToClient("You are already logged in.");
     }
     catch (IOException e)
     {
     }
     return;
   }
   client.setInfo("loginID", msg.toString().substring(6));
 }
 else 
 {
   if (client.getInfo("loginID") == null)
   {
     try
     {
       client.sendToClient("You need to login before you can command the server.");
       client.close();
     }
     catch (IOException e) {}
     return;
   }
   else {
	   try {
		   Class.forName(JDBC_DRIVER);

		   runC((String)msg, client);
 }
 catch (SQLException se) {
	   se.printStackTrace();
	   System.out.println("SQLException: " + se.getMessage());
	   System.out.println("SQLState: " + se.getSQLState());
	   System.out.println("VendorError: " + se.getErrorCode());
	   System.out.println("VendorError: " + se.getErrorCode());
     //client.sendToClient("Connection to the database failed.");
	} catch (Exception e) {
	   e.printStackTrace();
		//client.sendToClient("Connection to the database failed.");
	}
   //em7aha b3den
 //  System.out.println("Message received: " + msg + " from \"" + 
   //  client.getInfo("loginID") + "\" " + client);
   //this.sendToAllClients(client.getInfo("loginID") + "> " + msg);
   //l7ad hon
   }
 }
}

void runC(String msg, ConnectionToClient client)  throws SQLException, IOException {

	conn = DriverManager.getConnection(DB_URL, USER, PASS); 
	stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	
	
	
	ResultSet rs = null;
	   if (((String) msg).equalsIgnoreCase("print all")) {
	  		/////////////////////////// print all products
				
		   System.out.println("printing all\n");
		  // try {
			client.sendToClient(msg);
		//} catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
	//	}
	  		String sql = "SELECT * FROM products";
	 	   System.out.println("sql = "+sql+"\n");
	  		
		//	try {
	  		//System.out.println("fotna ltry");
				rs = stmt.executeQuery(sql);
			//	System.out.println("3mlna execute\n");
			//} catch (SQLException e) {
				// TODO Auto-generated catch block
			//	System.out.println("akel 5ara...\n");
			//	e.printStackTrace();
			//}
	  		String s = null;
	  		//try {
				while (rs.next()) {
					int num = rs.getInt("num");
					String name = rs.getString("name");
					int price = rs.getInt("price");
					s = s + String.format("Number %5s name %15s Price %5d\n", num, name, price);
				}
			//} catch (SQLException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
	  		//try {
				client.sendToClient(s);
		//	} catch (IOException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
	   }
	   else if (((String) msg).startsWith("print product ")){			
	  		/////////////////// select specified product and print
	  	 	int num = Integer.parseInt(((String) msg).substring(14));
	  		String sql = "SELECT * FROM products WHERE num = ?";
	  		PreparedStatement prep_stmt = null;
			try {
				prep_stmt = conn.prepareStatement(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  		try {
				prep_stmt.setInt(1, num);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				rs = prep_stmt.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  		try {
				while (rs.next()) {
					int num1 = rs.getInt("num");
					String name = rs.getString("name");
					int price = rs.getInt("price");
					client.sendToClient(String.format("Number %5s name %15s Price %5d\n", num1, name, price));
				}
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }
	   else if (((String) msg).startsWith("update price of ")) {		
	  	 	//////////////////////change price for specified product
	  	 	Scanner sc = new Scanner(((String) msg).substring(16));
	  		int num2 = sc.nextInt();
	  		String s = sc.next();
	  		int num = sc.nextInt();
	  		
	  		if(!updated.contains(num)) {
	  			updated.add(num);
	  		}
	  		
	  		
	  		String sql="SELECT * FROM products WHERE num = " + String.valueOf(num2); //Integer.toString(num2);
	  		rs = stmt.executeQuery(sql);
	  		rs.next();
	  		rs.updateInt("price", num);
	  		int up=rs.getInt("updated");
	  		rs.updateInt("updated", (up+1));
	  		rs.updateRow();
			client.sendToClient("Price updated");
	   }
	   
	   
	   else if (((String) msg).equalsIgnoreCase("print updated")){
		   String sql = "SELECT * FROM products WHERE updated > 0";
		   rs = stmt.executeQuery(sql);
		   while(rs.next())
		   {
				int num1 = rs.getInt("num");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				int up = rs.getInt("updated");
				client.sendToClient(String.format("Number %5s name %15s Price %5d updated %5s times!\n", num1, name, price,up));
		   }
		   
		   
		   
		   
	  		
			
	  	 }
		    
	   }


/**
* This method handles all data coming from the UI
*
* @param message The message from the UI
*/
public void handleMessageFromServerUI(String message)
{
   runCommand(message);
}

/**
* This method executes server commands.
*
* @param message String from the server console.
*/
private void runCommand(String message)
{
 // run commands
 // a series of if statements

 if (message.equalsIgnoreCase("quit"))
 {
   quit();
 }
 else if (message.equalsIgnoreCase("stop"))
 {
   stopListening();
 }
 else if (message.equalsIgnoreCase("close"))
 {
   try
   {
     close();
   }
   catch(IOException e) {}
 }
 else if (message.toLowerCase().startsWith("setport"))
 {
   if (getNumberOfClients() == 0 && !isListening())
   {
     // If there are no connected clients and we are not 
     // listening for new ones, assume server closed.
     // A more exact way to determine this was not obvious and
     // time was limited.
     int newPort = Integer.parseInt(message.substring(9));
     setPort(newPort);
     //error checking should be added
     serverUI.display
       ("Server port changed to " + getPort());
   }
   else
   {
     serverUI.display
       ("The server is not closed. Port cannot be changed.");
   }
 }
 else if (message.equalsIgnoreCase("start"))
 {
   if (!isListening())
   {
     try
     {
       listen();
     }
     catch(Exception ex)
     {
       serverUI.display("Error - Could not listen for clients!");
     }
   }
   else
   {
     serverUI.display
       ("The server is already listening for clients.");
   }
 }
 else if (message.equalsIgnoreCase("getport"))
 {
   serverUI.display("Currently port: " + Integer.toString(getPort()));
 }
}
 
/**
* This method overrides the one in the superclass.  Called
* when the server starts listening for connections.
*/
protected void serverStarted()
{
 System.out.println
   ("Server listening for connections on port " + getPort());
}

/**
* This method overrides the one in the superclass.  Called
* when the server stops listening for connections.
*/
protected void serverStopped()
{
 System.out.println
   ("Server has stopped listening for connections.");
}

/**
* Run when new clients are connected. Implemented by Benjamin Bergman,
* Oct 22, 2009.
*
* @param client the connection connected to the client
*/
protected void clientConnected(ConnectionToClient client) 
{
 // display on server and clients that the client has connected.
 String msg = "A Client has connected";
 System.out.println(msg);
 this.sendToAllClients(msg);
}

/**
* Run when clients disconnect. Implemented by Benjamin Bergman,
* Oct 22, 2009
*
* @param client the connection with the client
*/
synchronized protected void clientDisconnected(
 ConnectionToClient client)
{
 // display on server and clients when a user disconnects
 String msg = client.getInfo("loginID").toString() + " has disconnected";

 System.out.println(msg);
 this.sendToAllClients(msg);
}

/**
* Run when a client suddenly disconnects. Implemented by Benjamin
* Bergman, Oct 22, 2009
*
* @param client the client that raised the exception
* @param Throwable the exception thrown
*/
synchronized protected void clientException(
 ConnectionToClient client, Throwable exception) 
{
 String msg = client.getInfo("loginID").toString() + " has disconnected";

 System.out.println(msg);
 this.sendToAllClients(msg);
}

/**
* This method terminates the server.
*/
public void quit()
{
 try
 {
   close();
 }
 catch(IOException e)
 {
 }
 System.exit(0);
}


//Class methods ***************************************************

/**
* This method is responsible for the creation of 
* the server instance (there is no UI in this phase).
*
* @param args[0] The port number to listen on.  Defaults to 5555 
*          if no argument is entered.
*/
public static void main(String[] args) 
{
 int port = 0; //Port to listen on

 try
 {
   port = Integer.parseInt(args[0]); //Get port from command line
 }
 catch(Throwable t)
 {
   port = DEFAULT_PORT; //Set port to 5555
 }
	
 EchoServer sv = new EchoServer(port);
 
 try 
 {
   sv.listen(); //Start listening for connections
 } 
 catch (Exception ex) 
 {
   System.out.println("ERROR - Could not listen for clients!");
 }
}
}
//End of EchoServer class
