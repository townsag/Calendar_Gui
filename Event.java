import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * this is the event class meant to represent a single event
 * it stores the start time, end time, date, and name of the event
 */
public class Event implements Comparable<Event>, Serializable {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String name;

    /**
     * this is the constructor used to get a new event object
     * @param d local date representing the date which the event falls on
     * @param sTime local time representing the time that the event starts
     * @param eTime local time representing the time that the evnt ends
     * @param n string representing the name of the event
     */
    public Event(LocalDate d, LocalTime sTime, LocalTime eTime, String n){
        date = d; startTime = sTime; endTime = eTime; name = n;
    }

    public LocalDate getDate(){ return date; }
    public LocalTime getStartTime(){ return startTime; }
    public LocalTime getEndTime(){ return endTime; }
    public String getName(){ return name; }

    /**
     * used for both code testing and for building the day view string
     * @return a one line string representing that event
     */
    public String toString(){
        String result = "";
        result += name + ": " + date.toString() + " ";
        result += startTime.format(DateTimeFormatter.ofPattern("HH:mm")) + " - ";
        result += endTime.format(DateTimeFormatter.ofPattern("HH:mm")) + "\n";

        return result;
    }

    /**
     * used for code testing and debugging
     * prints the event to the terminal
     */
    public void print(){
        DateTimeFormatter tempDate = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter tempTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("Event name: " + name);
        System.out.println("Date: " + date.format(tempDate));
        System.out.println("Start Time: " + startTime.format(tempTime));
        System.out.println("End Time: " + endTime.format(tempTime));

    }

    /**
     * overides the compare to function so that events can be ordered in the array list and so that
     * they can be compared to check for any overlap between events
     * @param other the event being compared to the implicit parameter event
     * @return -1 for lowest to highest, 0 for overlap, +1 for highest to lowest
     */
    @Override
    //-1 if x < y
    //+1 if x > y
    public int compareTo(Event other) {
        if (this.date.equals(other.getDate())){
            //if B is less than(comes before) C
            if(this.endTime.compareTo(other.getStartTime()) <= 0){                //no overlap and this is before other
                //return this.endTime.compareTo(other.getStartTime());
                return -1;
            }
            //if A is greater than(comes after) D
            else if (this.startTime.compareTo(other.getEndTime()) >= 0){   //no overlap and other is before this
                //return this.startTime.compareTo(other.getEndTime());
                return +1;
            }
            else {                                                        //overlap
                //if i get here then two things are true
                // - the implicit event ends after the explicit event starts
                // - the explicit event ends after the implicit event starts
                return 0;
            }
        } else {
            return date.compareTo(other.getDate());
        }
    }
}
