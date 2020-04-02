package tech.openchat.telestore.cmd;

/**
 * @author vgorin
 * file created on 2020-04-02 19:48
 */

interface NamedCommand extends Command {
    String getDefaultCommandName();
}
