/* 
 * NapsterPacket.java
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

import java.io.FileOutputStream;
import java.io.StringWriter;
import java.io.PrintWriter;

/**
 * NapsterPacket.java
 *
 * Represents a Napster Packet.
 * The packet has the following format: 
 *
 *  ------------------
 *  1 Message Length 1
 *  ------------------
 *  1 Message Type   1
 *  ------------------
 *  1                1
 *  1    Message     1
 *  1................1
 *  
 * Message Length and Message Type are both 2 bytes. 
 * Message occupies Message Length number of bytes
 *  
 * Created: Thu Dec 09 13:35:44 1999
 *
 * @author  Harikrishnan Varma
 * @version 0.1
 */
public class NapsterPacket {
    /**
     * Constant for Login Request Message Type
     */
    public final static int LOGIN          = 2;    
    
    /**
     * Constant for Login Response Message Type
     */
    public final static int LOGINRESP      = 3;    

    /**
     * Constant for Search Songs Message Type
     */
    public final static int SEARCH         = 200;    

     /**
     * Constant for System Message Message Type
     */
    public final static int SYSMSG         = 621;

    /**
     * Constant for Status Update Message Type
     */
    public final static int FILECOUNT      = 214;
    
    /**
     * Constant for Search Song Result Message Type
     */
    public final static int SEARCHRESULT   = 201;

    /**
     * Constant for Search Song Complete Message Type
     */
    public final static int SEARCHCOMPLETE = 202;
    
    /**
     * Constant for File Info Request Message Type
     */
    public final static int FILEINFOREQ    = 203;
    
    /**
     * Constant for File Info Message Type
     */
    public final static int FILEINFO       = 204;

    /**
     * Constant for Songs Listing from user
     */
    public final static int SONGLIST       = 211;

    /**
     * Constant for Songs Listing response
     */
    public final static int SONGLISTRESP   = 212;

    /**
     * Constant for Songs Listing from user
     */
    public final static int EMPTYLIST      = 210;

    /**
     * Constant for Songs Listing response
     */
    public final static int INVALIDLIST    = 213;

    /**
     * Constant for song registration 
     */
    public final static int SONGREG        = 100;

    /**
     * Constant for packet size greater than MTU 
     */
    public final static int MTU            = 2048;

    

    /**
     * Message Length
     */
    int        msg_len;

    /**
     * Message Type
     */
    int        msg_type;

    /**
     * Message 
     */
    String     msg;

    /**
     * Constructs a NapsterPacket
     */
    NapsterPacket(int msg_type, String msg) {
	this.msg_len = (short) msg.length();
	this.msg_type = (short) msg_type;
	this.msg     = msg;
    } /* end of NapsterPacket() */

    /**
     * Returns the NapsterPacket as a byte array
     */
    public byte[] toByteArray() {
	
	/*
	 * Size of data is (size of short) + (size of short) + (size of msg)
	 *                  Message Length    Message Type       Message
	 */
	byte[] data = new byte[2 + 2 + msg.length()];

	data[0]     = (byte) msg_len;
	data[1]     = (byte) (msg_len >> 8);
	data[2]     = (byte) msg_type;
	data[3]     = (byte) (msg_type >> 8);

	System.arraycopy(msg.getBytes(), 0, data, 4, msg.length());	

	return data;
    } /* end of toByteArray() */

    /**
     * Returns the string representation of the Napster Packet
     */
    public String toString() {
	StringWriter stringwriter = new StringWriter();
	PrintWriter printwriter = new PrintWriter(stringwriter);

	printwriter.println(getClass().getName());
	printwriter.println("Message Length: " + msg_len);
	printwriter.println("Message Type  : " + msg_type);
	printwriter.println("Message       : " + msg);

	return stringwriter.toString();	
    } /* end of toString() */
    
    /**
     * Returns Message Length
     */
    public int getMessageLength() {
	return msg_len;
    } /* end of getMessageLength() */

    /**
     * Returns Message 
     */    
    public String getMessage() {
	return msg;
    } /* end of getMessage() */

    /**
     * Returns Message Type
     */
    public int getMessageType() {
	return msg_type;
    } /* end of getMessageType() */
} /* end of NapsterPacket */


