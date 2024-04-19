package uniqueItems;

import enumerations.eVarAllocate;
import enumerations.eVarType;

public class Address {
    private boolean enable = false;
    private eVarType varType = eVarType.EMPTY;
    private eVarAllocate varAllocate = eVarAllocate.EMPTY;
    private int word = 0;
    private int bit = 0;
    private String address = "";

    public Address (){}

    public Address (eVarType varType, eVarAllocate varAllocate, int word) {
        //constructor for all type, except BOOL
        this.enable = true;
        this.varType = varType;
        this.varAllocate = varAllocate;
        this.word = word;
        this.address = genAddress();
    }

    public Address (eVarType varType, eVarAllocate varAllocate, int word, int bit) {
        //constructor only for BOOL
        this.enable = true;
        this.varType = varType;
        this.varAllocate = varAllocate;
        this.word = word;
        this.bit = bit;
        this.address = genAddressBool();
    }

    private String genAddress ()
    {
        //generate string with correct variable address
        return "%" + varAllocate.getValue() + varType.getAddr() + word;
    }

    private String genAddressBool ()
    {
        //generate string with correct variable address
        return "%" + varAllocate.getValue() + varType.getAddr() + word + "." + bit;
    }

    public void deleteAddress()
    {
        this.enable = false;
        this.address = "";
    }

    public void updateAddress(){
        if (varType.getValue().equals(eVarType.BOOL.getValue())) {
            address = genAddressBool();
        }
        else {
            address = getAddress();
        }
    }

    public boolean isEnable() {
        return enable;
    }

    public eVarType getVarType() {
        return varType;
    }

    public void setVarType(eVarType varType) {
        this.varType = varType;
    }

    public eVarAllocate getVarAllocate() {
        return varAllocate;
    }

    public void setVarAllocate(eVarAllocate varAllocate) {
        this.varAllocate = varAllocate;
    }

    public int getWord() {
        return word;
    }

    public void setWord(int word) {
        this.word = word;
    }

    public int getBit() {
        return bit;
    }

    public void setBit(int bit) {
        this.bit = bit;
    }

    public String getAddress() {
        return address;
    }
}
