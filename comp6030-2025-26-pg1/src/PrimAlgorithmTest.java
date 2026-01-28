import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrimAlgorithmTest {

    @Test
    void createsGridCorrectDimensions() {
        PrimAlgorithm prim = new PrimAlgorithm(123);
        int width = 15;
        int height = 10;

        prim.generateMaze(width, height);
        int[][] grid = prim.getGrid();

        assertNotNull(grid, "grid not null");
        assertEquals(height, grid.length, "grid hight correct");
        assertEquals(width, grid[0].length, "grid width correct");
    }

    @Test
    void allCellsInMazeAndFrontierClear() {
        PrimAlgorithm prim = new PrimAlgorithm(456);
        int width = 20;
        int height = 20;

        prim.generateMaze(width, height);
        int[][] grid = prim.getGrid();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int cell = grid[row][col];

                assertTrue((cell & PrimAlgorithm.IN) != 0,
                        "all cells are marked as IN");

                assertEquals(0, cell & PrimAlgorithm.FRONTIER,
                        "frontier flags explored");
            }
        }
    }


    @Test
    void sameSeedSameMaze() {
        long seed = 999;
        int width = 20;
        int height = 20;

        PrimAlgorithm prim1 = new PrimAlgorithm(seed);
        PrimAlgorithm prim2 = new PrimAlgorithm(seed);

        prim1.generateMaze(width, height);
        prim2.generateMaze(width, height);

        int[][] grid1 = prim1.getGrid();
        int[][] grid2 = prim2.getGrid();

        assertTrue(gridsEqual(grid1, grid2),
                "mazes are identical with same seed");
    }

    private boolean gridsEqual(int[][] a, int[][] b) {
        if (a == null || b == null) return a == b;
        if (a.length != b.length) return false;
        if (a.length == 0) return true;
        if (a[0].length != b[0].length) return false;

        for (int row = 0; row < a.length; row++) {
            for (int col = 0; col < a[0].length; col++) {
                if (a[row][col] != b[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }
}

