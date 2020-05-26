import matplotlib.pyplot as plt

degree = []
amount = []


with open("dDistribution_sorted.txt") as f:
    for line in f:
        w, h = line.split(" ")
        degree.append(int(w))
        amount.append(int(h))


size = []
amount2 = []


with open("cDistribution_sorted.txt") as f:
    for line in f:
        w, h = line.split(" ")
        size.append(int(w))
        amount2.append(int(h))

plt.figure()

# degree regular
#plt.subplot(121)
#plt.plot(degree, amount)
#plt.title("degree")
#plt.grid(True)

# degree zoom
#plt.subplot(122)
#plt.plot(degree, amount)
#plt.axis([0, 600, 0, 850])
#plt.title("degree zoom")
#plt.grid(True)

# component
plt.subplot(121)
plt.plot(size, amount2)
plt.title("component size")
plt.grid(True)

# component zoom
plt.subplot(122)
plt.plot(size, amount2)
plt.axis([0, 400, 0, 600])
plt.title("component zoom")
plt.grid(True)

#plt.subplots_adjust(top = 0.95, bottom = 0.08, left = 0.10, right = 0.95, hspace = 0.25, wspace = 0.35)

plt.show()