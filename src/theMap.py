
import random
class mapInterface:

    def __init__(self):
        #intialize the map with purely random data
        self.my_map = [[random.random() for y in range(500)] for x in range(500)]

        
    #call to get the probability that an (x,y) pair is passable
    def getProbability(self,x,y):
        #check that coordinate pair is valid
        if x>=0 and x<len(self.my_map) and y>=0 and y<len(self.my_map[0]):
            return self.my_map[x][y]
        #coordinate pair is invalid, return -1
        return -1



    #the methods below can be called to generate a path on a map
    #for both of these methods the path begins at (0,0) and ends at (499,499)
    #for these maps a value of 0.0 means a point is definitely  passable,
    #a value of 1.0 means a point is definitely  not passable
    #to mark points as passable I used a value of n<=.3
    #to mark points as unpassable I used a double value of n>=.6

    
    #generates a lattice path
    #also randomly marks 2000 points as passable so path is not so obvious
    def makeLattice(self):
        x=0
        y=0
        count=0
        #create lattice path
        while(x!=len(self.my_map) and y!=len(self.my_map[0])):
            self.my_map[x][y]=random.random()*3/10
            if count%2==0:
                x+=1
            else:
                y+=1
            count+=1
        #randomly mark 2000 points 
        for i in range(2000):
            self.my_map[random.randint(0,len(self.my_map)-1)][random.randint(0,len(self.my_map[0])-1)]=random.random()*3/10

            
    #concatenates the txt map matching the give filename together multiple times to make a 500X500 map
    #txt maps are included in the zip file, all txt maps are 25X25
    def makeTxtMap(self,filename):
        #read in text map, "#" represents wall, " " represents open space
        f = open(filename, 'r')
        txt_map=[ [0 for y in range(25)] for x in range(25)]
        x=0
        for line in f:
            y=0
            line=line.rstrip("\n")
            for c in line:
                if c=="#":
                    txt_map[x][y]=random.random()*4/10+6/10
                else:
                    txt_map[x][y]=random.random()*3/10
                y+=1
            x+=1
        #concatenate txt_map together
        for i in range(20):
            self.my_map[i]=txt_map[i]*20
        
                
            
#example commmands
#intialize a map, with random data
m = mapInterface()
#make a lattice path map
m.makeLattice()
#make map using the given txt file
# m.makeTxtMap("map2.txt")
#getProbability that a given point is passable
m.getProbability(100,100)
