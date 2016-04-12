

/**
 * Título:
 * Descripcion:
 * Copyright:    Copyright (c) 2001
 * Empresa:
 * @author
 * @version 1.0
 */

// Imports
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

class CustomDataTableExample
		extends 	JFrame
 {
	// Instance attributes used in this example
	private	JPanel		topPanel;
	private	JTable		table;
	private	JScrollPane scrollPane;

	private	String		columnNames[];
	private	String		dataValues[][];
  JTable jTable1 = new JTable();


	// Constructor of main frame
	public CustomDataTableExample()
	{
		// Set the frame characteristics
		setTitle( "Custom Table Data Model Application" );
		setSize( 300, 200 );
		setBackground( Color.gray );

		// Create a panel to hold all other components
		topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );

		// Create the custom data model
		CustomDataModel customDataModel = new CustomDataModel();

		// Create a new table instance
		table = new JTable( customDataModel ); //dataValues, columnNames );

		// Create columns
		CreateColumns();

		// Configure some of JTable's paramters
		table.setShowHorizontalLines( false );
		table.setRowSelectionAllowed( true );
		table.setColumnSelectionAllowed( true );

		// Change the selection colour
		table.setSelectionForeground( Color.white );
		table.setSelectionBackground( Color.red );

		// Add the table to a scrolling pane
		scrollPane = new JScrollPane( table );
		topPanel.add( scrollPane, BorderLayout.CENTER );
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
}

	public void CreateColumns()
	{
		// Say that we are manually creating the columns
		table.setAutoCreateColumnsFromModel( false );

		for( int iCtr = 0; iCtr < 8; iCtr++ )
		{
			// Manually create a new column
			TableColumn column = new TableColumn( iCtr );
			column.setHeaderValue( (Object)("Col:" + iCtr) );

			// Add the column to the table
			table.addColumn( column );
		}
	}

	// Main entry point for this example
	public static void main( String args[] )
	{
		// Create an instance of the test application
		CustomDataTableExample mainFrame	= new CustomDataTableExample();
		mainFrame.setVisible( true );
	}
  private void jbInit() throws Exception {
    this.getContentPane().add(jTable1, BorderLayout.SOUTH);
  }
}

//The full code listing for the CustomDataModel.java is as follows:


// Imports

class CustomDataModel
			extends		AbstractTableModel
{

	public Object getValueAt( int iRowIndex, int iColumnIndex )
	{
		return "" + iColumnIndex + "," + iRowIndex;
	}

	public void setValueAt( Object aValue, int iRowIndex, int iColumnIndex )
	{
		// All data is manufactured - nothing to do here
	}

	public int getColumnCount()
	{
		// Return 0 because we handle our own columns
		return 0;
	}

	public int getRowCount()
	{
		return 500;
	}

}
