import view.Login;
import view.Home;
import model.Client;
import controller.Controller;

public class App {
    public static void main(String[] args)
    {
        Login login = new Login();
        Home home = new Home();
        Client client = new Client();

        Controller controller = new Controller(login, home, client);
        controller.initController();
    }
}
