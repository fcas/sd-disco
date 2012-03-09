package rn.ufrn.dimap;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientUDP implements Runnable {
	
	private int port ;
	private InetAddress server ;
	private DatagramSocket socket ;
	private String name;
	
	public ClientUDP(String name, InetAddress server, int port) {
		this.name=name;
		this.server = server ;
		this.port = port ;
		printMessage(this.name, "iniciando.");
	}
	
	
	private  void printMessage(String who,String action){
		System.out.printf("%s:>%s\n.",action);
	}
		
	public void run() {
		while(true) {
			try {
		
				byte[] buf = new byte[65536] ;
				DatagramPacket datagram = new DatagramPacket( buf, buf.length ) ;
				socket.receive( datagram ) ;
				String msg = new String( datagram.getData(), 0, datagram.getLength() ) ;
				printMessage(this.name, msg);
			} catch( IOException x) {}
		}
	}

	
	void doIt() {
		
		try {
			
						
			socket = new DatagramSocket() ;
			
			Thread t = new Thread(this) ;
			t.setDaemon( true ) ;	 
			t.start() ;				 

			Scanner in = new Scanner( System.in ) ;

			String request ;

			do {
				
				request = in.nextLine() ;
				
				byte[] requestData = request.getBytes() ;
				DatagramPacket req = new DatagramPacket( requestData, requestData.length, server, port);
				
				try {
					socket.send( req ) ;
					printMessage(this.name, "enviando "+request);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} while( ! request.equals("!fim")) ;

			socket.close() ;

			
		} catch (SocketException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		
		if( args.length != 2 ) {
			System.err.println("usage: java ClientUDP ip_servidor porta_servidor.") ;
			System.exit(0) ;
			}
			int port = Integer.parseInt( args[1]) ;
			InetAddress server=null;
			
			try {
				server = InetAddress.getByName( args[0] );
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			new ClientUDP("cliente",server, port ).doIt() ;

	}
	
}




