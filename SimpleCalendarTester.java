import java.time.LocalDate;

public class SimpleCalendarTester {

    public static void main(String[] args){

        //String sourceFile = "C:\\Users\\14083\\IdeaProjects\\Assignment5\\events.ser";
        String sourceFile = "/Users/andrewtownsend/IdeaProjects/CS151_HW5/events.ser";
        //String sourceFile2 = "/Users/andrewtownsend/IdeaProjects/CS151_HW5/src/events2.ser";

        DataModel tempModel = new DataModel(sourceFile);
        tempModel.setCurrentDate(LocalDate.now());

        CalandarFrame tempCF = new CalandarFrame(tempModel);
        AddEventFrame tempAEF = new AddEventFrame(tempModel);

        tempModel.addChangeListener(tempCF);
        tempModel.addChangeListener(tempAEF);


    }
}
