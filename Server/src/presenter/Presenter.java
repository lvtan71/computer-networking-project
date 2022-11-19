package presenter;

import model.Server;
import view.Terminal;

public class Presenter {
  private Server server;
  private Terminal terminal;

  public Presenter(Server server, Terminal terminal) {
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
