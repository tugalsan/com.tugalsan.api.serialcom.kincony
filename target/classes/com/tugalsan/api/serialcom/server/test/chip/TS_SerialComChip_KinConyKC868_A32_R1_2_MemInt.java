package com.tugalsan.api.serialcom.server.test.chip;

import com.tugalsan.api.cast.client.TGS_CastUtils;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.string.server.TS_StringUtils;
import java.util.stream.IntStream;

public class TS_SerialComChip_KinConyKC868_A32_R1_2_MemInt {

    final private static TS_Log d = TS_Log.of(TS_SerialComChip_KinConyKC868_A32_R1_2_MemInt.class);

    private TS_SerialComChip_KinConyKC868_A32_R1_2_MemInt(TS_SerialComChip_KinConyKC868_A32_R1_2 chip) {
        this.chip = chip;
    }
    final private TS_SerialComChip_KinConyKC868_A32_R1_2 chip;
    final private int[] buffer = new int[16];

    public static TS_SerialComChip_KinConyKC868_A32_R1_2_MemInt of(TS_SerialComChip_KinConyKC868_A32_R1_2 chip) {
        return new TS_SerialComChip_KinConyKC868_A32_R1_2_MemInt(chip);
    }

    public boolean refreshAll() {
        var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder.getMemInt_All();
        var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd, chip.timeout, chip.validReplyPrefix, true);
        if (reply.isEmpty()) {
            return false;
        }
        var resultString = reply.get();
        var results = TS_StringUtils.toList_spc(resultString);
        if (results.size() != buffer.length) {
            d.ce("refreshAll", "ERROR_SIZE_NOT_VALID", resultString, results);
            return false;
        }
        for (var val : results) {
            if (!TGS_CastUtils.isInteger(val)) {
                d.ce("refreshAll", "ERROR_NOT_INT", val, resultString, results);
                return false;
            }
        }
        d.ci("refreshAll", results);
        IntStream.range(0, buffer.length).forEach(i -> {
            buffer[i] = TGS_CastUtils.toInteger(results.get(i));
        });
        d.ci("refreshAll", results);
        return true;
    }

    public int get(int idx) {
        d.ci("get", "idx", idx);
        return buffer[idx];
    }

    public boolean set(int idx, int secDuration) {
        var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder.setMemInt_Idx(idx, secDuration);
        if (cmd.isEmpty()) {
            return false;
        }
        var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd.get(), chip.timeout, chip.validReplyPrefix, true);
        if (reply.isEmpty()) {
            return false;
        }
        var result = reply.get();
        d.ci("set", "result", result);
        var processed = result.endsWith(chip.validReplySuffixSet);
        if (processed) {
            buffer[idx] = secDuration;
        }
        return processed;
    }
}
