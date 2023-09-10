 The program takes in two command line arguments: the name of the file that stores the image and an integer'val' which represents the target value to look for in the image (255,or 0).The file should contain the dimensions of
 the image (number of rows and columns) followed by the values of each pixel in the image. The program first reads the input file and stores the image data in the img array. It then initializes the queue and the visited array,
 which are used in the breadth-first search algorithm to traverse the regions of the image.

 The findRegion method is used to find the size of each region that contains the target value 'val' in the image.
 It uses a breadth-first search algorithm to traverse the connected pixels with the target value, starting from a
 given starting pixel (row, col). The method returns the size of the region.

 Finally, the program outputs the number of regions found in the image, followed by a comma-separated list of the sizes
 of the regions, sorted in ascending order.

 Time Complexity?
 The findRegion method takes O(nm) time in the worst case as it performs a breadth-first search (BFS) of the image
 and visits each pixel only once. The space complexity of the given code is O(nm), as the queue array is used to store
 the pixels of the current region being processed. The visited array is used to keep track of the visited pixels and
 has the same space complexity as the queue array. So overall, the code runs in O(nm) time complexity.