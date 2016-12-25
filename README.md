# Classical Word Search Solver (diagonal searching to come!)

## What does it do?
Remember those little word search puzzles you used to do on the back cover of the newspaper when you were little? Exactly that!
Enter in a word search and a set of words to find and your word search will be solved in no time at all!

## Okay, but just how good is the solving algorithm?
For the purpose of a reasonable analysis I'm going to make the assumption that all words in a given word set exist somewhere in the word search. If so, the average runtime of the algorithm will grow at a linear rate with respect to the size of the word set (I will show this in a bit). For all intents and purposes let us focus on the runtime for finding a single word in the word search.

Out input is a word search, with *m* rows and *n* columns. Consider an Array *a* containing *m* strings of length *n*. To find a word we start off checking if our word is contained in any row, then any column (I could have implemented it vice versa; the choice is arbitrary). We go through each string in *a* and check if it contains the word we are looking for. A worst-case input will result in the word not found in any row. We iterate *m* times through through the rows in *a* and perform at most 2 checks for the containment of our word in the current row of iteration (one check each for obverse and reverse orientation). If no word is found our runtime for the first half of the algorithm amounts to O(2m).

The procdure for searching the columns is similar. To do this efficiently, we transpose our matrix into an Array *a'* containing *n* strings of length *m*. Once again we iterate through each string in *a'* and perform 2 to determine if our word is contained in that column of our word search. If the word isn't found in any of the columns we have sifted through *n* strings, thus our performance is resolved to O(2n). This makes for a total runtime of O(2m + 2n) = O(2(m + n)) = O(m + n). With respect to the total number of entries in the matrix, *m x n*, we can say the find algorithm runs in sublinear time compared to the number of characters in the word search and is contained in O(mn).

We can extend this finding to a set of words to be found using our find algorithm in a given *m x n* wordsearch. If we let *k* be the number of words in our word set the total average runtime scales linearly, amounting to O(k(m + n)) = O(m + n).
