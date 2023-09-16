import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TradeStock {

    public static float maxProfit(float[] stockPrices, int start, int end) {
        if (start >= end) {
            return 0;
        }
        int mid = (start + end) / 2;
        float leftProfit = maxProfit(stockPrices, start, mid);
        float rightProfit = maxProfit(stockPrices, mid + 1, end);
        float crossProfit = findMax(stockPrices, mid + 1, end) - findMin(stockPrices, start, mid);
        return Math.max(Math.max(leftProfit, rightProfit), crossProfit);
    }

    public static float findMax(float[] stockPrices, int start, int end) {
        float maxVal = stockPrices[start];
        for (int i = start + 1; i <= end; i++) {
            maxVal = Math.max(maxVal, stockPrices[i]);
        }
        return maxVal;
    }

    public static float findMin(float[] stockPrices, int start, int end) {
        float minVal = stockPrices[start];
        for (int i = start + 1; i <= end; i++) {
            minVal = Math.min(minVal, stockPrices[i]);
        }
        return minVal;
    }

    public static float[] readBinaryFile(String fileName) throws IOException {
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(fileName))) {
            int n = inputStream.readInt();
            if (n <= 0) {
                throw new IllegalArgumentException("Invalid number of stock prices: " + n);
            }
            float[] stockPrices = new float[n];
            for (int i = 0; i < n; i++) {
                float price = inputStream.readFloat();
                if (price < 0) {
                    throw new IllegalArgumentException("Invalid stock price: " + price);
                }
                stockPrices[i] = price;
            }
            return stockPrices;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found: " + fileName);
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java TradeStock <input_file> <algorithm>");
            return;
        }

        String inputFileName = args[0];
        String algorithm = args[1];

        if (!"Theta(n) Divide and Conquer".equalsIgnoreCase(algorithm)) {
            System.err.println("Unsupported algorithm: " + algorithm);
            return;
        }

        try {
            float[] stockPrices = readBinaryFile(inputFileName);
            float maxProfit = maxProfit(stockPrices, 0, stockPrices.length - 1);
            System.out.println("Aashish Dhakal");
            System.out.println(inputFileName);
            System.out.println(algorithm);
            System.out.printf("Maximum profit: %.4f%n", maxProfit);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
