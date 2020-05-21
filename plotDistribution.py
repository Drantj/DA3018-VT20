import matplotlib.pyplot as plt

degree = []
ammount = []

with open("dDistribution_sorted.txt") as f:
    for line in f:
        w, h = line.split(" ")
        degree.append(int(w))
        ammount.append(int(h))

print(degree)
print(ammount)

#plt.plot(degree, ammount)
#plt.show()


size = []
ammount2 = []
with open("cDistribution_sorted.txt") as f:
    for line in f:
        w, h = line.split(" ")
        size.append(int(w))
        ammount2.append(int(h))

print(size)
print(ammount2)

plt.plot(size, ammount2)
plt.grid()
plt.show()

tot = 0
for i in range(0, len(size)):
    nr = size[i] * ammount2[i]
    tot = tot + nr

print(tot)
print(len(size))
print(len(ammount2))