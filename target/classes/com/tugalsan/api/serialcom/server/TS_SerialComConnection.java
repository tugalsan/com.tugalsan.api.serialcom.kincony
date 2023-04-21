package com.tugalsan.api.serialcom.server;

import com.fazecast.jSerialComm.*;
import com.tugalsan.api.runnable.client.*;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.serialcom.server.utils.*;
import com.tugalsan.api.thread.server.TS_ThreadExecutable;

public class TS_SerialComConnection implements AutoCloseable {

    final private static TS_Log d = TS_Log.of(true, TS_SerialComConnection.class);

    final public String name() {
        return port == null ? null : TS_SerialComUtils.name(port);
    }

    final private boolean successfulPort() {
        return port != null;
    }

    final private boolean successfulSetup() {
        return successfulSetup;
    }
    private boolean successfulSetup;

    final private boolean successfulConnect() {
        return threadReply != null;
    }

    final public boolean isConnected() {
        return successfulPort() && successfulSetup() && successfulConnect() && port.isOpen();
    }

    private TS_SerialComConnection(TS_SerialComOnReply onReply) {
        //BIND MESSAGE BROKER
        if (onReply.onReply_customMessageBroker == null) {//use default broker
            this.messageBroker = TS_SerialComMessageBroker.of(onReply.defaultMessageBrokerMessageSize);
            this.messageBroker.setConnection(this);
            this.onReply = reply -> messageBroker.onReply(reply);
        } else {//if custom broker exists
            this.messageBroker = null;
            this.onReply = onReply.onReply_customMessageBroker;
        }
        //BIND PARAMETERS
        var parity = onReply.onConnectError.onSetupError.onPortError.parity.parity;
        this.parityName = parity == null ? null : parity.name();
        var stopBits = onReply.onConnectError.onSetupError.onPortError.parity.stopBits.stopBits;
        this.stopBitsName = stopBits == null ? null : stopBits.name();
        this.dataBits = onReply.onConnectError.onSetupError.onPortError.parity.stopBits.dataBits.dataBits;
        this.baudRate = onReply.onConnectError.onSetupError.onPortError.parity.stopBits.dataBits.baudRate.baudRate;
        //BIND PORT
        this.port = onReply.onConnectError.onSetupError.onPortError.parity.stopBits.dataBits.baudRate.port.serialPort;
        if (!successfulPort()) {
            d.ce("constructor", "error detected", "!successfulPort");
            onReply.onConnectError.onSetupError.onPortError.portError.run();
            return;
        }
        //BIND SETUP
        this.successfulSetup = TS_SerialComUtils.setup(port, baudRate, dataBits, stopBits, parity);
        if (!successfulSetup()) {
            d.ce("constructor", "error detected", "!successfulSetup");
            onReply.onConnectError.onSetupError.setupError.run();
            return;
        }
        //CONNECT AND BIND REPLY
        this.threadReply = TS_SerialComUtils.connect(port, this.onReply);
        if (!successfulConnect()) {
            d.ce("constructor", "error detected", "!successfulConnect");
            onReply.onConnectError.connectError.run();
            return;
        }
    }
    final private TS_SerialComMessageBroker messageBroker;
    private TS_ThreadExecutable threadReply;
    final public TGS_RunnableType1<String> onReply;
    final public String parityName;
    final public String stopBitsName;
    final public int dataBits;
    final public int baudRate;
    final public SerialPort port;

    public static TS_SerialComConnection of(TS_SerialComOnReply onReply) {
        return new TS_SerialComConnection(onReply);
    }

    @Override
    public /*boolean*/ void close() {
        if (!isConnected()) {
            d.ce("disconnect", "Error on not connected");
            return /*false*/;
        }
        /*return*/ TS_SerialComUtils.disconnect(port, threadReply);
    }

    public boolean send(String command) {
        if (!isConnected()) {
            d.ce("send", "Error on not connected");
            return false;
        }
        return TS_SerialComUtils.send(port, command);
    }

    public boolean useAndClose_WithDefaultMessageBroker(TGS_RunnableType2<TS_SerialComConnection, TS_SerialComMessageBroker> con_mb) {
        try {
            if (!isConnected()) {
                d.ce("useAndClose", "Error on not connected");
                return false;
            }
            con_mb.run(this, messageBroker);
        } catch (Exception e) {
            d.ct("useAndClose", e);
            return false;
        } finally {
            close();
        }
        return true;
    }

    public boolean useAndClose_WithCustomMessageBroker(TGS_RunnableType1<TS_SerialComConnection> con) {
        try {
            if (!isConnected()) {
                d.ce("useAndClose", "Error on not connected");
                return false;
            }
            con.run(this);
        } catch (Exception e) {
            d.ct("useAndClose", e);
            return false;
        } finally {
            close();
        }
        return true;
    }
}
