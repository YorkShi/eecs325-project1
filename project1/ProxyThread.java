import java.net.*;
import java.io.*;
import java.util.*;

public class ProxyThread extends Thread {

	Socket aSocket;
	
	ProxyThread(Socket a) {
		
		System.out.println("ProxyThread");
		this.aSocket = a;
	}
    
    int host, port;
    String url;
    final int BUFFER = 2048;
	
	public void run() {

        try {
        	
        	BufferedReader in = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
            DataOutputStream out = new DataOutputStream(aSocket.getOutputStream());

            String input;
            int index = 0;
            String url = "";
            ArrayList<String> httpreq = new ArrayList<String>();
            
            while ((input = in.readLine()) != null) {
                try {
                    
                    StringTokenizer nToken = new StringTokenizer(input);
                    nToken.nextToken();
                    
                } catch (Exception e) {
                    
                    break;
                    
                }

                if (index == 0) {
                    String[] tokens = input.split(" ");
                    url = tokens[1];
                    System.out.println("Request for : " + url);
                }
                else
                	httpreq.add(input);
                System.out.println(input);

                index++;
            }
            
            // we run Google as default
            String hName = "www.google.com";
            String directory = "/";
            
            try {
                
                    String domain = url.substring(7, url.length());
                    index = domain.indexOf("/");
                    hName = domain.substring(0, index);
                
                    directory = domain.substring(index, domain.length());
                    System.out.println(hName);
                    System.out.println(directory);
                
            }catch (Exception e) {
            	
                    System.out.println("This URL " + url + " was not able to be processed.");
            }
            
            System.out.println("Socket being created for "+ hName);

            Socket socket = new Socket(hName, 80);
            System.out.println("Socket created!");

    		PrintWriter serv = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))); 
    		System.out.println("Sending request to: " + url);
    		serv.println("GET " + directory + " HTTP/1.1");
    		for(int i = 0; i < httpreq.size(); i++) {
    			
    			serv.println(httpreq.get(i));
    		}
            
    		serv.println(); 
    		serv.flush(); 

    		InputStream is = socket.getInputStream();
    		
    		byte [] data = new byte[BUFFER];
    		int check = is.read(data, 0, BUFFER);
    		while(check != -1) {

    			out.write(data, 0, check);
    			check = is.read(data, 0, BUFFER);
    		}

    		socket.close();
    		in.close();
    		out.close();
    		is.close();
            
    		
        }
        catch (Exception e) {
        	
        	System.err.println("Error:" + e.getMessage());
        }
            
	}
}
