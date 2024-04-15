package com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2;

import com.tugalsan.api.cast.client.TGS_CastUtils;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2.core.TS_SerialComKinConyKC868_A32_R1_2_Chip;
import com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2.core.TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder;
import com.tugalsan.api.serialcom.server.utils.TS_SerialComUtils;
import com.tugalsan.api.stream.client.TGS_StreamUtils;
import com.tugalsan.api.string.server.TS_StringUtils;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import com.tugalsan.api.union.client.TGS_UnionExcuse;
import java.util.List;
import java.util.Optional;

public class TS_SerialComKinConyKC868_A32_R1_2 {

    final private static TS_Log d = TS_Log.of(TS_SerialComKinConyKC868_A32_R1_2.class);

    public static List<String> listPortNamesFull() {
        return TS_SerialComUtils.listNamesFull();
    }

    public static List<String> listPortNames() {
        return TS_SerialComUtils.listNamesPort();
    }

    public static TGS_UnionExcuse<String> chipName(TS_ThreadSyncTrigger killTrigger, String comX) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(
                killTrigger,
                comX,
                chip -> {
                    return chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.chipName(), chip.timeout, chip.validReplyPrefix, true);
                }
        );
    }

    public static TGS_UnionExcuse<Boolean> digitalIn_getIdx(TS_ThreadSyncTrigger killTrigger, String comX, int pin) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getDigitalIn(pin);
            if (cmd.isExcuse()) {
                return cmd.toExcuse();
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd.value(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            return TGS_UnionExcuse.of(reply.value().equals("1"));
        });
    }

    public static TGS_UnionExcuse<List<Boolean>> digitalIn_getAll(TS_ThreadSyncTrigger killTrigger, String comX) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getDigitalIn_All();
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            if (reply.value().length() != 32) {
                return TGS_UnionExcuse.ofExcuse(d.className, "digitalIn_getAll", "ERROR_SIZE_NOT_32: " + reply);
            }
            return TGS_UnionExcuse.of(TGS_StreamUtils.toLst(reply.value().chars().boxed().map(c -> c.equals(Integer.valueOf('1')))));
        });
    }

    public static TGS_UnionExcuse<Boolean> digitalOut_getIdx(TS_ThreadSyncTrigger killTrigger, String comX, int pin) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getDigitalOut(pin);
            if (cmd.isExcuse()) {
                return cmd.toExcuse();
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd.value(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            return TGS_UnionExcuse.of(reply.value().equals("1"));
        });
    }

    public static TGS_UnionExcuse<List<Boolean>> digitalOut_getAll(TS_ThreadSyncTrigger killTrigger, String comX) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getDigitalOut_All();
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            if (reply.value().length() != 32) {
                return TGS_UnionExcuse.ofExcuse(d.className, "digitalOut_getAll", "ERROR_SIZE_NOT_32: " + reply);
            }
            return TGS_UnionExcuse.of(TGS_StreamUtils.toLst(reply.value().chars().boxed().map(c -> c.equals(Integer.valueOf('1')))));
        });
    }

    public static TGS_UnionExcuse<Boolean> digitalOut_setAll(TS_ThreadSyncTrigger killTrigger, String comX, boolean value) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut_All(value);
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            return TGS_UnionExcuse.of(reply.value().endsWith(chip.validReplySuffixSet));
        });
    }

    public static TGS_UnionExcuse<Boolean> digitalOut_setIdx(TS_ThreadSyncTrigger killTrigger, String comX, int pin, boolean value) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut(pin, value);
            if (cmd.isExcuse()) {
                d.ce("digitalOut_setIdx", "ERROR_CMD_EMPTY", "pin", pin, "value", value);
                return cmd.toExcuse();
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd.value(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            return TGS_UnionExcuse.of(reply.value().endsWith(chip.validReplySuffixSet));
        });
    }

    public static TGS_UnionExcuse<Boolean> digitalOut_oscilate(TS_ThreadSyncTrigger killTrigger, String comX, int pin, int secDuration, int secGap, int count) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut_Oscillating(pin, secDuration, secGap, count);
            if (cmd.isExcuse()) {
                return cmd.toExcuse();
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd.value(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }

            return TGS_UnionExcuse.of(reply.value().endsWith(chip.validReplySuffixSet));
        });
    }

    public static TGS_UnionExcuse<List<Integer>> memInt_getAll(TS_ThreadSyncTrigger killTrigger, String comX) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getMemInt_All();
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            var results = TS_StringUtils.toList_spc(reply.value());
            results.stream()
                    .filter(val -> (!TGS_CastUtils.isInteger(val)))
                    .map(val -> {
                        d.ce("memInt_getAll", "ERROR_NOT_INT", val, reply.value(), results);
                        return val;
                    })
                    .forEachOrdered(_item -> {
                        Optional.empty();
                    });
            return TGS_UnionExcuse.of(TGS_StreamUtils.toLst(results.stream().mapToInt(s -> Integer.valueOf(s))));
        });
    }

    public static TGS_UnionExcuse<Integer> mode_getIdx(TS_ThreadSyncTrigger killTrigger, String comX) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getMode_Idx();
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            return TGS_CastUtils.toInteger(reply.value());
        });
    }

    public static TGS_UnionExcuse<Boolean> mode_setIdx(TS_ThreadSyncTrigger killTrigger, String comX, int idx) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setMode_Idx(idx);
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            return TGS_UnionExcuse.of(reply.value().endsWith(chip.validReplySuffixSet));
        });
    }

    public static TGS_UnionExcuse<Boolean> memInt_setIdx(TS_ThreadSyncTrigger killTrigger, String comX, int idx, int value) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setMemInt_Idx(idx, value);
            if (cmd.isExcuse()) {
                return cmd.toExcuse();
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd.value(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            return TGS_UnionExcuse.of(reply.value().endsWith(chip.validReplySuffixSet));
        });
    }

    public static TGS_UnionExcuse<Boolean> memInt_setAll(TS_ThreadSyncTrigger killTrigger, String comX, List<Integer> values16) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setMemInt_All(values16);
            if (cmd.isExcuse()) {
                return cmd.toExcuse();
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd.value(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            return TGS_UnionExcuse.of(reply.value().endsWith(chip.validReplySuffixSet));
        });
    }

    public static TGS_UnionExcuse<Boolean> digitalOut_oscilateAll(TS_ThreadSyncTrigger killTrigger, String comX, List<Integer> pins) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut_OscillatingAll(pins);
            if (cmd.isExcuse()) {
                return cmd.toExcuse();
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd.value(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            return TGS_UnionExcuse.of(reply.value().endsWith(chip.validReplySuffixSet));
        });
    }
}
