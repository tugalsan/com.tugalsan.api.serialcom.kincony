package com.tugalsan.api.serialcom.server;

import com.fazecast.jSerialComm.SerialPort;

public class TS_SerialComPort {

    private TS_SerialComPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }
    final protected SerialPort serialPort;

    public static TS_SerialComPort of(SerialPort serialPort) {
        return new TS_SerialComPort(serialPort);
    }

    public TS_SerialComBaudRate baudRate(int baudRate) {
        return TS_SerialComBaudRate.of(this, baudRate);
    }

    public TS_SerialComBaudRate baudRate_115200() {
        return TS_SerialComBaudRate.of(this, 115200);
    }
}
