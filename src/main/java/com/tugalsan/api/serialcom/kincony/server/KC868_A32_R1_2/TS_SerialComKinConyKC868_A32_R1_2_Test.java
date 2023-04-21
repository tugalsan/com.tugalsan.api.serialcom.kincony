package com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2;

import com.tugalsan.api.log.server.*;
import com.tugalsan.api.serialcom.server.*;
import java.util.stream.*;

public class TS_SerialComKinConyKC868_A32_R1_2_Test {

    final private static TS_Log d = TS_Log.of(TS_SerialComKinConyKC868_A32_R1_2_Test.class);

    public static void test(
            boolean debugEnable,
            boolean test_chipname,
            boolean test_digitalIn_getAll,
            boolean test_digitalIn_getIdx,
            boolean test_digitalOut_getAll,
            boolean test_digitalOut_getIdx,
            boolean test_digitalOut_setAll,
            boolean test_digitalOut_setIdx,
            boolean test_oscillate,
            boolean test_memory
    ) {
        if (debugEnable) {
            TS_SerialComMessageBroker.d.infoEnable = true;
        }

        //USAGE: GENERAL------------------------------------------
        if (test_chipname) {
            d.cr("test", "chipName", TS_SerialComKinConyKC868_A32_R1_2.chipName());
        }

        //USAGE: DIGITAL IN GET-----------------------------------
        if (test_digitalIn_getAll) {
            d.cr("test", "digitalIn_getAll", TS_SerialComKinConyKC868_A32_R1_2.digitalIn_getAll());
        }
        if (test_digitalIn_getIdx) {
            IntStream.range(0, 32).forEachOrdered(i -> {
                d.cr("test", "digitalIn_getIdx(" + i + ")", TS_SerialComKinConyKC868_A32_R1_2.digitalIn_getIdx(i));
            });
        }

        //USAGE: DIGITAL OUT GET----------------------------------
        if (test_digitalOut_getAll) {
            d.cr("test", "digitalOut_getAll", TS_SerialComKinConyKC868_A32_R1_2.digitalOut_getAll());
        }
        if (test_digitalOut_getIdx) {
            IntStream.range(0, 32).forEachOrdered(i -> {
                d.cr("test", "digitalOut_getIdx(" + i + ")", TS_SerialComKinConyKC868_A32_R1_2.digitalOut_getIdx(i));
            });
        }
        //USAGE: DIGITAL OUT SET----------------------------------
        if (test_digitalOut_setAll) {
            d.cr("test", "digitalOut_setAll(true)", TS_SerialComKinConyKC868_A32_R1_2.digitalOut_setAll(true));
            d.cr("test", "digitalOut_setAll(false)", TS_SerialComKinConyKC868_A32_R1_2.digitalOut_setAll(false));
        }
        if (test_digitalOut_setIdx) {
            IntStream.range(0, 32).forEachOrdered(i -> {
                d.cr("test", "digitalOut_setIdx(" + i + ", true)", TS_SerialComKinConyKC868_A32_R1_2.digitalOut_setIdx(i, true));
            });
            IntStream.range(0, 32).forEachOrdered(i -> {
                d.cr("test", "digitalOut_setIdx(" + i + ", false)", TS_SerialComKinConyKC868_A32_R1_2.digitalOut_setIdx(i, false));
            });
        }
        //USAGE: DIGITAL OUT OSCILLATE---------------------------
        if (test_oscillate) {
            d.cr("test", "digitalOut_oscilate", TS_SerialComKinConyKC868_A32_R1_2.digitalOut_oscilate(12, 1, 2, 5));
        }

        //USAGE: MEMORY-------------------------------------------
        if (test_memory) {
            d.cr("test", "memInt_setIdx(1, 15)", TS_SerialComKinConyKC868_A32_R1_2.memInt_setIdx(1, 15));
            d.cr("test", "memInt_getAll", TS_SerialComKinConyKC868_A32_R1_2.memInt_getAll());
        }
    }
}
