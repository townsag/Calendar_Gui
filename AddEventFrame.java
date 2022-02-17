import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * this is a secondary frame intended to perform the function of collecting the information required to add a new
 * event to the data model
 *
 * this frame holds a number of text fields to gather infomration from the user as well as a text area used to display
 * instructions
 *
 * this frame is a change listener which gets its information from the data model class
 */
public class AddEventFrame extends JFrame implements ChangeListener {

    private DataModel myModel;
    private JTextField nameField;
    private JLabel dateLabel;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JButton saveButton;
    private static ArrayList<String> instructionList;
    private JTextArea instructionsArea;

    /**
     * this is a constructor called by the tester class used to create a new add event frame
     * @param myDM the reference of a data model object, used to call accessor and mutator functions
     */
    public AddEventFrame(DataModel myDM){
        myModel = myDM;
        dateLabel = new JLabel();
        startTimeField = new JTextField("12:30",5);
        endTimeField = new JTextField("13:30",5);
        saveButton = new JButton("Save");
        instructionList = new ArrayList<>(Arrays.asList("Please enter your\nevent details",
                "Please enter a\nvalid event","This event conflicts\nwith another event"));
        instructionsArea = new JTextArea(2,19);


        saveButton.addActionListener(new SaveButtonListener());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        nameField = new JTextField("your event name here",20);
        topPanel.add(nameField);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        dateLabel.setText(myModel.getCurrentDate().format(DateTimeFormatter.ofPattern("LLLL dd, yyyy")) + ": ");
        bottomPanel.add(dateLabel);
        bottomPanel.add(startTimeField);
        bottomPanel.add(endTimeField);
        bottomPanel.add(saveButton);

        JPanel instructionsPanel = new JPanel();
        instructionsArea.setText(instructionList.get(0));
        instructionsPanel.add(instructionsArea);

        this.setLayout(new BorderLayout());
        this.add(topPanel,BorderLayout.NORTH);
        this.add(bottomPanel,BorderLayout.CENTER);
        this.add(instructionsPanel,BorderLayout.WEST);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(false);
        this.pack();
    }


    /**
     * this is the state changed function that is called by the data model
     * it updates the apperance of the frame based on what is required
     * the frame is made visible based on a flag in the data model
     * the frame is closed based on a flag in the data model
     * @param e the reference of the data model being sent
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        dateLabel.setText(myModel.getCurrentDate().format(DateTimeFormatter.ofPattern("LLLL dd, yyyy")) + ": ");
        //change the text box
        this.setVisible(myModel.isAddEventFrameVisible());
        this.repaint();

        if (myModel.isAddEventFrameClosed()){
            this.dispose();
        }
    }

    /**
     * this is the action listener intended to be attached to the Save JButton
     * this action listener adjusts the prompt if the input doesnt satisfy the
     * event format requirements or if the event conflicts with another event
     * if the event passes then the listener uses a mutator in model to update
     * the event array list
     */
    private class SaveButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //update the data model here
            DateTimeFormatter tempFormatter = DateTimeFormatter.ofPattern("HH:mm");
            String tempName = nameField.getText();
            LocalDate tempDate = myModel.getCurrentDate();
            LocalTime tempStart = LocalTime.parse(startTimeField.getText(),tempFormatter);
            LocalTime tempEnd = LocalTime.parse(endTimeField.getText(),tempFormatter);

            //check the data that was entered
            //first check that the start time is before the end time
            if(tempEnd.isBefore(tempStart)){
                instructionsArea.setText(instructionList.get(1));
                AddEventFrame.this.repaint();
                return;
            }

            //next check that its not a duplicate
            Event tempEvent = new Event(tempDate,tempStart,tempEnd,tempName);
            if (myModel.checkConflict(tempEvent)){
                instructionsArea.setText(instructionList.get(2));
                AddEventFrame.this.repaint();
                return;
            }

            instructionsArea.setText(instructionList.get(0));
            myModel.addEvent(tempEvent);
            myModel.setAddEventFrameVisible(false);

        }
    }
}
