
package GUI.Component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class InputImage extends JPanel implements ActionListener {

    private JButton btnChooseImg;
    private JLabel img;
    private String url_img;

    public InputImage() {

    }

    public InputImage(String title) {
        this.setBackground(Color.white);
        btnChooseImg = new JButton(title);
        img = new JLabel();
        img.setPreferredSize(new Dimension(250, 280));
        btnChooseImg.addActionListener(this);
        this.add(btnChooseImg);
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {

        if (url_img == null || url_img.isEmpty())
            return;

        String path = "src/img/img_product/" + url_img; // ✅ đúng folder

        ImageIcon imgicon = new ImageIcon(path);
        imgicon = new ImageIcon(scale(imgicon));

        btnChooseImg.setIcon(imgicon);
        btnChooseImg.setText("");

        this.url_img = url_img;
    }

    public void setUnable() {
        this.btnChooseImg.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setAcceptAllFileFilterUsed(false);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "png", "gif", "jpg", "jpeg");
        jfc.addChoosableFileFilter(filter);

        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            this.url_img = jfc.getSelectedFile().getPath();

            ImageIcon imgicon = new ImageIcon(url_img);
            imgicon = new ImageIcon(scale(imgicon));

            btnChooseImg.setText("");
            btnChooseImg.setIcon(imgicon);
        }
    }

    public Image scale(ImageIcon x) {
        int WIDTH = 250;
        int HEIGHT = 280;
        Image scaledImage = x.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        return scaledImage;
    }

    public static ImageIcon resizeImage(ImageIcon imageIcon, int newWidth) {
        int newHeight = (int) (imageIcon.getIconHeight() * ((double) newWidth / imageIcon.getIconWidth()));
        Image scaledImage = imageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
