import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Grid {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java NumberGrid <input_file> <output_file>");
            System.exit(1);
        }

        String inputFileName = args[0];
        String outputFileName = args[1];

        List<List<Integer>> grid = readInputFile(inputFileName);
        String[] result = findHighestSumPath(grid);
        writeOutputFile(outputFileName, result);
    }

    public static List<List<Integer>> readInputFile(String fileName) throws IOException {
        List<List<Integer>> grid = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.trim().split("\\s+");
                List<Integer> row = new ArrayList<>();
                for (String value : values) {
                    row.add(Integer.parseInt(value));
                }
                grid.add(row);
            }
        }

        return grid;
    }

    public static void writeOutputFile(String fileName, String[] result) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (String s : result) {
                writer.write(s + "\n");
            }
        }
    }

    public static String[] findHighestSumPath(List<List<Integer>> grid) {
        int n = grid.size();
        int[][] dp = new int[n][];
        String[][] path = new String[n][];

        for (int i = 0; i < n; i++) {
            int m = grid.get(i).size();
            dp[i] = new int[m];
            path[i] = new String[m];
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = grid.get(i).size() - 1; j >= 0; j--) {
                boolean canMoveRight = j + 1 < grid.get(i).size();
                boolean canMoveDown = i + 1 < n && j < grid.get(i + 1).size();

                if (!canMoveDown && !canMoveRight) {
                    dp[i][j] = grid.get(i).get(j);
                    path[i][j] = "";
                } else if (!canMoveDown) {
                    dp[i][j] = grid.get(i).get(j) + dp[i][j + 1];
                    path[i][j] = "R";
                } else if (!canMoveRight) {
                    dp[i][j] = grid.get(i).get(j) + dp[i + 1][j];
                    path[i][j] = "D";
                } else {
                    if (dp[i + 1][j] > dp[i][j + 1]) {
                        dp[i][j] = grid.get(i).get(j) + dp[i + 1][j];
                        path[i][j] = "D";
                    } else {
                        dp[i][j] = grid.get(i).get(j) + dp[i][j + 1];
                        path[i][j] = "R";
                    }
                }
            }
        }

        return new String[]{String.valueOf(dp[0][0]), buildPath(path)};
    }


    public static String buildPath(String[][] path) {
        StringBuilder sb = new StringBuilder();
        int i = 0, j = 0;

        while (i < path.length && j < path[i].length) {
            String direction = path[i][j];
            if (direction.equals("D")) {
                i++;
            } else {
                j++;
            }
            sb.append(direction);
        }

        return sb.toString();
    }
}

