package Pages;

import GUI.*;
import Network.*;
import Objects.User;

/**
 * Project05 -- LoginPage
 * <p>
 * Creates page that is shown Login button is clicked
 * on the Welcome Page.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 */
public class LoginPage extends Page implements PageInterface {
    private Label titleLabel;
    private TextField usernameField;
    private TextField passwordField;
    private Button loginButton;
    private Button backButton;

    public LoginPage(Client client) {
        super(client);
        initContent();
    }

    @Override
    public void initContent() {
        panel.removeAll();

        titleLabel = new Label("Login to your account", 42);
        usernameField = new TextField("Enter Username", GUIConstants.SIZE_400_40);
        passwordField = new TextField("Enter Password", GUIConstants.SIZE_400_40);
        loginButton = new Button("Login", () -> loginAction(), GUIConstants.SIZE_400_40);
        backButton = new Button("Go back", () -> window.switchPage(new WelcomePage(client)), GUIConstants.SIZE_400_40, true);

        addComponents();
    }

    @Override
    public void addComponents() {
        panel.add(new Spacer(200));
        panel.add(titleLabel);
        panel.add(new Spacer(40));
        panel.add(usernameField);
        panel.add(new Spacer(10));
        panel.add(passwordField);
        panel.add(new Spacer(40));
        panel.add(loginButton);
        panel.add(new Spacer(10));
        panel.add(backButton);

        panel.revalidate();
        panel.repaint();
    }

    private void loginAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Username and password cannot be empty.");
            return;
        }

        client.sendToServer(
                new NetworkMessage(ServerCommand.LOGIN, client.IDENTIFIER, String.format("%s,%s", username, password)));

        NetworkMessage message = client.listenToServer();
        switch ((ClientCommand) message.getCommand()) {
            case LOGIN_SUCCESS -> {
                client.setUser((User) message.getObject());
                window.switchPage(new MainMenu(client));
            }
            case LOGIN_FAILURE -> {
                showError("Login failed.");
            }
        }
    }
}
