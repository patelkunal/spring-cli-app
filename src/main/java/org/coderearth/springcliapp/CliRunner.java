package org.coderearth.springcliapp;

import org.apache.commons.cli.ParseException;
import org.coderearth.springcliapp.command.CliCommandManager;
import org.coderearth.springcliapp.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by kunal_patel on 20/11/17.
 */
@Component
public class CliRunner implements CommandLineRunner, ExitCodeGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CliRunner.class);

    @Autowired
    private CliCommandManager cliCommandManager;

    @Autowired
    private ApplicationContext context;

    @Value("${clirunner.exit.code:100}")
    private int exitCode;

    @Override
    public void run(String... args) throws Exception {
        try {
            final Command command = cliCommandManager.parse(args);
            LOGGER.info("{}", command);
        } catch (ParseException pe) {
            cliCommandManager.printCliHelp();
            LOGGER.error("Error occuered while parsing CLI arguments, " + pe.getMessage());
            System.exit(SpringApplication.exit(context, this));
        }
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
