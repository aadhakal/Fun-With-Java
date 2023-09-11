1.Read the input from the file and store it in a data structure, such as a list of lists (grid).

2.Initialize a dynamic programming (DP) table (dp) with the same structure as the grid,
and a separate table (path) to store the direction of each step (D: down, R: right).

3.Starting from the bottom-right corner of the grid, traverse it in reverse, i.e., from right to
left and bottom to top.

4.For each cell (i, j), calculate the maximum sum as follows:

a. If the cell is the bottom-right corner, store its value in the DP table (dp[i][j] = grid[i][j])
and set the path to an empty string (path[i][j] = "").

b. If the cell is on the right edge, move down (D) and add the value of the cell below to the current cell's value
(dp[i][j] = grid[i][j] + dp[i + 1][j]), and update the path accordingly (path[i][j] = "D").

c. If the cell is on the bottom edge, move right (R) and add the value of the cell to the right to the current
 cell's value (dp[i][j] = grid[i][j] + dp[i][j + 1]), and update the path accordingly (path[i][j] = "R").

d. If the cell is in the middle, compare the values of the cells below and to the right. Choose the one with the higher
value and update the DP table and path accordingly (either move down (D) or right (R)).

5.The maximum sum is now stored in the top-left cell of the DP table (dp[0][0]).

6.Build the path string by following the directions stored in the path table, starting from the top-left corner
(path[0][0]).

7.Write the maximum sum and the path string to the output file.