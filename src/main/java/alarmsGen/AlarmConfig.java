package alarmsGen;

public class AlarmConfig {
    private String category = "0: Category 0";
    private String priority = "Low";
    private String addressType = "Bit";
    private String plcNameRead = "CODESYS V3 (Ethernet)";
    private String dataTypeRead = "BOOL";
    private boolean systemTagRead = false;
    private boolean userDefinedTagRead = false;
    private String addressRead = "Application.GVL.warnOt_1";
    private String indexRead = null;
    private boolean enableNotification = false;
    private String condition = "bt: 1";
    private String triggerValue = "0";
    private String alarmMessage = "текст аварии";
    private boolean useLabelLibrary = false;
    private String labelName = "";
    private String font = "Droid Sans Fallback";
    private String color = "0:0:0";
    private int acknowledgeValue = 11;
    private boolean enableSound = true;
    private String soundLibraryName = "[ Project ]";
    private int soundIndex = 0;
    private boolean continuousBeep = false;
    private int timeIntervalOfBeeps = 10;

    // Метод инициализации
    AlarmConfig(String addressRead, String content) {
        this.addressRead = addressRead;
        this.alarmMessage = content;
    }

    // Геттеры и сеттеры для каждого поля
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getAddressType() { return addressType; }
    public void setAddressType(String addressType) { this.addressType = addressType; }

    public String getPlcNameRead() { return plcNameRead; }
    public void setPlcNameRead(String plcNameRead) { this.plcNameRead = plcNameRead; }

    public String getDataTypeRead() { return dataTypeRead; }
    public void setDataTypeRead(String dataTypeRead) { this.dataTypeRead = dataTypeRead; }

    public boolean isSystemTagRead() { return systemTagRead; }
    public void setSystemTagRead(boolean systemTagRead) { this.systemTagRead = systemTagRead; }

    public boolean isUserDefinedTagRead() { return userDefinedTagRead; }
    public void setUserDefinedTagRead(boolean userDefinedTagRead) { this.userDefinedTagRead = userDefinedTagRead; }

    public String getAddressRead() { return addressRead; }
    public void setAddressRead(String addressRead) { this.addressRead = addressRead; }

    public String getIndexRead() { return indexRead; }
    public void setIndexRead(String indexRead) { this.indexRead = indexRead; }

    public boolean isEnableNotification() { return enableNotification; }
    public void setEnableNotification(boolean enableNotification) { this.enableNotification = enableNotification; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public String getTriggerValue() { return triggerValue; }
    public void setTriggerValue(String triggerValue) { this.triggerValue = triggerValue; }

    public String getAlarmMessage() { return alarmMessage; }
    public void setAlarmMessage(String alarmMessage) { this.alarmMessage = alarmMessage; }

    public boolean isUseLabelLibrary() { return useLabelLibrary; }
    public void setUseLabelLibrary(boolean useLabelLibrary) { this.useLabelLibrary = useLabelLibrary; }

    public String getLabelName() { return labelName; }
    public void setLabelName(String labelName) { this.labelName = labelName; }

    public String getFont() { return font; }
    public void setFont(String font) { this.font = font; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getAcknowledgeValue() { return acknowledgeValue; }
    public void setAcknowledgeValue(int acknowledgeValue) { this.acknowledgeValue = acknowledgeValue; }

    public boolean isEnableSound() { return enableSound; }
    public void setEnableSound(boolean enableSound) { this.enableSound = enableSound; }

    public String getSoundLibraryName() { return soundLibraryName; }
    public void setSoundLibraryName(String soundLibraryName) { this.soundLibraryName = soundLibraryName; }

    public int getSoundIndex() { return soundIndex; }
    public void setSoundIndex(int soundIndex) { this.soundIndex = soundIndex; }

    public boolean isContinuousBeep() { return continuousBeep; }
    public void setContinuousBeep(boolean continuousBeep) { this.continuousBeep = continuousBeep; }

    public int getTimeIntervalOfBeeps() { return timeIntervalOfBeeps; }
    public void setTimeIntervalOfBeeps(int timeIntervalOfBeeps) { this.timeIntervalOfBeeps = timeIntervalOfBeeps; }

    @Override
    public String toString() {
        return "AlarmConfig{" +
                "category='" + category + '\'' +
                ", priority='" + priority + '\'' +
                ", addressType='" + addressType + '\'' +
                ", plcNameRead='" + plcNameRead + '\'' +
                ", dataTypeRead='" + dataTypeRead + '\'' +
                ", systemTagRead=" + systemTagRead +
                ", userDefinedTagRead=" + userDefinedTagRead +
                ", addressRead='" + addressRead + '\'' +
                ", indexRead='" + indexRead + '\'' +
                ", enableNotification=" + enableNotification +
                ", condition='" + condition + '\'' +
                ", triggerValue='" + triggerValue + '\'' +
                ", content='" + alarmMessage + '\'' +
                ", useLabelLibrary=" + useLabelLibrary +
                ", labelName='" + labelName + '\'' +
                ", font='" + font + '\'' +
                ", color='" + color + '\'' +
                ", acknowledgeValue=" + acknowledgeValue +
                ", enableSound=" + enableSound +
                ", soundLibraryName='" + soundLibraryName + '\'' +
                ", soundIndex=" + soundIndex +
                ", continuousBeep=" + continuousBeep +
                ", timeIntervalOfBeeps=" + timeIntervalOfBeeps +
                '}';
    }
}