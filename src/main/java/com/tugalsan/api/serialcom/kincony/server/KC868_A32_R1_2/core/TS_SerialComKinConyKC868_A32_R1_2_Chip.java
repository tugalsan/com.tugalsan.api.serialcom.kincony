package com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2.core;

import com.tugalsan.api.callable.client.TGS_CallableType1;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.serialcom.server.TS_SerialComBuilder;
import com.tugalsan.api.serialcom.server.TS_SerialComMessageBroker;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import com.tugalsan.api.union.client.TGS_UnionExcuse;
import java.time.Duration;

public class TS_SerialComKinConyKC868_A32_R1_2_Chip {

    final public static TS_Log d = TS_Log.of(TS_SerialComKinConyKC868_A32_R1_2_Chip.class);

    public static Duration defaultTimeoutDuration() {
        return Duration.ofSeconds(10);
    }

    public static int defaultBrokerSize() {
        return 10;
    }

    public TS_SerialComKinConyKC868_A32_R1_2_Chip(TS_SerialComMessageBroker mb, Duration timeout) {
        this.mb = mb;
        this.timeout = timeout;
    }
    final public TS_SerialComMessageBroker mb;
    final public Duration timeout;
    final public String validReplyPrefix = "REPLY_OF:";
    final public String validReplySuffixSet = "DONE";

    public static TS_SerialComKinConyKC868_A32_R1_2_Chip of(TS_SerialComMessageBroker mb, Duration timeout) {
        return new TS_SerialComKinConyKC868_A32_R1_2_Chip(mb, timeout);
    }

    public static TGS_UnionExcuse<Boolean> callBoolResult(TS_ThreadSyncTrigger killTrigger, String comX, TGS_CallableType1<TGS_UnionExcuse<Boolean>, TS_SerialComKinConyKC868_A32_R1_2_Chip> chip) {
        TGS_CallableType1<TGS_UnionExcuse<Boolean>, TS_SerialComKinConyKC868_A32_R1_2_Chip> chip2 = c -> chip.call(c);
        var result = callOptional(killTrigger, comX, chip2, defaultTimeoutDuration());
        return result;
    }

    public static TGS_UnionExcuse<String> callStrOptional(TS_ThreadSyncTrigger killTrigger, String comX, TGS_CallableType1<TGS_UnionExcuse<String>, TS_SerialComKinConyKC868_A32_R1_2_Chip> chip) {
        return callOptional(killTrigger, comX, chip, defaultTimeoutDuration());
    }

    public static <T> TGS_UnionExcuse<T> callOptional(TS_ThreadSyncTrigger killTrigger, String comX, TGS_CallableType1<TGS_UnionExcuse<T>, TS_SerialComKinConyKC868_A32_R1_2_Chip> chip) {
        return callOptional(killTrigger, comX, chip, defaultTimeoutDuration());
    }

    public static <T> TGS_UnionExcuse<T> callOptional(TS_ThreadSyncTrigger killTrigger, String comX, TGS_CallableType1<TGS_UnionExcuse<T>, TS_SerialComKinConyKC868_A32_R1_2_Chip> chip, Duration timeout) {
        var wrap = new Object() {
            TGS_UnionExcuse<T> u = TGS_UnionExcuse.ofExcuse(d.className, "callOptional", "not initilized");
        };
        TS_SerialComBuilder
                .port(comX)
                .baudRate_115200()
                .dataBits_8().oneStopBit().parityNone()
                .onPortError(() -> wrap.u = TGS_UnionExcuse.ofExcuse(d.className, "onPortError", "Did you connect the cable and power up the device?"))
                .onSetupError(() -> wrap.u = TGS_UnionExcuse.ofExcuse(d.className, "onSetupError", "Is the port selection correct for the device?"))
                .onConnectError(() -> wrap.u = TGS_UnionExcuse.ofExcuse(d.className, "onConnectError", "Have you already connected by another program (like arduino serial monitor)?"))
                .onReply_useDefaultMessageBroker_withMaxMessageCount(defaultBrokerSize())
                .onSuccess_useAndClose_defaultMessageBroker(killTrigger, (con, mb) -> {
                    var chipDriver = TS_SerialComKinConyKC868_A32_R1_2_Chip.of(mb, timeout);
                    wrap.u = chip.call(chipDriver);
                });
        return wrap.u;
    }

}
