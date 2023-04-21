package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.serialcom.server.utils.TS_SerialComUtils.PARITY;
import com.tugalsan.api.serialcom.server.utils.TS_SerialComUtils.STOP_BITS;

public class TS_SerialComStopBits {

    private TS_SerialComStopBits(TS_SerialComDataBits dataBits, STOP_BITS stopBits) {
        this.dataBits = dataBits;
        this.stopBits = stopBits;
    }
    final protected TS_SerialComDataBits dataBits;
    final protected STOP_BITS stopBits;

    public static TS_SerialComStopBits of(TS_SerialComDataBits dataBits, STOP_BITS stopBits) {
        return new TS_SerialComStopBits(dataBits, stopBits);
    }

    public TS_SerialComParity parity(PARITY parity) {
        return TS_SerialComParity.of(this, parity);
    }

    public TS_SerialComParity parityNone() {
        return TS_SerialComParity.of(this, PARITY.NO_PARITY);
    }

    public TS_SerialComParity parityEven() {
        return TS_SerialComParity.of(this, PARITY.EVEN_PARITY);
    }

    public TS_SerialComParity parityOdd() {
        return TS_SerialComParity.of(this, PARITY.ODD_PARITY);
    }
}
