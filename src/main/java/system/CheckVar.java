package system;

import enumerations.listBaseTypes;
import enumerations.listCustomTypes;

import java.util.Arrays;

/**
 * Special class for check of new variable names
 */
public class CheckVar {
    public Check checkName(String data){
        Check<String> check = new Check<>(data);
        //Check empty String
        if (check.getValue().isEmpty()) {
            check.setError();
            check.addMessage("Empty variable name.");
        }
        //Check correct regex
        if (!check.getValue().matches(RegexBase.regexVarName)) {
            check.setError();
            check.addMessage("Incorect variable name: " + data + ".");
        }
        //Error
        if (check.isError()) {
            String message = "system.CheckVar().checkName() -> ";
            System.out.println(message + check.getMessage());
        }
        //Result
        return check;
    }

    public Check checkType(String data){
        Check<String> check = new Check<>(data);
        //Check empty String
        if (check.getValue().isEmpty()) {
            check.setError();
            check.addMessage("Empty variable type.");
        }
        //Check correct variable type
        String dataType = "";
        if (data.length() <= 5) {
            String lowercaseInput = data.toLowerCase();
            //listBaseTypes result = listBaseTypes.empty;
            for (listBaseTypes e : listBaseTypes.values()) {
                if (e.getValue().equals(lowercaseInput)) {
                    dataType = e.getValue();
                    break;
                }
            }
        }
        if (data.length() > 5){
            //listCustomTypes result = listCustomTypes.empty;
            for (listCustomTypes e : listCustomTypes.values()) {
                if (e.getValue().equals(data)) {
                    dataType = e.getValue();
                    break;
                }
            }
        }
        if (dataType.equals(RegexBase.empty)) {
            check.setError();
            check.addMessage("Incorect variable type: " + data + ".");
        }
        //Error
        if (check.isError()) {
            String message = "system.CheckVar().checkType() -> ";
            System.out.println(message + check.getMessage());
        }
        //Result
        return check;
    }

    public Check checkComm(String data){
        Check<String> check = new Check<>(data);
        //Check empty String
        if (check.getValue().isEmpty()) {
            check.setError();
            check.addMessage("Empty variable comment.");
        }
        //Check correct regex
        if (!check.getValue().matches(RegexBase.regexVarComment)) {
            check.setError();
            check.addMessage("Incorect variable comment: " + data + ".");
        }
        //Error
        if (check.isError()) {
            String message = "system.CheckVarComm().check() -> ";
            System.out.println(message + check.getMessage());
        }
        //Result
        return check;
    }

    public Check check(String[] value){
        Check<String[]> check = new Check<>(value);
        Check<String>[] resultOfCheck = new Check[3];
        resultOfCheck[0] = checkName(value[0]);
        resultOfCheck[1] = checkType(value[1]);
        resultOfCheck[2] = checkComm(value[2]);
        //Check correct value
        for (Check<String> stringCheck : resultOfCheck) {
            if (stringCheck.isError()) {
                check.setError();
                //check.addMessage(resultOfCheck[i].getMessage() + "\n");
            }
        }
        //Error
        if (check.isError()) {
            check.addMessage("system.CheckVar().check() -> " + Arrays.stream(value).toList() + "\n");
            System.out.println(check.getMessage());
        }
        //Result
        return check;
    }
}
