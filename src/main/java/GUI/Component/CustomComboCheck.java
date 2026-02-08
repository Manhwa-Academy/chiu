package GUI.Component;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

public final class CustomComboCheck extends JComboBox<Object> {

    private Object firstItem;
    private JTextArea text;

    public CustomComboCheck(Vector<Object> v, JTextArea text) {
        super(v);
        this.text = text;

        setRenderer(new ComboRenderer());

        addActionListener((ActionEvent event) -> {
            ourItemSelectedText();
        });

        if (!v.isEmpty()) {
            firstItem = v.get(0);
        }
    }

    private void ourItemSelectedText() {
        Object selected = getSelectedItem();

        if (selected instanceof JCheckBox chk) {

            chk.setSelected(!chk.isSelected());
            repaint();

            if (!chk.isSelected()) {
                chk.setSelected(true);
                Object[] selections = chk.getSelectedObjects();
                if (selections != null) {
                    for (Object lastItem : selections) {
                        String txt = text.getText()
                                .replaceAll("(" + lastItem.toString() + ")\n", "");
                        text.setText(txt);
                    }
                }
                chk.setSelected(false);
            }

            Object[] selections = chk.getSelectedObjects();
            if (selections != null) {
                for (Object lastItem : selections) {
                    text.append(lastItem.toString() + "\n");
                }
            }
        }

        if (!getSelectedItem().equals(firstItem)) {
            setSelectedItem(firstItem);
            repaint();
        }
    }
}
