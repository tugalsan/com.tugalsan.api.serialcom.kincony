package com.tugalsan.api.serialcom.server.test.chip;

import com.tugalsan.api.log.server.TS_Log;
import java.util.Optional;

public class TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOutPin {

    final private static TS_Log d = TS_Log.of(TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOutPin.class);

    private TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOutPin(TS_SerialComChip_KinConyKC868_A32_R1_2 chip, int pin) {
        this.chip = chip;
        this.pin = pin;
    }
    final private TS_SerialComChip_KinConyKC868_A32_R1_2 chip;
    final private int pin;

    public static TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOutPin of(TS_SerialComChip_KinConyKC868_A32_R1_2 chip, int pin) {
        return new TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOutPin(chip, pin);
    }

    public Optional<Boolean> getValueFromChip() {
        var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder.getDigitalOut(pin);
        if (cmd.isEmpty()) {
            d.ce("getValueFromChip", "cmd.isEmpty()", "pin", pin);
            return Optional.empty();
        }
        var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd.get(), chip.timeout, chip.validReplyPrefix, true);
        if (reply.isEmpty()) {
            return Optional.empty();
        }
        value = reply.get().equals("1");
        return Optional.of(value);
    }

    public boolean getValueFromBuffer() {
        return value;
    }
    private volatile boolean value = false;

    public boolean setValue(boolean value) {
        var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut(pin, value);
        if (cmd.isEmpty()) {
            d.ce("setValue", "cmd.isEmpty()", "pin", pin, "value", value);
            return false;
        }
        var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd.get(), chip.timeout, chip.validReplyPrefix, true);
        if (reply.isEmpty()) {
            return false;
        }
        var result = reply.get();
        d.ci("setValue", "value", value, "result", result);
        var processed = result.endsWith(chip.validReplySuffixSet);
        if (processed) {
            this.value = value;
        }
        return processed;
    }

    public boolean oscillate(int secDuration, int secGap, int count) {
        var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut_Oscillating(pin, secDuration, secGap, count);
        if (cmd.isEmpty()) {
            d.ce("oscillate", "cmd.isEmpty()", "pin", pin, "secDuration", secDuration, "secGap", secGap, "count", count);
            return false;
        }
        var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd.get(), chip.timeout, chip.validReplyPrefix, true);
        if (reply.isEmpty()) {
            return false;
        }
        var result = reply.get();
        d.ci("setValue", "value", value, "result", result);
        var processed = result.endsWith(chip.validReplySuffixSet);
        if (processed) {
            this.value = false;
        }
        return processed;
    }

    public void setValueImitate(boolean value) {
        this.value = value;
    }
}
