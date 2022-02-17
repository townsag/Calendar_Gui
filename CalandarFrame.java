import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * this class is meant to be the primary frame for this application
 *
 * it holds the grid of month-day buttons as well as all of the buttons
 * which perform the various operations for the frame
 *
 * it also holds the view which shows the events for a day
 *
 * it also holds a panel with create, previous, next, and quit buttons
 *
 * this class has four inner classes, they are action listeners for the four buttons
 * held by the class
 */
public class CalandarFrame extends JFrame implements ChangeListener{

    private DataModel myModel;
    private MonthPanel myMonth;
    private DayPanel myDay;

    /**
     * constructor used by main to get an instance of the calanar frame
     * this method instantiates all of the swing components and action listeners
     * required to make the view and controller portions of the project
     * @param dm reference of a data model to get information from and use
     *           the modifiers of
     */
    public CalandarFrame(DataModel dm){
        myModel = dm;
        setLayout(new BorderLayout());

        //add the four buttons at the top
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton createButton = new JButton("create");
        JButton prevButton = new JButton("prev");
        JButton nextButton = new JButton("next");
        JButton quitButton = new JButton("quit");

        createButton.addActionListener(new CreateButtonListener());
        prevButton.addActionListener(new PrevButtonListener());
        nextButton.addActionListener(new NextButtonListener());
        quitButton.addActionListener(new QuitButtonListener());

        buttonPanel.add(createButton);
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(quitButton);

        //add the month view panel
        myMonth = new MonthPanel(myModel);
        //add the day view panel
        myDay = new DayPanel(myModel);


        this.add(buttonPanel,BorderLayout.NORTH);
        this.add(myMonth,BorderLayout.WEST);
        this.add(myDay,BorderLayout.EAST);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.pack();

    }

    /**
     * this is the state changed function that is called by data model
     * @param e the reference of the data model which calls state changed
     */
    @Override
    public void stateChanged(ChangeEvent e) {

        myMonth.updateView();
        myDay.updateView();
        this.setVisible(myModel.isCalandarFrameVisible());
        this.repaint();

        if (myModel.isCalandarFrameClosed()){
            this.dispose();
        }
    }

    /**
     * this is the action listener class specifically for the create button
     * it makes the create event frame visible
     */
    public class CreateButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            myModel.setAddEventFrameVisible(true);
        }
    }

    /**
     * this is the action listener associated with the previous button
     * it changes the current date of the data model
     */
    public class PrevButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            myModel.setCurrentDate(myModel.getCurrentDate().minusDays(1));
        }
    }

    /**
     * this is the action listener class associated with the next button
     * it changes the current date of the data model
     */
    public class NextButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            myModel.setCurrentDate(myModel.getCurrentDate().plusDays(1));
        }
    }

    /**
     * this is the action listener class of the quit button
     * it uses mutators to set which frames are closed
     * it also writes the contents of the array list to a file
     */
    public class QuitButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            myModel.printEvents();
            myModel.writeToFile();
            myModel.setCalandarFrameClosed(true);
            myModel.setAddEventFrameClosed(true);
        }
    }
}
