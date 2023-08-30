DESIGN EXPLANATION:
Parse input arguments: The program accepts three command-line arguments, representing the number of chips, the range of
possible target numbers, and the option to display specific results or play the game.

Calculate costs: The program calculates the minimum number of questions required to identify a target number for all
possible combinations of chips and ranges using a bottom-up approach. This involves filling a two-dimensional costs array
, where costs[i][j] represents the minimum number of questions needed to identify a target number with i chips and a range of j.

Calculate guesses: The program calculates the optimal guess for each combination of chips and ranges based on
the calculated costs. This involves filling a two-dimensional guesses array, where guesses[i][j] represents the optimal
guess for a combination of i chips and a range of j.

Print the results: 
   If option is 0 or 3, print the minimum number of questions required to identify the target number in the worst case
   If option is 1 or 2, print the costs array
   If option is 2, print the guesses array

Play the game: If option 3 is chosen, the program plays the "Guess the Number" game with the user, using the calculated
optimal guesses. The game continues until the target number is found.


