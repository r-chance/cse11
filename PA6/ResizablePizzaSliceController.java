/* Name:  Robert Chance
 * Login: cs11faay
 * Date:  Oct 22, 2015
 * File:  ResizablePizzaSliceController.java
 * Sources of Help:  Java: an Eventful Approach. CS lab tutoring staff.
 *
 * This program provides an interface for the user, as well as allowing the
 * user to interact with the interface.  Objects can be created with the 
 * controller, but they have no references to be controlled after they are 
 * created. 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Color;
import objectdraw.*;
/* This class is the controller class for the program. It allows the user to 
 * generate animated objects on a canvas.
 */

public class ResizablePizzaSliceController extends WindowController
                                           implements MouseListener,
                                                      MouseMotionListener,
                                                      ActionListener,
                                                      ChangeListener { 
  
  //  GLOBAL VARIABLES

  private static final int FRAME_WIDTH = 600;
  private static final int FRAME_HEIGHT = 600;
  private static final int LINE_H = 300;
  private static final int LINE_V = 300;  
  private static final int BOUNDARY = 5;
  private static final double START_PROPORTION = 0.5;
  private static final int MIN_SPEED = 1;
  private static final int MAX_SPEED = 100; 
  private static final int DEFAULT_SPEED = 50;

  private double widthProportion = START_PROPORTION; // Used for when canvas
  private double heightProportion = START_PROPORTION;// is resized

  // Boundaries of the canvas
  private int rightBoundary = canvas.getWidth() - BOUNDARY;
  private int leftBoundary = BOUNDARY;
  private int upperBoundary = BOUNDARY;
  private int lowerBoundary = canvas.getHeight() - BOUNDARY;

  // Initial locations to draw the two lines between
  private static final Location START_H = new Location( 0 - BOUNDARY, LINE_H );
  private static final Location START_V = new Location( LINE_V, 0 - BOUNDARY );
  private static final Location END_H = new Location( FRAME_WIDTH + BOUNDARY,
                                                      LINE_H );
  private static final Location END_V = new Location( LINE_V, FRAME_HEIGHT +
                                                      BOUNDARY ); 
  private static final int PIZZA_SIZE = 90;
  private double size = PIZZA_SIZE;  

  private ResizablePizzaSlice pizzaSlice;

  private Location lastPoint;
  private Location hLineEnd, vLineEnd;  // To get proportions after resizing

  private Line horizLine, vertLine; // Lines to be drawn
  private boolean grabH, grabV; // if lines were grabbed
  private boolean grabPizza;
  private double xEquals = LINE_V; // horizontal and vertical
  private double yEquals = LINE_H; // position of line on screen
  private int sliderSpeed = DEFAULT_SPEED; // speed shown by the slider bar  
  private int actionDelay;

  private int oldWidth, oldHeight; // to determine if canvas was resized

  private JButton startButton, stopButton, clearButton;
  private JSlider speedSlider;
  private JLabel sliderLabel;
  
    

  private boolean animate = true;
  private boolean clearAll = false;

  
  // Methods from ChangeListener interface

  public void stateChanged( ChangeEvent evt ) {
    
    sliderSpeed = speedSlider.getValue();
    sliderLabel.setText( "The speed is " + sliderSpeed );  
    
  //  int delayTime = MAX_SPEED - sliderSpeed;
  //  pizzaSlice.setDelayTime( delayTime ); 
    pizzaSlice.actionDelay = MAX_SPEED - sliderSpeed;
  
  
     
 
  }// end of stateChanged()

  // Methods from MouseListener interface



  public void mouseEntered( MouseEvent evt ) {
  }

  public void mouseExited( MouseEvent evt ) {
  }

  // Methods from MouseMotionListener

  public void mouseMoved( MouseEvent evt ) {
  }


  /** wasResized is a method to determine if the canvas has been resized.  It
   * returns a true or false.
   */
  private boolean wasResized() { 
    
    if( canvas.getWidth() != oldWidth || canvas.getHeight() != oldHeight ) {
      return true;
    }
    return false; 
  }



  /** moveH is a method to move the horizontal line vertically across the
   *  screen.  
   *  @param double dx - the number of pixels the line will move in the X-
   *                    direction.
   *  @param double dy - the number of pixels the line will move in the Y-
   *                    direction.
   */ 
  public void moveH( double dx, double dy ) {
    lowerBoundary = canvas.getHeight() - BOUNDARY;
    // Invokes move function when line is within boundaries
    if( horizLine.getEnd().getY() + dy >= upperBoundary &&
        horizLine.getEnd().getY() + dy <= lowerBoundary ) {
      horizLine.move( dx, dy );
    }// End if
  }// End method moveH



  /** moveV is a method to move the vertical line laterally across the screen.
   *
   * @param double dx - the number of pixels the line will move in the X-
   *                    direction.
   * @param double dy - the number of pixels the line will move in the Y-
   *                    direction.
   */
  public void moveV( double dx, double dy ) {
    rightBoundary = canvas.getWidth() - BOUNDARY;
    // Invokes move function when line is within boundaries
    if( vertLine.getEnd().getX() + dx >= leftBoundary && 
        vertLine.getEnd().getX() + dx <= rightBoundary ) {
      vertLine.move( dx, dy );
    }// End if
  }// End method moveV


//  public void movePizza( double dx, double dy ) {
   
//    rightBoundary = canvas.getWidth() - BOUNDARY;
//    lowerBoundary = canvas.getHeight() - BOUNDARY;

    // if pizzaSlice is on canvas
//    if( pizzaSlice.getLocation().getX() + dx >= leftBoundary &&
//        pizzaSlice.getLocation().getX() + dx <= rightBounday &&
//        pizzaSlice.getLocation().getY() + dy >= upperBoundary &&
//        pizzaSlice.getLocation().getY() + dy <= lowerBoundary ) {

//      pizzaSlice.move( dx, dy );

//    }// end if
//  }// end of movePizza()



  /** onMouseClick is invoked whenever the mouse is clicked.  Generates a pizza
   * slice upon mouse click.
   *
   * @param Location point - the location the mouse was clicked on the screen.
   */
   public void mouseClicked( MouseEvent e ) {
     
     double size = 0;

     Location point = new Location( e.getX(), e.getY() );

     pizzaSlice = new ResizablePizzaSlice( point, size, canvas, horizLine,
                                           vertLine );
  
     pizzaSlice.addMouseListener( this );
     pizzaSlice.addMouseMotionListener( this );
   }// end mouseClicked() 



  /** mousePressed is invoked whenever the mouse is pressed down.  Determines
   * whether a line has been grabbed.
   *
   * @param Location point - the location the mouse was pressed on the screen
   */
  public void mousePressed( MouseEvent e ) {
    
    Location point = new Location( e.getX(), e.getY() );
    lastPoint = point;

    grabH = horizLine.contains( point );
    grabV = vertLine.contains( point );

    ResizablePizzaSlice.pizzaGrabbed = pizzaSlice.contains( point );
    System.out.println( ResizablePizzaSlice.pizzaGrabbed );

  }// end of mousePressed



  /** onMouseDrag is called whenever the mouse is dragged. It functions to move
   *  the lines on the screen and get new proportion values
   *
   *  @param Location point - The location that the mouse pointed on the screen
   */
  public void mouseDragged( MouseEvent e ) {

    Location point = new Location( e.getX(), e.getY() );

    if( grabH && !grabV ) { // if horizLine was grabbed
        
       moveH( 0, point.getY() - lastPoint.getY() ); // move horizLine
       lastPoint = point;   
    } 
    if( grabV && !grabH ) { // if vertLine was grabbed
        
       moveV( point.getX() - lastPoint.getX(), 0 ); // move vertLine
       lastPoint = point;
    }      
    if( grabH && grabV ){ // if both lines grabbed
       moveH( 0, point.getY() - lastPoint.getY() ); // move horizLine 
       moveV( point.getX() - lastPoint.getX(), 0 ); // move vertLine
       lastPoint = point;
    } 
  }// end of mouseDragged()



  /** onMouseRelease is invoked when the mouse button is release.  It updates
   *  the location of the lines on the screen, in case they were moved.
   *
   *  @param Location point - The location that the mouse button was release
   */  
  public void mouseReleased( MouseEvent e ) {
      
      Location point = new Location( e.getX(), e.getY() );
      
      hLineEnd = horizLine.getEnd();
      vLineEnd = vertLine.getEnd();     
      
      // To get new proportions, in case window was resized
      heightProportion = hLineEnd.getY() / canvas.getHeight();   
      widthProportion = vLineEnd.getX() / canvas.getWidth();

  }

  
  public void actionPerformed( ActionEvent e ) {
    if( e.getSource() == startButton ) {
      
      ResizablePizzaSlice.animate = true;
    }
    else if( e.getSource() == stopButton ) {
       
      ResizablePizzaSlice.animate = false;
    }
    else if( e.getSource() == clearButton ) {
             
      ResizablePizzaSlice.clearAll = true;  
    }
    else if( e.getSource() == pizzaSlice ) {
     
      ResizablePizzaSlice.pizzaGrabbed = true;
    }

  }// end actionPerformed()


 
/** This method updates lines on the screen when the window is resized.
 */
  public void paint( java.awt.Graphics g ) {
    super.paint( g );
      
    if( wasResized() ) {  // If canvas window was resized
    
      // New start/end points of the lines if windows resized     
      Location newStartH = new Location( 0 , canvas.getHeight() *
                                         heightProportion );
      Location newStartV = new Location( canvas.getWidth() * widthProportion
                                         , 0 );
      Location newEndH = new Location( canvas.getWidth() , canvas.getHeight() * 
                                       heightProportion );
      Location newEndV = new Location( canvas.getWidth() * widthProportion,
                                       canvas.getHeight() );
   
      horizLine.setEndPoints( newStartH, newEndH );// Move the lines if
      vertLine.setEndPoints( newStartV, newEndV );// canvas is resized

      heightProportion = newEndH.getY() / canvas.getHeight();// set new height
      widthProportion = newEndV.getX() / canvas.getWidth();  // and width ratio 
      xEquals = newStartV.getX(); // x value of vertical line
      yEquals = newStartH.getY(); // y value of horizontal line
    }
  }



  public void setGUI() {
 
    JPanel northPanel = new JPanel();
    JPanel buttonPanel = new JPanel();    
    JPanel southPanel = new JPanel();

    JLabel panelLabel = new JLabel( "Resizable PizzaSlice Controls", 
                                    SwingConstants.CENTER );
    sliderLabel = new JLabel( "The speed is "+ sliderSpeed );

    startButton = new JButton( "Start" );
    stopButton = new JButton( "Stop" );
    clearButton = new JButton( "Clear All" );

    speedSlider = new JSlider( JSlider.HORIZONTAL, MIN_SPEED, MAX_SPEED, 
                               DEFAULT_SPEED );

    startButton.addActionListener( this );
    stopButton.addActionListener( this );
    clearButton.addActionListener( this );

    speedSlider.addChangeListener( this );  

    northPanel.setLayout( new GridLayout( 2, 1 ) );
       

    buttonPanel.add( startButton );
    buttonPanel.add( stopButton );
    buttonPanel.add( clearButton );
  
    northPanel.add( panelLabel );
    northPanel.add( buttonPanel );
 
    southPanel.add( sliderLabel );
    southPanel.add( speedSlider );

    Container contentPane = getContentPane();
    contentPane.add( northPanel, BorderLayout.NORTH );
    contentPane.add( southPanel, BorderLayout.SOUTH );

    this.validate();

    horizLine = new Line( START_H, END_H, canvas );
    vertLine = new Line( START_V, END_V, canvas );
    oldWidth = canvas.getWidth();
    oldHeight = canvas.getHeight();
  }// end setGUI()



  public void begin() {

    setGUI();
    
    canvas.addMouseListener( this );
    canvas.addMouseMotionListener( this ); 


  }// end begin()



/* This method generates the canvas */
  public static void main( String[] args ) {
    new Acme.MainFrame( new ResizablePizzaSliceController(), args, FRAME_WIDTH, 
                        FRAME_HEIGHT );     
  } 
}       
