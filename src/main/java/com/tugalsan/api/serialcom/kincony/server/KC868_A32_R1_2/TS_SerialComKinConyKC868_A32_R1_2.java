package com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2;

import com.tugalsan.api.cast.client.TGS_CastUtils;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2.core.TS_SerialComKinConyKC868_A32_R1_2_Chip;
import com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2.core.TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder;
import com.tugalsan.api.serialcom.server.utils.TS_SerialComUtils;
import com.tugalsan.api.stream.client.TGS_StreamUtils;
import com.tugalsan.api.string.server.TS_StringUtils;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
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

    public static Optional<String> chipName(TS_ThreadSyncTrigger killTrigger, String comX) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(killTrigger, comX, chip -> chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.chipName(), chip.timeout, chip.validReplyPrefix, true));
    }

    public static Optional<Boolean> digitalIn_getIdx(TS_ThreadSyncTrigger killTrigger, String comX, int pin) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getDigitalIn(pin);
            if (cmd.isEmpty()) {
                d.ce("digitalIn_getIdx", "ERROR_CMD_EMPTY", "pin", pin);
                return Optional.empty();
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd.get(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("digitalIn_getIdx", "ERROR_REPLY_EMPTY", reply);
                return Optional.empty();
            }
            return Optional.of(reply.get().equals("1"));
        });
    }

    public static Optional<List<Boolean>> digitalIn_getAll(TS_ThreadSyncTrigger killTrigger, String comX) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getDigitalIn_All();
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("digitalIn_getAll", "ERROR_REPLY_EMPTY", reply);
                return Optional.empty();
            }
            if (reply.get().length() != 32) {
                d.ce("digitalIn_getAll", "ERROR_SIZE_NOT_32", reply);
                return Optional.empty();
            }
            return Optional.of(TGS_StreamUtils.toLst(reply.get().chars().boxed().map(c -> c.equals(Integer.valueOf('1')))));
        });
    }

    public static Optional<Boolean> digitalOut_getIdx(TS_ThreadSyncTrigger killTrigger, String comX, int pin) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getDigitalOut(pin);
            if (cmd.isEmpty()) {
                d.ce("digitalOut_getIdx", "ERROR_CMD_EMPTY", "pin", pin);
                return Optional.empty();
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd.get(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("digitalOut_getIdx", "ERROR_REPLY_EMPTY", reply);
                return Optional.empty();
            }
            return Optional.of(reply.get().equals("1"));
        });
    }

    public static Optional<List<Boolean>> digitalOut_getAll(TS_ThreadSyncTrigger killTrigger, String comX) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getDigitalOut_All();
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("digitalOut_getAll", "ERROR_REPLY_EMPTY", reply);
                return Optional.empty();
            }
            if (reply.get().length() != 32) {
                d.ce("digitalOut_getAll", "ERROR_SIZE_NOT_32", reply);
                return Optional.empty();
            }
            return Optional.of(TGS_StreamUtils.toLst(reply.get().chars().boxed().map(c -> c.equals(Integer.valueOf('1')))));
        });
    }

    public static boolean digitalOut_setAll(TS_ThreadSyncTrigger killTrigger, String comX, boolean value) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut_All(value);
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("digitalOut_setAll", "ERROR_REPLY_EMPTY", "value", value);
                return false;
            }
            return reply.get().endsWith(chip.validReplySuffixSet);
        });
    }

    public static boolean digitalOut_setIdx(TS_ThreadSyncTrigger killTrigger, String comX, int pin, boolean value) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut(pin, value);
            if (cmd.isEmpty()) {
                d.ce("digitalOut_setIdx", "ERROR_CMD_EMPTY", "pin", pin, "value", value);
                return false;
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd.get(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("digitalOut_setIdx", "ERROR_REPLY_EMPTY", "pin", pin, "value", value);
                return false;
            }
            return reply.get().endsWith(chip.validReplySuffixSet);
        });
    }

    public static boolean digitalOut_oscilate(TS_ThreadSyncTrigger killTrigger, String comX, int pin, int secDuration, int secGap, int count) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut_Oscillating(pin, secDuration, secGap, count);
            if (cmd.isEmpty()) {
                d.ce("digitalOut_oscilate", "ERROR_CMD_EMPTY", "pin", pin, "secDuration", secDuration, "secGap", secGap, "count", count);
                return false;
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd.get(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("digitalOut_oscilate", "ERROR_REPLY_EMPTY", "pin", pin, "secDuration", secDuration, "secGap", secGap, "count", count);
            }
            return reply.get().endsWith(chip.validReplySuffixSet);
        });
    }

    public static Optional<List<Integer>> memInt_getAll(TS_ThreadSyncTrigger killTrigger, String comX) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getMemInt_All();
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("memInt_getAll", "ERROR_REPLY_EMPTY", reply);
                return Optional.empty();
            }
            var results = TS_StringUtils.toList_spc(reply.get());
            results.stream()
                    .filter(val -> (!TGS_CastUtils.isInteger(val)))
                    .map(val -> {
                        d.ce("memInt_getAll", "ERROR_NOT_INT", val, reply.get(), results);
                        return val;
                    })
                    .forEachOrdered(_item -> {
                        Optional.empty();
                    });
            return Optional.of(TGS_StreamUtils.toLst(results.stream().mapToInt(s -> Integer.valueOf(s))));
        });
    }

    public static Optional<Integer> mode_getIdx(TS_ThreadSyncTrigger killTrigger, String comX) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getMode_Idx();
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("getMode_Idx", "ERROR_REPLY_EMPTY", reply);
                return Optional.empty();
            }
            var result = TGS_CastUtils.toInteger(reply.get());
            return result == null ? Optional.empty() : Optional.of(result);
        });
    }

    public static boolean mode_setIdx(TS_ThreadSyncTrigger killTrigger, String comX, int idx) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setMode_Idx(idx);
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("setMode_Idx", "ERROR_REPLY_EMPTY", "idx", idx);
                return false;
            }
            return reply.get().endsWith(chip.validReplySuffixSet);
        });
    }

    public static boolean memInt_setIdx(TS_ThreadSyncTrigger killTrigger, String comX, int idx, int value) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setMemInt_Idx(idx, value);
            if (cmd.isEmpty()) {
                return false;
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd.get(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("memInt_setIdx", "ERROR_REPLY_EMPTY", "idx", idx, "value", value);
                return false;
            }
            return reply.get().endsWith(chip.validReplySuffixSet);
        });
    }

    public static boolean memInt_setAll(TS_ThreadSyncTrigger killTrigger, String comX, List<Integer> values16) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setMemInt_All(values16);
            if (cmd.isEmpty()) {
                return false;
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd.get(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("memInt_setAll", "ERROR_REPLY_EMPTY", "values", values16);
                return false;
            }
            return reply.get().endsWith(chip.validReplySuffixSet);
        });
    }

    public static boolean digitalOut_oscilateAll(TS_ThreadSyncTrigger killTrigger, String comX, List<Integer> pins) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut_OscillatingAll(pins);
            if (cmd.isEmpty()) {
                return false;
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd.get(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("digitalOut_oscilateAll", "ERROR_REPLY_EMPTY", "pins", pins);
                return false;
            }
            return reply.get().endsWith(chip.validReplySuffixSet);
        });
    }
}
