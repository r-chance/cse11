/* Name:   Robert Chance
 * Login:  cs11faay
 * Date:   Oct 22, 2015
 * File:   ResizablePizzaSlice.java
 * Sources of Help:  Java - an Eventful Approach, CS lab tutoring staff
 *
 * Program Description:  This program generates a canvas with 4 quadrants and
 * allows the user to click in one of those quadrants.  The program will 
 * determine, based off of the location of the mouse click and the two line
 * already on the screen, what quadrant the user clicked in.  The program will
 * then access a constructor to generate a pizza slice shaped object in the
 * quadrant that the user clicked.  The pizza slice will be colored and 
 * oriented in a particular way based on the quadrant that it is contained in.
 * Once the pizza slice has been generated, it will grow to twice its size
 * before shrinking back down to half of its original size, and it will keep
 * doing this process indefinitely.  All pizza slices will have this 
 * functionality.  The user is also able to resize the frame and move the lines
 * around.  If this changes the quadrant that the existing pizza slices are in,
 * those slices will change color to correspond with the color associated
 * with that quadrant.
 */


import java.awt.Color;
import objectdraw.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;



public class ResizablePizzaSlice extends ActiveObject {

  private static final double PAUSE_TIME = 33; // Time in between animations
  private static final double GROWTH = 5; // Rate that pizza slice grows at
  private static final double ARC_ANGLE = 45; // Angle to make pizza slice
  private static final double START_SIZE = 90; // Size of pizza slice

  private static final double ANGLE_OFFSET = 22.5;
  private static final int SIZE_INCREASE = 5;
  private static final double SIZE_OFFSET = 2.5;
  private static final int TWO = 2; // I use this number a lot
  private static final int COUNT_UPPER_LIMIT = 36; // Turn counter around 
  private static final int COUNT_LOWER_LIMIT = -19; // Turn counter around again
  private static final int MOUSE_OFFSET = 45;  

  private static final Color orange = new Color( 219, 162, 74 );
  private static final Color yellow = new Color( 225, 216, 0 );
  private static final Color red = new Color( 225, 35, 1 );
  private static final Color gold = new Color( 255, 250, 138 );

  private static final int FIRST_QUAD_ANGLE = 45;
  private static final int SEC_QUAD_ANGLE = FIRST_QUAD_ANGLE + 90;
  private static final int THIRD_QUAD_ANGLE = SEC_QUAD_ANGLE + 90;
  private static final int FOURTH_QUAD_ANGLE = THIRD_QUAD_ANGLE + 90;

  private static final int PIZZA_SPIN_ANGLE = 10;
  private static final int MAX_SPEED = 100;  

  public static boolean animate = true;
  public static boolean clearAll; 

  public static boolean pizzaGrabbed = false;

  public static int actionDelay = 50;

  private FilledArc pizzaSlice;
  private double startSize; //

  private Location tip; // tip of pizza slice ( center of arc's bounding box )
  private double tipX; 
  private double tipY;
  private Location mousePoint; // the point of the mouse
 
  private Line vLine; // vertical line
  private Line hLine; // horizontal line
  private double xEquals, yEquals; // X and Y values of the lines' input
  
  private DrawingCanvas canvas;


  /** Constructor generate a pizzaSlice when invoked
   *    @param Location center - The point where the mouse was clicked
   *    @param double size - The initial size of the pizzaSlice
   *    @param DrawingCanvas aCanvas - The canvas used by the controller
   *    @param Line hLine - The vertical line on the canvas
   *    @param Line vLine - The horizontal line on the canvas
   */
  public ResizablePizzaSlice( Location center, double size, DrawingCanvas
                              aCanvas, Line horizLine, Line vertLine ) {
    

    vLine = vertLine;
    hLine = horizLine;
    xEquals = vLine.getEnd().getX(); // To find which quadrant
    yEquals = hLine.getEnd().getY(); // the pizza should be in
    canvas = aCanvas;
    tip = new Location( center.getX() - MOUSE_OFFSET, // tip of pizza
                        center.getY() - MOUSE_OFFSET );
    
    tipX = tip.getX();
    tipY = tip.getY();
    
    mousePoint = center;

    // Create pizza slice if mouse was clicked in quadrant I
    if( center.getX() > xEquals && center.getY() < yEquals ) {
       pizzaSlice = new FilledArc( tipX, tipY, START_SIZE, START_SIZE,
                    FIRST_QUAD_ANGLE - ANGLE_OFFSET , ARC_ANGLE, canvas );
       pizzaSlice.setColor( yellow );
    } // end if statement
    // Create pizza slice if mouse was clicked in quadrant II   
    if( center.getX() < xEquals && center.getY() < yEquals ) {   
      pizzaSlice = new FilledArc( tipX, tipY, START_SIZE, START_SIZE, 
                   SEC_QUAD_ANGLE - ANGLE_OFFSET, ARC_ANGLE, canvas );
        pizzaSlice.setColor( orange );
    } // end if statement
    // Create pizza slice if mouse was clicked in quadrant III
    if( center.getX() < xEquals && center.getY() > yEquals ) {
      pizzaSlice = new FilledArc( tipX, tipY , START_SIZE, START_SIZE, 
                   THIRD_QUAD_ANGLE - ANGLE_OFFSET, ARC_ANGLE, canvas );
      pizzaSlice.setColor( red );
    } // end if statement
    // Create pizza slice if mouse was clicked in quadrant IV   
    if( center.getX() > xEquals && center.getY() > yEquals ) {
      pizzaSlice = new FilledArc( tipX, tipY, START_SIZE, START_SIZE,
                   FOURTH_QUAD_ANGLE - ANGLE_OFFSET, ARC_ANGLE, canvas );
      pizzaSlice.setColor( gold );
    } // end if statement

    ResizablePizzaSlice.clearAll = false;

    start(); // invokes the run() method
  }// End of constructor



  /** This method controls the animation of the objects on the canvas
   */
  public void run() {
    int counter = 0;
    
    while( true ) {  

      // While object is growing    
      while( increasing( counter ) ) {   
        xEquals = vLine.getEnd().getX();
        yEquals = hLine.getEnd().getY();
        updateColor();
     
        if( animate ) {    
          growPizza();
          rotatePizza();
     
          counter += TWO;
        }// end if

        // when object is at largest
        if( counter == COUNT_UPPER_LIMIT ) {
          counter --;
        }// end if
        pause( ResizablePizzaSlice.actionDelay );
        if( ResizablePizzaSlice.clearAll ) {
          
          pizzaSlice.hide();
          return;
        }
      }// end while
     
      // While object is shrinking 
      while( decreasing( counter ) ) {
       
        xEquals = vLine.getEnd().getX();
        yEquals = hLine.getEnd().getY();
        updateColor();

        if( animate ) {

          shrinkPizza();
          rotatePizza();
        
          counter -= TWO;
        }// end if  

        // When object is at smallest
        if( counter == COUNT_LOWER_LIMIT ) {
          counter ++;
        }// end if
        pause( ResizablePizzaSlice.actionDelay );
       
        if( ResizablePizzaSlice.clearAll ) {
           
           pizzaSlice.hide();
         //  ResizablePizzaSlice.clearAll = false;
           return;
         }

      }// end while
    
    }// end while
  }// End of run method



  /** Grows the pizza slice when invoked, also moves the entire image, based on
   *  the quadrant so that the tip of the pizza slice remains in place.
   */
  public void growPizza() {
    if( tipX > xEquals && tipY < yEquals ) { // If in Q1
      pizzaSlice.move( -SIZE_OFFSET , -SIZE_OFFSET );
    } 
    if( tipX < xEquals && tipY < yEquals ) { // If in Q2
      pizzaSlice.move( -SIZE_OFFSET, -SIZE_OFFSET );
    }
    if( tipX < xEquals && tipY > yEquals ) { // If in Q3
      pizzaSlice.move( -SIZE_OFFSET, -SIZE_OFFSET );
    }
    if( tipX > xEquals && tipY > yEquals ) { // If in Q4
      pizzaSlice.move( -SIZE_OFFSET, -SIZE_OFFSET );
    }
    
    pizzaSlice.setSize( pizzaSlice.getWidth() + SIZE_INCREASE, 
                        pizzaSlice.getHeight() + SIZE_INCREASE );
   
  }// End of method growPizza



  /** Shrinks the pizza slice when invoked, also moves the entire object, based
   * on the quadrant so that the tip of the pizza slice remains in place.
   */
  public void shrinkPizza() {
    if( tipX > xEquals && tipY < yEquals ) {
      pizzaSlice.move( SIZE_OFFSET, SIZE_OFFSET );
    }
    if( tipX < xEquals && tipY < yEquals ) {
      pizzaSlice.move( SIZE_OFFSET, SIZE_OFFSET );
    }
    if( tipX < xEquals && tipY > yEquals ) {
      pizzaSlice.move( SIZE_OFFSET, SIZE_OFFSET );
    }
    if( tipX > xEquals && tipY > yEquals ) {
      pizzaSlice.move( SIZE_OFFSET, SIZE_OFFSET );
    }

    pizzaSlice.setSize( pizzaSlice.getWidth() - SIZE_INCREASE,
                         pizzaSlice.getHeight() - SIZE_INCREASE );
  }// End of method shrinkPizza



  public void rotatePizza() {
    pizzaSlice.setStartAngle( pizzaSlice.getStartAngle() + PIZZA_SPIN_ANGLE );
  }

  public void movePizza( double dx, double dy ) {
    pizzaSlice.move( dx, dy );
  }
    
  public boolean contains( Location point ) {
    
    return pizzaSlice.contains( point );
  }//end contains()

  public void setDelayTime( int delay ) {
    this.actionDelay = delay;
  }    

 

  /** Method to update color of pizzaSlice based on what quadrant it is in
   */ 
  public void updateColor() {
    
     // If pizza in QI
     if( mousePoint.getX() >  xEquals && mousePoint.getY() < yEquals ) {
       pizzaSlice.setColor( yellow );
     }
     // If pizza in Q2    
     if( mousePoint.getX()  < xEquals && mousePoint.getY() < yEquals ) {
       pizzaSlice.setColor( orange );
     }
     // If pizza in Q3
     if( mousePoint.getX() < xEquals && mousePoint.getY() > yEquals ) {
       pizzaSlice.setColor( red );
     }
     // If pizza in Q4
     if( mousePoint.getX() > xEquals && mousePoint.getY() > yEquals ) {
       pizzaSlice.setColor( gold );
     }
  }



// If counter is even, increasing = true
  public boolean increasing( int count ) {
    if( count % 2 == 0 ) {
      return true;
    }
    return false;
  }// End of boolean increasing



// If counter is odd, decreasing = true
  public boolean decreasing( int count ) {
    if( count % 2 != 0 ) {
      return true;
    }
    return false;
  }// End of boolean decreasing
}// End of class ResizablePizzaSlice
