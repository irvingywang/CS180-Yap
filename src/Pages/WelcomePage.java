package Pages;

import GUI.*;
import Network.Client;

public class WelcomePage extends Page {
    Label label;
    Label label2;
    Button createAccountButton;
    Button loginButton;

    public WelcomePage(Client client) {
        super(client);
        initContent();
    }

    @Override
    public void initContent() {
        label = new Label("Connect and chat with your", 40);
        label2 = new Label("friends. Instantly.", 40);

        createAccountButton = new Button("Create an account",
                () -> window.switchPage(new CreateUserPage(client)), GUIConstants.SIZE_400_40, true);

        loginButton = new Button("Login",
                () -> window.switchPage(new LoginPage(client)), GUIConstants.SIZE_400_40);

        addComponents();
    }

    @Override
    public void addComponents() {
        panel.add(new Spacer(250));
        panel.add(label);
        panel.add(label2);
        panel.add(new Spacer(60));
        panel.add(loginButton);
        panel.add(new Spacer(10));
        panel.add(createAccountButton);
    }
}