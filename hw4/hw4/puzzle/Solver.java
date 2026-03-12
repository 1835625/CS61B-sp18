package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
/*
public class Solver {
    private MinPQ<SearchNode> pq;
    private int numOfInsertion;


    private static class SearchNode {
        public WorldState worldState;
        public int numOfMoves;
        public SearchNode prevNode;
        private int edtg; // Second optimization: the worldState's estimated distance to goal. Reduce time largely!

        private static class SearchNodeComparator implements Comparator<SearchNode> {
            @Override
            public int compare(SearchNode n1, SearchNode n2) {
                if (n1.edtg + n1.numOfMoves > n2.edtg + n2.numOfMoves) {
                    return 1;
                } else if (n1.edtg + n1.numOfMoves == n2.edtg + n2.numOfMoves) {
                    return 0;
                }
                return -1;
            }
        }

        public SearchNode(WorldState state, int movesSoFar, SearchNode prevSearchNode) {
            worldState = state;
            numOfMoves = movesSoFar;
            prevNode = prevSearchNode;
            edtg = state.estimatedDistanceToGoal();
        }
    }

    private static Comparator<SearchNode> SComparator() {
        return new SearchNode.SearchNodeComparator();
    }

    public Solver(WorldState initial) {
        numOfInsertion = 0;
        Comparator<SearchNode> sc = SComparator();
        pq = new MinPQ<>(sc);
        SearchNode initNode = new SearchNode(initial, 0, null);
        pq.insert(initNode);
    }

    public int moves() {
        int num = 0;
        for (WorldState ws : solution()) {
            num += 1;
        }
        return num - 1;
    }

    public Iterable<WorldState> solution() {
        List<WorldState> solution = new ArrayList<>();
        while (!pq.isEmpty()) {
            SearchNode node = pq.delMin();
            WorldState ws = node.worldState;
            if (ws.isGoal()) {
                for (SearchNode n = node; n != null; n = n.prevNode) {
                    solution.add(n.worldState);
                }
                break;
            }
            for (WorldState neighbor : ws.neighbors()) {
                if (node.prevNode != null && neighbor.equals(node.prevNode.worldState)) {
                    continue;
                }
                SearchNode neighborNode = new SearchNode(neighbor, node.numOfMoves + 1, node);
                pq.insert(neighborNode);
                numOfInsertion += 1;
            }
        }
        return solution.reversed();
    }

}*/

public class Solver {
    private final List<WorldState> solution;
    private final int moves;

    private static class SearchNode {
        private final WorldState worldState;
        private final int numOfMoves;
        private final SearchNode prevNode;
        private final int estimatedDistanceToGoal;

        SearchNode(WorldState ws, int movesSoFar, SearchNode prev) {
            this.worldState = ws;
            this.numOfMoves = movesSoFar;
            this.prevNode = prev;
            this.estimatedDistanceToGoal = ws.estimatedDistanceToGoal();
        }

        private int priority() {
            return numOfMoves + estimatedDistanceToGoal;
        }
    }

    private static class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode a, SearchNode b) {
            int pa = a.priority();
            int pb = b.priority();

            if (pa != pb) {
                return Integer.compare(pa, pb);
            }

            // 可选的 tie-breaker，不影响正确性，只是让行为更稳定一些
            return Integer.compare(a.estimatedDistanceToGoal, b.estimatedDistanceToGoal);
        }
    }

    public Solver(WorldState initial) {
        if (initial == null) {
            throw new IllegalArgumentException("initial world state cannot be null");
        }

        MinPQ<SearchNode> pq = new MinPQ<>(new SearchNodeComparator());
        pq.insert(new SearchNode(initial, 0, null));

        SearchNode goalNode = null;

        while (!pq.isEmpty()) {
            SearchNode current = pq.delMin();

            if (current.worldState.isGoal()) {
                goalNode = current;
                break;
            }

            for (WorldState neighbor : current.worldState.neighbors()) {
                if (current.prevNode != null
                        && neighbor.equals(current.prevNode.worldState)) {
                    continue;
                }

                pq.insert(new SearchNode(neighbor, current.numOfMoves + 1, current));
            }
        }

        if (goalNode == null) {
            // 按这次作业的测试，一般不会出现无解情况
            this.moves = -1;
            this.solution = new LinkedList<>();
        } else {
            this.moves = goalNode.numOfMoves;
            this.solution = buildSolution(goalNode);
        }
    }

    private List<WorldState> buildSolution(SearchNode goalNode) {
        LinkedList<WorldState> path = new LinkedList<>();
        SearchNode p = goalNode;

        while (p != null) {
            path.addFirst(p.worldState);
            p = p.prevNode;
        }

        return path;
    }

    public int moves() {
        return moves;
    }

    public Iterable<WorldState> solution() {
        return solution;
    }
}