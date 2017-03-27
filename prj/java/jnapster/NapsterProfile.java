/* 
 * NapsterProfile.java
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


/**
 * NapsterProfile.java
 *
 * Contains information about user connected to Napster Server
 *
 * Created: Thu Dec 09 14:25:02 1999
 *
 * @author  Harikrishnan Varma
 * @version 0.1
 */
public class NapsterProfile {
    /**
     * User ID
     */
    String user;

    /**
     * Password of user
     */
    String password;
    
    /**
     * Version of client used by user
     */
    String version = "\"v2.0 BETA 3\"";

    /**
     * Connection speed
     */
    String linespeed = "10";

    /**
     * Data port of connection used for upload
     */
    int    dataport  = 6699;
    
    /**
     * Constructs a NapsterProfile object with default values for version,
     * linespeed and dataport
     */
    public NapsterProfile(String user, String password) {
	this.user      = user;
	this.password  = password;
    } /* end of NapsterProfile() */
    
    /**
     * Constructs a NapsterProfile object
     */
    public NapsterProfile(String user, String password, String version, 
			  String linespeed, int dataport) {
	this.user      = user;
	this.password  = password;
	this.version   = version;
	this.linespeed = linespeed;
	this.dataport  = dataport;
    } /* end of NapsterProfile() */

    /**
     * Returns user
     */
    public String getUser() {
	return user; 
    } /* end of getUser() */

    /**
     * Returns password
     */
    public String getPassword() {
	return password;
    } /* end of getPassword() */

    
    /**
     * Returns version
     */    
    public String getVersion() {
	return version;
    } /* end of getVersion() */

    /**
     * Returns Connection speed
     */    
    public String getLineSpeed() {
	return linespeed;
    } /* end of getLineSpeed() */
    
    /**
     * Returns data port
     */
    public int getDataPort() {
	return dataport;
    } /* end of getDataPort() */
    
    /**
     * Returns string representation of Napster Profile
     */
    public String toString() {
	return user + " " + password + " " 
	    + dataport + " " + version + " " + linespeed;
    } /* end of toString() */
} /* end of NapsterProfile */
