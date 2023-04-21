module com.tugalsan.api.serialcom {
    requires com.fazecast.jSerialComm;
    requires com.tugalsan.api.runnable;
    requires com.tugalsan.api.thread;
    requires com.tugalsan.api.list;
    requires com.tugalsan.api.cast;
    requires com.tugalsan.api.validator;
    requires com.tugalsan.api.log;
    requires com.tugalsan.api.callable;
    requires com.tugalsan.api.string;
    requires com.tugalsan.api.pack;
    requires com.tugalsan.api.coronator;
    requires com.tugalsan.api.unsafe;
    exports com.tugalsan.api.serialcom.server;
    exports com.tugalsan.api.serialcom.server.test;
    exports com.tugalsan.api.serialcom.server.test.chip;
}
