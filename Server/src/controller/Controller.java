package controller;

import java.io.*;
import java.net.*;

import model.Server;
import view.Terminal;
import static model.Server.State.*;

public class Controller {
  private Server server;
  private Terminal terminal;

  public Controller(Server server, Terminal terminal) {
    this.server = server;
    this.terminal = terminal;
  } 

  public void run() {
    while(server.getState() != STOP) {


    }
  }
}
