package com.tugalsan.api.serialcom.server.test.chip;

import com.tugalsan.api.callable.client.TGS_CallableType1;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.serialcom.server.TS_SerialComBuilder;
import com.tugalsan.api.serialcom.server.TS_SerialComMessageBroker;
import java.time.Duration;
import java.util.Optional;

public class TS_SerialComChip_KinConyKC868_A32_R1_2 {

    final private static TS_Log d = TS_Log.of(TS_SerialComChip_KinConyKC868_A32_R1_2.class);

    public static Duration defaultTimeoutDuration() {
        return Duration.ofSeconds(10);
    }

    public static int defaultBrokerSize() {
        return 10;
    }

    private TS_SerialComChip_KinConyKC868_A32_R1_2(TS_SerialComMessageBroker mb, Duration timeout) {
        this.mb = mb;
        this.timeout = timeout;
        this.digitalIn = TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialIn.of(this);
        this.digitalOut = TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOut.of(this);
        this.mem_int = TS_SerialComChip_KinConyKC868_A32_R1_2_MemInt.of(this);
    }
    final public TS_SerialComMessageBroker mb;
    final public Duration timeout;
    final public TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialIn digitalIn;
    final public TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOut digitalOut;
    final public TS_SerialComChip_KinConyKC868_A32_R1_2_MemInt mem_int;
    final public String validReplyPrefix = "REPLY_OF:";
    final public String validReplySuffixSet = "DONE";

    public static TS_SerialComChip_KinConyKC868_A32_R1_2 of(TS_SerialComMessageBroker mb, Duration timeout) {
        return new TS_SerialComChip_KinConyKC868_A32_R1_2(mb, timeout);
    }

    public static boolean callBoolResult(TGS_CallableType1<Boolean, TS_SerialComChip_KinConyKC868_A32_R1_2> chip) {
        TGS_CallableType1<Optional<Boolean>, TS_SerialComChip_KinConyKC868_A32_R1_2> chip2 = c -> Optional.of(chip.call(c));
        return callOptional(chip2, defaultTimeoutDuration()).get();
    }

    public static Optional<String> callStrOptional(TGS_CallableType1<Optional<String>, TS_SerialComChip_KinConyKC868_A32_R1_2> chip) {
        return callOptional(chip, defaultTimeoutDuration());
    }

    public static <T> Optional<T> callOptional(TGS_CallableType1<Optional<T>, TS_SerialComChip_KinConyKC868_A32_R1_2> chip) {
        return callOptional(chip, defaultTimeoutDuration());
    }

    public static <T> Optional<T> callOptional(TGS_CallableType1<Optional<T>, TS_SerialComChip_KinConyKC868_A32_R1_2> chip, Duration timeout) {
        var result = new Object() {
            Optional<T> value;
        };
        TS_SerialComBuilder.portFirst()
                .baudRate_115200().dataBits_8().oneStopBit().parityNone()
                .onPortError(() -> d.ce("onPortError", "Did you connect the cable and power up the device?"))
                .onSetupError(() -> d.ce("onSetupError", "Is the port selection correct for the device?"))
                .onConnectError(() -> d.ce("onConnectError", "Have you already connected by another program (like arduino serial monitor)?"))
                .onReply_useDefaultMessageBroker_withMaxMessageCount(defaultBrokerSize())
                .onSuccess_useAndClose_defaultMessageBroker((con, mb) -> {
                    var chipDriver = TS_SerialComChip_KinConyKC868_A32_R1_2.of(mb, timeout);
                    result.value = chip.call(chipDriver);
                });
        return result.value;
    }

    public Optional<String> chipName() {
        if (chipName.isEmpty()) {
            var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder.chipName();
            var reply = mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd, timeout, validReplyPrefix, true);
            chipName = reply;
        }
        return chipName;
    }
    private volatile Optional<String> chipName = Optional.empty();
}
