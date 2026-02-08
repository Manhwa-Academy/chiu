package GUI.Component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class InputDate extends JPanel {

    private JLabel lblTitle;
    private JDateChooser dateChooser;

    // yyyy mới là năm chuẩn
    // private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public InputDate(String title) {
        initUI(title, -1, -1);
    }

    public InputDate(String title, int w, int h) {
        initUI(title, w, h);
    }

    private void initUI(String title, int w, int h) {
        setLayout(new GridLayout(2, 1, 0, 5));
        setBackground(Color.white);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        if (w > 0 && h > 0) {
            setPreferredSize(new Dimension(w, h));
        }

        lblTitle = new JLabel(title);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");

        add(lblTitle);
        add(dateChooser);
    }

    // ===== API dùng cho Panel =====
    public JDateChooser getDateChooser() {
        return dateChooser;
    }

    public Date getDate() {
        return dateChooser.getDate();
    }

    public void setDate(Date date) {
        dateChooser.setDate(date);
    }

    public void setDisable() {
        JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
        editor.setEditable(false);
    }
}
