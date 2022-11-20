# Qualification Round 2022

# [Problem A: A Second Hands]([url](https://www.facebook.com/codingcompetitions/hacker-cup/2022/qualification-round/problems/A))

Given an array [S<sub>1</sub>, ..., S<sub>n</sub>] and integer K, we must divide S into two sets where each set contains at most K unique elements. 
  Observe that it is not possible to parition S to such conditions if and only if one of the following conditions is true:
  
  1. |S| > 2 * K
  2. S contains more than two elements of the same kind

Thus we can keep track the count of each number in S and return "YES" if and only if N <= 2 * K and count of each number is less than 3.
This algorithm can be completed in O(N) via one for-loop.

# [Problem B1: Second Friend]([url](https://www.facebook.com/codingcompetitions/hacker-cup/2022/qualification-round/problems/B1))

In this problem, it is suffice to simplify the output such that all grids have a tree or does not have a tree. With this constraint, it is impossible
to set every tree to have at least two tree friends if and only if there is at least one tree and R == 1 or C == 1. Thus, we can traverse each grid to
check if there is a tree, and if there is, we set valid as true if R > 1 and C > 1. This solution takes O(R * C) time.

# [Problem B2: Second Second Friend]([url](https://www.facebook.com/codingcompetitions/hacker-cup/2022/qualification-round/problems/B2)) 

Problem B2 has the same setting as problem B1, except that there are rocks in the grid and the maximum grid size increased from 100 x 100 to 3,000 x 3,000.
Inspired from the solution of B1, we initially set all empty space as a "potential tree," indicating the grid will have a tree. Defining a tree with less than
two tree friends as an "invalid tree," we will traverse each grid and set all invalid trees as an empty space. For every invalid tree we reach, we will run dfs
at that grid, remove the invalid tree, and recurse dfs on the adjacent grid with an invalid tree. 

Although running dfs algorithm on each grid seems like the overall algorithm takes O(R^2 * C^2) time, it actually takes O(R * C) time. Suppose the grid has M total
invalid trees. Since each invalid tree will be visited only once during the dfs search, the algorithm takes O(R * C) time from traversal and O(M) = O(R * C) time
from the dfs.

# [Problem C1: Second Meaning]([url](https://www.facebook.com/codingcompetitions/hacker-cup/2022/qualification-round/problems/C1))

Observe than N, the number of code words, is less than or equal to 100. Since each code word can have maximum length of 200 and length of C<sub>1</sub> is 100, 
it is suffice to distinguish each code words by its length. Without the loss of generality, let the last character of C<sub>1</sub> be '-'. Then C<sub>i</sub> 
consists of (99 + i) '.' and end with '-'. Because length of C<sub>100</sub> will be 199 (number of '.') + 1 ('-' at the end) = 200, this algorithm generates codewords
that satisfies the constrant.

# [Problem C2: Second Second Meaning]([url](https://www.facebook.com/codingcompetitions/hacker-cup/2022/qualification-round/problems/C2))

This problem is similar to problem C1, except that each codeword must have 10 characters or less. To satisfy such constraint, we will generate our set such that
each codeword has length of 10. In order for our set to yield unambiguous encoding, no codeword should be a starting substring of another. For instance, if C<sub>1</sub> is "-.-." and C<sub>2</sub> is "-.-..", then this may lead ambigious encoding.

My algorithm consists of 3 main steps:
1. Generate a power set of 2<sup>10</sup> binary strings of length 10
2. If a string in the power set is a starting substring of C<sub>1</sub> or vice versa, remove it from the set
3. Output first N - 1 entries in the remaining power set.

One concern is that we may not have enough words remaining after step 2. Suppose that C<sub>1</sub> is '-'. Then from step 2, all string that starts with '-' must
be removed, and hence 2^<sup>9</sup> = 512 strings are removed. Nonetheless, our set have 512 valid strings remaining, which is suffice to generate 99 codewords.
In addition, the algorithm satisfies the time constraint. Step 1 and 3 takes 1,024 and N - 1 (99) operations. In step 2, substring comparison takes 10 operations
(as the length of each codeword is 10), and hence it takes 10 * 1,024 = 10,240 operations. Therefore, the overall algorithm requires less than 20,000 operations.

# [Problem D: Second Flight]([url](https://www.facebook.com/codingcompetitions/hacker-cup/2022/qualification-round/problems/D))

In this problem, given a query x and y, we must get the maximum number of passengers who can travel from airport x to airport y with less than 3 flights.
The airport can be represented as a bidirectional graph with edge weight equal to the flight capacity. We will denote the edge weight of edge (x, y) as C(x, y).
Because there are only two flight each day, we can only consider two cases: direct flight from airport x to y and transer flight at a different airport z. 
For the first case, we simply add 2 * C(x, y) to our answer, as C(x, y) number of tourists can take direct flight from x to y in the morning and the evening.
For the second case, we have find every airport z such that z is connected to both airport x and y. Then, we add min(C(x, z), C(z, y)) to our answer, as only
min(C(x, z), C(z, y)) passengers can travel from x to z to y. If we represent our airport graph as an adjacency list (list of hashset), then we can process each
query in O(1) time. Hence the overall time complexity is O(N + M) for graph construction and O(Q) for the queries.
