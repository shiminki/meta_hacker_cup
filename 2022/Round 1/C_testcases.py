import random
import numpy as np


def main():
    filename = "lemonade_life_input.txt"
    write = open(filename, 'w')
    T, N = 4, int(1e6)

    write.write(f"{T}\n")

    for t in range(T):
        K = int(np.power(10, 9 * random.random()))
        D = int(np.power(10, 9 * random.random()))
        write.write(f"{N} {K} {D}\n")

        for i in range(N):
            if i % 1000 == 0:
                print(i)

                
            x = int(random.randint(0, 1e9))
            y = int(random.randint(0, 1e9))
            write.write(f"{x} {y}\n")


if __name__ == "__main__":
    main()
