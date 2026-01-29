# Prim's Maze Generation

A Java Swing application for visualizing maze generation algorithms using graph theory. This project implements Prim's Algorithm for generating perfect mazes with an interactive GUI that shows the generation process in real-time.

## Features

- **Interactive GUI**: User-friendly interface with multiple screens for navigation
- **Real-time Visualization**: Watch mazes being generated step-by-step with color-coded cells
- **Prim's Algorithm**: Implementation of Prim's algorithm for maze generation
- **Extensible Design**: Interface-based architecture allows easy addition of new algorithms
- **Deterministic Generation**: Seed-based random generation for reproducible mazes
- **Unit Tests**: Comprehensive test suite for algorithm correctness


## Usage

1. **Launch the application** - Run `Main.java`
2. **Generate Maze** - Click "Generate Maze" on the home screen
3. **Select Algorithm** - Choose "Prim's Algorithm"
4. **Watch the Generation** - The maze will be generated and visualized in real-time
5. **Navigate Back** - Use the "Back" button to return to previous screens

### Maze Visualization Colors

- **White**: Cells that are part of the maze
- **Light Red**: Frontier cells (candidates for next addition)
- **Gray**: Unvisited cells
- **Black**: Walls


## Algorithm Details

### Prim's Algorithm

Prim's algorithm for maze generation works by:
1. Starting with a random cell marked as part of the maze
2. Adding all unvisited neighbors to a frontier set
3. Repeatedly selecting a random frontier cell
4. Connecting it to a random neighbor already in the maze
5. Marking it as part of the maze and updating the frontier
6. Continuing until all cells are part of the maze

This creates a perfect maze with no loops.

## Testing

The project includes unit tests