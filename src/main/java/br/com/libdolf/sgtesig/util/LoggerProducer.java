package br.com.libdolf.sgtesig.util;

import jakarta.enterprise.inject.Produces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggerProducer {
    @Produces
    public Logger produceLogger() {
        return LoggerFactory.getLogger(getClass());
    }
}
