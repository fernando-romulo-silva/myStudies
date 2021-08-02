package org.nandao.cap11.p04UsingTheNumericShaper_RangeEnumerationToSupportTheDisplayOfDigits;

import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.NumericShaper;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

// In this recipe we will demonstrate the use of java.awt.font.NumericShaper.Range
// enumeration to support the display of digits using the java.awt.font.NumericShaper
// class. Sometimes it is desirable to display numeric digits using a different language than is
// currently being used. For example, in an English language tutorial regarding the Mongolian
// language, we may want to explain the numeric system in English, but display numbers
// using the Mongolian digits. The NumericShaper class provides this support. The new
// NumericShaper.Range enumeration has simplified this support.
public class Test extends JFrame {

    private static final long serialVersionUID = 1L;

    static class NumericShaperPanel extends JPanel {

        private static final long serialVersionUID = 1L;

        private TextLayout layout;

        public NumericShaperPanel() {
            String text = "0 1 2 3 4 5 6 7 8 9";
            Font font = new Font("Mongolian Baiti", Font.PLAIN, 32);
            
            HashMap map = new HashMap();
            map.put(TextAttribute.FONT, font);
            map.put(TextAttribute.NUMERIC_SHAPING, NumericShaper.getShaper(NumericShaper.Range.MONGOLIAN));
            
            FontRenderContext fontRenderContext = new FontRenderContext(null, false, false);
            layout = new TextLayout(text, map, fontRenderContext);
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            layout.draw(g2d, 10, 50);
        }
    }

    public Test() {
        Container container = this.getContentPane();
        container.add("Center", new NumericShaperPanel());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("NumericShaper Example");
        this.setSize(250, 120);
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.setVisible(true);
    }

}
