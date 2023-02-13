import java.util.*;

public class Graph {
    private int [] [] matrix;
    private int nodeCount;
    boolean [] visited;

    public Graph(int [] [] matrix) {
        this.matrix = matrix;
        this.nodeCount = matrix.length;
        this.visited = new boolean[this.nodeCount];
    }

    public boolean isVisited(int node) {
        return visited[node];
    }

    public void wasVisited(int node) {
        visited[node] = true;
    }

    public boolean allVisited() {
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                return false;
            }
        }
        return true;
    }
    public List<Integer> getNeighbors(int node) {
        String answer = "";
        List<Integer> answerList = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            System.out.println("matrix[node][i] = " + matrix[node][i]);
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
        if (unvisited.size() == 0)
            return -1; // all neighbors have been visited
        else {
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
        return matrix[from][to] != -1 && matrix[from][to] != Integer.MAX_VALUE;
    }

    public void printArray(int [] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public void dijkstra(int startingNode) {
        int[] distances = new int[nodeCount];
        int[] nearest = new int[nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            distances[i] = Integer.MAX_VALUE; // infinite
            nearest[i] = 0;
        }

        // set distance to start vertex to zero
        distances[startingNode] = 0;
        wasVisited(startingNode);

        // while there remain some unvisited nodes
        while (!allVisited()) {
            int current = getLowestUnvisitedNeighbor(startingNode);
            for (int neighbor : getUnvisitedNeighbors(current)) {
                int newDistance = distances[current] + matrix[current][neighbor];
                if(newDistance < distances[neighbor]) {
                    distances[current] = newDistance;
                    nearest[neighbor] = current;
                }
            }
            wasVisited(current);
            printVisited();
            System.out.println("distances:");
            printArray(distances);
        }
        // print the answers
        System.out.println("distances:");
        printArray(distances);
        System.out.println("nearest verticies:");
        printArray(nearest);
    }

    private void printVisited() {
        System.out.print("visited = ");
        for (int i = 0; i < visited.length; i++) {
            System.out.print(visited[i] + " ");
        }
        System.out.println();
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
        line = scan.nextLine();
        System.out.println("line = " + line);
        String [] tokens = line.split(" ");
        int nodes = Integer.parseInt(tokens[0]);
        int edges = Integer.parseInt(tokens[1]);
        System.out.println("nodes = " + nodes);
        System.out.println("edges = " + edges);
        int[][] matrix = new int[nodes][nodes];
        for (int i = 0; i <nodes; i++) {
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
        Graph graph = new Graph(matrix);
        graph.dijkstra(0);

        /*
        for (int j = 0; j < graph.matrix.length; j++) {
            graph.dfs(j);
        }

         */
    }
}
