/*
 * eecs325
 * Yuanjing Shi
 * yxs638
 * project 1
 * Simple web proxy in Java
 */
 
import java.net.*;
import java.io.*;
public class Proxy{

	public static void main(String[] argz) throws IOException {
	
		ServerSocket serverSocket = null;
		
		//my port number 5000 + 638
		int port = 5638;
		
		try{
		
			serverSocket = new ServerSocket(port);
			System.out.println("Waiting for a request" + port);
			
		} catch(IOException e){
		
			System.err.println("Port Error:" + port);
			System.exit(-1);
		}
		
		while(true){
		
				new ProxyThread(serverSocket.accept()).start();
        }
		}
	}