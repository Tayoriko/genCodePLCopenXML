package genSystemFunctions;

import genSystemFunctions.subClasses.ConvertToTime;
import uniqueItems.OneVar;

public class SysPulse {

    private String name = "pulse";
    private ConvertToTime toTime = new ConvertToTime();
    private OneVar oneVar = new OneVar();

    public SysPulse () {
    }

    public SysPulse (String time) {
        this.name += time;
    }

    public String getName() {
        return name;
    }

    public void setFullTime (int day, int hour, int minute, int sec, int ms) {
        toTime = new ConvertToTime(day, hour, minute, sec, ms);
        oneVar.setComment("Internal system " + name + " T#" + toTime.getTime());
        name += toTime.getTime();
        oneVar.setName(this.name);
        oneVar.setType("bool");
    }

    public String getFullTime () {
        return name;
    }

    public OneVar getOneVar () {
        return oneVar;
    }

    public void setName(String name) {
        this.name = name;
    }
}
