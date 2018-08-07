/* Name:  Robert Chance
 * Login: cs11faay
 * PID:   A13088857
 * Date:  11/15/15
 *
 */

import java.awt.*;
import objectdraw.jar;

/** Class Snake generates a snake on the canvas and contains methods to control
 *  the movement of the snake.
 */
public class Snake extends ActiveObject implements KeyListener {

  private static final int GROW_BY = 1;

  private int leftToGrow;
  
  private int size;
  private int delay;
  private DrawingCanvas canvas;

  private Direction currentDir;

  private boolean isRunning = false,
                  pause = false;

  private Coordinate nextApple;

  ArrayList<SnakeSegment> snake;

  SnakeSegment head;

  SnakeController controller;


  /** Snake ctor generates a snake on the canvas, made of individual segments
   */
  public Snake( Coodinate coord, int size, int delay, DrawingCanvas canvas,
                SnakeController controller ) {

  }// end Snake ctor

  /** Method move() will move the snake in the appropriate direction and if
   *  an appple was consumed, a new body segment will be added.
   *
   *  @return will return true if move is good, false if move ends in game over
   */
  private boolean move() {
 
  }// end move()



  /** keyPressed() is the event handler for pressing a keyboard key.  It will 
   *  be used to control the movements of the snake on the canvas.
   */
  public void keyPressed( KeyEvent e )



  /** Class SnakeSegment will generate a snake segment on the canvas when 
   *  invoked.
   */
  private class SnakeSegment {

    // visible appearance of the snake
    private final Color SNAKE_COLOR = color.GREEN;
    private final Color SNAKE_OUTLINE = Color.BLACK;
    private FilledArc Segment;
 
    // the location of each snake segment
    private Coordinate coord;

    // head constants
    private static final double UP_ANGLE = 90 + 22.5;
    private static final double LEFT_ANGLE = 90 + UP_ANGLE;
    private static final double DOWN_ANGLE = 90 + LEFT_ANGLE;
    private static final double RIGHT_ANGLE = 90 + DOWN_ANGLE;
    private static final double HEAD_ARC_ANGLE = 360 - 45;
    private double BODY_ARC_ANGLE = 360;

    /** SnakeSegment ctor creates a snake segment on the canvas
     */
    public SnakeSegment( Coordinate coord, int size, boolean isHead,
                         DrawingCanvas canvas ) {

    }// end of SnakeSegment ctor
  }// end of class SnakeSegment
}// end of class Snake










   
