package com.tugalsan.api.serialcom.server;

public class TS_SerialComBaudRate {

    private TS_SerialComBaudRate(TS_SerialComPort port, int baudRate) {
        this.port = port;
        this.baudRate = baudRate;
    }
    final protected TS_SerialComPort port;
    final protected int baudRate;

    public static TS_SerialComBaudRate of(TS_SerialComPort port, int baudRate) {
        return new TS_SerialComBaudRate(port, baudRate);
    }

    public TS_SerialComDataBits dataBits(int dataBits) {
        return TS_SerialComDataBits.of(this, dataBits);
    }

    public TS_SerialComDataBits dataBits_8() {
        return dataBits(8);
    }
}
