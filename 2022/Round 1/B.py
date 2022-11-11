def main():
    filename = "watering_well_chapter_2_input.txt"
    f = open(filename, "r")
    out = open("output.txt", "w")

    T = int(f.readline())
    NUM = 1000000007

    for t in range(1, T + 1):
        N = int(f.readline())
        A, B = [], []
        for i in range(N):
            a, b = (int(s) for s in f.readline().split(" "))
            A.append(a)
            B.append(b)

        Q = int(f.readline())
        X, Y = [], []
        for j in range(Q):
            x, y = (int(s) for s in f.readline().split(" "))
            X.append(x)
            Y.append(y)

        ans = 0
        x_sum, y_sum = sum(X), sum(Y)

        for j in range(Q):
            ans += N * ((X[j] * X[j] + Y[j] * Y[j]) % NUM)
            ans %= NUM

        for i in range(N):
            ans += Q * ((A[i] * A[i] + B[i] * B[i]) % NUM)
            ans -= 2 * (A[i] * x_sum + B[i] * y_sum) % NUM
            ans %= NUM

        if ans < 0:
            ans += NUM

        out.write(f"Case #{t}: {ans}\n")


if __name__ == "__main__":
    main()
