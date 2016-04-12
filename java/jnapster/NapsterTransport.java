/* 
 * NapsterTransport.java
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

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import jnapster.logging.LogManager;

/**
 * NapsterTransport.java
 *
 * Represents the transport mechanism used to deliver the Napster Packets
 *
 * Created: Thu Dec 09 14:36:29 1999
 *
 * @author  Harikrishnan Varma
 * @version 0.1
 */
public class NapsterTransport {
    
    /**
     * Sends the specified Napster Packet
     */
    public static void send(NapsterPacket napsterPacket, 
			    OutputStream outputStream) throws IOException {
	byte[] data = napsterPacket.toByteArray();
	outputStream.write(data);
	LogManager.instance().info("Sending " + napsterPacket);
	outputStream.flush();
    } /* end of send() */

    /**
     * Recieves a Napster Packet
     */    
    public static NapsterPacket recieve(InputStream inputStream) 
	throws IOException, NapsterException {
	byte[] data = new byte[2];
	inputStream.read(data);	
	int msg_len = NapsterUtility.bytesToShort(data);
	inputStream.read(data);	
	int msg_num = NapsterUtility.bytesToShort(data);

	if (msg_len <= 0) {
	    NapsterPacket napsterPacket = new NapsterPacket(msg_num, "");
	    LogManager.instance().info("Recieved " + napsterPacket);	
	    return napsterPacket;
	} /* end of if */

	data = new byte[msg_len];
	int packet_len = inputStream.read(data);	


	
	
	/*
	 * While bytes recieved is less than size Message Length
	 * keep reading from the socket input stream
	 */
	while (packet_len < msg_len) {
	    LogManager.instance().warn("Insufficient Data Recieved: Expected " 
				       + msg_len + " got " + packet_len);	    
	    byte[] buffer = new byte[msg_len - packet_len];
	    int len = inputStream.read(buffer);
	    System.arraycopy(buffer, 0, data, packet_len, len);
	    packet_len += len;
	} /* end of while */

	NapsterPacket napsterPacket = new NapsterPacket(msg_num, new String(data));	
	LogManager.instance().info("Recieved " + napsterPacket);
	
	return napsterPacket;
    } /* end of recieve() */
} /* end of NapsterTransport */
