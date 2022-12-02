import model.Server;
import view.Home;
import controller.Controller;

import java.net.UnknownHostException;

public class App
{
    public static void main(String[] args) throws UnknownHostException {
        Server server = new Server();
        Home home = new Home();
        Controller controller = new Controller(server, home);
    }
}