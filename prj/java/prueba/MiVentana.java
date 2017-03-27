import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

/**
 * 
 */

/**
 * @author usuario
 *
 */
public class MiVentana extends JFrame {

	/**
	 * @throws HeadlessException
	 */
	public MiVentana() throws HeadlessException {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public MiVentana(GraphicsConfiguration arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public MiVentana(String arg0) throws HeadlessException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MiVentana(String arg0, GraphicsConfiguration arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MiVentana mv=new MiVentana();
		mv.setVisible(true);
		

	}

}
