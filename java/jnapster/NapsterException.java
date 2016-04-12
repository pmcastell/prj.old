/* 
 * NapsterException.java
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
 * NapsterException.java
 *
 * Signals that an exception related to Napster operation has occurred
 * Created: Thu Dec 09 14:20:39 1999
 *
 * @author  Harikrishnan Varma
 * @version 0.1
 */
public class NapsterException extends Exception {
    /**
     * Constructs a NapsterException object with the specified detail message
     */
    public NapsterException(String msg) {
	super(msg);
    } /* end of NapsterException() */
} /* end of NapsterException */
