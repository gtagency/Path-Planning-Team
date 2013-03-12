#possible directions and the limiting factor of x and y
DIRECTIONS = {0: (-1, -1), 1: (0, -1), 2: (1, -1), 3: (1, 0), 4: (1, 1), 5: (0, 1), 6: (-1, 1), 7: (-1, 0)}
XLIMIT = (0, 499)
YLIMIT = (0, 499)


class State:

    #Initial x, y and direction of vehicle
    def __init__(self, x, y, direction, speed, probability):
        self.x = x
        self.y = y
        self.direction = direction
        self.probability = probability
        self.speed = speed

    #getters and setters for x and y coordinates
    def getX(self):
        return self.x

    def getY(self):
        return self.y

    def getXY(self):
        return (self.x, self.y)

    def setX(self, x):
        self.x = x

    def setY(self, y):
        self.y = y

    def setXY(self, xy):
        self.x, self.y = xy

    #Getter and setter for direction
    #Direction in form:
    #0   1   2
    #7  car  3
    #6   5   4
    #So 1 is "up"
    def getDirection(self):
        return self.direction

    def setDirection(self, direction):
        self.direction = direction

    #Included to say the probability that I am at this state.
    def getProbability(self):
        return self.probability

    def setProbability(self, probability):
        self.probability = probability

    def getSpeed(self):
        return self.speed

    def setSpeed(self, speed):
        self.speed = speed

    #Gets the succeors of this state
    #Assumes that (0,0) is the upper left corner and (499,499) is the bottom right corner
    def getSuccessors(self):
        successors = []
        if(self.speed == 1 or self.speed == -1):
            for i in range(self.direction-1, self.direction+2):
                directionToGo = i % 8
                xUpdate, yUpdate = DIRECTIONS[directionToGo]
                if((self.x+xUpdate) >= XLIMIT[0] and (self.x+xUpdate) <= XLIMIT[1] and (self.y+yUpdate) >= YLIMIT[0] and (self.y+yUpdate) <= YLIMIT[1]):
                    successors.append(State(self.x+xUpdate, self.y+yUpdate, directionToGo, self.speed, self.probability))
            successors.append(self.x, self.y, self.direction, 0, self.probability)
        elif(self.speed == 0):
            for i in range(self.direction-1, self.direction+2):
                directionToGo = i % 8
                xUpdate, yUpdate = DIRECTIONS[directionToGo]
                if((self.x+xUpdate) >= XLIMIT[0] and (self.x+xUpdate) <= XLIMIT[1] and (self.y+yUpdate) >= YLIMIT[0] and (self.y+yUpdate) <= YLIMIT[1]):
                    successors.append(State(self.x+xUpdate, self.y+yUpdate, directionToGo, 1, self.probability))
            for i in range(self.direction+7, self.direction+10):
                directionToGo = i % 8
                xUpdate, yUpdate = DIRECTIONS[directionToGo]
                if((self.x+xUpdate) >= XLIMIT[0] and (self.x+xUpdate) <= XLIMIT[1] and (self.y+yUpdate) >= YLIMIT[0] and (self.y+yUpdate) <= YLIMIT[1]):
                    successors.append(State(self.x+xUpdate, self.y+yUpdate, directionToGo, -1, self.probability))
        return successors


# Testing
# state = State(5,7,1,1)
# successors = state.getSuccessors()
# for successor in successors:
#     print "New X is: {0} New Y is: {1}\n".format(successor.getX(),successor.getY())


