# Round 1 2022

# [Problem A1: Consecutive Cuts - Chapter 1]([url](https://www.facebook.com/codingcompetitions/hacker-cup/2022/round-1/problems/A1))

Without considering K, deck A can be reordered to deck B if and only if they have the same circular order. Hence, we first check if both decks are circular in by
checking whether A[i] = B[(i + j) mod N] holds true for some integer j. This can be done in O(N) time by keeping track of the indicies via hashmap. Given that 
the two decks have the same circular order, there are three cases where deck A still may not be reordered to deck B.

1. When K is zero while j (offset) is not. Since we cannot cut deck A, it is impossible to reorder deck A to B
2. When K is 1 while j is zero. While deck A and B are the same, we must cut deck A once, making it impossible to reorder A to B.
3. When N is 2 and j != K mod 2. When N is 2, a cut is equivalent to swapping two cards. Without the loss of generality, suppose j is 1. Then there should be
odd number of swaps in order to reorder A to B. Thus K must be odd. Similar logic applies when j is zero.

# Problem B: Watering Well [Chapter 1]([url](https://www.facebook.com/codingcompetitions/hacker-cup/2022/round-1/problems/B1)) & [Chapter 2]([url](https://www.facebook.com/codingcompetitions/hacker-cup/2022/round-1/problems/B2))

We will directly solve problem B2, as it's constraints cover those of B1. Given the inputs, the naive approach of calculating the inconvienience is

$$ \sum_{i = 1}^N \sum_{j = 1}^Q \Big[(A[i] - X[j])^2 + (B[i] - Y[j])^2\Big] $$

which will take O(N * Q) time. However, we must further optimize the solution, as maximum value of N and Q is 500,000. We aim to reduce the time complexity to
O(N + Q) by further factoring the above expression and eliminate the nexted summation. We will first consider the first term and then apply the same logic to
the second term.

$$
\begin{alignat*}{4}
& \sum_{i = 1}^N \sum_{j = 1}^Q (A[i] - X[j])^2 &&= \sum_{i = 1}^N \sum_{j = 1}^Q \Big[A[i]^2 - 2 * A[i] * X[j] + X[j]^2\Big] \\
&  &&= Q * \sum_{i = 1}^N A[i]^2 + N * \sum_{j = 1}^Q X[j]^2 - 2 * \sum_{i = 1}^N \sum_{j = 1}^Q A[i] * X[j] \\
&  &&= Q * \sum_{i = 1}^N A[i]^2 + N * \sum_{j = 1}^Q X[j]^2 - 2 * \sum_{i = 1}^N A[i] * \big( \sum_{j = 1}^Q X[j] \big) \\
&  &&= Q * \sum_{i = 1}^N A[i]^2 + N * \sum_{j = 1}^Q X[j]^2 - 2 * \sum_{i = 1}^N A[i] * X_{sum}
\end{alignat*}
$$

Since we can pre-calculate $X_{sum}$ in O(Q) time, this expression can be calculated in O(N + Q) time. We can apply the same logic for $\sum_{i = 1}^N \sum_{j = 1}^Q (B[i] - Y[j])^2$, and thus the overall algorithm takes O(N + Q) time.


