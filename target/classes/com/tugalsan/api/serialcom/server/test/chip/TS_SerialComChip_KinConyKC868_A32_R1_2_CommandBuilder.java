package com.tugalsan.api.serialcom.server.test.chip;

import java.util.Optional;

public class TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder {

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
    public static Optional<String> setDigitalOut_Oscillating(int pin, int secDuration, int secGap, int count) {
        if (!isPinValid(pin)) {
            return Optional.empty();
        }
        return Optional.of("!DO_SET_IDX_TRUE_UNTIL %d %d %d %d".formatted(pin, secDuration, secGap, count));
    }

    //USAGE: MEMORY-------------------------------------------
    //USAGE: getMemIntAll as (cmd) ex: !MEMINT_GET_ALL
    //USAGE: setMemInt as (cmd, idx) ex: !MEMINT_SET_IDX 5 2
    public static String getMemInt_All() {
        return "!MEMINT_GET_ALL";
    }

    public static Optional<String> setMemInt_Idx(int idx, int secDuration) {
        if (!isPinValid(idx)) {
            return Optional.empty();
        }
        return Optional.of("!MEMINT_SET_IDX %d %d".formatted(idx, secDuration));
    }
}
