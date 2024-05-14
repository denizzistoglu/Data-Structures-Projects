package javaapplication8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Graph1 {

    public static int[][] adjacencyMatrix;
    private static LinearProbingHash<String> corners;
    private static int verticesCount;
    public static int size = 53;

    public Graph1(int verticesCount) {
        this.verticesCount = verticesCount;
        adjacencyMatrix = new int[verticesCount][verticesCount];
        corners = new LinearProbingHash<>(verticesCount);
    }
    public static void ReadGraphFromFile(String filePath) {
        try {
            Scanner sc = new Scanner(new File(filePath));
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] parts = line.split("->");
                String vertexName = parts[0].trim();
                String[] edgesInfo = parts[1].split(",");
                if (!corners.contains(vertexName)) {
                    corners.insert(vertexName);
                }
                int vertexIndex = corners.index(vertexName);
                for (String edgeInfo : edgesInfo) {
                    String[] edgeParts = edgeInfo.trim().split(":");
                    String neighborName = edgeParts[0].trim();
                    int weight = Integer.parseInt(edgeParts[1].trim());
                    if (!corners.contains(neighborName)) {
                        corners.insert(neighborName);
                    }
                    int neighborIndex = corners.index(neighborName);
                    adjacencyMatrix[vertexIndex ][neighborIndex ] = weight;
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
    }
    public static String maxDegree() {
        int maxDegree = 0;
        ArrayList<String> vmaxDegree = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int degree = degree(i);
            if (maxDegree < degree) {
                maxDegree = degree;
                vmaxDegree.clear();
                vmaxDegree.add(corners.intToString(i));
            } else if (degree == maxDegree) {
                vmaxDegree.add(corners.intToString(i));
            }
        }
        if (vmaxDegree.isEmpty()) {
            return null;
        } else if (vmaxDegree.size() == 1) {
            return vmaxDegree.get(0);
        } else {
            return String.join(",", vmaxDegree);
        }
    }
    public static int degree(int v) {
        int degree = 0;
        for (int i = 1; i < size * 2; i++) {
            degree += (int) adjacencyMatrix[v][i];
        }
        return degree;
    }

    public static void printAdjacencyMatrix() {
        for (int i = 0; i < verticesCount; i++) {
            for (int j = 0; j < verticesCount; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Graph1 graph = new Graph1(106);
        ReadGraphFromFile("graph.txt");
        printAdjacencyMatrix();
        boolean isOver = false;
        while (!isOver) {
            System.out.println(" ");
            System.out.println("---------- Menu -----------");
            System.out.println("1- is there a path between a and b ?");
            System.out.println("2- print BFS from a to b ");
            System.out.println("3- print DFS from a to b ");
            System.out.println("4- what is shortest path length from a to b ? ");
            System.out.println("5- what is the number of simple paths from a to b ? ");
            System.out.println("6- what are the neighbors of a ? ");
            System.out.println("7- what is the vertex with the highest degree ?");
            System.out.println("8- is the graph directed ? ");
            System.out.println("9- are a and b adjacent ? ");
            System.out.println("10-is there a cycle path which starts and ends with a ? ");
            System.out.println("11-print the number of vertices in component ? ");
            System.out.println("12-quit");
            System.out.println("----------------------------");
            System.out.println("enter the number of choosed option: ");
            Scanner input = new Scanner(System.in);
            int option = input.nextInt();
            switch (option) {
                case 1://is there a path between a and b
                    System.out.println("Enter the start: ");
                    String ap = input.next();
                    System.out.println("Enter the finish: ");
                    String bp = input.next();
                    if (NumberOfSimplePaths(ap, bp) > 0) {
                        System.out.println("There is a path between " + ap + " and " + bp);
                    } else {
                        System.out.println("There is no path between " + ap + " and " + bp);
                    }
                    break;
                case 2://print BFS from a to b
                    System.out.println("Enter the start: ");
                    String startd = input.next();
                    System.out.println("Enter the finish: ");
                    String finishd = input.next();
                    bfs(startd, finishd);
                    break;
                case 3://print DFS from a to b 
                    System.out.println("Enter the start: ");
                    String startb = input.next();
                    System.out.println("Enter the finish: ");
                    String finishb = input.next();
                    boolean[] ifExists = new boolean[size * 2];
                    dfs(startb, finishb, ifExists);
                    break;
                case 4://what is shortest path length from a to b ? 
                    System.out.println("Enter the a: ");
                    String aS = input.next();
                    System.out.println("Enter the b: ");
                    String bS = input.next();
                    ShortestPath(aS, bS);
                    break;
                case 5://what is the number of simple paths from a to b ?
                    System.out.println("Enter the a: ");
                    String a = input.next();
                    System.out.println("Enter the b: ");
                    String b = input.next();
                    NumberOfSimplePaths(a, b);
                    break;
                case 6://what are the neighbors of a ? 
                    System.out.println("Enter the a: ");
                    String neighborsa = input.next();
                    Neighbors(neighborsa);
                    break;
                case 7://what is the vertex with the highest degree ?
                    System.out.println("Highest Degree Vertex(s): " + maxDegree());
                    break;
                case 8://is the graph directed ?
                    if (IsDirected()) {
                        System.out.println("yes, it is directed");
                    } else {
                        System.out.println("no, it is not directed");
                    }
                    break;
                case 9://are a and b adjacent ?
                    System.out.println("Enter the a: ");
                    String v2 = input.next();
                    System.out.println("Enter the b: ");
                    String d = input.next();
                    if (AreTheyAdjacent(v2, d)) {
                        System.out.println("yes, they are adjacent");
                    } else {
                        System.out.println("no, they are not adjacent");
                    }
                    break;
                case 10://is there a cycle path which starts and ends with a ?
                    System.out.println("Enter the a: ");
                    String v1 = input.next();
                    IsThereACycle(v1);
                    break;
                case 11://print the number of vertices in component ?
                       System.out.println(corners.N);
                    break;
                case 12://quit
                    isOver = true;
                    break;
            }
        }
    }  public static boolean dfs(String start1, String end1, boolean[] visited) {
        int start = corners.hash(start1);
        int end = corners.hash(end1);
        System.out.print(start1 + " ");
        visited[start] = true;
        for (int i = 0; i < adjacencyMatrix[start].length; i++) {
            if (adjacencyMatrix[start][i] != 0 && !start1.equals(end1)) {
                String i1 = corners.intToString(i);
                if (i1 == null) {
                 System.out.println("Edge from " + start1 + " to " + end1  );

                    break ;
                }
                if (!visited[i]) {
                    System.out.println("Edge from " + start1 + " to " + i1  );
                    if (dfs(i1, end1, visited)) {
                        break; 
                    }
                } 
            }
        }
        return false; 
    }
    public static boolean dfsForACycle(String start1, String end1, boolean[] visited, int parent) {
        int start = corners.hash(start1);
        int end = corners.hash(end1);
        System.out.print(start1 + " ");
        visited[start] = true;
        for (int i = 0; i < adjacencyMatrix[start].length; i++) {
            if (adjacencyMatrix[start][i] != 0 && !start1.equals(end1)) {
                String i1 = corners.intToString(i);
                if (i1 == null) {
                    break;
                }
                if (!visited[i]) {
                    System.out.println("Edge from " + start1 + " to " + i1  );
                    if (dfsForACycle(i1, end1, visited, start)) {
                        return true; 
                    }
                } else if (i != parent) {
                    return true;
                }
            }
        }
        return false; 
    }
    public static boolean IsThereACycle(String v1) {
        boolean[] visited = new boolean[size * 2];
        int start = corners.hash(v1);
        boolean hasCycle = dfsForACycle(v1, v1, visited, -1);
        if (hasCycle) {
            System.out.println("Cycle detected starting from vertex " + v1);

        } else {
            System.out.println("No cycle found starting from vertex " + v1);

        }
        return hasCycle;
    }
    public static void bfs(String start1, String end1) {
        boolean[] visited = new boolean[size * 2];
        int start = corners.hash(start1);
        int end = corners.hash(end1);
        ArrayList<String> que = new ArrayList<>();
        ArrayList<String> path = new ArrayList<>();
        que.add(start1);
        visited[start] = true;
        while (!que.isEmpty()) {
            String vis = que.get(0);
            path.add(vis);
            System.out.print(vis + " ");
            que.remove(que.get(0));
            int vis1 = corners.index(vis);
            if (vis.equals(end1)) {
                break;
            }
            for (int i = 0; i < size * 2; i++) {
                if (adjacencyMatrix[vis1][i] > 1 && !visited[i]) {
                    String i1 = corners.intToString(i);
                    visited[i] = true;
                    que.add(i1);
                    //System.out.println("Edge from " + vis + " to " + i1 );
                }
            }
        }
        System.out.println("BFS Path from " + start1 + " to " + end1 + ": " + String.join(" -> ", path));
    }
    public static boolean IsDirected() {
        for (int i = 0; i < size * 2; i++) {
            for (int j = 0; j < size * 2; j++) {
                if (adjacencyMatrix[i][j] > 0 && adjacencyMatrix[j][i] > 0) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean AreTheyAdjacent(String v1, String v2) {
        int idx1 = corners.location(v1);
        int idx2 = corners.location(v2);
        if (adjacencyMatrix[idx1][idx2] == 0) {
            return false;
        }
        return true;
    }
    public static void Neighbors(String v1) {
        int vertexIndex = corners.location(v1);
        if (vertexIndex != -1) {
            System.out.println("Neighbors of " + v1 + " : ");
            for (int i = 0; i < size * 2; i++) {
                if (adjacencyMatrix[vertexIndex][i] > 0) {
                    System.out.print(corners.intToString(i) + " - ");
                }
            }
            System.out.println();
        } else {
            System.out.println(v1 + "not found in the graph");
        }
    }
    public static int NumberOfSimplePaths(String v1, String v2) {
        int start = corners.hash(v1);
        int end = corners.hash(v2);
        boolean[] visited = new boolean[size * 2];
        Stack<Integer> pathStack = new Stack<>();
        int[] pathCount = {0};
        dfsForNumberOfPaths(start, end, visited, pathStack, pathCount);
        System.out.println("Number of simple paths from " + v1 + " to " + v2 + ": " + pathCount[0]);
        return pathCount[0];
    }
    private static void dfsForNumberOfPaths(int current, int end, boolean[] visited, Stack<Integer> pathStack, int[] pathCount) {
        visited[current] = true;
        pathStack.push(current);
        if (current == end) {
            pathCount[0]++;
            printPath(pathStack);
        } else {
            for (int i = 0; i < size * 2; i++) {
                if (adjacencyMatrix[current][i] != 0 && !visited[i]) {
                    int degree = adjacencyMatrix[current][i];
                    dfsForNumberOfPaths(i, end, visited, pathStack, pathCount);
                }
            }
        }
        visited[current] = false;
        pathStack.pop();
    }
    private static void printPath(Stack<Integer> pathStack) {
        System.out.print("Path " + pathStack.size() + ": ");
        for (int vertex : pathStack) {
            System.out.print(corners.intToString(vertex) + " - ");
        }
        System.out.println();
    }
    private static void dfsForShortestPath(int current, int end, boolean[] visited, Stack<Integer> pathStack, int[] minDegreeSum, int currentDegreeSum) {
        visited[current] = true;
        pathStack.push(current);
        if (current == end) {
            if (currentDegreeSum < minDegreeSum[0]) {
                minDegreeSum[0] = currentDegreeSum;
                printPath(pathStack);
                System.out.println("Sum of path's degrees: "+currentDegreeSum);
            }
        } else {
            for (int i = 0; i < size * 2; i++) {
                if (adjacencyMatrix[current][i] != 0 && !visited[i]) {
                    int degree = adjacencyMatrix[current][i];
                    dfsForShortestPath(i, end, visited, pathStack, minDegreeSum, currentDegreeSum + degree);
                }
            }
        }
        visited[current] = false;
        pathStack.pop();
    }
    public static void ShortestPath(String v1, String v2) {
        int start = corners.hash(v1);
        int end = corners.hash(v2);
        boolean[] visited = new boolean[size * 2];
        Stack<Integer> pathStack = new Stack<>();
        int[] minDegreeSum = {Integer.MAX_VALUE};
        dfsForShortestPath(start, end, visited, pathStack, minDegreeSum, 0);
        System.out.println("Smallest sum of degrees from " + v1 + " to " + v2 + ": " + minDegreeSum[0]);
    }
}
