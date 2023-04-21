package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.runnable.client.*;

public class TS_SerialComOnSetupError {

    private TS_SerialComOnSetupError(TS_SerialComOnPortError onPortError, TGS_Runnable setupError) {
        this.onPortError = onPortError;
        this.setupError = setupError;
    }
    final protected TS_SerialComOnPortError onPortError;
    final protected TGS_Runnable setupError;

    public static TS_SerialComOnSetupError of(TS_SerialComOnPortError onPortError, TGS_Runnable setupError) {
        return new TS_SerialComOnSetupError(onPortError, setupError);
    }

    public TS_SerialComOnConnectError onConnectError(TGS_Runnable connectError) {
        return TS_SerialComOnConnectError.of(this, connectError);
    }
}
