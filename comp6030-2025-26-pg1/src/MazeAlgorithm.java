//created an interface to simplify things, keeps method names uniform and avoids merge conflicts bc
//it allows for us to create diff classes easier by letting us swap which algo to use w o rewriting gui code
import java.awt.Graphics2D;

public interface MazeAlgorithm {
    // This method handles the logic of generating the maze
    void generateMaze(int width, int height);

    // This method handles drawing the specific result of that algorithm
    void draw(Graphics2D g2d, int panelWidth, int panelHeight);
}