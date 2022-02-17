import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * this class extends jpanel and is held by the calandar frame
 * is used to encapsulate all of the view componests for the moth view
 */
public class MonthPanel extends JPanel {

    private DataModel myDataModel;
    private LocalDate currentDate;
    private ArrayList<MonthDayButton> dateButtons;
    private JPanel buttonPanel;
    private JLabel header;

    /**
     * this is a constructor used by calandar frame to get a new month panel object
     * @param dm the data model to be referenced when getting data
     */
    public MonthPanel(DataModel dm){
        myDataModel = dm;
        currentDate = myDataModel.getCurrentDate();
        dateButtons = new ArrayList<>();
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6,7));
        for(int i = 0; i < 42; i++){
            MonthDayButton temp = new MonthDayButton(LocalDate.MAX,false,false,myDataModel);
            dateButtons.add(temp);
            buttonPanel.add(temp);
        }
        header = new JLabel(currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")) + "     MON - SUN");


        populateValues();

        this.setLayout(new BorderLayout());
        this.add(header,BorderLayout.NORTH);
        this.add(buttonPanel,BorderLayout.CENTER);

    }

    /**
     * this is a private member function used to update the values of the day month buttons
     * it edits all of the button fields
     */
    private void populateValues(){
        header.setText(currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")) + "     MON - SUN");
        //populate each button depending on the month
        Month temp = currentDate.getMonth();
        GregorianCalendar tempGC = new GregorianCalendar();
        LocalDate firstDate = LocalDate.of(currentDate.getYear(),temp.getValue(),1);
        LocalDate lastDate =
                LocalDate.of(currentDate.getYear(), temp.getValue(),temp.length(tempGC.isLeapYear(currentDate.getYear())));

        int dayCount = 0;
        int defaultDayCount = 0;
        LocalDate tempDay = firstDate;
        //enum goes from 1 to 7
        //gotta subtract 1 from enum value cuz for loop starts at 0
        for(int i = 0; i < firstDate.getDayOfWeek().getValue() - 1; i ++){
            dateButtons.get(i).makeDefaultButton();
            dayCount++;
            defaultDayCount++;
        }

        for(int i = dayCount; i < lastDate.getDayOfMonth() + defaultDayCount; i++){
            dateButtons.get(i).removeAllActionListeners();
            dateButtons.get(i).setButtonDate(tempDay);
            dateButtons.get(i).setIsActive(true);
            if(tempDay.equals(currentDate)){
                dateButtons.get(i).setIsCurrentDate(true);
            } else {
                dateButtons.get(i).setIsCurrentDate(false);
            }
            tempDay = tempDay.plusDays(1);
            dayCount++;
        }

        for(int i = dayCount; i < 42; i++ ){
            dateButtons.get(i).makeDefaultButton();
            dayCount++;
            defaultDayCount++;
        }

    }

    /**
     * this is a method called by the calandar frame change listener to initiate the update of the month panel
     */
    public void updateView(){
        currentDate = myDataModel.getCurrentDate();
        populateValues();
    }
}
