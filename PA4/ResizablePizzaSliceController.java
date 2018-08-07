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


import java.awt.Color;
import objectdraw.*;
/* This class is the controller class for the program. It allows the user to 
 * generate animated objects on a canvas.
 */

public class ResizablePizzaSliceController extends WindowController{

  private static final int FRAME_WIDTH = 600;
  private static final int FRAME_HEIGHT = 600;
  private static final int LINE_H = 300;
  private static final int LINE_V = 300;  
  private static final int BOUNDARY = 5;
  private static final double START_PROPORTION = 0.5;
 
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

  private Location lastPoint;
  private Location hLineEnd, vLineEnd;  // To get proportions after resizing

  private Line horizLine, vertLine; // Lines to be drawn
  private boolean grabH, grabV; // if lines were grabbed
  private double xEquals = LINE_V; // horizontal and vertical
  private double yEquals = LINE_H; // position of line on screen
    

  private int oldWidth, oldHeight; // to determine if canvas was resized

/* wasResized is a method to determine if the canvas has been resized.  It
 * returns a true or false.
 */
  private boolean wasResized() { 
    
    if( canvas.getWidth() != oldWidth || canvas.getHeight() != oldHeight ) {
      return true;
    }
    return false; 
  }
/* moveH is a method to move the horizontal line vertically across the screen.
 *
 * @param double dx - the number of pixels the line will move in the X-
 *                    direction.
 * @param double dy - the number of pixels the line will move in the Y-
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

/* moveV is a method to move the vertical line laterally across the screen.
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

/* onMouseClick is invoked whenever the mouse is clicked.  Generates a pizza
 * slice upon mouse click.
 *
 * @param Location point - the location the mouse was clicked on the screen.
 */
   public void onMouseClick( Location point ) {
   double size = 0;
   new ResizablePizzaSlice( point, size, canvas, horizLine, vertLine );
  } 

/* onMousePress is invoked whenever the mouse is pressed down.  Determines
 * whether a line has been grabbed.
 *
 * @param Location point - the location the mouse was pressed on the screen
 */
  public void onMousePress( Location point ) {
    lastPoint = point;

    grabH = horizLine.contains( point );
    grabV = vertLine.contains( point );
  }

/* onMouseDrag is called whenever the mouse is dragged. It functions to move
 * the lines on the screen and get new proportion values
 *
 * @param Location point - The location that the mouse pointed on the screen
 */
  public void onMouseDrag( Location point ) {

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
  }

/* onMouseRelease is invoked when the mouse button is release.  It updates
 * the location of the lines on the screen, in case they were moved.
 *
 * @param Location point - The location that the mouse button was release
 */  
  public void onMouseRelease( Location point ) {
      hLineEnd = horizLine.getEnd();
      vLineEnd = vertLine.getEnd();     
      
      // To get new proportions, in case window was resized
      heightProportion = hLineEnd.getY() / canvas.getHeight();   
      widthProportion = vLineEnd.getX() / canvas.getWidth();

  }

/* This method updates lines on the screen when the window is resized.
 */
  public void paint( java.awt.Graphics g ) {
    super.paint( g );
    if( wasResized() ) {  // If canvas window was resized
    
      // New start/end points if windows resized     
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

/*  This method runs initial statements for this program */
  public void begin() {
    horizLine = new Line( START_H, END_H, canvas );
    vertLine = new Line( START_V, END_V, canvas );
    oldWidth = canvas.getWidth();
    oldHeight = canvas.getHeight();
  }

/* This method generates the canvas */
  public static void main( String[] args ) {
    new Acme.MainFrame( new ResizablePizzaSliceController(), args, FRAME_WIDTH, 
                        FRAME_HEIGHT );     
  } 
}       
