import javax.swing.*;
import java.awt.*;

public class Main {

    private JFrame frame;
    private JPanel mainContainer;
    private CardLayout cardLayout;

    // this is the algorithm the user picks
    private MazeAlgorithm currentAlgorithm;
    private JPanel visualisationPanel; // area to draw the maze

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().createAndShowGUI());
    }

    public void createAndShowGUI() {
        frame = new JFrame("Maze Generation & Search Guide");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        //cardlayout is what is used to switch between pages
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        //this is how you make new pages (panels)
        JPanel homePanel = createHomePanel();
        JPanel selectionPanel = createSelectionPanel();
        JPanel visualisationPanel = createVisualisationPanel();

        //this is how you add them to the cardlayout w a unique id which lets you switch to which page you want
        mainContainer.add(homePanel, "HOME");
        mainContainer.add(selectionPanel, "MAZE_SELECTION");
        mainContainer.add(visualisationPanel, "VISUALISE");

        frame.add(mainContainer);
        frame.setVisible(true);
    }

    private JPanel createHomePanel() {
        //just styling
        JPanel panel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        //this is spacing
        gbc.insets = new Insets(10, 0, 20, 0);

        JLabel titleLabel = new JLabel("Maze Generation and Algorithms Guide");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));

        JButton generateBtn = new JButton("Generate Maze");
        generateBtn.setFont(new Font("Arial", Font.PLAIN, 20));
        generateBtn.setFocusPainted(false);

        // switch to the selection screen
        generateBtn.addActionListener(e -> cardLayout.show(mainContainer, "MAZE_SELECTION"));

        centerPanel.add(titleLabel, gbc);
        centerPanel.add(generateBtn, gbc);


        JLabel creditsLabel = new JLabel("by Nazem, Bright, Nick, Rafael and Adam", SwingConstants.CENTER);
        creditsLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        creditsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(creditsLabel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createSelectionPanel() {
        //mainly styling
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // end row after each component
        gbc.fill = GridBagConstraints.HORIZONTAL;     // make buttons same width
        gbc.insets = new Insets(10, 50, 10, 50);      // margin

        JLabel title = new JLabel("Select Algorithm", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        gbc.insets = new Insets(10, 50, 30, 50);
        panel.add(title, gbc);

        gbc.insets = new Insets(10, 50, 10, 50);

        JButton primBtn = new JButton("Prim's Algorithm");
        JButton kruskalBtn = new JButton("Kruskal's Algorithm");
        //add more buttons as needed here
        JButton backBtn = new JButton("Back");
        Font btnFont = new Font("Arial", Font.PLAIN, 18);
        primBtn.setFont(btnFont);
        kruskalBtn.setFont(btnFont);
        backBtn.setFont(btnFont);

        primBtn.addActionListener(e -> {
            PrimAlgorithm prim = new PrimAlgorithm();
            prim.setPanel(visualisationPanel);
            currentAlgorithm = prim;
            cardLayout.show(mainContainer, "VISUALISE");
            new Thread(() -> {
                currentAlgorithm.generateMaze(20, 20);
            }).start();
        });

        kruskalBtn.addActionListener(e -> {
            //same idea as shown in prims
        });

        backBtn.addActionListener(e -> cardLayout.show(mainContainer, "HOME"));

        panel.add(primBtn, gbc);
        panel.add(kruskalBtn, gbc);

        gbc.insets = new Insets(40, 50, 10, 50);
        panel.add(backBtn, gbc);

        return panel;
    }

    private JPanel createVisualisationPanel() {
        // this panel uses the method the currentAlgorithm has to draw the maze
        visualisationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (currentAlgorithm != null) {
                    currentAlgorithm.draw((Graphics2D) g, getWidth(), getHeight());
                }
            }
        };

        // add a back button to the visualisation screen
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> cardLayout.show(mainContainer, "MAZE_SELECTION"));
        visualisationPanel.add(backBtn);

        return visualisationPanel;
    }
}