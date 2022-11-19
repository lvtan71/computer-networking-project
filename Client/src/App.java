import view.Login;
import view.Home;
import model.Client;
import presenter.Presenter;

public class App {
    public static void main(String[] args)
    {
        Login login = new Login();
        Home home = new Home();
        Client client = new Client();

        Presenter presenter = new Presenter(login, home, client);
        presenter.initController();
    }
}
