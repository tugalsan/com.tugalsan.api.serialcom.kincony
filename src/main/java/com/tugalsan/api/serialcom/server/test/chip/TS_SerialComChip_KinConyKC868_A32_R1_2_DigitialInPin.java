package com.tugalsan.api.serialcom.server.test.chip;

import com.tugalsan.api.log.server.TS_Log;
import java.util.Optional;

public class TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialInPin {

    final private static TS_Log d = TS_Log.of(TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialInPin.class);

    private TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialInPin(TS_SerialComChip_KinConyKC868_A32_R1_2 chip, int pin) {
        this.chip = chip;
        this.pin = pin;
    }
    final private TS_SerialComChip_KinConyKC868_A32_R1_2 chip;
    final private int pin;

    public static TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialInPin of(TS_SerialComChip_KinConyKC868_A32_R1_2 chip, int pin) {
        return new TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialInPin(chip, pin);
    }

    public Optional<Boolean> getValueFromChip() {
        var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder.getDigitalIn(pin);
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

    public void setValueImitate(boolean value) {
        this.value = value;
    }
}
