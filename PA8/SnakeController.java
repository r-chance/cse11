/* Name:  Robert Chance
 * Login: cs11faay
 * PID:   A13088857
 * Date:  11/15/15
 * Sources of Help:
 *
 * Program Description:
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import objectdraw.*;
import java.util.Random;

public class SnakeController extends WindowController
                             implements ActionListener, KeyListener {

  private static final int Y_PADDING = 50;
  private static final int LINUX_PADDING = 6;
  private static final int MIN_DIM = 500;
  private static final int MAX_DIM = 800;
  private static final int MAX_SPEED = 100;
  private static final int MIN_SPEED = 1000;
  private static final int MIN_SIZE = 20;
  private static final int MAX_SIZE = 400;
  private static final int NUM_TOP_COLUMNS = 2;
  private static final int NUM_TOP_ROWS = 1;
  private static final int FONT_SIZE = 12;

  private static int dimensions;

  private int side;
  private int size;
  private int delay;
  private int seed;
  private int score;
  private int highScore;
 
  private JPanel northPanel;
  
  private JLabel scoreLabel;
  private JLabel highScoreLabel;
  
  private JButton newgame;
 
  private Text gameOverText;
  private Text winText;
  private Text pauseText;

  private boolean gameOver;
  private boolean won;
  private boolean paused;

  private Snake snake;
 
  private FilledRect apple;

  private Random randomIndexGenerator = new Random();



  /** main() generates the canvas that we will use
   *
   *  @param args The arguments we passed in from the command line
   */
  public static void main( String[] args ) {

    // Construct the canvas
    new Acme.MainFrame( new SnakeController(), args, FRAME_WIDTH, 
                        FRAME_HEIGHT );
  }// end of main()


  /** This constructor receives arguments from the user at the console and 
   *  passes them into the Snake contructor
   *
   *  @param args the user entered arguments for the Snake game
   */
  public SnakeController( String[] args ) {

  }// end of SnakeController ctor


  /** Method actionPerformed() is the event handler for mouse clicks on the
   *  New Game button.
   */
  public void actionPerformed( MouseEvent e ) {
 
  }// end of actionPerformed()
  


  /** Method keyPressed() is the event handler for certain keystrokes in this
   *  game.
   *
   *  @param e the key event
   */
  public void keyPressed( keyEvent e ) {
  
  }// end of keyPressed()


  /** setGUI() adds the GUI elements to the program, such as buttons and labels
   */
  public setGUI() {

    northPanel = new JPanel();

    scoreLabel = new JLabel( "Score: "+score, Font.BOLD, FONT_SIZE );
    highScoreLabel = new JLable( "High Score: "+ highScore, Font.BOLD,
                                 FONT_SIZE );

    newGame = new JButton( "New Game" );

    newGame.addActionListener( this );

    northPanel.setLayout( new GridLayout( NUM_TOP_ROWS, NUM_TOP_COLUMNS );

    northPanel.add( scoreLabel );
    northPanel.add( highScoreLabel );

    Container contentPane = getContentPane();

    contentPane.add( northPanel, BorderLayout.NORTH );
    contentPane.add( newGame, BorderLayout.SOUTH );

    this.validate();
  }// end of setGUI()


}// end of SnakeController class
