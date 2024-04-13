import java.io.*;
import java.net.Socket;

public class Client implements ClientInterface, Runnable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private final ClientGUI clientGUI = new ClientGUI(this);
    private User user;
    public static final String IDENTIFIER = "CLIENT";


    public static void main(String[] args) {
        Client client = new Client();
        Thread clientThread = new Thread(client);
        clientThread.start();
    }

    @Override
    public void run() {
        if (connectToServer()) {
            clientGUI.welcomePage();
            clientGUI.loginPage();
            //user = database.getUser(); //TODO get user from server
        } else {
            clientGUI.showError("Connection to server failed.");
        }
    }

    @Override
    public boolean connectToServer() {
        try {
            socket = new Socket(Server.SERVER_ADDRESS, Server.PORT);
            out = new ObjectOutputStream(socket.getOutputStream()); //Output must be initialized first
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean sendToServer(NetworkMessage message) {
        try {
            out.writeObject(message);
            out.flush();
            return true;
        } catch (Exception e) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, e.getMessage());
            return false;
        }
    }

    @Override
    public NetworkMessage readMessage() {
        try {
            return (NetworkMessage) in.readObject();
        } catch (Exception e) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, e.getMessage());
            return null;
        }
    }

    @Override
    public void listenToServer() {
        try {
            while (true) {
                NetworkMessage message = readMessage();
                if (message != null) {
                    // Process the message from the server
                    // For example:
                    System.out.println("Received: " + message.getMessage());
                } else {
                    // Handle null message or decide if the loop should end
                    break;
                }
            }
        } catch (Exception e) {
            clientGUI.showError("Error listening to server: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                clientGUI.showError("Error closing socket: " + e.getMessage());
            }
        }
    }

    //TODO Client Functions
}