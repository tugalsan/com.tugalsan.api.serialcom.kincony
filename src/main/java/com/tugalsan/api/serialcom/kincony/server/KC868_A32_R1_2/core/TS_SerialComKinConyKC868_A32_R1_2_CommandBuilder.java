package com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2.core;

import com.tugalsan.api.log.server.TS_Log;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder {

    final private static TS_Log d = TS_Log.of(TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.class);

    public static boolean isPinValid(int pin) {
        return pin >= 0 && pin < 32;
    }

//USAGE: GENERAL------------------------------------------
//USAGE: getChipName as (cmd) ex: !CHIP_NAME
    public static String chipName() {
        return "!CHIP_NAME";
    }

//USAGE: DIGITAL IN GET-----------------------------------
//USAGE: getDigitalInAll as (cmd) ex: !DI_GET_ALL
//USAGE: getDigitalInIdx as (cmd, pin1-32) ex: !DI_GET_IDX 1
    public static String getDigitalIn_All() {
        return "!DI_GET_ALL";
    }

    public static Optional<String> getDigitalIn(int pin) {
        if (!isPinValid(pin)) {
            return Optional.empty();
        }
        return Optional.of("!DI_GET_IDX %d".formatted(pin));
    }

//USAGE: DIGITAL OUT GET----------------------------------
//USAGE: getDigitalOutAll as (cmd) ex: !DO_GET_ALL
//USAGE: getDigitalOutIdx as (cmd, pin1-32) ex: !DO_GET_IDX 1
    public static String getDigitalOut_All() {
        return "!DO_GET_ALL";
    }

    public static Optional<String> getDigitalOut(int pin) {
        if (!isPinValid(pin)) {
            return Optional.empty();
        }
        return Optional.of("!DO_GET_IDX %d".formatted(pin));
    }
//USAGE: DIGITAL OUT SET----------------------------------
//USAGE: setDigitalOutAllAsTrue as (cmd) ex: !DO_SET_ALL_TRUE
//USAGE: setDigitalOutAllAsFalse as (cmd) ex: !DO_SET_ALL_FALSE
//USAGE: setDigitalOutIdxTrue as (cmd, pin1-32) ex: !DO_SET_IDX_TRUE 1
//USAGE: setDigitalOutIdxFalse as (cmd, pin1-32) ex: !DO_SET_IDX_FALSE 1

    public static String setDigitalOut_All(boolean value) {
        return value ? "!DO_SET_ALL_TRUE" : "!DO_SET_ALL_FALSE";
    }

    public static Optional<String> setDigitalOut(int pin, boolean value) {
        if (!isPinValid(pin)) {
            return Optional.empty();
        }
        return Optional.of((value ? "!DO_SET_IDX_TRUE %d" : "!DO_SET_IDX_FALSE %d").formatted(pin));
    }

//USAGE: DIGITAL OUT OSCILLATE---------------------------
//USAGE: setDigitalOutOscillating as (cmd, pin1-32, secDuration, secGap, count) ex: !DO_SET_IDX_TRUE_UNTIL 12 2 1 5
    public static Optional<String> setDigitalOut_OscillatingAll(List<Integer> pins) {
        if (pins.stream().filter(pin -> !isPinValid(pin)).findAny().isPresent()) {
            d.ce("setDigitalOut_OscillatingAll", "ERROR_PIN_NOT_VALID");
            return Optional.empty();
        }
        return Optional.of("!DO_SET_ALL_UNTIL %s".formatted(pins.stream().map(i -> i.toString()).collect(Collectors.joining("-"))));
    }

    public static Optional<String> setDigitalOut_Oscillating(int pin, int secDuration, int secGap, int count) {
        if (!isPinValid(pin)) {
            return Optional.empty();
        }
        if (secDuration > MAX_VALUE) {
            d.ce("setDigitalOut_Oscillating", "ERROR_MAX_VALUE_THRESHOLD", "secDuration", secDuration);
            return null;
        }
        if (secDuration > secGap) {
            d.ce("setDigitalOut_Oscillating", "ERROR_MAX_VALUE_THRESHOLD", "secGap", secGap);
            return null;
        }
        if (secDuration > count) {
            d.ce("setDigitalOut_Oscillating", "ERROR_MAX_VALUE_THRESHOLD", "count", count);
            return null;
        }
        return Optional.of("!DO_SET_IDX_TRUE_UNTIL %d %d %d %d".formatted(pin, secDuration, secGap, count));
    }

    //USAGE: MEMORY-------------------------------------------
    //USAGE: getMemIntAll as (cmd) ex: !MEMINT_GET_ALL
    //USAGE: setMemInt as (cmd, idx) ex: !MEMINT_SET_IDX 5 2
    public static String getMemInt_All() {
        return "!MEMINT_GET_ALL";
    }

    public static String getMode_Idx() {
        return "!MODE_GET_IDX";
    }

    public static String setMode_Idx(int idx) {
        return "!MODE_SET_IDX %d".formatted(idx);
    }

    public static Optional<String> setMemInt_Idx(int idx, int value) {
        if (value > MAX_VALUE) {
            d.ce("setMemInt_Idx", "ERROR_MAX_VALUE_THRESHOLD");
            return Optional.empty();
        }
        return Optional.of("!MEMINT_SET_IDX %d %d".formatted(idx, value));
    }

    public static Optional<String> setMemInt_All(List<Integer> values16) {
        if (values16.stream().filter(i -> i > MAX_VALUE).findAny().isPresent()) {
            d.ce("setMemInt_All", "ERROR_MAX_VALUE_THRESHOLD");
            return Optional.empty();
        }
        return Optional.of("!MEMINT_SET_ALL %s".formatted(values16.stream().map(i -> i.toString()).collect(Collectors.joining("-"))));
    }

    final public static int MAX_VALUE = 2147483647;
}
