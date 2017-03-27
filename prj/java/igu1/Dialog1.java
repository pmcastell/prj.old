
//Title:        Your Product Name
//Version:      
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description



import java.awt.*;
import java.awt.event.*;

public class Dialog1 extends Dialog {
   Panel panel1 = new Panel();
   BorderLayout borderLayout1 = new BorderLayout();

   
   public Dialog1(Frame frame, String title, boolean modal) {
      super(frame, title, modal);
      enableEvents(AWTEvent.WINDOW_EVENT_MASK);
      try  {
         jbInit();
         add(panel1);
         pack();
      }
      catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   
   public Dialog1(Frame frame) {
      this(frame, "", false);
   }

   
   public Dialog1(Frame frame, boolean modal) {
      this(frame, "", modal);
   }

   
   public Dialog1(Frame frame, String title) {
      this(frame, title, false);
   }

   void jbInit() throws Exception {
      panel1.setLayout(borderLayout1);
   }

   protected void processWindowEvent(WindowEvent e) {
      if (e.getID() == WindowEvent.WINDOW_CLOSING) {
         cancel();
      }
      super.processWindowEvent(e);
   }

   void cancel() {
      dispose();
   }
}

 
