
/* 
 * NapsterSession.java
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

import java.util.Vector;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;


import jnapster.logging.LogManager;

/**
 * NapsterSession.java
 *
 * Represents a session with a Napster Server
 * A session is setup by logging into a Napster
 * Server
 *
 * Created: Thu Dec 09 14:01:12 1999
 *
 * @author  Harikrishnan Varma
 * @version 0.1
 */
public class NapsterSession {
    /**
     * Profile of user connected to the Napster Server
     */
    NapsterProfile napsterProfile;
    
    /**
     * List of file transfers in the session
     */
    Vector           napsterTransfers;
    
    /**
     * Socket used for setting up the session
     */
    private Socket         socket;

    /**
     * Socket's output stream
     */
    public OutputStream   socketOutputStream;

    /**
     * Socket's input stream
     */
    public InputStream    socketInputStream;

    /**
     * Session state. It can be connected, disconnected
     */
    private String         state;


    /**
     * Creates a NapsterSession object
     */
    public NapsterSession(NapsterProfile napsterProfile) {
	this.napsterProfile = napsterProfile;
    } /* end of NapsterSession() */

    /**
     * Login to the session with an optimal server
     */
    public  void login() throws NapsterException {
	napsterTransfers = new Vector();
	NapsterServer napsterServer = NapsterServer.getOptimalServer();
	
	if (napsterServer == null) {
	    LogManager.instance().error("Search Server Busy");
	    throw new NapsterException("Search Server Busy");      
	} /* end of if */
	
	login(napsterServer.getHost(), napsterServer.getPort());
    } /* end of login() */
    
    /**
     * Login to a session with the specified server
     */
    public void login(String server, int port) throws NapsterException {
	napsterTransfers = new Vector();
	LogManager.instance().info("Logging into " + server + ":" + port);	
	try {
	    socket = new Socket(server, port);
	}catch (UnknownHostException e3) {
	    LogManager.instance().error("Unknown Host: " + server);
	    throw new NapsterException("Unknown Napster Server: " 
				       + server);
	} catch (SocketException e2) {
	    String message = "Could not connect to Napster Search Server " 
		+ server;
	    LogManager.instance().error(message);
	    throw new NapsterException(message);
	} catch (IOException e1) {
	    String message = "Could not connect to Napster Search Server " 
		+ server;
	    LogManager.instance().error(message);
	    throw new NapsterException(message);	
	}/* end of try-catch */
	try {
	    socketOutputStream = socket.getOutputStream();
	} catch (IOException e) {
	    String message = "Getting outputstream from Napster Search Server";
	    LogManager.instance().error(message);
	    throw new NapsterException(message);
	} /* end of try-catch */  
	
	try {
	    socketInputStream = socket.getInputStream();		
	} catch (IOException e) {
	    String message = "Getting inputstream from Napster Search Server";
	    LogManager.instance().error(message);
	    throw new NapsterException(message);
	} /* end of try-catch */  
	
	NapsterPacket napsterPacket = null;
	try {
	    napsterPacket = 
		new NapsterPacket(NapsterPacket.LOGIN, napsterProfile.toString());
	    NapsterTransport.send(napsterPacket, socketOutputStream);
	    napsterPacket = NapsterTransport.recieve(socketInputStream);
	    napsterPacket = NapsterTransport.recieve(socketInputStream);
	} catch (IOException e) {
	    String message = "Napster send/recieve failed";
	    LogManager.instance().error(message);
	    throw new NapsterException(message);
	} catch (NapsterException e) {
	    String message = "Napster send/recieve failed";
	    LogManager.instance().error(message);
	    throw e;
	} /* end of try-catch */

	NapsterSession napsterSession = null;
	
	if (napsterPacket.getMessageType() != 0) {
	    /*
	     * TODO: Add a state here
	     */
	    // napsterSession = new NapsterSession(napsterProfile, socket);
	} else {
	    LogManager.instance().error("Could not log in: " + napsterPacket.getMessage());
	    throw new NapsterException(napsterPacket.getMessage());
	}
	
	LogManager.instance().info("Logged into " + server + ":" + port);
	
	do {
	    try {
		napsterPacket = NapsterTransport.recieve(socketInputStream);	    
	    } catch (IOException e) {
		String message = "Napster recieve failed";
		LogManager.instance().error(message);
		throw new NapsterException(message);
	    } /* end of try-catch */
	} while (napsterPacket.getMessageType() == NapsterPacket.SYSMSG);
	
	LogManager.instance().info("Napster Session Created");	
    } /* end of login() */    
    
    /**
     * Retrieves the specified song
     */
    public void downloadSong(NapsterSong napsterSong) {	
	LogManager.instance().info("Getting Song " + napsterSong.getTitle());

	NapsterTransfer napsterTransfer = new NapsterTransfer(napsterProfile, 
							      napsterSong, socket);
	/*
	 * Add the transfer to the session queue
	 */ 
	napsterTransfers.addElement(napsterTransfer);
	/*
	 * Start the download thread
	 */
	Thread thread = new Thread(napsterTransfer);
	thread.start();
    } /* end of downloadSong() */

    /**
     * Retrieves the song list of the specified user
     */
    public Vector getSongList(String user) throws NapsterException {
	LogManager.instance().info("Getting Song List from " + user);
	String message = user;
	NapsterPacket napsterPacket = null;

	try {
	    napsterPacket = 
		new NapsterPacket(NapsterPacket.SONGLIST, message);
	    NapsterTransport.send(napsterPacket, socketOutputStream);
	    napsterPacket = NapsterTransport.recieve(socketInputStream);
	} catch (NapsterException e) {
	    LogManager.instance().error("Could not send/recieve packet");
	    throw e;
	} catch (IOException e) {
	    LogManager.instance().error("Could not send/recieve packet");
	    throw new NapsterException("Could not send/recieve packet");
	} /* end of try-catch */
	
	Vector songs = new Vector();
	
	if (napsterPacket.getMessageType() == NapsterPacket.EMPTYLIST
	    || napsterPacket.getMessageType() == NapsterPacket.INVALIDLIST) {
	    LogManager.instance().info(user + " has empty song list");

	    return null;
	} /* end of if */

	while (napsterPacket.getMessageType() == NapsterPacket.SONGLISTRESP) {
	    /*
	     * Napster Packet contains song information
	     */
	    String songinfo = napsterPacket.getMessage();
	    songinfo = songinfo.trim();
	    
	    if (songinfo.length() > 0) {
		try {
		    NapsterSong napsterSong = new NapsterSong(songinfo,
							      false);
		    songs.addElement(napsterSong);
		} catch (NapsterException e) {
		    LogManager.instance().warn("Error creating Napster Song" 
					       + e);		    
		} /* end of try-catch */
	    } /* end of if */

	    try {
		napsterPacket = NapsterTransport.recieve(socketInputStream);
	    } catch (NapsterException e) {
		LogManager.instance().error("Could not send/recieve packet");
		throw new NapsterException("Could not send/recieve packet");
	    } catch (IOException e) {
		LogManager.instance().error("Could not send/recieve packet");
		throw new NapsterException("Could not send/recieve packet");
	    } /* end of try-catch */
	} /* end of while */ 
	
	return songs;
    } /* end of getSongList() */
    
    public void sendSongList(Vector songs) throws NapsterException {	

	String message;
	
	for (int i = 0; i < songs.size(); i++) {
	    NapsterSong napsterSong = (NapsterSong) songs.elementAt(i);
	    message = napsterSong.getTitle() 
		+ " " + napsterSong.getChecksum()
		+ " " + napsterSong.getSize()
		+ " " + napsterSong.getBitRate()
		+ " " + napsterSong.getFrequency()
		+ " " + napsterSong.getTime();
	    NapsterPacket napsterPacket = null;

	    try {
		napsterPacket = new NapsterPacket(NapsterPacket.SONGREG, 
						  message);
		NapsterTransport.send(napsterPacket, socketOutputStream);
	    } catch (IOException e) {
		LogManager.instance().error("Could not send/recieve packet");
		throw new NapsterException("Could not send/recieve packet");
	    } /* end of try-catch */
	} /* end of for */
    } /* end of sendSongList() */
    
    /**
     * Returns list of songs with matching artist and title
     */
    public Vector findSongs(String title) throws NapsterException {
	LogManager.instance().info("Finding Songs containing " + title);

	Vector songs = new Vector();

	String message = "FILENAME CONTAINS \"" 
	    + title + "\" MAX_RESULTS 100";
	
	
	NapsterPacket napsterPacket = null;
	/*
	 * Send search Napster Packet
	 */
	try {
	    napsterPacket = new NapsterPacket(NapsterPacket.SEARCH, message);
	    NapsterTransport.send(napsterPacket, socketOutputStream);
	} catch (IOException e) {
	    LogManager.instance().error("Could not send packet");
	    //throw new NapsterException("Could not send/recieve packet");
	} /* end of try-catch */
	
	boolean search_complete = false;

	do {
	    try {
		napsterPacket = NapsterTransport.recieve(socketInputStream);
	    } catch (IOException e) {
		LogManager.instance().error("Could not recieve");
		continue;
	    } /* end of try-catch */

	    switch (napsterPacket.getMessageType()) {
		/*
		 * If message type of recieved Napster packet is 
		 * SEARCHCOMPLETE, all song names have been recieved.
		 */
	    case NapsterPacket.SEARCHCOMPLETE:
		search_complete = true;
		break;
	    case NapsterPacket.SEARCHRESULT:
		/*
		 * Napster Packet contains song information
		 */
		String songinfo = napsterPacket.getMessage();
		songinfo = songinfo.trim();
		
		if (songinfo.length() > 0) {
		    try {
			songs.addElement(new NapsterSong(songinfo));
		    } catch (NapsterException e) {
			LogManager.instance().warn("Corrupt Napster Song");
		    } /* end of try-catch */
		} /* end of if */
		
		break;
	    default:
		break;
	    } /* end of switch */
	    
	} while (!search_complete);

	return songs;
    } /* end of findSongs() */    
 
    /**
     * Returns Napster Transfers in the session
     */
    public Vector getTransfers() {
	return napsterTransfers;
    } /* end of getTransfers() */

    /**
     * Logout from the session
     */
    public void logout() throws NapsterException {
	try {
	    if (socket != null) {
		socket.close();
	    } /* end of if */
	    
	    if (socketInputStream != null) {
		socketInputStream.close();
	    } /* end of if */
	    
	    if (socketOutputStream != null) {
		socketOutputStream.close();
	    } /* end of if */	
	} catch (IOException e) {
	    String errMessage = "Could not log out";
	    LogManager.instance().error(errMessage);
	    throw new NapsterException(errMessage);
	} /* end of try-catch */
    } /* end of logout() */
} /* end of NapsterSession */
