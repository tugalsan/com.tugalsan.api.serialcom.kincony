package com.tugalsan.api.serialcom.kincony.server;

import com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2.TS_SerialComKinConyKC868_A32_R1_2;
import com.tugalsan.api.log.server.*;
import com.tugalsan.api.serialcom.server.*;
import java.util.stream.IntStream;

public class TS_SerialComKinConyTest {

    final private static TS_Log d = TS_Log.of(TS_SerialComKinConyTest.class);

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
            d.cr("test", "chipName", TS_SerialComKinConyKC868_A32_R1_2.callOptional(chip -> chip.chipName()));
        }

        //USAGE: DIGITAL IN GET-----------------------------------
        if (test_digitalIn_getAll) {
            d.cr("test", "digitalIn.refreshAll", TS_SerialComKinConyKC868_A32_R1_2.callBoolResult(chip -> chip.digitalIn.refreshAll()));
        }
        if (test_digitalIn_getIdx) {
            IntStream.range(0, 32).forEachOrdered(i -> {
                d.cr("test", "chip.digitalIn.pin(" + i + ").getValueFromChip", TS_SerialComKinConyKC868_A32_R1_2.callOptional(chip -> {
                    return chip.digitalIn.pin(i).getValueFromChip();
                }));
            });
        }

        //USAGE: DIGITAL OUT GET----------------------------------
        if (test_digitalOut_getAll) {
            d.cr("test", "digitalOut.refreshAll", TS_SerialComKinConyKC868_A32_R1_2.callBoolResult(chip -> chip.digitalOut.refreshAll()));
        }
        if (test_digitalOut_getIdx) {
            IntStream.range(0, 32).forEachOrdered(i -> {
                d.cr("test", "chip.digitalOut.pin(" + i + ").getValueFromChip", TS_SerialComKinConyKC868_A32_R1_2.callOptional(chip -> {
                    return chip.digitalOut.pin(i).getValueFromChip();
                }));
            });
        }
        //USAGE: DIGITAL OUT SET----------------------------------
        if (test_digitalOut_setAll) {
            d.cr("test", "digitalOut.refreshAll", TS_SerialComKinConyKC868_A32_R1_2.callBoolResult(chip -> chip.digitalOut.setAll(true)));
            d.cr("test", "digitalOut.refreshAll", TS_SerialComKinConyKC868_A32_R1_2.callBoolResult(chip -> chip.digitalOut.setAll(false)));
        }
        if (test_digitalOut_setIdx) {
            IntStream.range(0, 32).forEachOrdered(i -> {
                d.cr("test", "chip.digitalOut.pin(" + i + ").setValue(true)", TS_SerialComKinConyKC868_A32_R1_2.callBoolResult(chip -> {
                    return chip.digitalOut.pin(i).setValue(true);
                }));
            });
            IntStream.range(0, 32).forEachOrdered(i -> {
                d.cr("test", "chip.digitalOut.pin(" + i + ").setValue(false)", TS_SerialComKinConyKC868_A32_R1_2.callBoolResult(chip -> {
                    return chip.digitalOut.pin(i).setValue(false);
                }));
            });
        }
        //USAGE: DIGITAL OUT OSCILLATE---------------------------
        if (test_oscillate) {
            d.cr("test", "digitalOut.oscillate", TS_SerialComKinConyKC868_A32_R1_2.callBoolResult(chip -> chip.digitalOut.pin(12).oscillate(2, 1, 5)));
        }

        //USAGE: MEMORY-------------------------------------------
        if (test_memory) {
            d.cr("test", "memory.refreshAll", TS_SerialComKinConyKC868_A32_R1_2.callBoolResult(chip -> chip.mem_int.refreshAll()));
            d.cr("test", "memory.set", TS_SerialComKinConyKC868_A32_R1_2.callBoolResult(chip -> chip.mem_int.set(1, 15)));
            d.cr("test", "memory.refreshAll", TS_SerialComKinConyKC868_A32_R1_2.callBoolResult(chip -> chip.mem_int.refreshAll()));
            d.cr("test", "memory.refreshAll", TS_SerialComKinConyKC868_A32_R1_2.callBoolResult(chip -> chip.mem_int.refreshAll()));
        }
    }
}
