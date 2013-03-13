import random
import sys
width = 50
height = 20
map = [['']*width]*height
print "-"*width
for y in range(height-1):
    sys.stdout.write("|")
    for x in range(width-1):
        gen = random.random()*1
        char = ''
        if gen < .7:
            char = " "
        else:
            char = "#"
        map[y][x] = char
        sys.stdout.write(char)

    sys.stdout.write("|")
    print ""
print "-"*width
