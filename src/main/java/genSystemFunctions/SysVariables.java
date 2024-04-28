package genSystemFunctions;

import sql.SQLRequest;
import system.GDB;
import uniqueItems.OneVar;

import java.sql.Connection;
import java.util.*;

public class SysVariables {

    public Map<String, List<OneVar>> mapSysVar = new LinkedHashMap<>();
    public Map<String, SysPulse> mapPulse;
    public Map<String, SysPulse> mapClock;
    private List<OneVar> sysReset = new ArrayList<>();
    private List<OneVar> sysEmergency = new ArrayList<>();
    private List<OneVar> sysSim = new ArrayList<>();
    private List<OneVar> sysAuto = new ArrayList<>();
    private List<OneVar> sysSilence = new ArrayList<>();
    private List<OneVar> sysSave = new ArrayList<>();
    private List<OneVar> sysZero = new ArrayList<>();
    private List<OneVar> sysPulse = new ArrayList<>();
    private List<OneVar> sysClock = new ArrayList<>();

    SQLRequest sqlRequest = new SQLRequest();

    public SysVariables () {
        reInitFromDB();
        getPulseList();
        getClockList();
        collectToMap();
    }

    private void reInitFromDB() {
        SQLRequest sqlRequest = new SQLRequest();
        Connection conn = sqlRequest.connect(GDB.filepathDataBase + GDB.dataBase);
        sysReset.add(sqlRequest.OneVarDao("sysReset", conn));
        sysEmergency.add(sqlRequest.OneVarDao("sysEmergency", conn));
        sysSim.add(sqlRequest.OneVarDao("sysSim", conn));
        sysAuto.add(sqlRequest.OneVarDao("sysAuto", conn));
        sysSilence.add(sqlRequest.OneVarDao("sysSilence", conn));
        sysSave.add(sqlRequest.OneVarDao("sysSave", conn));
        sysZero.add(sqlRequest.OneVarDao("sysZero", conn));
        genPulses(conn);
    }

    private void collectToMap () {
        mapSysVar.put("sysReset", sysReset);
        mapSysVar.put("sysEmergency", sysEmergency);
        mapSysVar.put("sysSim", sysSim);
        mapSysVar.put("sysAuto", sysAuto);
        mapSysVar.put("sysSilence", sysSilence);
        mapSysVar.put("sysSave", sysSave);
        mapSysVar.put("sysZero", sysZero);
        mapSysVar.put("sysPulse", sysPulse);
        mapSysVar.put("sysClock", sysClock);
    }

    private void genPulses (Connection conn) {
        this.mapPulse = sqlRequest.SysPulseClockDao("Pulse", conn);
        this.mapClock = sqlRequest.SysPulseClockDao("Clock", conn);
    }

    private void getPulseList () {
        for (Map.Entry<String, SysPulse> pulse : mapPulse.entrySet()) {
            OneVar oneVar = new OneVar();
            oneVar = pulse.getValue().getOneVar();
            this.sysPulse.add(oneVar);
        }
    }

    private void getClockList () {
        for (Map.Entry<String, SysPulse> clock : mapClock.entrySet()) {
            OneVar oneVar = new OneVar();
            oneVar = clock.getValue().getOneVar();
            this.sysClock.add(oneVar);
        }
    }


}
