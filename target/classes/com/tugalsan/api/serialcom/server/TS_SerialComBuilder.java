package com.tugalsan.api.serialcom.server;

import com.fazecast.jSerialComm.SerialPort;
import com.tugalsan.api.callable.client.TGS_CallableType1;
import com.tugalsan.api.runnable.client.TGS_RunnableType1;
import com.tugalsan.api.serialcom.server.utils.TS_SerialComUtils;
import java.util.List;

public class TS_SerialComBuilder {

    public static TS_SerialComPort port(SerialPort serialPort) {
        return TS_SerialComPort.of(serialPort);
    }

    public static TS_SerialComPort port(TGS_CallableType1<SerialPort, List<SerialPort>> choose) {
        return TS_SerialComPort.of(choose.call(TS_SerialComUtils.list()));
    }

    public static TS_SerialComPort portFirst() {
        return portFirst(port -> {
        });
    }

    public static TS_SerialComPort portFirst(TGS_RunnableType1<SerialPort> port) {
        var list = TS_SerialComUtils.list();
        var firstPort = list.isEmpty() ? null : list.get(0);
        port.run(firstPort);
        return TS_SerialComPort.of(firstPort);
    }
}
