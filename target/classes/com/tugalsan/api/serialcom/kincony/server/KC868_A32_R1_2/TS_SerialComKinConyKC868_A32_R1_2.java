package com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2;

import com.tugalsan.api.cast.client.TGS_CastUtils;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2.core.TS_SerialComKinConyKC868_A32_R1_2_Chip;
import com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2.core.TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder;
import com.tugalsan.api.stream.client.TGS_StreamUtils;
import com.tugalsan.api.string.server.TS_StringUtils;
import java.util.List;
import java.util.Optional;

public class TS_SerialComKinConyKC868_A32_R1_2 {

    final private static TS_Log d = TS_Log.of(TS_SerialComKinConyKC868_A32_R1_2.class);

    public static Optional<String> chipName() {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(chip -> chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.chipName(), chip.timeout, chip.validReplyPrefix, true));
    }

    public static Optional<Boolean> digitalIn_getIdx(int pin) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getDigitalIn(pin);
            if (cmd.isEmpty()) {
                d.ce("digitalIn_getIdx", "ERROR_CMD_EMPTY", "pin", pin);
                return Optional.empty();
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd.get(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("digitalIn_getIdx", "ERROR_REPLY_EMPTY", reply);
                return Optional.empty();
            }
            return Optional.of(reply.get().equals("1"));
        });
    }

    public static Optional<List<Boolean>> digitalIn_getAll() {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getDigitalIn_All();
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd, chip.timeout, chip.validReplyPrefix, true);
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

    public static Optional<Boolean> digitalOut_getIdx(int pin) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getDigitalOut(pin);
            if (cmd.isEmpty()) {
                d.ce("digitalOut_getIdx", "ERROR_CMD_EMPTY", "pin", pin);
                return Optional.empty();
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd.get(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("digitalOut_getIdx", "ERROR_REPLY_EMPTY", reply);
                return Optional.empty();
            }
            return Optional.of(reply.get().equals("1"));
        });
    }

    public static Optional<List<Boolean>> digitalOut_getAll() {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getDigitalOut_All();
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd, chip.timeout, chip.validReplyPrefix, true);
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

    public static boolean digitalOut_setAll(boolean value) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut_All(value);
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("digitalOut_setAll", "ERROR_REPLY_EMPTY", "value", value);
                return false;
            }
            return reply.get().endsWith(chip.validReplySuffixSet);
        });
    }

    public static boolean digitalOut_setIdx(int pin, boolean value) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut(pin, value);
            if (cmd.isEmpty()) {
                d.ce("digitalOut_setIdx", "ERROR_CMD_EMPTY", "pin", pin, "value", value);
                return false;
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd.get(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("digitalOut_setIdx", "ERROR_REPLY_EMPTY", "pin", pin, "value", value);
                return false;
            }
            return reply.get().endsWith(chip.validReplySuffixSet);
        });
    }

    public static boolean digitalOut_oscilate(int pin, int secDuration, int secGap, int count) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut_Oscillating(pin, secDuration, secGap, count);
            if (cmd.isEmpty()) {
                d.ce("digitalOut_oscilate", "ERROR_CMD_EMPTY", "pin", pin, "secDuration", secDuration, "secGap", secGap, "count", count);
                return false;
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd.get(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("digitalOut_oscilate", "ERROR_REPLY_EMPTY", "pin", pin, "secDuration", secDuration, "secGap", secGap, "count", count);
            }
            return reply.get().endsWith(chip.validReplySuffixSet);
        });
    }

    public static Optional<List<Integer>> memInt_getAll() {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callOptional(chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getMemInt_All();
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("memInt_getAll", "ERROR_REPLY_EMPTY", reply);
                return Optional.empty();
            }
            var results = TS_StringUtils.toList_spc(reply.get());
            for (var val : results) {
                if (!TGS_CastUtils.isInteger(val)) {
                    d.ce("memInt_getAll", "ERROR_NOT_INT", val, reply.get(), results);
                    Optional.empty();
                }
            }
            return Optional.of(TGS_StreamUtils.toLst(results.stream().mapToInt(s -> Integer.valueOf(s))));
        });
    }

    public static boolean memInt_setIdx(int idx, int value) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setMemInt_Idx(idx, value);
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("memInt_setIdx", "ERROR_REPLY_EMPTY", "idx", idx, "value", value);
                return false;
            }
            return reply.get().endsWith(chip.validReplySuffixSet);
        });
    }

    public static boolean memInt_setAll(List<Integer> values16) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.callBoolResult(chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setMemInt_All(values16);
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isEmpty()) {
                d.ce("memInt_setIdx", "ERROR_REPLY_EMPTY", "values", values16);
                return false;
            }
            return reply.get().endsWith(chip.validReplySuffixSet);
        });
    }
}
