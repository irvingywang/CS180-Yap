import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a message between two users.
 * A message has a sender, a recipient, a message string, and a read status.
 */
public class Message implements MessageInterface, Serializable {
    private User sender;
    private User recipient;
    private String message;
    private String imagePath;
    private Date timestamp;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
    private boolean isRead;

    /**
     * Constructs a Message object using a sender, recipient, message, and imagePath.
     *
     * @param sender    - the sender User
     * @param recipient - the recipient User
     * @param message   - the message string
     * @param imagePath - the image path
     */
    public Message(User sender, User recipient, String message, String imagePath) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.imagePath = imagePath;
        this.timestamp = new Date();
        this.isRead = false;
    }

    /**
     * Constructs a Message object using a sender, recipient, and message string.
     *
     * @param sender    - the sender User
     * @param recipient - the recipient User
     * @param message   - the message string
     */
    public Message(User sender, User recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.imagePath = imagePath;
        this.timestamp = new Date();
        this.isRead = false;
    }

    /**
     * @return the sender User
     */
    public User getSender() {
        return sender;
    }

    /**
     * @return the recipient User
     */
    public User getRecipient() {
        return recipient;
    }

    /**
     * @return the message string
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the timestamp of the message
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @return true if the message is read, false otherwise
     */
    public boolean isRead() {
        return isRead;
    }

    /**
     * Marks the message as read.
     */
    public void markAsRead() {
        isRead = true;
    }

    /**
     * @return string representation of the Message
     */
    @Override
    public String toString() {
        return String.format("%s, Message from %s to %s: %s",
                dateFormat.format(timestamp), sender.getUsername(), recipient.getUsername(), message);
    }

    /**
     * Checks if this Message is equal to another object.
     *
     * @param obj - the object to compare this Message to
     * @return true if the object is a Message and has the same sender, recipient,
     * message, and read status as this Message, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Message) {
            Message other = (Message) obj;
            return this.sender.equals(other.sender) && this.recipient.equals(other.recipient)
                    && this.message.equals(other.message) && this.isRead == other.isRead;
        }
        return false;
    }
}
