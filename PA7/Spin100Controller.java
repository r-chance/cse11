/* Name:  Robert Chance
 * Login: cs11faay
 * PID:   A13088857
 * Date:  Nov 9, 2015
 * Sources of Help: Java - An Eventful Approach, the tutoring staff
 *
 * Program Description: This program is a game similar to the that seen in the
 *   popular gameshow "The Price is Right", as the contestants spin the wheel
 *   trying to get closest to $1.00 without going over, and ultimately winning
 *   their way into the Showcase Showdown.  This game offers no such reward for
 *   winning and probably isn't quite as fun, but it works in a similar manner.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Color;
import objectdraw.*;
import java.util.Random;


/** Spin100Controller is the controller class for the Spin100 program. It 
 *  controls the GUI and keeps track of the score.
 */
public class Spin100Controller extends WindowController
                               implements ActionListener {

  private static final int FRAME_WIDTH = 840;
  private static final int FRAME_HEIGHT = 660;
  private static final int NAME_FONT_SIZE = 24; 
  private static final int WIN_FONT_SIZE = 15;
  private static final int BUTTON_SPACING = 15;
  private static final int MIN_TICKS = 50;
  private static final int WHEEL_DELAY = 1; 
  private static final int MAX_VALUE = 100;
  private static final double IMAGE_WIDTH = 185;
  private static final double WHEEL_Y_COORD = 10;
  private static final double GAME_OVER_Y = 5;
  private static final int NUM_OF_IMAGES = 20;
  private static final int TWO = 2;
  private static final int FIVE = 5;
  private static final Location P1_WIN_LOC = new Location( 16, GAME_OVER_Y );
  private static final Location P2_WIN_LOC = new Location( 744, GAME_OVER_Y );
  private static final Location TIE_LOC = new Location( 406, GAME_OVER_Y );

  private static final Color GREEN = new Color( 0, 255, 0 );
  private static final Color BLUE = new Color( 0, 0, 255 );

  public static final int[] imageOrder = { 30, 85, 50, 95, 55, 75, 40, 20, 60,
                                           35, 80, 15, 100, 5, 90, 25, 70, 45,
                                           10, 65 };
 
  private Location wheelLoc1;// Location that the
  private Location wheelLoc2;// wheels are drawn

  private Text p1Win, p2Win, tie;
  
  Font winnerFont = new Font( "Tiresias PCfont Z", Font.BOLD, WIN_FONT_SIZE );

  private Spin100Controller thisController = this;// this controller object
  private Spin100Wheel p1Wheel, p2Wheel; // Spin100Wheel objects
 
  // GUI elements
  private JPanel northPanel, southPanel, buttonPanel;
  private JLabel nameLabel, p1Score, p2Score;
  private JButton p1Spin, p2Spin, p1Finish, p2Finish, restart;

  private java.awt.Image[] images = new java.awt.Image[NUM_OF_IMAGES];
 
  private Random ticksGen = new Random(); 
  private double ticksValue;// number from RNG
  private int ticks = 0;// this will get passed to the constructor

  private boolean p1Turn = true;
  private boolean p2Turn = false;
  private int score1 = 0;
  private int score2 = 0;
 
  private boolean isSpinning = false;

  private int p1Counter = 0;
  private int p2Counter = 0;



  /** Method main() constructs the canvas
   * 
   *  @param args An array of strings
   */
  public static void main( String[] args ) {

    // Construct the canvas
    new Acme.MainFrame( new Spin100Controller(), args, FRAME_WIDTH, 
                                                     FRAME_HEIGHT);
  }// end of method main()



  /** Method setGUI() implements all of the GUI features for the program,
   *  including JPanels, JButtons, and JLabels.
   */
  public void setGUI() {
 
    northPanel = new JPanel();
    southPanel = new JPanel();
    buttonPanel = new JPanel();

    nameLabel = new JLabel( "Spin100", SwingConstants.CENTER );
    Font nameFont = new Font( "Tiresias PCfont Z", Font.BOLD, NAME_FONT_SIZE );
    nameLabel.setFont( nameFont );

    p1Score = new JLabel( "Player 1's score is " + score1,
                          SwingConstants.CENTER );
    p2Score = new JLabel( "Player 2's score is " + score2, 
                          SwingConstants.CENTER );
    p1Spin = new JButton( "Click to Spin P1" );
    p2Spin = new JButton( "Click to Spin P2" );
    p1Finish = new JButton( "Finish Player 1" );
    p2Finish = new JButton( "Finish Player 2" );
    restart = new JButton( "Restart" );

    p1Spin.addActionListener( this );
    p2Spin.addActionListener( this );
    p1Finish.addActionListener( this );
    p2Finish.addActionListener( this );
    restart.addActionListener( this );

    northPanel.setLayout( new GridLayout( 3 , 1 ) );
    buttonPanel.setLayout( new FlowLayout( BUTTON_SPACING ) );  
 
    // Add labels to the north panel
    northPanel.add( nameLabel );
    northPanel.add( p1Score );
    northPanel.add( p2Score );

    // Add 5 buttons to the button panel
    buttonPanel.add( p1Spin );
    buttonPanel.add( p1Finish );
    buttonPanel.add( restart );
    buttonPanel.add( p2Spin );
    buttonPanel.add( p2Finish ); 

    // Add the button panel the the south panel
    southPanel.add( buttonPanel );

    Container contentPane = getContentPane();

    contentPane.add( northPanel, BorderLayout.NORTH );
    contentPane.add( southPanel, BorderLayout.SOUTH );

    this.validate();

  }// end of method setGUI()
    

  /** Method begin() is the starting place for the program.  It sets up the 
   *  GUI, and all of the screen components.  It calls two constructors, one
   *  for each wheel.
   */
  public void begin() {
 
    // Add user interface elements
    setGUI();

    // Add (and hide) winning messages
    p1Win = new Text( "P1 Winner", P1_WIN_LOC, canvas );
    p1Win.setFont( winnerFont );
    p1Win.setColor( GREEN ); 
    p1Win.hide();
    
    p2Win = new Text( "P2 Winner", P2_WIN_LOC, canvas );
    p2Win.setFont( winnerFont );
    p2Win.setColor( GREEN );
    p2Win.hide();
   
    tie = new Text( "Tie", TIE_LOC, canvas );
    tie.setFont( winnerFont );
    tie.setColor( BLUE );
    tie.hide();
 
    // Location the wheels will be drawn
    wheelLoc1 = new Location( ( ( canvas.getWidth()/TWO - IMAGE_WIDTH ) / TWO ),
                                 WHEEL_Y_COORD );
    wheelLoc2 = new Location( wheelLoc1.getX() + canvas.getWidth()/TWO , 
                              wheelLoc1.getY() ); 
   

    // Populate the Image[] array
    for( int i = 0 ; i < NUM_OF_IMAGES ; i++ ) {
  
      int imageIndex = ( i + 1 ) * FIVE;
     
      images[i] = getImage( "Big_Wheel-"+imageOrder[i]+".png" );

    }// end of for


    // Wheel for P1
    p1Wheel = new Spin100Wheel( images, MIN_TICKS, WHEEL_DELAY , 
                                wheelLoc1, thisController, canvas );
 
    // Wheel for P2
    p2Wheel = new Spin100Wheel( images, MIN_TICKS, WHEEL_DELAY , 
                                wheelLoc2, thisController, canvas );

  }// end of method begin()



  /** Method setScore() is called in the event handling after each spin and
   *  updates the score on the screen.
   *
   *  @param score This is score that will be updated on the screen
   */
   public void setScore( int score ) {
         
     if( p1Turn ) {

       score1 += imageOrder[score];  
       p1Score.setText( "Player 1's score is " + score1 );     

       if( score1 > MAX_VALUE ) {
 
         endTurnP1();        

       }// end of if
     }// end of if

     if( ( p2Turn ) ) {

       score2 += imageOrder[score];
       p2Score.setText( "Player 2's score is " + score2 );     

       if( score2 > MAX_VALUE ) {
 
         endTurnP2();
       }// end of if
     }// end of if
   }// end of setScore()



  /** method actionPerfored() is the event handler for the JButtons on the GUI.
   *  actionPerfored() communicates with the Spin100Wheel class to cause the
   *  wheel to spin.
   *
   *  @param e The JButton being clicked
   */
  public void actionPerformed( ActionEvent e ) {  

    
    // If the user clicked on P1 Spin
    if( e.getSource() == p1Spin && !( isSpinning ) ) {

      if( p1Turn ) {
       
        p2Turn = false;// needed to avoid scoring error
        ticksValue = ticksGen.nextDouble();// get a random #
        ticks = p1Wheel.getTicks( ticksValue );// send it to get a # of ticks
        p1Wheel.setTicks( ticks );// start the well spinning
        p1Wheel.setDelay( 1 ); // set the delay of the spinning
        isSpinning = true;
        p1Counter++; 
 
      }// end of if
    }// end of if

  
    // If the user clicked on P1 Finish
    if( e.getSource() == p1Finish && !( isSpinning ) && p1Counter != 0 ) {

      if( p1Turn ) {
    
        endTurnP1();
      
      }// end of if
    }// end of if


    // If the user clicked on Restart
    if( e.getSource() == restart ) {
    
      p1Turn = true;
 
      isSpinning = false;

      score1 = 0;
      score2 = 0;

      p1Score.setText( "Player 1's score is " + score1 );
      p2Score.setText( "Player 2's score is " + score2 );

      p1Wheel.resetWheel();
      p2Wheel.resetWheel();

      p1Counter = 0;
      p2Counter = 0;

      // Reactivates any buttons that were disabled
      if( !( p1Spin.isEnabled() ) ) {

        p1Spin.setEnabled( true );
      }// end of if

      if( !( p2Spin.isEnabled() ) ) {
  
        p2Spin.setEnabled( true );
      }// end of if

      if( !( p1Finish.isEnabled() ) ) {
 
        p1Finish.setEnabled( true );
      }// end of if

      if( !( p2Finish.isEnabled() ) ) {

        p2Finish.setEnabled( true );
      }// end of if

      // Hide any winner text
      if( !(p1Win.isHidden() ) ) {

        p1Win.hide();
      }// end of if

      if( !(p2Win.isHidden() ) ) {
 
        p2Win.hide();
      }// end of if

      if( !( tie.isHidden() ) ) {

        tie.hide();
      }// end of if
    }// end of if


    // If the user clicked on P2 Spin
    if( e.getSource() == p2Spin && !( isSpinning ) ) { 
      
      if( !( p1Turn ) ) {
        
        p2Turn = true;
        ticksValue = ticksGen.nextDouble();// get a random double
        ticks = p2Wheel.getTicks( ticksValue );// send it to get a # of ticks
        p2Wheel.setTicks( ticks );// start the wheel spinning
        p2Wheel.setDelay( 1 );// add a delay to the wheel
        isSpinning = true;
        p2Counter++;        
       
      }// end of if
    }// end of if


    // If the user clicked on P2 Finish   
    if( e.getSource() == p2Finish && !( isSpinning ) && p2Counter != 0  ) {
 
      if( !( p1Turn ) ) {
 
        endTurnP2();
 
      }// end of if
    }// end of if
  }// end of actionPerformed()



  /** endTurnP1() is called at the end of player 1's turn; it disables p1's 
   *  buttons and allows p2 to spin the wheel
   */
  public void endTurnP1() {
 
    p1Spin.setEnabled( false );
    p1Finish.setEnabled( false );
    p1Turn = false;
  } //end of endTurnP1()



  /** endTurnP2() is called at the end of player 2's turn and it disables p2's
   *  buttons and announces the winner of the game.
   */
  public void endTurnP2() {

    p2Spin.setEnabled( false );
    p2Finish.setEnabled( false );
    announceWinner();
  }// end of endTurnP2()



  /** announceWinner() is called when player 2's turn ends and it compares the
   *  scores to determine who won, and reveals the winner text for that player.
   */
  public void announceWinner() {

    if( score1 > score2 && score1 <= MAX_VALUE ) {

      p1Win.show();
    }// end of if
  
    if( score1 == score2 || score1 > MAX_VALUE && score2 > MAX_VALUE ) {

      tie.show();
    }// end of if
 
    if( score1 < score2 && score2 <= MAX_VALUE  ) {

      p2Win.show();
    }// end of if

    if( score1 < score2 && score2 > MAX_VALUE && score1 <= MAX_VALUE ) {
    
      p1Win.show();
    }// end of if

    if( score1 > score2 && score1 > MAX_VALUE && score2 <= MAX_VALUE ) {
  
      p2Win.show();
    }// end of if
  }// end of announceWinner()



  /** stillSpinning() determines if the wheel is still spinning, and changes
   *  a boolean to disallow button clicks during a spin
   *
   *  @return returns true if the wheel is spinning
   */
  public void stillSpinning( boolean spinning ) {

    isSpinning = spinning;

  }// end of stillSpinning()
}// end of class Spin100Controller


 
