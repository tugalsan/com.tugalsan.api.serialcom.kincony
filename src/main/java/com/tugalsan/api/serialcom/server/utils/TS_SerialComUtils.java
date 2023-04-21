package com.tugalsan.api.serialcom.server.utils;

import com.fazecast.jSerialComm.*;
import com.tugalsan.api.coronator.client.TGS_Coronator;
import com.tugalsan.api.runnable.client.*;
import com.tugalsan.api.list.client.TGS_ListUtils;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.thread.server.*;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import java.util.ArrayList;
import java.util.List;

public class TS_SerialComUtils {

    final private static TS_Log d = TS_Log.of(TS_SerialComUtils.class);

    public static enum PARITY {
        NO_PARITY, ODD_PARITY, EVEN_PARITY;
    }

    public static enum STOP_BITS {
        ONE_STOP_BIT, ONE_POINT_FIVE_STOP_BITS, TWO_STOP_BITS;
    }

    public static boolean send(SerialPort serialPort, String command) {
        d.ci("send", command);
        var byteArray = command.getBytes();
        var result = serialPort.writeBytes(byteArray, byteArray.length) != -1;
        if (!result) {
            return false;
        }
        if (!command.endsWith("\n")) {
            return send(serialPort, "\n");
        }
        return result;
    }

    public static List<SerialPort> list() {
        if (!dontDeleteMyTemporaryFilesDuringBootUp) {
            dontDeleteMyTemporaryFilesDuringBootUp = true;
            System.setProperty("fazecast.jSerialComm.appid", d.className);
        }
        d.ci("list", "");
        var listArr = SerialPort.getCommPorts();
        if (listArr == null || listArr.length == 0) {
            return new ArrayList();
        }
        return TGS_ListUtils.of(listArr);
    }
    private static boolean dontDeleteMyTemporaryFilesDuringBootUp = false;

    public static boolean disconnect(SerialPort serialPort) {
        d.ci("disconnect", "");
        return disconnect(serialPort, null);
    }

    public static boolean disconnect(SerialPort serialPort, TS_ThreadExecutable threadReply) {
        d.ci("disconnect", "threadReply");
        if (threadReply != null) {
            threadReply.killMe = true;
        }
        serialPort.removeDataListener();
        TS_ThreadWait.seconds(null, 2);//FOR ARDUINO
        return serialPort.closePort();
    }

    public static TS_ThreadExecutable connect(SerialPort serialPort, TGS_RunnableType1<String> onReply) {
        d.ci("connect", "onReply", onReply != null);
        var result = serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
        if (!result) {
            d.ce("connect", "Error on setComPortTimeouts");
            return null;
        }
        result = serialPort.openPort();
        if (!result) {
            d.ce("connect", "Error on openPort");
            return null;
        }
        var threadReply = new TS_ThreadExecutable() {

            private void waitForNewData() {
                while (serialPort.bytesAvailable() == 0 || serialPort.bytesAvailable() == -1) {
                    if (killMe) {
                        return;
                    }
                    TS_ThreadWait.milliseconds(20);
                }
            }

            private void appendToBuffer() {
                if (killMe) {
                    return;
                }
                var bytes = new byte[serialPort.bytesAvailable()];
                var length = serialPort.readBytes(bytes, bytes.length);
                if (length == -1) {
                    return;
                }
                var string = new String(bytes);
                if (string.isEmpty()) {
                    return;
                }
                buffer.append(string);
            }

            private void processBuffer() {
                if (killMe) {
                    return;
                }
                //IS THERE ANY CMD?
                var idx = buffer.indexOf("\n");
                if (idx == -1) {
                    return;
                }
                //PROCESS FIRST CMD
                var firstCommand = buffer.substring(0, idx).replace("\r", "");
                onReply.run(firstCommand);
                //REMOVE FIRST CMD FROM BUFFER
                var leftOver = buffer.length() == idx + 1
                        ? ""
                        : buffer.substring(idx + 1, buffer.length());
                buffer.setLength(0);
                buffer.append(leftOver);
                //PROCESS LEFT OVER CMDS
                processBuffer();
            }

            private void handleError(Exception e) {
                if (killMe) {
                    return;
                }
                e.printStackTrace();
            }

            @Override
            public void run() {
                while (!killMe) {
                    TGS_UnSafe.run(() -> {
                        waitForNewData();
                        appendToBuffer();
                        processBuffer();
                    }, e -> handleError(e));
                }
            }
            final private StringBuilder buffer = new StringBuilder();
        };
        TS_ThreadRun.now(threadReply.setName(d.className + ".connect.threadReply"));
        return threadReply;
    }

    public static String name(SerialPort serialPort) {
        d.ci("name", "");
        return serialPort.getDescriptivePortName();
    }

    public static boolean setup(SerialPort serialPort, int baudRate, int dataBits, STOP_BITS stopBits, PARITY parity) {
        d.ci("setup", baudRate, dataBits, stopBits, parity);
        var result = serialPort.setBaudRate(baudRate);
        if (!result) {
            d.ce("setup", "Error on setBaudRate");
            return false;
        }
        result = serialPort.setNumDataBits(dataBits);
        if (!result) {
            d.ce("setup", "Error on setNumDataBits");
            return false;
        }
        result = serialPort.setNumStopBits(TGS_Coronator.ofInt()
                .anoint(val -> SerialPort.ONE_STOP_BIT)
                .anointAndCoronateIf(val -> stopBits == STOP_BITS.ONE_POINT_FIVE_STOP_BITS, val -> SerialPort.ONE_POINT_FIVE_STOP_BITS)
                .anointAndCoronateIf(val -> stopBits == STOP_BITS.TWO_STOP_BITS, val -> SerialPort.TWO_STOP_BITS)
                .coronate());
        if (!result) {
            d.ce("setup", "Error on setNumStopBits");
            return false;
        }
        result = serialPort.setParity(TGS_Coronator.ofInt()
                .anoint(val -> SerialPort.NO_PARITY)
                .anointAndCoronateIf(val -> parity == PARITY.EVEN_PARITY, val -> SerialPort.EVEN_PARITY)
                .anointAndCoronateIf(val -> parity == PARITY.ODD_PARITY, val -> SerialPort.ODD_PARITY)
                .coronate());
        if (!result) {
            d.ce("setup", "Error on setParity");
            return false;
        }
        return true;
    }

}
