package Pages;

import GUI.*;
import Network.*;
import Objects.User;

/**
 * Project05 -- EditProfile
 * <p>
 * Creates page that is shown after Edit Profile button
 * is clicked on the Main Menu
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 */
public class EditProfilePage extends Page {
    // Declare components here
    private Label titleLabel;
    private TextField displayNameField;
    private TextField usernameField;
    private TextField statusField;
    private TextField passwordField;
    private Dropdown publicDropdown;
    private Button saveButton;
    private Button backButton;


    public EditProfilePage(Client client) {
        super(client);
    }

    @Override
    public void initContent() {
        // Initialize components here
        titleLabel = new Label("Edit Profile", 42);
        String displayName = client.getDisplayName();
        displayName = displayName.isEmpty() ? "Enter Display Name" : displayName;
        displayNameField = new TextField(displayName, GUIConstants.SIZE_400_40);
        usernameField = new TextField(client.getUsername(), GUIConstants.SIZE_400_40);
        passwordField = new TextField(client.getPassword(), GUIConstants.SIZE_400_40);
        if (client.getStatus() == null)
            statusField = new TextField(" What's on your mind? ", GUIConstants.SIZE_400_40);
        else
            statusField = new TextField(client.getStatus(), GUIConstants.SIZE_400_40);
        String[] privacy = client.isPublicProfile() ? new String[]{"Public", "Private"} : new String[]{"Private", "Public"};
        publicDropdown = new Dropdown(privacy, GUIConstants.SIZE_400_40);
        saveButton = new Button("Save", () -> saveAction(), GUIConstants.SIZE_400_40);
        backButton = new Button("Back to menu", () -> window.switchPage(new MainMenu(client)), GUIConstants.SIZE_400_40, true);

        addComponents();
    }

    @Override
    public void addComponents() {
        // Add components to panel here
        panel.add(new Spacer(180));
        panel.add(titleLabel);
        panel.add(new Spacer(40));
        panel.add(displayNameField);
        panel.add(new Spacer(10));
        panel.add(usernameField);
        panel.add(new Spacer(10));
        panel.add(passwordField);
        panel.add(new Spacer(10));
        panel.add(statusField);
        panel.add(new Spacer(10));
        panel.add(publicDropdown);
        panel.add(new Spacer(40));
        panel.add(saveButton);
        panel.add(new Spacer(10));
        panel.add(backButton);

        panel.revalidate();
    }

    private void saveAction() {
        String displayName = displayNameField.getText();
        displayName = displayName.isEmpty() ? client.getDisplayName() : displayName;
        String username = usernameField.getText();
        username = username.isEmpty() ? client.getUsername() : username;
        String password = passwordField.getText();
        password = password.isEmpty() ? client.getPassword() : password;
        String status = statusField.getText();
        status = status.isEmpty() ? client.getStatus() : status;

        String privacy = (String) publicDropdown.getSelectedItem();

        client.sendToServer(
                new NetworkMessage(ServerCommand.SAVE_PROFILE, client.IDENTIFIER,
                        String.format("%s:%s:%s:%s:%s", displayName, username, password, status, privacy)));

        NetworkMessage response = client.listenToServer();
        switch ((ClientCommand) response.getCommand()) {
            case SAVE_PROFILE_SUCCESS -> {
                client.setUser((User) response.getObject());
                window.switchPage(new EditProfilePage(client));
            }
            case SAVE_PROFILE_FAILURE -> {
                showError("Error. User already exists.");
            }
        }
    }
}