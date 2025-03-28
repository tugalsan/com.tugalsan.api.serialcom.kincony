package com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2;

import com.tugalsan.api.cast.client.TGS_CastUtils;
import com.tugalsan.api.log.server.*;
import com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2.core.TS_SerialComKinConyKC868_A32_R1_2_Chip;
import com.tugalsan.api.serialcom.server.*;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import com.tugalsan.api.function.client.TGS_FuncUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.*;

public class TS_SerialComKinConyKC868_A32_R1_2_Test {

    final private static TS_Log d = TS_Log.of(TS_SerialComKinConyKC868_A32_R1_2_Test.class);

    public static void main(String... s) {
        var killTrigger = TS_ThreadSyncTrigger.of("main");
        try (var reader = new BufferedReader(new InputStreamReader(System.in));) {
            var debugEnabled = true;
            while (true) {
                d.ci("test", "--------------------------------------------");
                d.ci("test", "choice", "0: exit");
                d.ci("test", "choice", debugEnabled ? "1: debugEnabled" : "1: debugDisabled");
                d.ci("test", "choice", "2: test_chipname");
                d.ci("test", "choice", "3: test_digitalIn_getAll");
                d.ci("test", "choice", "4: test_digitalIn_getIdx");
                d.ci("test", "choice", "5: test_digitalOut_getAll");
                d.ci("test", "choice", "6: test_digitalOut_getIdx");
                d.ci("test", "choice", "7: test_digitalOut_setAll");
                d.ci("test", "choice", "8: test_digitalOut_setIdx");
                d.ci("test", "choice", "9: test_oscillate");
                d.ci("test", "choice", "10: test_register");
                System.out.print("choice: ");
                var choiceStr = reader.readLine();
                var choiceInt = TGS_CastUtils.toInteger(choiceStr).orElse(null);
                var comX = "COM3";
                if (choiceInt == null) {
                    d.cr("test", "custom", choiceStr, TS_SerialComKinConyKC868_A32_R1_2_Chip.call(killTrigger, comX, chip -> {
                        return chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, choiceStr, chip.timeout, chip.validReplyPrefix, true);
                    }));
                    continue;
                }
                switch (choiceInt) {
                    case 0 ->
                        System.exit(0);
                    case 1 ->
                        debugEnabled = !debugEnabled;
                    case 2 ->
                        TS_SerialComKinConyKC868_A32_R1_2_Test.test(killTrigger, debugEnabled, true, false, false, false, false, false, false, false, false);
                    case 3 ->
                        TS_SerialComKinConyKC868_A32_R1_2_Test.test(killTrigger, debugEnabled, false, true, false, false, false, false, false, false, false);
                    case 4 ->
                        TS_SerialComKinConyKC868_A32_R1_2_Test.test(killTrigger, debugEnabled, false, false, true, false, false, false, false, false, false);
                    case 5 ->
                        TS_SerialComKinConyKC868_A32_R1_2_Test.test(killTrigger, debugEnabled, false, false, false, true, false, false, false, false, false);
                    case 6 ->
                        TS_SerialComKinConyKC868_A32_R1_2_Test.test(killTrigger, debugEnabled, false, false, false, false, true, false, false, false, false);
                    case 7 ->
                        TS_SerialComKinConyKC868_A32_R1_2_Test.test(killTrigger, debugEnabled, false, false, false, false, false, true, false, false, false);
                    case 8 ->
                        TS_SerialComKinConyKC868_A32_R1_2_Test.test(killTrigger, debugEnabled, false, false, false, false, false, false, true, false, false);
                    case 9 ->
                        TS_SerialComKinConyKC868_A32_R1_2_Test.test(killTrigger, debugEnabled, false, false, false, false, false, false, false, true, false);
                    case 10 ->
                        TS_SerialComKinConyKC868_A32_R1_2_Test.test(killTrigger, debugEnabled, false, false, false, false, false, false, false, false, true);
                    default ->
                        d.ce("test", "WHAT_TO_DO_WITH_THIS", choiceStr);
                }
            }
        } catch (Exception e) {
            TGS_FuncUtils.throwIfInterruptedException(e);
//            e.printStackTrace();
        }
    }

    public static void test(TS_ThreadSyncTrigger killTrigger,
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
        var comX = "COM3";
        if (debugEnable) {
            TS_SerialComMessageBroker.d.infoEnable = true;
        }

        //USAGE: GENERAL------------------------------------------
        if (test_chipname) {
            d.cr("test", "chipName", TS_SerialComKinConyKC868_A32_R1_2.chipName(killTrigger, comX));
        }

        //USAGE: DIGITAL IN GET-----------------------------------
        if (test_digitalIn_getAll) {
            d.cr("test", "digitalIn_getAll", TS_SerialComKinConyKC868_A32_R1_2.digitalIn_getAll(killTrigger, comX));
        }
        if (test_digitalIn_getIdx) {
            IntStream.range(0, 32).forEachOrdered(i -> {
                d.cr("test", "digitalIn_getIdx(" + i + ")", TS_SerialComKinConyKC868_A32_R1_2.digitalIn_getIdx(killTrigger, comX, i));
            });
        }

        //USAGE: DIGITAL OUT GET----------------------------------
        if (test_digitalOut_getAll) {
            d.cr("test", "digitalOut_getAll", TS_SerialComKinConyKC868_A32_R1_2.digitalOut_getAll(killTrigger, comX));
        }
        if (test_digitalOut_getIdx) {
            IntStream.range(0, 32).forEachOrdered(i -> {
                d.cr("test", "digitalOut_getIdx(" + i + ")", TS_SerialComKinConyKC868_A32_R1_2.digitalOut_getIdx(killTrigger, comX, i));
            });
        }
        //USAGE: DIGITAL OUT SET----------------------------------
        if (test_digitalOut_setAll) {
            d.cr("test", "digitalOut_setAll(true)", TS_SerialComKinConyKC868_A32_R1_2.digitalOut_setAll(killTrigger, comX, true));
            d.cr("test", "digitalOut_setAll(false)", TS_SerialComKinConyKC868_A32_R1_2.digitalOut_setAll(killTrigger, comX, false));
        }
        if (test_digitalOut_setIdx) {
            IntStream.range(0, 32).forEachOrdered(i -> {
                d.cr("test", "digitalOut_setIdx(" + i + ", true)", TS_SerialComKinConyKC868_A32_R1_2.digitalOut_setIdx(killTrigger, comX, i, true));
            });
            IntStream.range(0, 32).forEachOrdered(i -> {
                d.cr("test", "digitalOut_setIdx(" + i + ", false)", TS_SerialComKinConyKC868_A32_R1_2.digitalOut_setIdx(killTrigger, comX, i, false));
            });
        }
        //USAGE: DIGITAL OUT OSCILLATE---------------------------
        if (test_oscillate) {
            d.cr("test", "digitalOut_oscilate", TS_SerialComKinConyKC868_A32_R1_2.digitalOut_oscilate(killTrigger, comX, 12, 1, 2, 5));
        }

        //USAGE: MEMORY-------------------------------------------
        if (test_memory) {
            d.cr("test", "memInt_setIdx(1, 15)", TS_SerialComKinConyKC868_A32_R1_2.memInt_setIdx(killTrigger, comX, 1, 15));
            d.cr("test", "memInt_getAll", TS_SerialComKinConyKC868_A32_R1_2.memInt_getAll(killTrigger, comX));
        }
    }
}
