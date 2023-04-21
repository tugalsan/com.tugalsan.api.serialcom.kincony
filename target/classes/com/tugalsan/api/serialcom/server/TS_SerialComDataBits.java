package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.serialcom.server.utils.TS_SerialComUtils.STOP_BITS;

public class TS_SerialComDataBits {

    private TS_SerialComDataBits(TS_SerialComBaudRate baudRate, int dataBits) {
        this.baudRate = baudRate;
        this.dataBits = dataBits;
    }
    final protected TS_SerialComBaudRate baudRate;
    final protected int dataBits;

    public static TS_SerialComDataBits of(TS_SerialComBaudRate baudRate, int dataBits) {
        return new TS_SerialComDataBits(baudRate, dataBits);
    }

    public TS_SerialComStopBits stopBits(STOP_BITS stopBits) {
        return TS_SerialComStopBits.of(this, stopBits);
    }

    public TS_SerialComStopBits oneStopBit() {
        return TS_SerialComStopBits.of(this, STOP_BITS.ONE_STOP_BIT);
    }

    public TS_SerialComStopBits oneStopPointFiveBit() {
        return TS_SerialComStopBits.of(this, STOP_BITS.ONE_POINT_FIVE_STOP_BITS);
    }

    public TS_SerialComStopBits twoStopBit() {
        return TS_SerialComStopBits.of(this, STOP_BITS.TWO_STOP_BITS);
    }
}
