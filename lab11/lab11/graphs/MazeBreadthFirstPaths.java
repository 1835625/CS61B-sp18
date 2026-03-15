package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int s;
    private int t;
    private Queue<Integer> fringe;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = m.xyTo1D(sourceX, sourceY);
        t = m.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        fringe = new ArrayDeque<>();
        fringe.add(s);
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        marked[s] = true;
        announce();

        while (!fringe.isEmpty()) {
            int v = fringe.remove();
            if (v == t) {
                return;
            }
            for (int w : maze.adj(v)) {
                if (marked[w]) {
                    continue;
                }
                fringe.add(w);
                marked[w] = true;
                distTo[w] = distTo[v] + 1;
                edgeTo[w] = v;
                announce();

                if (w == t) {
                    return;
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

