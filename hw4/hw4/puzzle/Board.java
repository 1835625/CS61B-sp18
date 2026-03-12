package hw4.puzzle;

import java.util.ArrayList;
import java.util.List;

public class Board implements WorldState{

    private int[][] tiles;
    private int N;

    public Board(int[][] tiles) {
        N = tiles.length;
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        if (!isValidTile(i, j)) {
            throw new IndexOutOfBoundsException();
        }
        return tiles[i][j];
    }

    public int size() {
        return N;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        List<WorldState> neighbors = new ArrayList<>();
        int r0 = 0;
        int c0 = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) == 0) {
                    r0 = i;
                    c0 = j;
                }
            }
        }
        if (isValidTile(r0 - 1, c0)) { // up
            int[][] newTiles = swap((r0-1)*N+c0, r0*N+c0);
            Board newBoard = new Board(newTiles);
            neighbors.add(newBoard);
        }
        if (isValidTile(r0 + 1, c0)) { // down
            int[][] newTiles = swap((r0+1)*N+c0, r0*N+c0);
            Board newBoard = new Board(newTiles);
            neighbors.add(newBoard);
        }
        if (isValidTile(r0, c0 - 1)) { // left
            int[][] newTiles = swap(r0*N+c0-1, r0*N+c0);
            Board newBoard = new Board(newTiles);
            neighbors.add(newBoard);
        }
        if (isValidTile(r0, c0 + 1)) { // right
            int[][] newTiles = swap(r0*N+c0+1, r0*N+c0);
            Board newBoard = new Board(newTiles);
            neighbors.add(newBoard);
        }
        return neighbors;
    }

    private boolean isValidTile(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }

    private int[][] swap(int a, int b) {
        int[][] retArray = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                retArray[i][j] = tiles[i][j];
            }
        }
        retArray[a/N][a%N] = tiles[b/N][b%N];
        retArray[b/N][b%N] = tiles[a/N][a%N];
        return retArray;
    }

    public int hamming() {
        int numOfWrong = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int val = tiles[i][j];
                int goalVal = i * N + j + 1;
                if (i == N - 1 && j == N - 1) {
                    goalVal = 0;
                }
                if (val != 0 && val != goalVal) {
                    numOfWrong += 1;
                }
            }
        }
        return numOfWrong;
    }

    public int manhattan() { // Do not need to consider the '0' tile!
        int totalDist = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int val = tiles[i][j];
                if (val != 0) {
                    int goalRow = (val - 1) / N;
                    int goalCol = (val - 1) % N;
                    totalDist += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }
        return totalDist;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null || y.getClass() != this.getClass()) {
            return false;
        }
        Board other = (Board) y;
        if (this.N != other.N) {
            return false;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != other.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
