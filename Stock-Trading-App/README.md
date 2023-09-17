Alg1: maxProfitDivideAndConquerNlogN

1.A new instance of the "float[]" array is created to store the results of the maximum profit calculation.
2.If the start and end indices of the array are equal, it checks if the start index is less than the length of
 the array minus one.
3.If so, it calculates the maximum profit that can be made from the two prices at the start and start+1 indices,
 and sets the appropriate values in the result array.
4.If not, it sets the appropriate values in the result array to represent a negative infinity profit and the start
and end indices.
5.If the start and end indices of the array are not equal, it calculates the index of the middle element, "mid".
6.It recursively calls the "maxProfitDivideAndConquerNlogN" function for the left subarray, from "start" to "mid",
and for the right subarray, from "mid+1" to "end", and stores the returned values in "leftResult" and "rightResult".
7.It finds the minimum and maximum prices and their respective indices in the left and right subarrays, and sets
 these values to the appropriate variables.
8.It calculates the cross difference between the maximum price in the right subarray and the minimum price
in the left subarray.
9.It compares the "maxProfit" of the "leftResult", "rightResult", and "crossDifference", and sets the "maxProfit",
"buyIndex", and "sellIndex" of the result array accordingly.
10.It returns the result array.

The time complexity of this algorithm can be calculated using the recurrence equation: T(n) = 2T(n/2) + n, T(1) = 1.
Using the master's theorem, the time complexity of the algorithm is Theta(n log n).

*************************************************************************************************************
Alg2:maxProfitDecreaseAndConquerLinear:

1.It initializes the "maxProfit" to be the difference between the second and first elements of the "stockPrices" array.
2.It initializes the "buyingPoint" to be the index of the first element and "tempBuyingPoint" to be the same.
3.It also initializes the "sellingPoint" to be the index of the second element and "minPrice" to be the first element.
4.It loops through the "stockPrices" array from index 1 to the last element.
5.For each iteration of the loop, it calculates the current profit by subtracting the "minPrice" from the
current stock price.
6.If the current profit is greater than the "maxProfit", it updates the "maxProfit", "sellingPoint", and "buyingPoint".
7.The "buyingPoint" is set to the "tempBuyingPoint", which tracks the index of the lowest stock price seen so far.
8.If the current stock price is less than the "minPrice", it updates the "minPrice" and the "tempBuyingPoint" to be the
index of the new lowest price seen so far.
9.Finally, it returns an array containing the "maxProfit", "buyingPoint", and "sellingPoint".

The time complexity of this algorithm is Theta(n), as it simply loops through the "stockPrices" array once.

*******************************************************************************************************
Alg3:divideAndConquerMaxProfitLinear

1.It creates a new instance of the "StockData" class named "stockData".
2.It checks if the difference between "right" and "left" is 1, and if so, calculates the maximum profit that
can be made from the two prices, sets the "maxPrice" and "minPrice" accordingly, and returns the "stockData".
3.If the difference between "right" and "left" is not 1, it calculates the index of the middle element, "mid".
4.It recursively calls the "divideAndConquerMaxProfitLinear" function for the left subarray, from "left" to "mid", and
 for the right subarray, from "mid" to "right", and stores the returned values in "leftSubArray" and "rightSubArray".
5.It sets the "minPrice" and "minPriceIndex" of "stockData" to be the minimum price and index of either the left or
right subarray.
6.It sets the "maxPrice" and "maxPriceIndex" of "stockData" to be the maximum price and index of either the left
 or right subarray.
7.It calculates the difference between the maximum price in the right subarray and the minimum price in the
left subarray.
8.It compares the "maxProfit" of the "leftSubArray", "rightSubArray", and "crossDifference", and sets the "maxProfit",
 "buyingIndex", and "sellingIndex" of "stockData" accordingly.
9. It returns the "stockData".

The time complexity of this algorithm is Theta(n), since it is a divide-and-conquer algorithm that recursively divides
the input array in half, and each level of the recursion takes O(1) time to calculate the "StockInfo" object for that
subarray.The recurrence equation is:
        T(n) = 2T(n/2) + 1, T(1) = 1
        Using Master's Theorem: Theta(n).
