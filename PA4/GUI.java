//Import Statements
import java.lang.StringBuilder;//Used for toString()
import java.lang.Integer;// used for Integer.parseInt()
import java.util.Arrays;//Used for Arrays.sort
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;//needed for ChangeListener
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.BoxLayout;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GUI{
	//Global Variables
    private static int currentSpeed = 50;
    private static ChangeListener sliderListener;
	//GUI COMPONENTS
	//ButtonPanel
	private static JButton startBtn;
	private static JButton stopBtn;
    private static JButton clearBtn;
	private static JLabel buttonLabel;
	//SliderPanel
	private static JLabel speedLabel;
    private static JSlider speedSlider;
	

	//Constructor
	public GUI(){
		//does nothing
	}//END Consturctor

	//main()
	public static void main(String[] args){
		//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI.createAndShowGUI();
            }
        });
		
	}//END main()

	//GUI FXNS START///////////////////////////////////////////////////////////////////////////////////////////////////////////
	//createAndShowGUI():
	public static void createAndShowGUI() {
        JFrame frame = new JFrame("GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComponent contentPane = GUI.createContentPane();
        frame.setContentPane(contentPane);
        frame.setLocation(300, 200);
        //frame.setPreferredSize(new Dimension(1000, 800));
        frame.pack();
        frame.setVisible(true);
    }//END createAndShowGUI()

    //createContentPane:
    private static JComponent createContentPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        // add all the components to the content pane
        panel.add(GUI.createButtonPanel());
        panel.add(GUI.createSliderPanel());
        return panel;
    }//END createContentPane()

    //createGraphPanel:
    private static JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        
        GUI.startBtn = new JButton("Start");
        GridBagConstraints cStart = new GridBagConstraints();
        cStart.gridx = 0;
        cStart.gridy = 1;
        GUI.stopBtn = new JButton("Stop");
        GridBagConstraints cStop = new GridBagConstraints();
        cStop.gridx = 1;
        cStop.gridy = 1;
        GUI.clearBtn = new JButton("Clear All");
        GridBagConstraints cClear = new GridBagConstraints();
        cClear.gridx = 2;
        cClear.gridy = 1;
        GUI.buttonLabel = new JLabel("Resizable PizzaSlice Controller");
        GridBagConstraints cLabel = new GridBagConstraints();
        cLabel.gridx = 0;
        cLabel.gridy = 0;
        cLabel.gridwidth = 3;

        panel.add(GUI.buttonLabel,cLabel);
        panel.add(GUI.startBtn, cStart);
        panel.add(GUI.stopBtn, cStop);
        panel.add(GUI.clearBtn, cClear);
        //change panel color
        panel.setBackground(Color.gray);
        return panel;
    }//END createGraphPanel()

    //createSliderPanel:
    private static JPanel createSliderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        //label config
        GUI.speedLabel = new JLabel("The speed is " + Integer.toString(GUI.currentSpeed));
        //slider config
        GUI.speedSlider = new JSlider();
        GUI.sliderListener = new SliderListener();
        GUI.speedSlider.addChangeListener(GUI.sliderListener);
        //Add components to panel
        panel.add(GUI.speedLabel);
        panel.add(GUI.speedSlider);
        //change panel color
        panel.setBackground(Color.gray);
        return panel;
    }//END createSrcNodePanel()
	//GUI FXNS END/////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//HELPER FXNS START////////////////////////////////////////////////////////////////////////////////////////////////////////
	//HELPER FXNS END//////////////////////////////////////////////////////////////////////////////////////////////////////////

	//GENERAL FXNS START///////////////////////////////////////////////////////////////////////////////////////////////////////
	//GENERAL FXNS END/////////////////////////////////////////////////////////////////////////////////////////////////////////

	//STATIC FXNS START////////////////////////////////////////////////////////////////////////////////////////////////////////
	//STATIC FXNS END//////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static class SliderListener implements ChangeListener {
        public SliderListener(){
            super();
        }//END Constructor
        public void stateChanged(ChangeEvent e){
            GUI.currentSpeed = GUI.speedSlider.getValue();
            GUI.speedLabel.setText("The speed is " + Integer.toString(GUI.currentSpeed));
        }//END stateChanged()
    }//END SliderListener class
}//END GraphBuilder Class