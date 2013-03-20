import math
from state import *
from theMap import *

# Minimum required probability of safety
MINSAFETYPROB = .50
THRESHOLD = abs(math.log(MINSAFETYPROB))

# Amount to decrement safety threshold each search
SAFETYDEC = .05


def search(map, start):
    currentCostLimit = heuristic(start)
    solution = []

    while currentCostLimit <= THRESHOLD and not solution:
        stack = [start]
        currentCost = 0

        # Depth first search
        while stack:
            currentState = stack.pop()
            if currentState is None:
                # Marker to indicate branch is finished
                # Remove state from solution
                removeState = solution.pop()
                currentCost -= stepCost(removeState)
            else:
                # Add state to solution
                currentCost += stepCost(currentState)
                solution.append(currentState)
                # Add marker to later indicate when branch is finished
                stack.append(None)
                estimated = currentCost + heuristic(currentState)
                if estimated <= currentCostLimit:
                    if isGoal(currentState):
                        return solution
                    else:
                        for successor in currentState.getSuccessors():
                            stack.append(successor)

        # Decrement probability threshold
        probabilityLimit = math.exp(-currentCostLimit)
        probabilityLimit -= SAFETYDEC
        print probabilityLimit
        currentCostLimit = abs(math.log(probabilityLimit))

    return None


def heuristic(state):
    return 0


def isGoal(state):
    return state.getY() == 0


def stepCost(state):
    probability = 1 - map.getProbability(state.getX(), state.getY())
    return abs(math.log(probability))

map = mapInterface()
map.makeLattice
startState = State(250, 250, 5, 1, 1)
solution = search(map, startState)
print solution
