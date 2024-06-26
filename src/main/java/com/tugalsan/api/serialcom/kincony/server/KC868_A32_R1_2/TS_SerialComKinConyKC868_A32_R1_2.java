package com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2;

import com.tugalsan.api.cast.client.TGS_CastUtils;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2.core.TS_SerialComKinConyKC868_A32_R1_2_Chip;
import com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2.core.TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder;
import com.tugalsan.api.serialcom.server.utils.TS_SerialComUtils;
import com.tugalsan.api.stream.client.TGS_StreamUtils;
import com.tugalsan.api.string.client.TGS_StringUtils;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import com.tugalsan.api.union.client.TGS_UnionExcuse;
import com.tugalsan.api.union.client.TGS_UnionExcuseVoid;
import java.util.List;

public class TS_SerialComKinConyKC868_A32_R1_2 {

    final private static TS_Log d = TS_Log.of(TS_SerialComKinConyKC868_A32_R1_2.class);

    public static List<String> listPortNamesFull() {
        return TS_SerialComUtils.listNamesFull();
    }

    public static List<String> listPortNames() {
        return TS_SerialComUtils.listNamesPort();
    }

    public static TGS_UnionExcuse<String> chipName(TS_ThreadSyncTrigger killTrigger, String comX) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.call(killTrigger, comX, chip -> {
            return chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(
                    killTrigger,
                    TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.chipName(),
                    chip.timeout,
                    chip.validReplyPrefix,
                    true
            );
        });
    }

    public static TGS_UnionExcuse<Boolean> digitalIn_getIdx(TS_ThreadSyncTrigger killTrigger, String comX, int pin) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.call(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getDigitalIn(pin);
            if (cmd.isEmpty()) {
                return TGS_UnionExcuse.ofExcuse(d.className, "digitalIn_getIdx", "ERROR_CMD_EMPTY -> pin:" + pin);
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd.get(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            return TGS_UnionExcuse.of(reply.value().equals("1"));
        });
    }

    public static TGS_UnionExcuse<List<Boolean>> digitalIn_getAll(TS_ThreadSyncTrigger killTrigger, String comX) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.call(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getDigitalIn_All();
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return TGS_UnionExcuse.ofExcuse(d.className, "digitalIn_getAll", "ERROR_REPLY_EMPTY");
            }
            if (reply.value().length() != 32) {
                return TGS_UnionExcuse.ofExcuse(d.className, "digitalIn_getAll", "reply.value().length() != 32");
            }
            return TGS_UnionExcuse.of(TGS_StreamUtils.toLst(reply.value().chars().boxed().map(c -> c.equals(Integer.valueOf('1')))));
        });
    }

    public static TGS_UnionExcuse<Boolean> digitalOut_getIdx(TS_ThreadSyncTrigger killTrigger, String comX, int pin) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.call(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getDigitalOut(pin);
            if (cmd.isEmpty()) {
                return TGS_UnionExcuse.ofExcuse(d.className, "digitalOut_getIdx", "ERROR_CMD_EMPTY -> pin:" + pin);
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd.get(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            return TGS_UnionExcuse.of(reply.value().equals("1"));
        });
    }

    public static TGS_UnionExcuse<List<Boolean>> digitalOut_getAll(TS_ThreadSyncTrigger killTrigger, String comX) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.call(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getDigitalOut_All();
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            if (reply.value().length() != 32) {
                return TGS_UnionExcuse.ofExcuse(d.className, "digitalOut_getAll", "reply.value().length() != 32");
            }
            return TGS_UnionExcuse.of(TGS_StreamUtils.toLst(reply.value().chars().boxed().map(c -> c.equals(Integer.valueOf('1')))));
        });
    }

    public static TGS_UnionExcuseVoid digitalOut_setAll(TS_ThreadSyncTrigger killTrigger, String comX, boolean value) {
        TGS_UnionExcuse<Boolean> u = TS_SerialComKinConyKC868_A32_R1_2_Chip.call(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut_All(value);
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            return TGS_UnionExcuse.of(reply.value().endsWith(chip.validReplySuffixSet));
        });
        if (u.isExcuse()) {
            return u.toExcuseVoid();
        }
        if (!u.value()) {
            return TGS_UnionExcuseVoid.ofExcuse(d.className, "digitalOut_setAll", "!u.value()");
        }
        return TGS_UnionExcuseVoid.ofVoid();
    }

    public static TGS_UnionExcuseVoid digitalOut_setIdx(TS_ThreadSyncTrigger killTrigger, String comX, int pin, boolean value) {
        var u = TS_SerialComKinConyKC868_A32_R1_2_Chip.call(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut(pin, value);
            if (cmd.isExcuse()) {
                return cmd.toExcuse();
            }
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd.value(), chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            return TGS_UnionExcuse.of(reply.value().endsWith(chip.validReplySuffixSet));
        });
        if (u.isExcuse()) {
            return u.toExcuseVoid();
        }
        if (!u.value()) {
            return TGS_UnionExcuseVoid.ofExcuse(d.className, "digitalOut_setIdx", "!u.value()");
        }
        return TGS_UnionExcuseVoid.ofVoid();
    }

    public static TGS_UnionExcuseVoid digitalOut_oscilate(TS_ThreadSyncTrigger killTrigger, String comX, int pin, int secDuration, int secGap, int count) {
        var u = TS_SerialComKinConyKC868_A32_R1_2_Chip.call(killTrigger, comX, chip -> {
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
        if (u.isExcuse()) {
            return u.toExcuseVoid();
        }
        if (!u.value()) {
            return TGS_UnionExcuseVoid.ofExcuse(d.className, "digitalOut_oscilate", "!u.value()");
        }
        return TGS_UnionExcuseVoid.ofVoid();
    }

    public static TGS_UnionExcuse<List<Integer>> memInt_getAll(TS_ThreadSyncTrigger killTrigger, String comX) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.call(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getMemInt_All();
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            var results = TGS_StringUtils.jre().toList_spc(reply.value());
//            results.stream()
//                    .filter(val -> (!TGS_CastUtils.isInteger(val)))
//                    .map(val -> {
//                        d.ce("memInt_getAll", "ERROR_NOT_INT", val, reply.value(), results);
//                        return val;
//                    })
//                    .forEachOrdered(_item -> {
////                        Optional.empty();
//                    });
            return TGS_UnionExcuse.of(TGS_StreamUtils.toLst(results.stream().mapToInt(s -> Integer.valueOf(s))));
        });
    }

    public static TGS_UnionExcuse<Integer> mode_getIdx(TS_ThreadSyncTrigger killTrigger, String comX) {
        return TS_SerialComKinConyKC868_A32_R1_2_Chip.call(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.getMode_Idx();
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            var result = TGS_CastUtils.toInteger(reply.value());
            if (result == null) {
                return TGS_UnionExcuse.ofExcuse(d.className, "mode_getIdx", "result == null");
            }
            return TGS_UnionExcuse.of(result);
        });
    }

    public static TGS_UnionExcuseVoid mode_setIdx(TS_ThreadSyncTrigger killTrigger, String comX, int idx) {
        var u = TS_SerialComKinConyKC868_A32_R1_2_Chip.call(killTrigger, comX, chip -> {
            var cmd = TS_SerialComKinConyKC868_A32_R1_2_CommandBuilder.setMode_Idx(idx);
            var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(killTrigger, cmd, chip.timeout, chip.validReplyPrefix, true);
            if (reply.isExcuse()) {
                return reply.toExcuse();
            }
            return TGS_UnionExcuse.of(reply.value().endsWith(chip.validReplySuffixSet));
        });
        if (u.isExcuse()) {
            return u.toExcuseVoid();
        }
        if (!u.value()) {
            return TGS_UnionExcuseVoid.ofExcuse(d.className, "mode_setIdx", "!u.value()");
        }
        return TGS_UnionExcuseVoid.ofVoid();
    }

    public static TGS_UnionExcuseVoid memInt_setIdx(TS_ThreadSyncTrigger killTrigger, String comX, int idx, int value) {
        var u = TS_SerialComKinConyKC868_A32_R1_2_Chip.call(killTrigger, comX, chip -> {
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
        if (u.isExcuse()) {
            return u.toExcuseVoid();
        }
        if (!u.value()) {
            return TGS_UnionExcuseVoid.ofExcuse(d.className, "mode_setIdx", "!u.value()");
        }
        return TGS_UnionExcuseVoid.ofVoid();
    }

    public static TGS_UnionExcuseVoid memInt_setAll(TS_ThreadSyncTrigger killTrigger, String comX, List<Integer> values16) {
        var u = TS_SerialComKinConyKC868_A32_R1_2_Chip.call(killTrigger, comX, chip -> {
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
        if (u.isExcuse()) {
            return u.toExcuseVoid();
        }
        if (!u.value()) {
            return TGS_UnionExcuseVoid.ofExcuse(d.className, "mode_setIdx", "!u.value()");
        }
        return TGS_UnionExcuseVoid.ofVoid();
    }

    public static TGS_UnionExcuseVoid digitalOut_oscilateAll(TS_ThreadSyncTrigger killTrigger, String comX, List<Integer> pins) {
        var u = TS_SerialComKinConyKC868_A32_R1_2_Chip.call(killTrigger, comX, chip -> {
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
        if (u.isExcuse()) {
            return u.toExcuseVoid();
        }
        if (!u.value()) {
            return TGS_UnionExcuseVoid.ofExcuse(d.className, "mode_setIdx", "!u.value()");
        }
        return TGS_UnionExcuseVoid.ofVoid();
    }
}
