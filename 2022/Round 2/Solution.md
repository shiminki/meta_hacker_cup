# Round 2 2022

# [Problem A1: Perfectly Balanced - Chapter 1]([url](https://www.facebook.com/codingcompetitions/hacker-cup/2022/round-2/problems/A1))

Define $f(c, i, j)$ as the frequency of character $c$ in substring $S_{i, j}$. Then for a given substring $S_{i, j}$ and the middle index $mid = floor((i + j) / 2)$,
$\sum_{c \in \[a...z]}|f(c, i, mid) - f(c, mid + 1, j)|$ or $\sum_{c \in \[a...z]}|f(c, i, mid - 1) - f(c, mid, j)|$ should be 1, as exactly one character should
have one more count on either half of the substring in order for it to be almost perfectly balanced. Since there are 4,000,000 total queries, we must process them
in O(1) time to meet the time constraint. For each letters in the alphabet, we can maintain a prefix sum array for string S, in which $f(c, i, j) = prefix[j] - prefix[i]$
Because there are 26 total letters, the overall time complexity is O(|S| + Q) from creating prefix arrays and processing queries.
