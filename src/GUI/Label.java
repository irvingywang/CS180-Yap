package GUI;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {
    public Label (String text, int fontSize) {
        super(text);
        setForeground(GUIConstants.PRIMARY_WHITE);
        setVisible(true);
        setOpaque(false);
        setFont(new Font("Arial", Font.PLAIN, fontSize));
    }
}
