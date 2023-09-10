import java.io.File;                  //reading from a file
import java.io.FileNotFoundException; //is thrown when the file is not found
import java.util.Arrays;              // used for sorting Arrays
import java.util.LinkedList;          //storing elements in the form of lists
import java.util.Scanner;             // for reading files

class RegionGrow {
    private static int val;             // value of the target color
    private static int width;           // width of the image
    private static int height;          //height of the image
    private static int[][] img;         //2D-array that stores the image data
    private static int[][] queue;       //2D-array used to implement the BFS Algorithm
    private static boolean[][] visited; //2D-array to keep track of the visited pixels.

    private RegionGrow() {              // the construction is private, so no object of this class can be created
    }

    public static void main(String[] args) {
        if (args.length == 2) {                  // check if there are two command lines arguments: name of the input file, and the value fo the target color
            String fileName = args[0];              //file name in args array at 0th index
            val = Integer.parseInt(args[1]);        //target color in args array at 1st index
            System.out.println("Aashish Dhakal");
            System.out.println(fileName);

            try {
                readFile(fileName);                   // calls the readFile method, passing the fileName as an Argument
            } catch (FileNotFoundException e) {       //if file not found, it will be printed on the console
                e.printStackTrace();
            }

            LinkedList<Integer> sizes = new LinkedList<>();            //linkedList is created to store the sizes of the region
            int count = 0;                                             // to count the variable sof number of regions found
            queue = new int[2][height * width + 1];                    // initialize 2-D array

            for (int row = 0; row < height; row++) {                   //visits row
                for (int col = 0; col < width; col++) {                // visits column
                    if (img[row][col] == val && !visited[row][col]) {  // if the value of the pixel is Target color and not visited before
                        int size = findRegion(row, col);               // call the find region method and pass current row and column as arguments
                        sizes.add(size);                               //the size of the region starting at the current pixel is added to the linked list
                        count++;                                       // increment the count
                    }
                }
            }

            Integer[] sortedSizes = sizes.toArray(new Integer[sizes.size()]); // takes the linkedList sizes and convert into an array: sortedSizes
            Arrays.sort(sortedSizes);                                         // The array is sorted
            System.out.printf("%d", count);                                   //prints the count of the number of regions found

            for (int size : sortedSizes) {
                System.out.printf(", %d", size);                              //prints the list of the sizes of the comma-separated list of the regions in ascending order
            }

            System.out.println();
        } else {                                                                // if the command line arguments is not equal to 2
            System.out.println("Incorrect number of command line arguments.");   // prints the message
        }
    }

    // File read method
    private static void readFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);                               //creates a new "File" object using the file name passed as an argument to the method.
        Scanner scanner = new Scanner(file);                         //This allows the code to read data from the file
        String[] dimensions = scanner.nextLine().split(" ");   // This line reads the first line of the file and splits it into an array
        // of strings using the space character as a delimiter.
        width = Integer.parseInt(dimensions[1]);                    //This converts the second element, width, from string to integer
        height = Integer.parseInt(dimensions[2]);                   // This converts the third element, height, from string to integer
        img = new int[height][width];                               // creates a new 2D-array with height and weight
        visited = new boolean[height][width];                       //creates a new 2D array called "visited" with the specified height and width.

        for (int row = 0; row < height; row++) {                   //This line reads the next integer from the file and stores
            // it in the current row and column of the "img" array.
            for (int col = 0; col < width; col++) {
                img[row][col] = scanner.nextInt();
                visited[row][col] = false;                        // mark all row and column as not-visited
            }
        }

        scanner.close();                                           // closes the scanner
    }

    private static int findRegion(int row, int col) {      //Find region method
        int head = 0, tail = 0, size = 0;                  //head and tail to keep track of the first and last element of the queue, and size of the region
        visited[row][col] = true;                          //Marks the starting cell as visited to prevent visiting it again.
        queue[0][tail] = row;                              //Adds the starting cell coordinates to the queue
        queue[1][tail++] = col;                            //and increments the tail value by 1.}
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};// Declares a 2D array directions that stores the coordinates of
                                                                // the four neighboring cells (up, down, left, right).

        while (head <= tail) {                              //The loop continues until all the elements in the queue have been processed.
            int currentRow = queue[0][head];                //Retrieves the first element from the queue increments the head value by 1.
            int currentCol = queue[1][head++];              //Retrieves the first element from the queue and increments the head value by 1.
            size++;                                         //Increments the size value by 1 as the current cell is part of the region.

            for (int[] direction : directions) {          // iterate to check four neighbouring cells
                int nextRow = currentRow + direction[0];  //Calculates the coordinates of the next cell to be processed.
                int nextCol = currentCol + direction[1];  //Calculates the coordinates of the next cell to be processed.

                //This line checks if the next pixel is within the bounds of the image, has the same value as val, and has not been visited.
                if (nextRow >= 0 && nextRow < height && nextCol >= 0 && nextCol < width && img[nextRow][nextCol] == val && !visited[nextRow][nextCol]) {
                    queue[0][tail] = nextRow;    // if the above condition is met, next pixel is added to the queue
                    queue[1][tail++] = nextCol;   //if the above condition is met, next pixel is added to the queue
                    visited[nextRow][nextCol] = true; //The next pixel is marked as visited.
                }
            }
        }
        return size;      //after all pixel is visited, return the size of the region
    }

}