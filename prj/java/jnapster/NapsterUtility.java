/* 
 * NapsterUtility.java
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

public class NapsterUtility {

    /**
     * Returns Hex String representation of specified byte array
     */
    public static final String bytesToHex (byte[] a) {
        StringBuffer s = new StringBuffer ();

        for (int i = 0; i < a.length; ++ i)  {
            s.append (Character.forDigit ((a[i] >> 4) & 0xf, 16));
            s.append (Character.forDigit (a[i] & 0xf, 16));
        } /* end of for */
        return s.toString ();
    } /* end of bytesToHex() */
    
    /**
     * Returns Hex String representation of specified byte array
     */
    public static final String bytesToHex (byte[] a, int offset, int count) {
        StringBuffer s = new StringBuffer ();

        for (int i = offset; i < count; ++ i)  {
            s.append (Character.forDigit ((a[i] >> 4) & 0xf, 16));
            s.append (Character.forDigit (a[i] & 0xf, 16));
        } /* end of for */
        return s.toString ();
    } /* end of bytesToHex() */

    /**
     * Returns the specified byte array as a short
     */
    public static final short bytesToShort(byte[] num) {
	short value = 0;
	value |= (short)(0x00FF & num[0]);
	value |= (short)(0xFFFF & num[1] << 8);

	return value;
    } /* end of bytesToShort() */

    /**
     * Returns the specified int in XX.XX.XX.XX format
     */
    public static String uintToIP(int ip) {	
	StringBuffer s = new StringBuffer();
	
	s.append(String.valueOf(ip & 0xFF) + ".");
	s.append(String.valueOf((ip >> 8)  & 0xFF) + ".");
	s.append(String.valueOf((ip >> 16) & 0xFF) + ".");
	s.append(String.valueOf((ip >> 24) & 0xFF));

	return s.toString();
    } /* end of uintToIP() */
} /* end of NapsterUtility */
