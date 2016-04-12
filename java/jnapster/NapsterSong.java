/* 
 * NapsterSong.java
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

import java.util.StringTokenizer;
import java.io.PrintWriter;
import java.io.StringWriter;
import jnapster.logging.LogManager;

/**
 * NapsterSong.java
 *
 * Represents an MP3 in the Napster Music Community
 *
 * Created: Thu Dec 09 15:22:59 1999
 *
 * @author  Harikrishnan Varma
 * @version 0.1
 */
public class NapsterSong {
    
    /**
     * Owner of the song
     */
    String user;
    /**
     * Title of the song
     */
    String title;

    /**
     * Filename of the song
     */
    public String filename;    

    /**
     * Checksum
     */
    String checksum;

    /**
     * Size in bytes of the MP3
     */
    long   size;

    /**
     * Bitrate
     */
    int    bitrate;

    /**
     * Frequency of the song
     */
    int    frequency;

    /**
     * Time duration 
     */
    int    time;

    /**
     * Host
     */
    String host;


    String unknown;

    /**
     * Connection speed to host
     */
    int    linespeed;

    /**
     * Constructs a NapsterSong object
     */
    public NapsterSong(String info) throws NapsterException{
	int index = info.lastIndexOf("\"");

	if (index == -1) {
	    LogManager.instance().error("Invalid Song Info"  + info);
	    throw new NapsterException("Invalid Song Info");
	} /* end of if */

	title = info.substring(0, index + 1);
	
	int in = title.lastIndexOf("\\");

	if (in == -1) {
	    in = title.lastIndexOf("/");
	} /* end of if */
	
	if (in == -1) {
	    filename = title;
	} else {	
	    filename = title.substring(in + 1, title.length() - 1);
	} /* end of if */
	
	info = info.substring(index + 2);

	StringTokenizer stringtokenizer = new StringTokenizer(info, " ");

	if (stringtokenizer.countTokens() < 8) {
	    LogManager.instance().error("Invalid Song Entry");
	    throw new NapsterException("Invalid Song Entry");
	} /* end of if */

	String token;

	for (int i = 0; i < 8; i++) {
	    token = stringtokenizer.nextToken();
	    
	    switch (i) {
	    case 0:
		checksum = token;
		break;
	    case 1:
		size = Long.parseLong(token);
		break;
	    case 2:
		bitrate = Integer.parseInt(token);
		break;
	    case 3:
		frequency = Integer.parseInt(token);
		break;
	    case 4:
		time = Integer.parseInt(token);
		break;
	    case 5:
		host = token;
		break;
	    case 6:
		unknown = token;
		break;
	    case 7:
		linespeed = Integer.parseInt(token);
		break;
	    default:
		break;		
	    } /* end of switch */
	} /* end of for */
    } /* end of NapsterSong() */




    /**
     * Constructs a NapsterSong object
     */
    public NapsterSong(String info, boolean flag) throws NapsterException{
	int index1 = info.indexOf("\"");
	int index2 = info.lastIndexOf("\"");
	
	title = info.substring(index1, index2 + 1);


	int index = info.lastIndexOf("\"");

	if (index == -1) {
	    LogManager.instance().error("Invalid Song Info"  + info);
	    throw new NapsterException("Invalid Song Info");
	} /* end of if */
	
	int in = title.lastIndexOf("\\");

	if (in == -1) {
	    in = title.lastIndexOf("/");
	} /* end of if */
	
	if (in == -1) {
	    filename = title;
	} else {	
	    filename = title.substring(in + 1, title.length() - 1);
	} /* end of if */

	String front = info.substring(0, index1 - 1);
	String back  = info.substring(index2 + 1);

	info = front + back;

	StringTokenizer stringtokenizer = new StringTokenizer(info, " ");

	if (stringtokenizer.countTokens() < 6) {
	    LogManager.instance().error("Invalid Song Entry");
	    throw new NapsterException("Invalid Song Entry");
	} /* end of if */

	String token;

	for (int i = 0; i < 6; i++) {
	    token = stringtokenizer.nextToken();
	    
	    switch (i) {
	    case 0:
		user = token;
		host = token;
		break;
	    case 1:
		checksum = token;
		break;
	    case 2:
		size = Integer.parseInt(token);
		break;
	    case 3:
		bitrate = Integer.parseInt(token);
		break;
	    case 4:
		frequency = Integer.parseInt(token);
		break;
	    case 5:
		time = Integer.parseInt(token);
		break;
	    default:
		break;		
	    } /* end of switch */
	} /* end of for */
    } /* end of NapsterSong() */


    /**
     * Returns filename 
     */
    public String getFilename() {
	return filename;
    } /* end of getFilename() */
    
    /**
     * Returns title
     */
    public String getTitle() {
	return title;
    } /* end of getTitle() */

    /**
     * Returns checksum
     */
    public String getChecksum() {
	return checksum;
    } /* end of getChecksum() */

    /**
     * Returns size
     */
    public long getSize() {
	return size;
    } /* end of getSize() */

    /**
     * Returns bitrate
     */
    public long getBitRate() {
	return bitrate;
    } /* end of getBitRate() */

    /**
     * Returns frequency
     */
    public int getFrequency() {
	return frequency;
    } /* end of getFrequency() */

    /**
     * Returns time
     */
    public int getTime() {
	return time;
    } /* end of getTime() */

    /**
     * Returns host
     */
    public String getHost() {
	return host;
    } /* end of getHost() */

    /**
     * Returns linespeed
     */
    public int getLineSpeed() {
	return linespeed;
    } /* end of getLineSpeed() */

    /**
     * Returns string representation of Napster Song
     */
    public String toString() {
	StringWriter stringwriter = new StringWriter();
	PrintWriter printwriter = new PrintWriter(stringwriter);

	printwriter.println(getClass().getName());
	printwriter.println("Title    : " + title);
	printwriter.println("Filename : " + filename);
	printwriter.println("Checksum : " + checksum);
	printwriter.println("Size     : " + size);
	printwriter.println("Bitrate  : " + bitrate);
	printwriter.println("Frequency: " + frequency);
	printwriter.println("Time     : " + time);
	printwriter.println("Host     : " + host);
	printwriter.println("LineSpeed: " + linespeed);

	return stringwriter.toString();	
    } /* end of toString() */
} /* end of NapsterSong */

