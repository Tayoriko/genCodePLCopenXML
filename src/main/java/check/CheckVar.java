package check;

import enums.eRegex;

/**
 * Special class for check of new variable names
 */
public class CheckVar {
    //Check device name
    public Check checkDevName(String data){
        Check<String> check = new Check<>(data);
        //Check EMPTY String
        if (check.getValue().isEmpty()) {
            check.setError();
            check.addMessage("Empty device name.");
        }
        //Check correct regex
        if (!check.getValue().matches(String.valueOf(eRegex.devName))) {
            check.setError();
            check.addMessage("Incorect device name: " + data + ".");
        }
        //Error
        if (check.isError()) {
            String message = "system.CheckVar().checkDevName() -> ";
            System.out.println(message + check.getMessage());
        }
        //Result
        return check;
    }

}
