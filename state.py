#possible directions and the limiting factor of x and y
DIRECTIONS = [0,1,2,3,4,5,6,7]
XLIMIT = 500
YLIMIT = 500
class state:
    #Initial x, y and direction of vehicle
    def __init__(self, x, y, direction,probability):
        self.x = x
        self.y = y
        self.direction = direction
        self.probability = probability

    #getters and setters for x and y coordinates
    def getX(self):
        return self.x
    def getY(self):
        return self.y
    def getXY(self):
        return (self.x,self.y)
    def setX(self,x):
        self.x = x
    def setY(self,y):
        self.y = y
    def setXY(self,xy):
        self.x,self.y = xy

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

    def getProbability(self):
        return self.probability
    def setProbability(self,probability):
        self.probability = probability

    def getSuccessors(self):
        successors = []


