/* 
 * NapsterTransfer.java
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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.StringTokenizer;

import jnapster.logging.LogManager;

/**
 * NapsterTransfer.java
 *
 * Represents an MP3 file transfer
 *
 * Created: Thu Dec 09 15:36:01 1999
 *
 * @author  Harikrishnan Varma
 * @version 0.1
 */
public class NapsterTransfer implements Runnable {
    /**
     * Profile of user involved in the transfer
     */
    NapsterProfile napsterProfile;
    
    /**
     * Song being transferred
     */
    NapsterSong napsterSong;

    /**
     * State of the transfer
     */
    String      state;
    
    /**
     * Socket used for the transfer
     */
    private Socket      socket;

    /**
     * OutputStream used for the transfer
     */

    private OutputStream dataOutputStream;

    /**
     * InputStream used for the transfer
     */
    private InputStream dataInputStream;
    
    /**
     * FileOutputStream used for writing the file locally
     */
    private FileOutputStream fileOutputStream;

    /**
     * Transfer rate
     */
    float       transfer_rate;

    /**
     * Time of last transfer rate calculation
     */
    long        prev_read_time;

    /**
     * Size of the file being transferred
     */
    long        file_size;

    /**
     * Bytes transferred till now
     */
    long        bytes_transferred;

    /**
     * Constructs a NapsterTransfer object
     */
    NapsterTransfer(NapsterProfile napsterProfile, NapsterSong napsterSong, Socket socket) {
	this.napsterSong = napsterSong;
	this.napsterProfile = napsterProfile;
	this.socket = socket;
    } /* end of NapsterTransfer () */

    /**
     * Starts a transfer
     */
    public void start() {
	try {
	    start(0);
	} catch (Exception e) {
	    LogManager.instance().info(e.toString());
	} /* end of try-catch */
    } /* end of start() */

    /**
     * Starts a transfer from the specified file offset
     */    
    public void start(long offset) throws NapsterException {
	LogManager.instance().info("Downloading" 
				  + napsterSong.getTitle() 
				  + " from " + napsterSong.getHost());
	
	/*
	 * Send file info request using host (username) and title
	 */
	String message = napsterSong.getHost() + " " + napsterSong.getTitle();
	LogManager.instance().info("Sending File Info Request: " + message);
	state = "Getting File Info";
	NapsterPacket napsterPacket = new NapsterPacket(NapsterPacket.FILEINFOREQ, message);    

	OutputStream outputStream = null;
	InputStream inputStream = null;
	try {
	    outputStream = socket.getOutputStream();
	    inputStream = socket.getInputStream();
	} catch (IOException e) {
	    String errMessage = "Error getting socket stream";
	    LogManager.instance().error(errMessage);
	    throw new NapsterException(errMessage);		
	} /* end of try-catch */

	try {
	    NapsterTransport.send(napsterPacket, outputStream);
	    napsterPacket = NapsterTransport.recieve(inputStream);	
	} catch (IOException e) {
	    String errMessage = "Error send/recieve Napster Packet";
	    LogManager.instance().error(errMessage);
	    throw new NapsterException(errMessage);	    	    
	} /* end of try-catch */
	
	/*
	 * FILECOUNT packets keep coming in between, giving updates of 
	 * songs, libraries, size etc
	 * Skip over all such spurious packets
	 */
	while (napsterPacket.getMessageType() != NapsterPacket.FILEINFO) {
	    try {
	    napsterPacket = NapsterTransport.recieve(inputStream);
	    } catch (IOException e) {

	    } /* end of try-catch */
	} /* end of while */
	
	/*
	 * Recieved response to the file info request
	 */
	String info = napsterPacket.getMessage();
	LogManager.instance().info("Recieved File Info: " + info);
	
	StringTokenizer stringtokenizer = new StringTokenizer(info, " ");

	if (stringtokenizer.countTokens() < 2) {
	    throw new NapsterException("Invalid Song Entry");
	} /* end of if */

	String token = stringtokenizer.nextToken();
	token = stringtokenizer.nextToken();
	
	/*
	 * Get the IP address in String format from unsigned 32 bit int
	 */
	String host = NapsterUtility.uintToIP((int)Long.parseLong(token));
	token = stringtokenizer.nextToken();
	int port   = Integer.parseInt(token);

	LogManager.instance().info("Connecting to " + host + ":" + port);
	state = "Connecting";
	
	try {
	    socket = new Socket(host, port);
	} catch (IOException e) {
	    String errMessage = "Could not connect to " + host;
	    LogManager.instance().error(errMessage);
	    throw new NapsterException(errMessage);
	} /* end of try-catch */

	try {
	    dataOutputStream = socket.getOutputStream();
	    dataInputStream  = socket.getInputStream();
	} catch (IOException e) {
	    String errMessage = "Error getting socket stream";
	    LogManager.instance().error(errMessage);
	    throw new NapsterException(errMessage);
	} /* end of try-catch */
	
	/*
	 * Read Magic number '1'
	 */
	char c; 
	try {
	    c = (char) dataInputStream.read();
	} catch (IOException e) {
	    String errMessage = "Did not recieve magic number";
	    LogManager.instance().error(errMessage);
	    throw new NapsterException(errMessage);	    
	} /* end of try-catch */

	LogManager.instance().info("Recieved magic number " + c);

	state = "Requesting File";

	/*
	 * GET request needs to be split over 2 packets
	 */
	message = "GET";
	LogManager.instance().info("Sending request: " + message);
	try {
	    dataOutputStream.write(message.getBytes());
	    dataOutputStream.flush();
	} catch (IOException e) {
	    String errMessage = "Could not write " + message;
	    LogManager.instance().error(errMessage);
	    throw new NapsterException(errMessage);	    	    
	} /* end of try-catch */
	
	/*
	 * Here comes the song requested
	 */
	message = napsterProfile.getUser() + " " 
	    + napsterSong.getTitle() + " " +offset;
	LogManager.instance().info("Sending request: " + message);

	try {
	dataOutputStream.write(message.getBytes());
	dataOutputStream.flush();
	} catch (IOException e) {
	    String errMessage = "Could not write " + message;
	    LogManager.instance().error(errMessage);
	    throw new NapsterException(errMessage);	    	    
	} /* end of try-catch */
	
	/*
	 * Data buffer needs to be greater than MTU
	 */
	byte[] data = new byte[NapsterPacket.MTU];	
	String file_len = null;	
	
	/*
	 * Read until we get 255 which is the first byte of the MP3.
	 * Bytes in between contain the size of the MP3 file
	 */
	int b = 0;

	for (int i = 0; i < NapsterPacket.MTU; i++) {
	    try {
		b = dataInputStream.read();
	    } catch (SocketException e) {
		String message1 = "Error reading from socket";
		LogManager.instance().error(message1);
		throw new NapsterException(message1);
	    } catch (IOException e) {
		String message2 = "Error reading from socket";
		LogManager.instance().error(message2);
		throw new NapsterException(message2);
	    } /* end of try-catch */

	    if (b == 255) {
		file_len = new String(data, 0, i);
		break;
	    } /* end of if */
	    data[i] = (byte) b;		
	} /* end of for */
	
	if (file_len == null) {
	    LogManager.instance().error("Could not read File Size");
	    throw new NapsterException("Could not read File Size");
	} /* end of if */
	
	file_size = Long.parseLong(file_len);
	LogManager.instance().info("Recieved File Size " + file_size);

	LogManager.instance().info("Reading File Data ");
	
	state = "Downloading";
	try {
	    fileOutputStream = 
		new FileOutputStream(napsterSong.getFilename());	    
	    /*
	     * The first byte (255) has already been read
	     */	
	    fileOutputStream.write((byte)255);
	} catch (IOException e) {
	    String errMessage = "Could not write file " 
		+ napsterSong.getFilename();
	    LogManager.instance().error(errMessage);
	    throw new NapsterException(errMessage);	    	    
	} /* end of try-catch */
	
	bytes_transferred = 1;		
	int bytes_read = 0;	
	
	do {
	    try {
		bytes_read = dataInputStream.read(data);

		if (bytes_read == -1) {
		    break;
		} /* end of if */

		bytes_transferred += bytes_read;	    
	    } catch (SocketException e) {
		if (bytes_transferred < file_size) {
		    LogManager.instance().error("Socket Reset by Peer");
		    break;
		} /* end of if */
	    } catch (IOException e) {
		if (bytes_transferred < file_size) {
		    LogManager.instance().error("Socket Reset by Peer");
		    break;
		} /* end of if */
	    } /* end of try-catch */
	    
	    try {
		fileOutputStream.write(data, 0, bytes_read);	    
	    } catch (IOException e) {
		String errMessage = "Could not write data to file " 
		    + napsterSong.getFilename();
		LogManager.instance().error(errMessage);
		throw new NapsterException(errMessage);	    	    	
	    } /* end of try-catch */

	    
	    LogManager.instance().info(".");
	    
	    calculateTransferRate(bytes_read);
	    	    
	} while (bytes_transferred < file_size);
	
	try {
	    dataInputStream.close();
	} catch (IOException e) {
	    String errMessage = "Could not close socket "; 

	    LogManager.instance().error(errMessage);
	    throw new NapsterException(errMessage);	    	    	    
	} /* end of try-catch */

	try {
	    fileOutputStream.flush();
	    fileOutputStream.close();
	} catch (IOException e) {
	    String errMessage = "Could not close file"; 
	    
	    LogManager.instance().error(errMessage);
	    throw new NapsterException(errMessage);	    	    	    
	} /* end of try-catch */
	LogManager.instance().info("Finished Reading File Data ");
	state = "File Complete";
    } /* end of start() */

    /**
     * Calculates the file transfer rate
     */
    public void calculateTransferRate(int bytes_read) {
	long read_time = System.currentTimeMillis();
	long delta_time = (read_time - prev_read_time) / 1000;
	prev_read_time = read_time;
	
	if (delta_time != 0) {
	    transfer_rate = bytes_read / delta_time;
	} /* end of if */
    } /* end of calculateTransferRate() */
    
    /**
     * Stop the file transfer
     */
    public void stop() {
    
    } /* end of stop() */

    /**
     * Resumes the file transfer
     */
    public void resume() throws Exception {
	if (state.equalsIgnoreCase("File Complete")) {
	    LogManager.instance().error("Download Complete"); 
	    throw new NapsterException("Download Complete");
	} /* end of if */
	start(bytes_transferred - NapsterPacket.MTU);
    } /* end of resume() */

    /**
     * Get the current state of the file transfer
     */
    public String getState() {
	return state;
    } /* end of getState() */

    /**
     * Method for Runnable interface
     */
    public void run() {
	start();
    } /* end of run() */

    /**
     * Returns a string representation of the object
     */
    public String toString() {
	StringWriter stringwriter = new StringWriter();
	PrintWriter printwriter = new PrintWriter(stringwriter);
	
	printwriter.println(getClass().getName());
	printwriter.println(napsterSong);
	printwriter.println("Filesize          : " + file_size);
	printwriter.println("Bytes Transferred : " + bytes_transferred);
	printwriter.println("State             : " + state);
	printwriter.println("Transfer Rate     : " + transfer_rate);

	return stringwriter.toString();	
    } /* end of toString() */
    
} /* end of NapsterTransfer */
