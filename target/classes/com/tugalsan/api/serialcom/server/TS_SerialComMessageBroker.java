package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.thread.server.TS_ThreadCall;
import com.tugalsan.api.thread.server.TS_ThreadSafeLst;
import com.tugalsan.api.thread.server.TS_ThreadWait;
import com.tugalsan.api.thread.server.core.TS_ThreadCallParallelTimeoutException;
import com.tugalsan.api.validator.client.TGS_ValidatorType1;
import java.time.Duration;
import java.util.Optional;

public class TS_SerialComMessageBroker {

    final public static TS_Log d = TS_Log.of(true, TS_SerialComMessageBroker.class);

    public TS_SerialComMessageBroker(int maxSize) {
        this.maxSize = maxSize;
    }
    final public TS_ThreadSafeLst<String> replies = new TS_ThreadSafeLst();
    final public int maxSize;

    public static TS_SerialComMessageBroker of(int maxSize) {
        return new TS_SerialComMessageBroker(maxSize);
    }

    public void setConnection(TS_SerialComConnection con) {
        this.con = con;
    }
    private TS_SerialComConnection con;

    public void onReply(String reply) {
        replies.add(reply);
        replies.cropToLengthFast(maxSize);
        if (d.infoEnable) {
            if (reply.startsWith("REPLY_OF:")) {
                d.cr("onReply", reply);
                return;
            }
            if (reply.startsWith("ERROR")) {
                d.ce("onReply", reply);
                return;
            }
            d.ci("onReply", reply);
        }
    }

    public Optional<String> sendTheCommand_and_fetchMeReplyInMaxSecondsOf(String command, Duration maxDuration, String filterPrefix, boolean filterContainCommand) {
        if (!con.send(command)) {
            d.ce("sendTheCommand_and_fetchMeReplyInMaxSecondsOf", command, "ERROR_SENDING");
            return Optional.empty();
        }
        TGS_ValidatorType1<String> condition = val -> {
            if (filterContainCommand && !val.contains(command)) {
                return false;
            }
            if (filterPrefix != null && !val.startsWith(filterPrefix)) {
                return false;
            }
            return true;
        };
        var run = TS_ThreadCall.single(maxDuration, () -> {
            String reply = null;
            while (reply == null) {
                reply = replies.findFirst(val -> condition.validate(val));
                TS_ThreadWait.of(Duration.ofMillis(100));
                Thread.yield();
            }
            if (filterContainCommand && filterPrefix != null) {
                reply = reply.substring(filterPrefix.length() + command.length() + "->".length());
            } else if (filterContainCommand) {
                reply = reply.substring(command.length() + "->".length());
            } else if (filterPrefix != null) {
                reply = reply.substring(filterPrefix.length() + "->".length());
            }
            return reply;
        });
        replies.removeAll(val -> condition.validate(val));
        if (run.resultsForSuccessfulOnes.isEmpty() || run.resultsForSuccessfulOnes.get(0) == null) {
            run.exceptions.forEach(e -> {
                if (e instanceof TS_ThreadCallParallelTimeoutException) {
                    d.ce("sendTheCommand_and_fetchMeReplyInMaxSecondsOf", command, "ERROR_TIMEOUT");
                    return;
                }
                d.ct("sendTheCommand_and_fetchMeReplyInMaxSecondsOf->" + command, e);
            });
            return Optional.empty();
        }
        return Optional.of(run.resultsForSuccessfulOnes.get(0));
    }
}
