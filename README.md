# Classical Word Search Solver (diagonal searching to come!)

## What does it do?
Remember those little word search puzzles you used to do on the back cover of the newspaper when you were little? Exactly that!
Enter in a word search and a set of words to find and your word search will be solved in no time at all!

## How do I use it?
Make sure you have sbt and Scala properly installed. After cloning/downloading the reposity navigate to the root directory in your terminal of choice and and run `sbt console`. Then type `Solver.solve` in the Scala repl and you will be prompted to enter the name of a file containing your wordsearch and wordlist. The information contained in the file you enter should be formatted like such, with each entry contained on a separate line. To get a better idea of how to format it, take a look at the two .txt files I have provided for testing.

```
<# of rows in wordsearch>
<the wordsearch itself>
<# of words in the wordset>
<the wordset itself>
```

Once you enter the name of your wordsearch you're all set! Just press return and you'll be looking at the solved puzzle.

```
scala> Solver.solve
Enter the name of the file containing your wordsearch: words.txt
Solving your wordsearch...

          c a l c u l u s n n
            n           u o i
n           o         m i n
o       g n i r u t b t v l
i m p e r a t i v e c e a
t           p   r n n r     c
a           a s u t u       o
e   l       r f i d         m
r y   o     t o e   t       p
c   r   g   n c     y       u
      o   i o       p       t
        m r c       e       e
        p e   i     d       r
            m   a   e       s
r o s s e c o r p n f
```

## Okay, but just how good is the solving algorithm?
For the purpose of a reasonable analysis I'm going to make the assumption that all words in a given word set exist somewhere in the word search. If so, the average runtime of the algorithm will grow at a linear rate with respect to the size of the word set (I will show this in a bit). For all intents and purposes let us focus on the runtime for finding a single word in the word search.

Out input is a word search, with *m* rows and *n* columns. Consider an Array *a* containing *m* strings of length *n*. To find a word we start off checking if our word is contained in any row, then any column (I could have implemented it vice versa; the choice is arbitrary). We go through each string in *a* and check if it contains the word we are looking for. A worst-case input will result in the word not found in any row. We iterate *m* times through through the rows in *a* and perform at most 2 checks for the containment of our word in the current row of iteration (one check each for obverse and reverse orientation). If no word is found our runtime for the first half of the algorithm amounts to O(2m).

The procdure for searching the columns is similar. To do this efficiently, we transpose our matrix into an Array *a'* containing *n* strings of length *m*. Once again we iterate through each string in *a'* and perform 2 to determine if our word is contained in that column of our word search. If the word isn't found in any of the columns we have sifted through *n* strings, thus our performance is resolved to O(2n). This makes for a total runtime of O(2m + 2n) = O(2(m + n)) = O(m + n). With respect to the total number of entries in the matrix, *m x n*, we can say the find algorithm runs in sublinear time compared to the number of characters in the word search and is contained in O(mn).

We can extend this finding to a set of words to be found using our find algorithm in a given *m x n* wordsearch. If we let *k* be the number of words in our word set the total average runtime scales linearly, amounting to O(k(m + n)) = O(m + n).
