package assignment1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int gridSize;
    private boolean[][] grid;
    private int openSitesCount;
    private final WeightedQuickUnionUF ufPercolates;
    private final WeightedQuickUnionUF ufIsFull;
    private final int virtualTopIndex;
    private final int virtualBottomIndex;

    // Instance variables will go here

    // create n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        // To be implemented
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        this.gridSize = n;
        this.openSitesCount = 0;
        this.grid = new boolean[n][n];

        int totalSites = n * n;
        this.virtualTopIndex = 0; // Index 0 for the virtual top
        this.virtualBottomIndex = totalSites + 1; // Last index for the virtual bottom
        this.ufPercolates = new WeightedQuickUnionUF(totalSites + 2);
        this.ufIsFull = new WeightedQuickUnionUF(totalSites + 1); // No virtual bottom

        // Connect top and bottom rows to virtual sites
        for (int col = 1; col <= n; col++) {
            int topRowSiteIndex = xyTo1D(1, col);
            int bottomRowSiteIndex = xyTo1D(n, col);

            // Connect top row to virtual top in both UF objects
            ufPercolates.union(virtualTopIndex, topRowSiteIndex);
            ufIsFull.union(virtualTopIndex, topRowSiteIndex);

            // Connect bottom row to virtual bottom in ONLY the percolates UF object
            ufPercolates.union(virtualBottomIndex, bottomRowSiteIndex);
        }

    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        // 1. Validate the row and column.
        validate(row, col);

        // 2. If the site is already open, do nothing.
        if (isOpen(row, col)) {
            return;
        }

        // 3. Mark the site as open.
        grid[row - 1][col - 1] = true;
        openSitesCount++;

        // 4. Connect to adjacent open sites.
        int currentSiteIndex = xyTo1D(row, col);

        // Check and connect to the site ABOVE
        if (row > 1 && isOpen(row - 1, col)) {
            int neighborIndex = xyTo1D(row - 1, col);
            ufPercolates.union(currentSiteIndex, neighborIndex);
            ufIsFull.union(currentSiteIndex, neighborIndex);
        }

        // Check and connect to the site BELOW
        if (row < gridSize && isOpen(row + 1, col)) {
            int neighborIndex = xyTo1D(row + 1, col);
            ufPercolates.union(currentSiteIndex, neighborIndex);
            ufIsFull.union(currentSiteIndex, neighborIndex);
        }

        // Check and connect to the site to the LEFT
        if (col > 1 && isOpen(row, col - 1)) {
            int neighborIndex = xyTo1D(row, col - 1);
            ufPercolates.union(currentSiteIndex, neighborIndex);
            ufIsFull.union(currentSiteIndex, neighborIndex);
        }

        // Check and connect to the site to the RIGHT
        if (col < gridSize && isOpen(row, col + 1)) {
            int neighborIndex = xyTo1D(row, col + 1);
            ufPercolates.union(currentSiteIndex, neighborIndex);
            ufIsFull.union(currentSiteIndex, neighborIndex);
        }

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int siteIndex = xyTo1D(row, col);
        return ufIsFull.connected(virtualTopIndex, siteIndex);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return ufPercolates.connected(virtualTopIndex, virtualBottomIndex);
    }

    // test client (optional)
    public static void main(String[] args) {
        // You can add your own tests here later
    }

    // maps 2D (row, col) coordinates to 1D union-find index
    private int xyTo1D(int row, int col) {
        // We will add validation here later
        return (row - 1) * gridSize + col;
    }

    private void validate(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) {
            throw new IllegalArgumentException("Row or column index is out of bounds");
        }
    }

}