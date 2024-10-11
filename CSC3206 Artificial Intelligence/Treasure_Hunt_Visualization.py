import numpy as np
import matplotlib.pyplot as plt
import matplotlib.animation as animation
import matplotlib.patches as patches
from matplotlib.lines import Line2D 

# Hex grid map (cube coordinates)
HEX_GRID = {
    (0, 0, 0): "Empty", (0, -1, 1): "Empty", (0, -2, 2): "Empty", 
    (0, -3, 3): "Obstacle", (0, -4, 4): "Empty", (0, -5, 5): "Empty",
    (1, 0, -1): "Empty", (1, -1, 0): "Trap2", (1, -2, 1): "Empty", 
    (1, -3, 2): "Reward1", (1, -4, 3): "Empty", (1, -5, 4): "Empty",
    (2, -1, -1): "Empty", (2, -2, 0): "Empty", (2, -3, 1): "Obstacle", 
    (2, -4, 2): "Empty", (2, -5, 3): "Trap2", (2, -6, 4): "Empty",
    (3, -1, -2): "Empty", (3, -2, -1): "Trap4", (3, -3, 0): "Empty", 
    (3, -4, 1): "Obstacle", (3, -5, 2): "Treasure", (3, -6, 3): "Empty",
    (4, -2, -2): "Reward1", (4, -3, -1): "Treasure", (4, -4, 0): "Obstacle", 
    (4, -5, 1): "Empty", (4, -6, 2): "Obstacle", (4, -7, 3): "Empty",
    (5, -2, -3): "Empty", (5, -3, -2): "Empty", (5, -4, -1): "Empty", 
    (5, -5, 0): "Trap3", (5, -6, 1): "Empty", (5, -7, 2): "Reward2",
    (6, -3, -3): "Empty", (6, -4, -2): "Trap3", (6, -5, -1): "Empty", 
    (6, -6, 0): "Obstacle", (6, -7, 1): "Obstacle", (6, -8, 2): "Empty",
    (7, -3, -4): "Empty", (7, -4, -3): "Empty", (7, -5, -2): "Reward2", 
    (7, -6, -1): "Treasure", (7, -7, 0): "Obstacle", (7, -8, 1): "Empty",
    (8, -4, -4): "Empty", (8, -5, -3): "Obstacle", (8, -6, -2): "Trap1", 
    (8, -7, -1): "Empty", (8, -8, 0): "Empty", (8, -9, 1): "Empty",
    (9, -4, -5): "Empty", (9, -5, -4): "Empty", (9, -6, -3): "Empty", 
    (9, -7, -2): "Treasure", (9, -8, -1): "Empty", (9, -9, 0): "Empty"
}

# Define trap and reward effects
TRAPS = {
    "Trap1": {"effect": "increase gravity", "multiplier": 2},
    "Trap2": {"effect": "decrease speed", "multiplier": 2},
    "Trap3": {"effect": "teleport", "distance": 2},
    "Trap4": {"effect": "remove uncollected treasures"}
}

REWARDS = {
    "Reward1": {"effect": "decrease gravity", "multiplier": 0.5},
    "Reward2": {"effect": "increase speed", "multiplier": 0.5}
}

class Cell:
    def __init__(self, current=None, parent=None, g=0, h=0, f=0, direction=None):
        self.current = current
        self.parent = parent
        self.g = g  # Cost from start to this cell
        self.h = h  # Heuristic cost from this cell to the goal
        self.f = f  # Total step cost (g + h)
        self.direction = direction  # Direction to reach this cell from the parent (for Trap3 effect)
        self.children = []

    def addChildren(self, children):
        self.children.extend(children)

# Related variable states that can be affected by trap and reward effects
class State:
    def __init__(self, step_cost=1, energy_cost=1, found_reward1 = False, found_trap1 = False):
        self.step_cost = step_cost
        self.energy_cost = energy_cost
        self.found_reward1 = found_reward1
        self.found_trap1 = found_trap1
    
# Heuristic function using adapted Manhattan distance formula
def heuristic(position, goal, checkTreasure=False):
    # Avoid altering the heuristic value of the rewards and traps when locating the nearest treasure
    if not checkTreasure:
        if position in HEX_GRID:
            cell_type = HEX_GRID[position]
            if cell_type in REWARDS:
                return float('-inf')  # Strong preference for reward cells
            elif cell_type in TRAPS:
                return float('inf')   # Strong avoidance for trap cells
    
    # Default to Manhattan distance for non-reward and non-trap cells
    return max(abs(position[0] - goal[0]), abs(position[1] - goal[1]), abs(position[2] - goal[2]))

def find_nearest_treasure(current_position):
    # Check if the current position is a treasure
    if HEX_GRID.get(current_position) == "Treasure":
        return current_position

    # Find all treasure locations
    treasure_locations = [position for position, cell_type in HEX_GRID.items() if cell_type == "Treasure"]
    if not treasure_locations:
        return None  # No treasures left

    # Initialize variables for the nearest treasure
    nearest_treasure = None
    min_h_value = float('inf')

    # Find the treasure with the lowest heuristic value from the current position
    for treasure in treasure_locations:
        h_value = heuristic(current_position, treasure, True)
        if h_value < min_h_value:
            min_h_value = h_value
            nearest_treasure = treasure

    return nearest_treasure

def expandAndReturnChildren(cell, goal, step_cost=1, visited=None):
    children = []
    if visited is None:
        visited = set()

    DIRECTIONS = [ (1, -1, 0), (1, 0, -1), (0, 1, -1), (-1, 1, 0), (-1, 0, 1), (0, -1, 1)]
    for direction in DIRECTIONS:
        neighbor = (cell.current[0] + direction[0], cell.current[1] + direction[1], cell.current[2] + direction[2])
        # Check if neighbor is within HEX_GRID bounds and prevent revisiting cells
        if neighbor not in HEX_GRID or neighbor in visited:
            continue
        
        # Skip if neighbor is an obstacle
        if HEX_GRID[neighbor] == "Obstacle":
            continue

        steps = cell.g + step_cost  # Update the steps taken
        h_cost = heuristic(neighbor, goal)
        f_cost = steps + h_cost
        child_cell = Cell(current=neighbor, parent=cell, g=steps, h=h_cost, f=f_cost, direction=direction)
        children.append(child_cell)

    return children

def appendAndSort(frontier, cell):
    for i, f in enumerate(frontier):
        if f.current == cell.current:
            # Prioritize reward cell over treasure if and only if the reward cell is adjacent
            if HEX_GRID[cell.parent.current] not in REWARDS:
                if f.f > cell.f:
                    frontier[i] = cell
                return frontier
            else:
                frontier[i] = cell
                return frontier 

    insert_index = len(frontier)
    for i, f in enumerate(frontier):
        if f.f > cell.f:
            insert_index = i
            break
    frontier.insert(insert_index, cell)
    
    return frontier

def a_star(start, visited, state):
    frontier = []
    explored = []
    goal = find_nearest_treasure(start)

    # No treasure found
    if not goal:
        return [], float('inf'), state

    frontier.append(Cell(current=start, g=0, h=heuristic(start, goal), f=heuristic(start, goal), direction=None))

    while frontier:
        current_cell = frontier.pop(0)
        # Reconstruct path
        if current_cell.current == goal:
            path = []
            cell = current_cell
            while cell:
                path.insert(0, cell.current)
                cell = cell.parent
            return path, current_cell.g, state

        explored.append(current_cell)

        cell_type = HEX_GRID[current_cell.current]
        # Apply trap and reward effects
        if cell_type in TRAPS:
            trap = TRAPS[cell_type]
            if cell_type == "Trap3":
                # Trap 3: move two cells away in the current direction
                teleport_target = (current_cell.current[0] + current_cell.direction[0] * trap["distance"],
                                   current_cell.current[1] + current_cell.direction[1] * trap["distance"],
                                   current_cell.current[2] + current_cell.direction[2] * trap["distance"])
                if teleport_target in HEX_GRID:
                    current_cell.current = teleport_target
            elif cell_type == "Trap2":
                state.step_cost *= trap["multiplier"]
            elif cell_type == "Trap4":
                # Trap 4: remove all treasures that have not been collected
                for key in list(HEX_GRID.keys()):
                    if HEX_GRID[key] == "Treasure":
                        HEX_GRID[key] = "Empty"

        elif cell_type in REWARDS:
            reward = REWARDS[cell_type]
            if cell_type == "Reward2":
                state.step_cost *= reward["multiplier"]

        # Dynamically update the goal to the nearest treasure for every cell movement
        nearest_treasure = find_nearest_treasure(current_cell.current)
        if nearest_treasure and nearest_treasure != goal:
            goal = nearest_treasure
            frontier = sorted(frontier, key=lambda cell: cell.g + heuristic(cell.current, goal))

        children = expandAndReturnChildren(current_cell, goal, state.step_cost, visited)
        current_cell.addChildren(children)

        for child in children:
            if not any(e.current == child.current for e in explored):
                frontier = appendAndSort(frontier, child)

    return None, float('inf'), state  # No path found

def treasure_hunt(start):
    complete_path = []
    total_step_cost = 0
    total_energy_cost = 0
    current_position = start
    visited = set()
    state = State()

    while True:
        path, step, state = a_star(current_position, visited, state)
        if not path:
            # Check each position in the path for rewards or traps
            for i, position in enumerate(complete_path):
                cell_type = HEX_GRID[position]
                
                # Skip energy cost addition for the very first position
                if i > 0:
                    total_energy_cost += state.energy_cost  # Add energy cost for every element passed through
                
                if cell_type == "Reward1":
                    state.found_reward1 = True
                if cell_type == "Trap1":
                    state.found_trap1 = True
                
                # Controlling continuous effect activation
                if state.found_reward1:
                    state.energy_cost *= REWARDS["Reward1"]["multiplier"]
                if state.found_trap1:
                    state.energy_cost *= TRAPS["Trap1"]["multiplier"]

            break  # Exit loop if no path is found

        # Avoid duplicating the starting point in the solution path
        if complete_path and path[0] == complete_path[-1]:
            path = path[1:]

        # Add current path and costs to total
        complete_path.extend(path)
        total_step_cost += step

        # Move to the last position in the current path (new start position)
        current_position = path[-1]

        # Add the completed path to the visited set
        visited.update(path)

        # Mark the reached treasure as "Collected"
        HEX_GRID[current_position] = "Collected"

        # Print out the current step details
        print(f"Reached treasure at {current_position}.")
        print(f"Current path: {complete_path}")
        print(f"Total steps taken so far: {total_step_cost} steps")
        print()

    return complete_path, total_step_cost, total_energy_cost

# Hexagon creator
def hexagon(x_center, y_center, size=1):
    return [(x_center + size * np.cos(np.pi / 3 * i), y_center + size * np.sin(np.pi / 3 * i)) for i in range(6)]

def draw_symbol(ax, x, y, cell_type):
    circle = patches.Circle((x, y), radius=0.2, color='none', ec='black', linewidth=1.5)
    square = patches.Rectangle((x - 0.2, y - 0.2), 0.4, 0.4, color='none', ec='black', linewidth=1.5)
    hr_line = Line2D([x - 0.2, x + 0.2], [y, y], color='black', linewidth=1.5)
    ver_line = Line2D([x, x], [y - 0.1, y + 0.1], color='black', linewidth=1.5)
    forward_slash = Line2D([x - 0.1, x + 0.1], [y - 0.1, y + 0.1], color='black', linewidth=1.5)
    backward_slash = Line2D([x - 0.1, x + 0.1], [y + 0.1, y - 0.1], color='black', linewidth=1.5)
    
    if cell_type == "Trap1":
        # Circle with a horizontal line
        ax.add_patch(circle)
        ax.add_line(hr_line)
        
    elif cell_type == "Trap2":
        # Plus inside a circle
        ax.add_patch(circle)
        ax.add_line(hr_line)
        ax.add_line(ver_line)
        
    elif cell_type == "Trap3":
        # Cross inside a circle
        ax.add_patch(circle)
        ax.add_line(forward_slash)
        ax.add_line(backward_slash)
        
    elif cell_type == "Trap4":
        # Forward slash inside a circle
        ax.add_patch(circle)
        ax.add_line(forward_slash)

    elif cell_type == "Reward1":
        # Plus inside a square
        ax.add_patch(square)
        ax.add_line(hr_line)
        ax.add_line(ver_line)

    elif cell_type == "Reward2":
        # Cross inside a square
        ax.add_patch(square)
        ax.add_line(forward_slash)
        ax.add_line(backward_slash)

def visualize_path(path):
    fig, ax = plt.subplots(figsize=(8, 8))
    fig.canvas.manager.set_window_title('treasure Hunt Visualization')

    hexagons = {}
    for (q, r, s), cell_type in HEX_GRID.items():
        x = q * 1.5
        y = np.sqrt(3) * (r + q / 2)
        hex_shape = hexagon(x, y)
        hexagons[(q, r)] = hex_shape
        color = 'white'

        if cell_type == "Obstacle":
            color = "#808080"
        elif cell_type == "Collected":
            color = "#feb850"
        elif cell_type in TRAPS:
            color = "#cf94d8"
        elif cell_type in REWARDS:
            color = "#4db5ac"

        ax.fill(*zip(*hex_shape), color=color, edgecolor='black')
        draw_symbol(ax, x, y, cell_type)

    path_line, = ax.plot([], [], color='red', linewidth=2, label='Path Taken')
    ax.set_title('Virtual Treasure Hunt Visualization')
    ax.set_aspect('equal')
    plt.axis('off')
    ax.legend(loc='upper right', bbox_to_anchor=(0.1, 1), borderaxespad=0.) 

    ani = animation.FuncAnimation(fig, update, frames=len(path), 
                                  fargs=(path, path_line), interval=500, repeat=False)
    plt.show()

# Path taken animation
def update(frame, path, path_line):
    if frame < len(path):
        path_x = [q * 1.5 for (q, r, s) in path[:frame + 1]]
        path_y = [np.sqrt(3) * (r + q / 2) for (q, r, s) in path[:frame + 1]]
        path_line.set_data(path_x, path_y)
    return path_line,

# Test treasure hunt game
if __name__ == "__main__":
    start_position = (0, 0, 0)
    solution_path, solution_cost, solution_energy_cost = treasure_hunt(start_position)
    print("Solution Path:", solution_path)
    print("Total Steps Taken:", solution_cost, "steps")
    print("Total Energy Usage:", round(solution_energy_cost, 2), "kj")
    visualize_path(solution_path)