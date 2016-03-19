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
	
		ServerSocket ss = null;
		
		//my port number
		int port = 5638;
		
		try{
		
			ss = new ServerSocket(port);
			System.out.println("Waiting for a request" + port);
			
		} catch(IOException e){
		
			System.err.println("Couldn't listen to port" + port);
			System.exit(-1);
		}
		
		while(true){
		
				new ProxyThread(ss.accept()).start();
				
			}
		}
	}