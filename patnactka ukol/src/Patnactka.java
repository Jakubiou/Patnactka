import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class Patnactka extends JFrame implements ActionListener {
    private JPanel panel;
    private JButton[] buttons;
    private final int size = 4; // Velikost herní mřížky
    private final int tileCount = size * size;
    private ArrayList<Integer> tileList;

    public Patnactka() {
        setTitle("Patnáctka");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);

        panel = new JPanel(new GridLayout(size, size));
        buttons = new JButton[tileCount];
        tileList = new ArrayList<>();

        JOptionPane.showMessageDialog(this, "Seradte cisla za sebou 0 je prazdne pole");

        initializeTiles();
        shuffleTiles();

        for (int i = 0; i < tileCount; i++) {
            JButton button = new JButton(String.valueOf(tileList.get(i)));
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            buttons[i] = button;
            button.addActionListener(this);
            panel.add(button);
        }


        add(panel);
        setVisible(true);
    }

    private void initializeTiles() {
        for (int i = 0; i < tileCount; i++) {
            tileList.add(i + 1);
        }
        tileList.set(tileCount - 1, 0); // Prázdné políčko označeno nulou
    }

    private void shuffleTiles() {
        Collections.shuffle(tileList);
    }

    private void checkForWin() {
        ArrayList<Integer> currentTiles = new ArrayList<>();
        for (JButton button : buttons) {
            currentTiles.add(Integer.parseInt(button.getText()));
        }
        currentTiles.remove(Integer.valueOf(0)); // Odstranění nuly (prázdného políčka) pro kontrolu výhry

        if (currentTiles.equals(tileList.subList(0, tileCount - 1))) {
            JOptionPane.showMessageDialog(this, "Gratuluji! Vyhráli jste!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        int emptyTileIndex = tileList.indexOf(0);
        int clickedIndex = -1;

        for (int i = 0; i < tileCount; i++) {
            if (buttons[i] == clickedButton) {
                clickedIndex = i;
                break;
            }
        }

        if (isAdjacent(emptyTileIndex, clickedIndex)) {
            Collections.swap(tileList, emptyTileIndex, clickedIndex);
            updateButtons();
            checkForWin();
        }
    }

    private boolean isAdjacent(int empty, int clicked) {
        return (Math.abs(empty - clicked) == 1 && (Math.min(empty, clicked) / size == Math.max(empty, clicked) / size)
                || (Math.abs(empty - clicked) == size));
    }

    private void updateButtons() {
        for (int i = 0; i < tileCount; i++) {
            buttons[i].setText(String.valueOf(tileList.get(i)));
        }
    }
}

