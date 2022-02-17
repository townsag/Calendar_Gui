import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * this is a panel held by the calendar frame used to display information about the current date(the date the user wants
 * to view)
 * holds a label that displays the name of the current day and a text area that holds the content of the current dat
 */
public class DayPanel extends JPanel{

    private DataModel myModel;
    private LocalDate currentDate;
    private JLabel headerLabel;
    private JTextArea eventDisplay;

    /**
     * constructor called by calendar frame to get an instance of the day panel
     * @param myDM the data model to be referenced for information to be displayed
     */
    public DayPanel(DataModel myDM){
        myModel = myDM;
        currentDate = myModel.getCurrentDate();

        headerLabel = new JLabel();
        headerLabel.setText(currentDate.format(DateTimeFormatter.ofPattern("EEEE MM/dd")));
        eventDisplay = new JTextArea(20,40);
        eventDisplay.setText(myModel.getDayEvents());

        this.setLayout(new BorderLayout());
        this.add(headerLabel, BorderLayout.NORTH);
        this.add(eventDisplay,BorderLayout.CENTER);
    }

    /**
     * called by the state changed function in the calendar frame class to edit the view so that it
     * reflects any changes made in the data model
     */
    public void updateView(){
        currentDate = myModel.getCurrentDate();
        headerLabel.setText(currentDate.format(DateTimeFormatter.ofPattern("EEEE MM/dd")));
        eventDisplay.setText(myModel.getDayEvents());
    }
}
