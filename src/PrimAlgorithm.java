import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PrimAlgorithm implements MazeAlgorithm {

    /* 
     * Bit flags for cell state
     */
    static final int N = 1;        // 000001
    static final int S = 2;        // 000010
    static final int E = 4;        // 000100
    static final int W = 8;        // 001000
    static final int IN = 16;    // 010000
    static final int FRONTIER = 32; // 100000

    static final Map<Integer, Integer> OPPOSITE = Map.of(
            N, S,
            S, N,
            E, W,
            W, E
    );

    /*
     * Maze data
     */
    private int width;
    private int height;
    private int[][] grid;
    private final List<Point> frontier = new ArrayList<>();
    private Random random;
    private long seed;
    private JPanel panel;

    /* 
     * Colors for rendering
     */
    private static final Color UNVISITED = new Color(210, 210, 210);
    private static final Color FRONTIER_COLOR = new Color(255, 170, 170);
    private static final Color IN_MAZE = Color.WHITE;
    private static final Color WALL = Color.BLACK;

    /* 
     * Constructor
     */
    public PrimAlgorithm() {
        this.seed = System.currentTimeMillis();
        this.random = new Random(seed);
    }

    public PrimAlgorithm(long seed) {
        this.seed = seed;
        this.random = new Random(seed);
    }

    /*
     * Setting the panel reference for the animation
     */
    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    /* 
     * Frontier helpers
     */
    private void addFrontier(int col, int row) {
        if (col >= 0 && row >= 0 && col < width && row < height && grid[row][col] == 0) {
            grid[row][col] |= FRONTIER;
            frontier.add(new Point(col, row));
        }
    }

    private void mark(int col, int row) {
        grid[row][col] = (grid[row][col] & ~FRONTIER) | IN; // updated to remove frontier flag aswell
        addFrontier(col - 1, row);
        addFrontier(col + 1, row);
        addFrontier(col, row - 1);
        addFrontier(col, row + 1);
    }

    private List<Point> neighbors(int col, int row) {
        List<Point> adjacentCells = new ArrayList<>();
        if (col > 0 && (grid[row][col - 1] & IN) != 0) adjacentCells.add(new Point(col - 1, row));
        if (col + 1 < width && (grid[row][col + 1] & IN) != 0) adjacentCells.add(new Point(col + 1, row));
        if (row > 0 && (grid[row - 1][col] & IN) != 0) adjacentCells.add(new Point(col, row - 1));
        if (row + 1 < height && (grid[row + 1][col] & IN) != 0) adjacentCells.add(new Point(col, row + 1));
        return adjacentCells;
    }

    private int direction(int fromCol, int fromRow, int toCol, int toRow) {
        if (fromCol < toCol) return E;
        if (fromCol > toCol) return W;
        if (fromRow < toRow) return S;
        return N;
    }

    /*
     * MazeAlgorithm interface implementation
     */
    @Override
    public void generateMaze(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new int[height][width];
        this.frontier.clear();

        try {
            // starting from a random cell
            mark(random.nextInt(width), random.nextInt(height));
            repaintIfAnimated();

            // the main loop for the algorithm
            while (!frontier.isEmpty()) {
                // picking a random frontier cell
                Point frontierCell = frontier.remove(random.nextInt(frontier.size()));
                List<Point> adjacentCells = neighbors(frontierCell.x, frontierCell.y);

                if (!adjacentCells.isEmpty()) {
                    // connecting to a random neighbor that's already in the maze
                    Point neighborCell = adjacentCells.get(random.nextInt(adjacentCells.size()));

                    int dir = direction(frontierCell.x, frontierCell.y, neighborCell.x, neighborCell.y);
                    grid[frontierCell.y][frontierCell.x] |= dir;
                    grid[neighborCell.y][neighborCell.x] |= OPPOSITE.get(dir);
                }

                // finally marking this cell as part of the maze
                mark(frontierCell.x, frontierCell.y);
                repaintIfAnimated();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Seed: " + seed);
    }

    /*
     * Repainting the panel if animation is enabled
     */
    private void repaintIfAnimated() throws InterruptedException {
        if (panel != null) {
            SwingUtilities.invokeLater(() -> panel.repaint());
            Thread.sleep(10);
        }
    }

    @Override
    public void draw(Graphics2D g2d, int panelWidth, int panelHeight) {
        if (grid == null) return;

        int cellSize = Math.min(panelWidth / width, panelHeight / height);
        
        int offsetX = (panelWidth - (width * cellSize)) / 2;
        int offsetY = (panelHeight - (height * cellSize)) / 2;

        g2d.setStroke(new BasicStroke(1));

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int cell = grid[row][col];
                int pixelX = offsetX + col * cellSize;
                int pixelY = offsetY + row * cellSize;

                // ---- Cell background ----
                if ((cell & IN) != 0) {
                    g2d.setColor(IN_MAZE);
                } else if ((cell & FRONTIER) != 0) {
                    g2d.setColor(FRONTIER_COLOR);
                } else {
                    g2d.setColor(UNVISITED);
                }
                g2d.fillRect(pixelX, pixelY, cellSize, cellSize);

                // ---- Walls ----
                g2d.setColor(WALL);

                if ((cell & N) == 0)
                    g2d.drawLine(pixelX, pixelY, pixelX + cellSize, pixelY);

                if ((cell & W) == 0)
                    g2d.drawLine(pixelX, pixelY, pixelX, pixelY + cellSize);

                if ((cell & S) == 0)
                    g2d.drawLine(pixelX, pixelY + cellSize, pixelX + cellSize, pixelY + cellSize);

                if ((cell & E) == 0)
                    g2d.drawLine(pixelX + cellSize, pixelY, pixelX + cellSize, pixelY + cellSize);
            }
        }
    }

    /**
     * used for tests.
     * returns the grid representing the maze layout
     */
    int[][] getGrid() {
        return grid;
    }
}
