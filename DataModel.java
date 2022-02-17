import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

/**
 * this is the data model class, it satisfies the model portion of the program
 * it holds an array of events and an array of change listeners
 * also it holds the current day
 */
public class DataModel {
    private ArrayList<Event> myEvents;
    private LocalDate currentDate;
    private ArrayList<ChangeListener> myListeners;

    private String sourceFile;

    private boolean isCalandarFrameVisible;
    private boolean isAddEventFrameVisible;

    private boolean isCalandarFrameClosed;
    private boolean isAddEventFrameClosed;

    /**
     * this is the constructor which allocates memory for the variouse objects in the data model
     * it also reads from a file the contents of the events array
     * @param sf this is the string file path representing the location of the file that hold the
     *           serialized array list
     */
    public DataModel(String sf){
        //myEvents = new ArrayList<>();
        myListeners = new ArrayList<>();

        sourceFile = sf;

        isCalandarFrameVisible = true;
        isAddEventFrameVisible = false;

        //add deserialization here
        try{
            FileInputStream input = new FileInputStream(sourceFile);
            ObjectInputStream oInput = new ObjectInputStream(input);
            myEvents = (ArrayList<Event>) oInput.readObject();

        } catch (IOException ioe){
            ioe.printStackTrace();
            myEvents = new ArrayList<>();
        } catch(ClassNotFoundException ce){
            ce.printStackTrace();
            myEvents = new ArrayList<>();
        }
    }

    /**
     * this method is a mutator which adds an event to the array list
     * it is called by the action listeners in add event frame
     * once the event is added the change listeners are notified
     * @param e the event to be added
     */
    public void addEvent(Event e){
        myEvents.add(e);
        Collections.sort(myEvents);
        notifyListeners();
    }

    /**
     * this is an accessor that tells calaendar frame if it should paint itself
     */
    public boolean isCalandarFrameVisible() {return isCalandarFrameVisible;}

    /**
     * this is a mutator called by a change listener so that the visibility of calendar frame can be changed
     * @param calandarFrameVisible flag representing yes or no
     */
    public void setCalandarFrameVisible(boolean calandarFrameVisible) {
        isCalandarFrameVisible = calandarFrameVisible;
        notifyListeners();
    }

    /**
     * this is an accessor which tells event frame if it should paint itself
     * @return boolean representing yes and no
     */
    public boolean isAddEventFrameVisible() {return isAddEventFrameVisible;}

    /**
     * this is an accessor called by change listeners to change the visibility of the add event frame
     * @param addEventFrameVisible flag representing yes or no
     */
    public void setAddEventFrameVisible(boolean addEventFrameVisible) {
        isAddEventFrameVisible = addEventFrameVisible;
        notifyListeners();
    }

    /**
     * this is an accessor which tells the calendar frame if it should .dispose() itself
     * @return flag representing yes or no
     */
    public boolean isCalandarFrameClosed() {return isCalandarFrameClosed;}

    /**
     * this is a mutator called by change listeners to change the status of the calendar frame
     * @param calandarFrameClosed flag representing yes or no
     */
    public void setCalandarFrameClosed(boolean calandarFrameClosed) {
        isCalandarFrameClosed = calandarFrameClosed;
        notifyListeners();
    }

    /**
     * this is an accessor which tells event frame if it should .dispose() itself
     * @return flag representing yes or no
     */
    public boolean isAddEventFrameClosed() {return isAddEventFrameClosed;}

    /**
     * this is a mutator that change listeners can use to set the status of the event frame
     * @param addEventFrameClosed flag representing yes or no
     */
    public void setAddEventFrameClosed(boolean addEventFrameClosed) {
        isAddEventFrameClosed = addEventFrameClosed;
        notifyListeners();
    }

    /**
     * this is called by the save button action listener to verify the validity of a temp event
     * @param otherE the event to be compared to the existing events
     * @return a boolean value, true for conflicts and false for does not conflict
     */
    public boolean checkConflict(Event otherE){
        for(Event e : myEvents){
            if(e.compareTo(otherE) == 0){
                return true;
            }
        }
        return false;
    }

    /**
     * this was mostly a debugging tool
     */
    public void printEvents(){
        Collections.sort(myEvents);
        for(Event e : myEvents){
            e.print();
        }
    }

    /**
     * this is called by the quit button action listener
     * it saves the array list of events to a file
     */
    public void writeToFile(){
        try{
            FileOutputStream out = new FileOutputStream(sourceFile);
            ObjectOutputStream tempStream = new ObjectOutputStream(out);
            tempStream.writeObject(myEvents);
            tempStream.close();
            out.close();

        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * this is called by the day view panel to display all the events for that day
     * @return a long string representing all of the events for that day
     */
    public String getDayEvents(){
        String result = "";

        for(Event e : myEvents){
            if(e.getDate().equals(currentDate)){
                result += e.toString();
            }
        }

        return result;
    }

    /**
     * this method is intended to be called by the buttons which are part of the month view
     * repaints all of the panels which are listening
     * @param updatedDate the date associated with the button that was pressed or the date
     *                    given to data model by the next or prev buttons
     */
    public void setCurrentDate(LocalDate updatedDate){
        currentDate = updatedDate;
        notifyListeners();
    }

    /**
     * mutator used to add a change listener to the data model
     * @param cl the change listener to be added
     */
    public void addChangeListener(ChangeListener cl){
        myListeners.add(cl);
    }

    /**
     * accessor used by some of the view componets to get the date to be displayed
     * @return local date representing the current date(the date that the user is viewing)
     */
    public LocalDate getCurrentDate(){
        return currentDate;
    }

    /**
     * this is called by all of the data model functions whenever anything is changed in the data model so that
     * the change listeners can reflect that change
     */
    private void notifyListeners(){
        for(ChangeListener cl : myListeners){
            cl.stateChanged(new ChangeEvent(this));
        }
    }


}
