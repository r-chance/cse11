/* Name:  Robert Chance 
 * Login: cs11faay
 * PID:   A13088857
 * Date:  Nov 9, 2015
 */

import objectdraw.*;
import java.awt.Color;
import java.util.Random;

/** Spin100Wheel is the constructor class for the Spin100Controller. It
 *  generates a wheel with a pattern of recurring numbers.
 */
public class Spin100Wheel extends ActiveObject {

  private static final int NUM_OF_IMAGES = 20;
  private static final int NUM_OF_BOXES = 5;
  private static final double IMAGE_WIDTH = 185;
  private static final double IMAGE_HALF_WIDTH = 93;
  private static final double IMAGE_HEIGHT = 99;
  private static final int ARC_ANGLE = 30;
  private static final int START_ANGLE = -15;
  private static final int NEEDLE_SIZE = 150;
  private static final int NEEDLE_OFFSET = 25;
  private static final int ONE = 1;
  private static final int TWO = 2;
  private static final Color RED = new Color( 255, 0, 0 );   
  private static final double TICK_MULTIPLE = 20;
  private static final double TICK_THRESHOLD = 13;
  private static final double TICK_INTERCEPT = 26;
  private static final int LOW_SPEED = 500;
  private static final int MED_SPEED = 200;
  private static final int HIGH_SPEED = 20;
  private static final int CINCO = 5;
  private static final int VIENTE = 20;
  private static final int LAST_ELEMENT = 19;

  private Random ticksGen = new Random();

  private int delay;

  private Location[] wheelLocation = new Location[NUM_OF_BOXES];
  private VisibleImage[] wheelNumber = new VisibleImage[NUM_OF_IMAGES];
  private FilledArc needle;
  private DrawingCanvas canvas;  
  private Spin100Controller controller;

  private int ticks = 0;// ACTUAL number of ticks remaining on the wheel
  private double ticksValue; //from the RNG
  private int ticksMin;// min num of ticks, passed from controller
 
  private int[] counter = new int[NUM_OF_BOXES];// represents boxes on screen 
  private int middleBox;// for determining the score

  private int score;// automatically updated when the wheel spins



  /** Spin100 is the constructor.  When invoked, it draws an ActiveObject
   *  numbered wheel on the screen, with functionality controlled by the 
   *  Spin100Controller.  
   *  
   *  @param images An image array of the numbers that appear on the wheel
   *  @param minTicks The minimum number of frame changes for the wheel size
   *  @param delay Controls the speed that the frames changes at
   *  @param wheelLoc The location the wheel will be drawn at
   *  @param controller A reference to the Spin100Controller 
   *  @param aCanvas The drawing canvas created in the controller
   */
  public Spin100Wheel( java.awt.Image[] images, int minTicks, int initialDelay, 
                  Location wheelLoc, Spin100Controller aController, 
                  DrawingCanvas aCanvas ) {
   

    // These will be the locations the numbers are placed at
    wheelLocation[0] = wheelLoc;

    // Populate the remaining Location array
    for( int i = 1 ; i < NUM_OF_BOXES ; i++ ) {
      
      Location prevLoc =  wheelLocation[i - 1];
      wheelLocation[i] = new Location( prevLoc.getX() ,
                                       prevLoc.getY() + IMAGE_HEIGHT ); 
    }// end of for

    ticksMin = minTicks;
    canvas = aCanvas; 
    delay = initialDelay;    
    controller = aController;
   
    // Draw the images on the screen and hide them for now
    for( int i = 0 ; i < NUM_OF_IMAGES ; i++ ) {

      // Draw the wheel number on the screen (ex. draw '50') 
      wheelNumber[i] = new VisibleImage( images[i], wheelLoc, IMAGE_WIDTH,
                                         IMAGE_HEIGHT, canvas );
      wheelNumber[i].hide();  
    }// end of for

    // Draw the wheel and needle pointing to the default value '50'
    needle = new FilledArc( wheelLocation[TWO].getX() + IMAGE_HALF_WIDTH,
                            wheelLocation[TWO].getY() - NEEDLE_OFFSET ,
                            NEEDLE_SIZE , NEEDLE_SIZE, START_ANGLE , 
                            ARC_ANGLE ,  canvas );

    needle.setColor( RED );

    // Move the numbers to the appropriate screen locations
    for( int i = 0 ; i < NUM_OF_BOXES ; i++ ) {
 
      wheelNumber[i].moveTo( wheelLocation[i] );
      wheelNumber[i].show();
      counter[i] = i;

    }// end of for
 
    start();// calls the run() method

  }// end of Spin100Wheel constructor



  /** Method run() is a method executed by the ActiveObject threads.  It 
   *  invokes methods to spin the wheel and update the system pause time.
   */
  public void run() { 

    while( true ) {// infinite loop

      // spins the wheel if there are any ticks remaining
      while( ticks > 0 ) {           
 
        spinWheel();

        setTicks( ticksLeft() - 1 );  
     
        pause( delay );// pauses the system for an animated effect

        setDelay( delay ); // updates the delay  

        if( ticks == 1 ) {
   
          controller.stillSpinning( false );
          controller.setScore( middleBox );
        }// end of if
      }// end of while   

      pause( delay );
    }// end of infinite while
  }// end of run()



  /** Method ticksLeft() returns the number of ticks left to the controller
   *  and run() so it can know when the wheel has stopped spinning.
   *
   *  @return returns the number of ticks the wheel has left
   */
  public synchronized int ticksLeft() {
    return ticks;
  }// end of ticksLeft()
   


  /** Method getTicks() takes in a randomly generated double and returns an int
   *  number of ticks, based off of the value of the random number.
   *
   *  @param random a randomly generated int between 0 and 1
   *
   *  @return returns the number of ticks( frame changes ) the wheel will make
   */
  public synchronized int getTicks( double random ) {

    if( random >= 0 && random < TICK_THRESHOLD ) {

      // returns an increasingly larger number, until random > TICK_THRESHOLD
      return (int)( ticksMin + random * TICK_MULTIPLE ) ;
    }// end of if

    else {
    
      // returns a slightly smaller value if random > TICK_THRESHOLD
      return (int) ( ticksMin + (-TICK_MULTIPLE * random ) + TICK_INTERCEPT );
    }// end of else
  
  }// end of method getTicks()  

  

  /** Method setTicks() is called in the controller when a player clicks a
   *  button to spin.
   *
   *  @param randomTicks The number of ticks from controller
   */
   public synchronized void setTicks( int randomTicks ) {

     ticks = randomTicks; 

   }// end of setTicks()


 
  /** Method setDelay() takes in the current system delay time and modifies it
   *  based on the value, to give the appearance of spinning the wheels.
   *
   *  @param delay The system delay for the spinner, reset upon spin
   *
   *  @return this method returns an updated delay time
   */
  public int setDelay( int newDelay ) {
   
   delay = newDelay;

    
    if( delay > LOW_SPEED ) {

      return delay;
    }// end of if

    if( delay > MED_SPEED ) {
  
      delay += VIENTE; // increments by 20 on every call
      return delay;
    }// end of if

    if( delay > HIGH_SPEED ) {

      delay += CINCO;// increments by 5 on every call
      return delay;
    }// end of if

    if( delay > 0 ) {
 
      delay++;// increments by 1 on every call
      return delay;
    }// end of if

    return delay;
  }// end of method setDelay()



  /** Method spinWheel() gives the appearance of animating a spinning wheel by
   *  hiding, moving, and showing wheel numbers on the screen.  counter[] 
   *  represents the spaces on the screen, i.e. counter[0] is the top block;
   *  counter[4] is the bottom block.
   *
   */
  public void spinWheel() {

    for( int i = 0 ; i < NUM_OF_BOXES ; i++ ) {

      // remove the old image    
      wheelNumber[counter[i]].hide();
    
      // loop the counter back up to 20 when it goes negative
      if( counter[i] - 1 < 0 ) {
        
        counter[i] = VIENTE; 
      }// end of if

      // add the new image to the current location
      wheelNumber[counter[i] - 1].show();
      wheelNumber[counter[i] - 1].moveTo( wheelLocation[i] ); 
      
      counter[i]--;  // decrement the image number in the counter
       
      // Get the array element position of the answer (from imageOrder[])
      if( i + TWO >= NUM_OF_BOXES ) {

        middleBox = counter[i - NUM_OF_BOXES + TWO];
      }// end of if
        
      else{

        middleBox = counter[i];
      }// end of else
      
    }// end of for

  }// end of spinWheel();


 
  /** Method resetWheel() is invoked by the controller class when the user
   *  clicks the "Restart" button.  It returns the wheels to their original
   *  position, as in a new game.
   */
  public void resetWheel() {

    ticks = 0;
    // hide any images that are showing
    for( int i = 0 ; i < NUM_OF_IMAGES ; i++ ) {
    
      if( !( wheelNumber[i].isHidden() ) ) {

        wheelNumber[i].hide();
      }// end of if
    }// end of for

    // redraw the wheel as it originally was
    for( int i = 0 ; i < NUM_OF_BOXES ; i++ ) {
  
      wheelNumber[i].moveTo( wheelLocation[i] );
      wheelNumber[i].show();
      counter[i] = i;
    }// end of for
  }// end of resetWheel()

}// end of class Spin100Wheel
