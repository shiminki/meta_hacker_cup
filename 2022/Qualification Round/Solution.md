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

