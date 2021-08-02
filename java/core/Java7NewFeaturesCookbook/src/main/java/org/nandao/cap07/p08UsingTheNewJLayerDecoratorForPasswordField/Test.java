package org.nandao.cap07.p08UsingTheNewJLayerDecoratorForPasswordField;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Composite;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.plaf.LayerUI;

// Java 7 supports the decoration of GUI components, such as textboxes and panels. Decoration is
// the process of drawing on top of the component to give it a special appearance. For example, we
// may want to watermark an interface to show that it is a beta version, or possibly to provide an
// indication of an error with a graphical X in a text field that is not otherwise possible.
public class Test extends JFrame {

    private static final long serialVersionUID = 1L;

    public Test() {

        this.setTitle("Example");
        this.setSize(300, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final LayerUI<JPanel> layerUI = new PasswordLayerUI();
        final JLayer<JPanel> jlayer = new JLayer<JPanel>(getPanel(), layerUI);
        this.add(jlayer);

    }

    private JPanel getPanel() {
        final JPanel panel = new JPanel(new BorderLayout());

        final JPanel gridPanel = new JPanel(new GridLayout(1, 2));
        final JLabel quantityLabel = new JLabel("Password");
        gridPanel.add(quantityLabel);

        final JPasswordField passwordField = new JPasswordField();
        gridPanel.add(passwordField);

        panel.add(gridPanel, BorderLayout.CENTER);

        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        final JButton okButton = new JButton("OK");
        buttonPanel.add(okButton);

        final JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    class PasswordLayerUI extends LayerUI<JPanel> {

        private String errorMessage = "Password too short";

        @Override
        public void paint(Graphics g, JComponent c) {
            FontMetrics fontMetrics;
            final Font font;
            int height;
            final int width;

            super.paint(g, c);
            final Graphics2D g2d = (Graphics2D) g.create();
            final int componentWidth = c.getWidth();
            final int componentHeight = c.getHeight();
            // Display error message
            g2d.setFont(c.getFont());
            fontMetrics = g2d.getFontMetrics(c.getFont());
            height = fontMetrics.getHeight();
            g2d.drawString(errorMessage, componentWidth / 2 + 10, componentHeight / 2 + height);

            // Display watermark
            final String displayText = "Beta Version";
            font = new Font("Castellar", Font.PLAIN, 16);
            fontMetrics = g2d.getFontMetrics(font);
            g2d.setFont(font);
            width = fontMetrics.stringWidth(displayText);
            height = fontMetrics.getHeight();

            final Composite com = g2d.getComposite();
            final AlphaComposite ac = AlphaComposite.getInstance(((AlphaComposite) com).getRule(), 0.25f);
            g2d.setComposite(ac);
            g2d.drawString(displayText, (componentWidth - width) / 2, (componentHeight - height) / 2);

            g2d.dispose();
        }

        @Override
        public void installUI(JComponent component) {
            super.installUI(component);
            ((JLayer) component).setLayerEventMask(AWTEvent.KEY_EVENT_MASK);
        }

        @Override
        public void uninstallUI(JComponent component) {
            super.uninstallUI(component);
            ((JLayer) component).setLayerEventMask(0);
        }

        @Override
        protected void processKeyEvent(KeyEvent event, JLayer layer) {
            final JTextField f = (JTextField) event.getSource();
            if (f.getText().length() < 6) {
                errorMessage = "Password too short";
            } else {
                errorMessage = "";
            }
            layer.repaint();
        }
    }

    public static void main(String[] args) {

        JFrame.setDefaultLookAndFeelDecorated(true);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final Test window = new Test();
                window.setVisible(true);
            }
        });
    }
}
