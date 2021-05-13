package mediabiblioteket;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Testing {

    private JList jList1;
    private JPanel jPanel1;
    private JTextArea jTextArea1;

    public Testing() {
        initComponents();
    }

    private void initComponents() {
        JFrame f = new JFrame();
        jPanel1 = new JPanel();
        jList1 = new JList();
        jTextArea1 = new JTextArea();

        jList1.setModel(new AbstractListModel() {

            String[] strings = {"Item 1", "Item 2"};

            @Override
            public int getSize() {
                return strings.length;
            }

            @Override
            public Object getElementAt(int i) {
                return strings[i];
            }
        });
        jList1.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);

        jPanel1.add(jList1);
        jPanel1.add(jTextArea1);
         f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.add(jPanel1);
        f.pack();
        f.setVisible(true);
    }

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {
        //set text on right here
        String s = (String) jList1.getSelectedValue();
        if (s.equals("Item 1")) {
            jTextArea1.setText("You clicked on list 1");
        }
        if (s.equals("Item 2")) {
            jTextArea1.setText("You clicked on list 2");
        }
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Testing();
            }
        });
    }
}

