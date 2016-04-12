/* 
 * NapsterServer.java
 *
 * jNapster Library
 * By Harikrishnan Varma <varma@teil.soft.net>
 * Copyright 1999 Harikrishnan Varma
 *
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package jnapster;

import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import jnapster.logging.LogManager;

/**
 * NapsterServer.java
 * 
 * A server in the Napster Music Community.
 * The server can be either a search server
 * or a service server.
 *
 * Created: Thu Dec 09 12:08:40 1999
 *
 * @author  Harikrishnan Varma
 * @version 0.1
 */
public class NapsterServer {
    /**
     * IP address of the Napster Search Server
     */
    final static String   SEARCH_SERVER = "server.napster.com";

    /**
     * Port number of the Napster Search Server
     */
    final static int      SEARCH_PORT = 8875;    

    /**
     * Name of the Napster Server
     */
    String host;
    
    /**
     * Contact port of the Napster Server
     */
    int    port;    

    /**
     * Constructs a Napster Server with specified host and port
     */
    public NapsterServer(String host, int port) {
	this.host = host;
	this.port = port;
    } /* end of NapsterServer() */
    
    /**
     * Returns host
     */
    public String getHost() {
	return host;
    } /* end of getHost() */

    /**
     * Returns port
     */
    public int getPort() {
	return port;
    } /* end of getPort() */

    /**
     * Returns the best NapsterServer available.
     * The Napster Search Server is contacted and queried
     * to find the host:port for the optimal Napster Server online
     */
    public static NapsterServer getOptimalServer() throws NapsterException {
	/*
	 * Contacting Napster Search Server to get the optimal server 
	 * to login to
	 */	
	LogManager.instance().info("Contacting " + SEARCH_SERVER 
				   + ":" 
				   + SEARCH_PORT 
				   + " (Napster Search Server)");
	Socket socket = null;
	try {
	    socket = new Socket(SEARCH_SERVER, SEARCH_PORT);
	} catch (UnknownHostException e3) {
	    LogManager.instance().error("Unknown Host: " + SEARCH_SERVER);
	    throw new NapsterException("Unknown Napster Server: " 
				       + SEARCH_SERVER);
	} catch (SocketException e2) {
	    String message = "Could not connect to Napster Search Server " 
		+ SEARCH_SERVER;
	    LogManager.instance().error(message);
	    throw new NapsterException(message);
	} catch (IOException e1) {
	    String message = "Could not connect to Napster Search Server " 
		+ SEARCH_SERVER;
	    LogManager.instance().error(message);
	    throw new NapsterException(message);	
	}/* end of try-catch */

	/*
	 * Read the response from the Napster Search Server
	 */
	InputStream socketInputStream = null;
	try {
	    socketInputStream = socket.getInputStream();		
	} catch (IOException e) {
	    String message = "Getting inputstream from Napster Search Server";
	    LogManager.instance().error(message);
	    throw new NapsterException(message);
	} /* end of try-catch */
	
	byte[] data = new byte[256];
	int len = 0;
	
	try {
	    len = socketInputStream.read(data);
	} catch (IOException e) {
	    String message = "No response from Napster Search Server";
	    LogManager.instance().error(message);
	    throw new NapsterException(message);
	} /* end of try-catch */

	String message = new String(data, 0, len);
	message = message.trim();

	LogManager.instance().info("Search Server returned " + message);
	
	/*
	 * Handle "wait" and "busy" response from Napster Search Server
	 * 
	 */
	if ((message.indexOf("wait") != -1) 
	    ||(message.indexOf("busy") != -1)) {
	    LogManager.instance().error("Search Server busy");
	    return null;
	} /* end of if */
	
	/*
	 * If the Napster Search Server is not busy/wait, the response is in
	 * "server:port" format. Tokenize it to retrieve server and port values
	 * used for actual login
	 */ 
	StringTokenizer stringtokenizer = new StringTokenizer(message, ":");
	String server = stringtokenizer.nextToken();
	int    port   = Integer.parseInt(stringtokenizer.nextToken());	

	return new NapsterServer(server, port);
    } /* end of getOptimalServer() */
} /* end of NapsterServer */
