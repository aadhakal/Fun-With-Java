import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TradeStock {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java TradeStock <input_file> <algorithm>");
            return;
        }

        String inputFileName = args[0];
        int algorithm = Integer.parseInt(args[1]);

        if (algorithm < 1 || algorithm > 3) {
            System.err.println("Unsupported algorithm. Use 1, 2, or 3");
            return;
        }

        try {
            float[] stockPrices = readBinaryFile(inputFileName);
            float maxProfit = 0;
            String algorithmName = "";
            int buyingPoint = -1;
            int sellingPoint = -1;

            System.out.println("Aashish Dhakal");
            System.out.println("loading " + inputFileName + " ...");
            System.out.println("Input size = " + stockPrices.length);

            long startTime = System.nanoTime();

            if (algorithm == 1) {
                algorithmName = "Theta(n * log n) Divide and Conquer";
                float[] results = maxProfitDivideAndConquerNlogN(stockPrices, 0, stockPrices.length - 1);
                maxProfit = results[0];
                buyingPoint = (int) results[1];
                sellingPoint = (int) results[2];
            } else if (algorithm == 2) {
                algorithmName = "Theta(n) Decrease and Conquer";
                float[] results = maxProfitDecreaseAndConquerLinear(stockPrices);
                maxProfit = results[0];
                buyingPoint = (int) results[1];
                sellingPoint = (int) results[2];
            } else if (algorithm == 3) {
                algorithmName = "Theta(n) Divide and Conquer";
                StockData stockData = divideAndConquerMaxProfitLinear(stockPrices, 0, stockPrices.length - 1);
                maxProfit = stockData.maxProfit;
                buyingPoint = stockData.buyingIndex;
                sellingPoint = stockData.sellingIndex;
            }
            long endTime = System.nanoTime();
            long timeElapsed = TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);

            System.out.println(algorithmName);
            System.out.printf("buyingPoint: %d , sellingPoint: %d, maxProfit: %.4f%n", buyingPoint, sellingPoint, maxProfit);
            System.out.println("Time taken in milli seconds: " + timeElapsed);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static float[] readBinaryFile(String fileName) throws IOException {
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(fileName))) {
            int n = inputStream.readInt(); // Reads a big-endian 4-byte int
            if (n <= 0) {
                throw new IllegalArgumentException("Invalid number of stock prices: " + n);
            }
            float[] stockPrices = new float[n];
            for (int i = 0; i < n; i++) {
                float price = inputStream.readFloat(); // Reads a big-endian 4-byte float
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

    public static float[] maxProfitDivideAndConquerNlogN(float[] stockPrices, int start, int end) {
        if (start == end) {
            if (start < stockPrices.length - 1) {
                float profit = stockPrices[start + 1] - stockPrices[start];
                return new float[]{profit, start, start + 1};
            } else {
                return new float[]{Float.NEGATIVE_INFINITY, start, end};
            }
        }
        int mid = (start + end) / 2;
        float[] leftResult = maxProfitDivideAndConquerNlogN(stockPrices, start, mid);
        float[] rightResult = maxProfitDivideAndConquerNlogN(stockPrices, mid + 1, end);

        int leftMinIndex = (int) leftResult[1];
        int rightMaxIndex = (int) rightResult[2];

        float leftMin = stockPrices[leftMinIndex];
        float rightMax = stockPrices[rightMaxIndex];

        for (int i = start; i <= mid; i++) {
            if (stockPrices[i] < leftMin) {
                leftMin = stockPrices[i];
                leftMinIndex = i;
            }
        }
        for (int i = mid + 1; i <= end; i++) {
            if (stockPrices[i] > rightMax) {
                rightMax = stockPrices[i];
                rightMaxIndex = i;
            }
        }
        float crossProfit = rightMax - leftMin;
        if (crossProfit >= leftResult[0] && crossProfit >= rightResult[0]) {
            return new float[]{crossProfit, leftMinIndex, rightMaxIndex};
        } else if (leftResult[0] >= rightResult[0]) {
            return new float[]{leftResult[0], leftResult[1], leftResult[2]};
        } else {
            return new float[]{rightResult[0], rightResult[1], rightResult[2]};
        }
    }
    // ********************** DecreaseAndConquerLinear *******************************************************
    public static float[] maxProfitDecreaseAndConquerLinear(float[] stockPrices) {
        float maxProfit = stockPrices[1] - stockPrices[0];
        int buyingPoint = 0;
        int tempBuyingPoint = 0;
        int sellingPoint = 1;
        float minPrice = stockPrices[0];

        for (int i = 1; i < stockPrices.length; i++) {
            float currentProfit = stockPrices[i] - minPrice;
            if (currentProfit > maxProfit) {
                maxProfit = currentProfit;
                sellingPoint = i;
                buyingPoint = tempBuyingPoint;
            }

            if (stockPrices[i] < minPrice) {
                minPrice = stockPrices[i];
                tempBuyingPoint = i;
            }
        }
        return new float[]{maxProfit, buyingPoint, sellingPoint};
    }

    // **********************  Divide And Conquer Linear*******************************************************
    public static class StockData {
        public float maxProfit;
        public int buyingIndex;
        public int sellingIndex;
        public float minPrice;
        public int minPriceIndex;
        public float maxPrice;
        public int maxPriceIndex;

        public StockData() {
            maxProfit = 0.0f;
            buyingIndex = -1;
            sellingIndex = -1;
            minPrice = Float.MAX_VALUE;
            minPriceIndex = -1;
            maxPrice = Float.MIN_VALUE;
            maxPriceIndex = -1;
        }
    }

    public static StockData divideAndConquerMaxProfitLinear(float[] stockPrices, int left, int right) {
        StockData stockData = new StockData();
        if (right - left <= 1) {
            stockData.maxProfit = stockPrices[right] - stockPrices[left];
            stockData.buyingIndex = left;
            stockData.sellingIndex = right;
            stockData.minPrice = stockPrices[left];
            stockData.minPriceIndex = left;
            stockData.maxPrice = stockPrices[right];
            stockData.maxPriceIndex = right;
        } else {
            int mid = (left + right) / 2;
            StockData leftSubArray = divideAndConquerMaxProfitLinear(stockPrices, left, mid);
            StockData rightSubArray = divideAndConquerMaxProfitLinear(stockPrices, mid, right);

            if (leftSubArray.minPrice < rightSubArray.minPrice) {
                stockData.minPrice = leftSubArray.minPrice;
                stockData.minPriceIndex = leftSubArray.minPriceIndex;
            } else {
                stockData.minPrice = rightSubArray.minPrice;
                stockData.minPriceIndex = rightSubArray.minPriceIndex;
            }
            if (leftSubArray.maxPrice > rightSubArray.maxPrice) {
                stockData.maxPrice = leftSubArray.maxPrice;
                stockData.maxPriceIndex = leftSubArray.maxPriceIndex;
            } else {
                stockData.maxPrice = rightSubArray.maxPrice;
                stockData.maxPriceIndex = rightSubArray.maxPriceIndex;
            }

            float difference = rightSubArray.maxPrice - leftSubArray.minPrice;
            float leftDifference = leftSubArray.maxProfit;
            float rightDifference = rightSubArray.maxProfit;

            // Find the maximum profit
            stockData.maxProfit = Math.max(leftDifference, Math.max(rightDifference, difference));

            if (stockData.maxProfit == leftDifference) {
                stockData.buyingIndex = leftSubArray.buyingIndex;
                stockData.sellingIndex = leftSubArray.sellingIndex;
            } else if (stockData.maxProfit == rightDifference) {
                stockData.buyingIndex = rightSubArray.buyingIndex;
                stockData.sellingIndex = rightSubArray.sellingIndex;
            } else {
                stockData.buyingIndex = leftSubArray.minPriceIndex;
                stockData.sellingIndex = rightSubArray.maxPriceIndex;
            }
        }

        return stockData;
    }

}



