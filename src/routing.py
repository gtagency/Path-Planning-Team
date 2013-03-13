from Location import Location

data = []
loc1 = Location()
loc1.populate(3)
loc2 = Location()
loc2.populate(4)
data.append(loc1)
data.append(loc2)

cost = 4
currentCost = 0
for i in range(cost):
    stack = [data[0]]
    for child in 
        stack.append((child, currentCost + parentToChildCost))