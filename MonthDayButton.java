import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * this is a custom button class that extends JButton
 * it is held by the calandar frame and used to update datamodel
 */
public class MonthDayButton extends JButton {

    private static final int ICON_WIDTH = 30;

    private LocalDate buttonDate;
    private boolean isCurrentDate;
    private boolean isActive;
    private boolean isDefault;

    private DataModel myDataModel;

    /**
     * this is a constructor used by month panel to get a new button
     * @param bd the date associated with that specific button
     * @param iCD the flag for if this button is the current button
     * @param iA the falg for if this button is active
     * @param mDM the reference of the data model that the button updates
     */
    public MonthDayButton(LocalDate bd, boolean iCD, boolean iA, DataModel mDM){
        buttonDate = bd; isCurrentDate = iCD; isActive = iA; myDataModel = mDM;
        //setPreferredSize(new Dimension(ICON_WIDTH,ICON_WIDTH));
    }

    /**
     * this is called by the state changed method in the month panel class to reset each button when the current date
     * is changed
     * this function removes any action listeners that are specific to an instance of the button from the button
     */
    public void removeAllActionListeners(){
        ActionListener[] tempArray = this.getActionListeners();
        if(tempArray != null) {
            for (int i = 0; i < tempArray.length; i++) {
                this.removeActionListener(tempArray[i]);
            }
        }

        /*
        if(tempArray.length != 0 && tempArray != null) {
            ArrayList<ActionListener> tempListenerList = (ArrayList<ActionListener>) Arrays.asList(tempArray);
            for (ActionListener al : tempListenerList) {
                this.removeActionListener(al);
            }
        }*/
    }

    /**
     * mutator that changes the date and color and text of the button
     * @param bd
     */
    public void setButtonDate(LocalDate bd){
        buttonDate = bd;
        this.setText(buttonDate.format(DateTimeFormatter.ofPattern("dd")));
        setBackground(Color.WHITE);
    }

    /**
     * mutator that changes the isActive flag. if the flag is set then an action listener is added to the button
     * @param flag represents yes or no
     */
    public void setIsActive(boolean flag){
        isActive = flag;
        if (flag){
            addActionListener(new CustomActionListener());
        }
    }

    /**
     * mutator that changes the current date flag. if the flag is set then the color is set to green
     * @param flag represents yes or no
     */
    public void setIsCurrentDate(boolean flag){
        isCurrentDate = flag;
        if(flag){
            setBackground(Color.GREEN);
            setOpaque(true);
        }
    }

    /**
     * accessor that returns the date associated with the instance of the button
     * @return the Local date object held by the button
     */
    public LocalDate getButtonDate(){
        return buttonDate;
    }

    /**
     * accessor used to get the status of the button
     * @return flag representing active or not active
     */
    public boolean getIsActive(){
        return isActive;
    }

    /**
     * public method used to make a button into a gray button with no action listeners
     */
    public void makeDefaultButton(){
        removeAllActionListeners();
        setButtonDate(LocalDate.MAX);
        setIsActive(false);
        setIsCurrentDate(false);
        setText("");
        setBackground(Color.LIGHT_GRAY);
    }

    /**
     * action listener class specifically created for the buttons
     * can modify the data model to change the current day to the local date associated with the button which is presssed
     */
    private class CustomActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            myDataModel.setCurrentDate(MonthDayButton.this.buttonDate);
            System.out.println("new date: " + buttonDate.toString());
        }
    }
}
