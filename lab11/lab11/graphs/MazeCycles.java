package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean cycleFound = false;
    private int[] parentOf;

    public MazeCycles(Maze m) {
        super(m);
        parentOf = new int[m.V()];
        parentOf[0] = 0;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfs(0);
    }

    // Helper methods go here
    private void dfs(int v) {
        marked[v] = true;
        announce();
        for (int w : maze.adj(v)) {
            if (marked[w]) {
                if (parentOf[v] == w) {
                    continue;
                }
                cycleFound = true;
                buildCycle(v, w);
                announce();
                return;
            } else {
                parentOf[w] = v;
                dfs(w);
                if (cycleFound) {
                    return;
                }
            }

        }
    }

    private void buildCycle(int v, int w) {
        edgeTo[w] = v; // The last edge of the cycle.
        // Trace back from v to w in the 'parentOf' array.
        int x = v;
        while (x != w) {
            edgeTo[x] = parentOf[x];
            x = parentOf[x];
        }
    }
}

