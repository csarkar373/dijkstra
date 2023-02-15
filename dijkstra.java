import java.util.*;

public class Graph {
    private int[][] matrix;
    private int nodeCount;
    List<Boolean> visited;

    public Graph(int[][] matrix) {
        this.matrix = matrix;
        this.nodeCount = matrix.length;
        this.visited = new ArrayList<Boolean>();
        for (int i = 0; i < nodeCount; i++) {
            visited.add(false);
        }
    }

    public boolean isVisited(int node) {
        return visited.get(node);
    }

    public void wasVisited(int node) {
        visited.set(node, true);
    }

    public boolean allVisited() {
        for (int i = 0; i < visited.size(); i++) {
            if (!visited.get(i)) {
                return false;
            }
        }
        return true;
    }

    public List<Integer> getNeighbors(int node) {
        System.out.println("getting neighbors for node: " + node);
        String answer = "";
        List<Integer> answerList = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            //System.out.println("matrix[node][i] = " + matrix[node][i]);
            if (matrix[node][i] != -1 && matrix[node][i] != Integer.MAX_VALUE) {
                answer += i;
                answerList.add(i);
            }
        }
        System.out.println("neighbors: " + answer);
        return answerList;
    }

    public List<Integer> getUnvisitedNeighbors(int node) {
        List<Integer> unvisitedList = getNeighbors(node);
        for (int i = 0; i < unvisitedList.size(); i++) {
            if (isVisited(unvisitedList.get(i))) {
                unvisitedList.remove(i);
                i--;
            }
        }
        System.out.println("unvisited neighbors: " + unvisitedList);
        return unvisitedList;
    }

    public int getLowestUnvisitedNeighbor(int node) {
        List<Integer> unvisited = getUnvisitedNeighbors(node);
        if (unvisited.size() == 0) {
            return -1; // all neighbors have been visited
        } else {
            int lowestIndex = 0;
            int lowestWeight = matrix[node][unvisited.get(0)];
            for (int i = 1; i < unvisited.size(); i++) {
                if (matrix[node][unvisited.get(i)] < lowestWeight) {
                    lowestWeight = matrix[node][unvisited.get(i)];
                    lowestIndex = i;
                }
            }
            System.out.println("lowest unvisited neighbor: " + unvisited.get(lowestIndex));
            return unvisited.get(lowestIndex);
        }
    }

    public boolean hasEdge(int from, int to) {
        // -1 and MAX_VALUE are the two representations of infinity that we use
        return from != to && matrix[from][to] != -1 && matrix[from][to] != Integer.MAX_VALUE;
    }


    public void dijkstra(int startingNode) {
        List<Integer> distances = new ArrayList<>();
        List<Integer> nearest = new ArrayList<>();

        for (int i = 0; i < nodeCount; i++) {
            distances.add(Integer.MAX_VALUE); // infinite
            nearest.add(0);
        }

        // set distance to start vertex to zero
        distances.set(startingNode,0);
        wasVisited(startingNode);
        System.out.println("distances: " + distances);

        // while there remain some unvisited nodes
        int current = startingNode;
        while (!allVisited()) {
            for (int neighbor : getUnvisitedNeighbors(current)) {
                    int newDistance = distances.get(current) + matrix[current][neighbor];
                    System.out.println("current = " + current + " neighbor = " + neighbor);
                    System.out.println("new distance = " + newDistance +
                            " old distance = " + distances.get(neighbor));
                    if (newDistance < distances.get(neighbor)) {
                        distances.set(neighbor, newDistance);
                        nearest.set(neighbor, current);
                    }
            }
            wasVisited(current);
            System.out.println("visited: " + visited);
            System.out.println("distances: " + distances);
            current = getLowestUnvisitedNeighbor(current);
        }
        // print the answers
        System.out.println("distances: " + distances);
        System.out.println("nearest vertices: " + nearest);
    }


    private static void printMatrix(int[][] matrix) {
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[0].length; c++) {
                String item = matrix[r][c] + "";
                if (matrix[r][c] == -1)
                    item = "-";
                System.out.printf("%2s ", item);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        int graphCount = Integer.parseInt(line);
        System.out.println("graph count = " + graphCount);
        Graph[] graphs = new Graph[graphCount];
        for (int trials = 0; trials < graphCount; trials++) {
            line = scan.nextLine();
            System.out.println("line = " + line);
            String[] tokens = line.split(" ");
            int nodes = Integer.parseInt(tokens[0]);
            int edges = Integer.parseInt(tokens[1]);
            System.out.println("nodes = " + nodes);
            System.out.println("edges = " + edges);
            int[][] matrix = new int[nodes][nodes];
            for (int i = 0; i < nodes; i++) {
                for (int j = 0; j < nodes; j++) {
                    matrix[i][j] = -1;
                }
            }
            for (int j = 0; j < edges; j++) {
                line = scan.nextLine();
                tokens = line.split(" ");
                int from = Integer.parseInt(tokens[0]);
                int to = Integer.parseInt(tokens[1]);
                if (tokens.length == 3) { // weighted graph
                    matrix[from][to] = Integer.parseInt(tokens[2]);
                    matrix[to][from] = Integer.parseInt(tokens[2]);
                } else { // unweighted graph; use default weight of 1
                    matrix[from][to] = 1;
                    matrix[to][from] = 1;
                }
            }
            printMatrix(matrix);
            graphs[trials] = new Graph(matrix);
            graphs[trials].dijkstra(0);
        }
    }
}
