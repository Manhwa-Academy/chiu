package GUI.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * Notification with slide + fade animation
 * Rewritten to use Swing Timer (NO TimingFramework)
 */
public class Notification extends JComponent {

    private JDialog dialog;
    private final Frame fram;
    // private boolean showing = false;

    // ===== ANIMATION =====
    private Timer timer;
    private float alpha = 0f;
    private int animate = 10;
    private int x, top;
    private boolean topToBottom;

    private BufferedImage imageShadow;
    private int shadowSize = 6;
    private Type type;
    private Location location;

    public Notification(Frame fram, Type type, Location location, String message) {
        this.fram = fram;
        this.type = type;
        this.location = location;
        initComponents();
        init(message);
    }

    private void init(String message) {
        setBackground(Color.WHITE);

        dialog = new JDialog(fram);
        dialog.setUndecorated(true);
        dialog.setFocusableWindowState(false);
        dialog.setBackground(new Color(0, 0, 0, 0));
        dialog.add(this);
        dialog.setSize(getPreferredSize());

        if (type == Type.SUCCESS) {
            lbIcon.setIcon(new ImageIcon(getClass().getResource("/icon/sucess.png")));
            lbMessage.setText("Success");
        } else if (type == Type.INFO) {
            lbIcon.setIcon(new ImageIcon(getClass().getResource("/icon/info.png")));
            lbMessage.setText("Info");
        } else {
            lbIcon.setIcon(new ImageIcon(getClass().getResource("/icon/warning.png")));
            lbMessage.setText("Warning");
        }
        lbMessageText.setText(message);
    }

    // ================= PUBLIC API =================
    public void showNotification() {
        startAnimation(false);
    }

    private void closeNotification() {
        startAnimation(true);
    }

    // ================= ANIMATION =================
    private void startAnimation(boolean hide) {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        if (!hide) {
            alpha = 0f;
            calculateLocation();
            dialog.setOpacity(0f);
            dialog.setVisible(true);
        }

        timer = new Timer(15, e -> {
            if (!hide) {
                alpha += 0.05f;
                if (alpha >= 1f) {
                    alpha = 1f;
                    timer.stop();
                    // showing = true;
                    autoClose();
                }
            } else {
                alpha -= 0.05f;
                if (alpha <= 0f) {
                    alpha = 0f;
                    timer.stop();
                    // showing = false;
                    dialog.dispose();
                }
            }
            moveDialog(hide);
            dialog.setOpacity(alpha);
        });
        timer.start();
    }

    private void autoClose() {
        Timer wait = new Timer(2200, e -> closeNotification());
        wait.setRepeats(false);
        wait.start();
    }

    private void moveDialog(boolean hide) {
        int yOffset = (int) ((1f - alpha) * animate);
        if (topToBottom) {
            dialog.setLocation(x, hide ? top - yOffset : top + yOffset);
        } else {
            dialog.setLocation(x, hide ? top + yOffset : top - yOffset);
        }
    }

    private void calculateLocation() {
        int margin = 10;
        int y;

        if (location == Location.TOP_CENTER) {
            x = fram.getX() + (fram.getWidth() - dialog.getWidth()) / 2;
            y = fram.getY();
            topToBottom = true;
        } else if (location == Location.TOP_RIGHT) {
            x = fram.getX() + fram.getWidth() - dialog.getWidth() - margin;
            y = fram.getY();
            topToBottom = true;
        } else if (location == Location.TOP_LEFT) {
            x = fram.getX() + margin;
            y = fram.getY();
            topToBottom = true;
        } else if (location == Location.BOTTOM_CENTER) {
            x = fram.getX() + (fram.getWidth() - dialog.getWidth()) / 2;
            y = fram.getY() + fram.getHeight() - dialog.getHeight();
            topToBottom = false;
        } else if (location == Location.BOTTOM_RIGHT) {
            x = fram.getX() + fram.getWidth() - dialog.getWidth() - margin;
            y = fram.getY() + fram.getHeight() - dialog.getHeight();
            topToBottom = false;
        } else if (location == Location.BOTTOM_LEFT) {
            x = fram.getX() + margin;
            y = fram.getY() + fram.getHeight() - dialog.getHeight();
            topToBottom = false;
        } else {
            x = fram.getX() + (fram.getWidth() - dialog.getWidth()) / 2;
            y = fram.getY() + (fram.getHeight() - dialog.getHeight()) / 2;
            topToBottom = true;
        }
        top = y;
        dialog.setLocation(x, y);
    }

    // ================= PAINT =================
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(imageShadow, 0, 0, null);

        int x = shadowSize;
        int y = shadowSize;
        int w = getWidth() - shadowSize * 2;
        int h = getHeight() - shadowSize * 2;

        g2.setColor(getBackground());
        g2.fillRect(x, y, w, h);

        if (type == Type.SUCCESS) {
            g2.setColor(new Color(18, 163, 24));
        } else if (type == Type.INFO) {
            g2.setColor(new Color(28, 139, 206));
        } else {
            g2.setColor(new Color(241, 196, 15));
        }
        g2.fillRect(6, 5, 5, h + 1);
        g2.dispose();
        super.paint(g);
    }

    @Override
    public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x, y, w, h);
        createImageShadow();
    }

    private void createImageShadow() {
        imageShadow = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imageShadow.createGraphics();
        g2.drawImage(createShadow(), 0, 0, null);
        g2.dispose();
    }

    private BufferedImage createShadow() {
        BufferedImage img = new BufferedImage(
                getWidth() - shadowSize * 2,
                getHeight() - shadowSize * 2,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.fillRect(0, 0, img.getWidth(), img.getHeight());
        g2.dispose();
        return new ShadowRendererNotification(shadowSize, 0.3f,
                new Color(100, 100, 100)).createShadow(img);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        lbIcon = new javax.swing.JLabel();
        panel = new javax.swing.JPanel();
        lbMessage = new javax.swing.JLabel();
        lbMessageText = new javax.swing.JLabel();
        cmdClose = new javax.swing.JButton();

        lbIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        panel.setOpaque(false);

        lbMessage.setFont(new java.awt.Font("sansserif", 1, 14));
        lbMessage.setForeground(new java.awt.Color(38, 38, 38));
        lbMessage.setText("Message");

        lbMessageText.setForeground(new java.awt.Color(127, 127, 127));
        lbMessageText.setText("Message Text");

        cmdClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close.png")));
        cmdClose.setBorder(null);
        cmdClose.setContentAreaFilled(false);
        cmdClose.setFocusable(false);
        cmdClose.addActionListener(evt -> closeNotification());

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbMessage)
                                        .addComponent(lbMessageText))
                                .addContainerGap(167, Short.MAX_VALUE)));
        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbMessage)
                                .addGap(3, 3, 3)
                                .addComponent(lbMessageText)
                                .addContainerGap()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lbIcon)
                                .addGap(10, 10, 10)
                                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdClose)
                                .addGap(15, 15, 15)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cmdClose, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbIcon, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10)));
    }// </editor-fold>

    public static enum Type {
        SUCCESS, INFO, WARNING
    }

    public static enum Location {
        TOP_CENTER, TOP_RIGHT, TOP_LEFT,
        BOTTOM_CENTER, BOTTOM_RIGHT, BOTTOM_LEFT,
        CENTER
    }

    // Variables declaration
    private javax.swing.JButton cmdClose;
    private javax.swing.JLabel lbIcon;
    private javax.swing.JLabel lbMessage;
    private javax.swing.JLabel lbMessageText;
    private javax.swing.JPanel panel;
}
