package controller;

import java.io.*;
import java.net.*;

import model.Server;
import view.Terminal;

public class Controller {
  private Server server;
  private Terminal terminal;

  public Controller(Server server, Terminal terminal) {
    this.terminal = terminal;
    this.server = server;

    this.server.addController(this);
  } 

  public void start() {
    server.run();
  }

  public void handleAction(String action) {
    terminal.print(action);
  }
}
