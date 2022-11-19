import model.Server;
import view.Terminal;
import presenter.Presenter;

public class App {
    public static void main(String[] args) {
        Server server = new Server(9999, 5);
        Terminal terminal = new Terminal();
        Presenter presenter = new Presenter(server, terminal);


        presenter.start();
    }
}
