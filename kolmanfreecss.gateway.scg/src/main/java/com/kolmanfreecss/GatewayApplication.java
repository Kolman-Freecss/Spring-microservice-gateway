package com.kolmanfreecss;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.SpringVersion;

import java.nio.charset.Charset;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

@SpringBootApplication
@Log4j2
public class GatewayApplication {

    public static void main(String[] args) {
        printServerInfo();
        showLevel();
        SpringApplication.run(GatewayApplication.class, args);
    }

    private static void printServerInfo() {
        log.info("[ Server Info ]");
        log.info("Spring Version: {}", SpringVersion.getVersion());
        log.info("Java Version: {}/{}", System.getProperty("java.version"), System.getProperty("java.runtime.version"));
        log.info("Java VM Vendor: {}/{}", System.getProperty("java.vm.vendor"), System.getProperty("java.vm.version"));
        log.info("OS Name: {}/{}", System.getProperty("os.name"), System.getProperty("os.version"));
        log.info("Default Locale: {}", Locale.getDefault());
        log.info("File Encoding: {}", System.getProperty("file.encoding"));
        log.info("Default Charset: {}", Charset.defaultCharset().name());
        log.info("File Separator: [{}]", System.getProperty("file.separator"));
        log.info("Decimal separador/Thousands separator: [{}]/[{}]",
                DecimalFormatSymbols.getInstance().getDecimalSeparator(),
                DecimalFormatSymbols.getInstance().getGroupingSeparator());
        log.info("Double 12345.6789 - Formatted: {}", NumberFormat.getInstance().format(12345.6789));
    }

    private static void showLevel() {
        log.trace("Log Level TRACE activated.");
        log.debug("Log Level DEBUG activated.");
        log.info("Log Level INFO activated.");
        log.warn("Log Level WARN activated.");
        log.error("Log Level ERROR activated.");
    }

}
