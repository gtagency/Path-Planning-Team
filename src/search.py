

def search(map, start):
    cost = heuristic(start)
    solution = None
    while solution is None:
        solution, cost = aStarSearch([start], 0, cost)

    return solution


# solutionPath: path so far
# currentCost: cost of solution path
# costLimit: cutoff for this serach
# returns (None, nextCost) if cost limit exceeded or (solution, cost) if found
def aStarSearch(solutionPath, currentCost, costLimit):
    lastState = solutionPath[len(solutionPath) - 1]
    totalCost = currentCost + heuristic(lastState)

    # If over cost limit, returns estimated cost to find next limit
    if totalCost > costLimit:
        return (None, totalCost)
    if isGoal(lastState):
        return (solutionPath, currentCost)

    nextCostLimit = float("inf")
    solutions = []
    for successor in lastState.getSuccessors():
        if successor not in solutionPath:
            # Upstaes cost and solution path
            solutionPath.append(successor)
            currentCost += stepCost(successor)

            # Uses edges to minimize next cost limit or adds solution
            result = aStarSearch(solutionPath, currentCost, costLimit)
            if result[0] is None:
                nextCostLimit = min(nextCostLimit, result[1])
            else:
                # Requires copy of solution list because path is modified
                solutions.append((list(result[0]), result[1]))

            # Restores cost and solution path
            currentCost -= stepCost(successor)
            solutionPath.pop()

    if len(solutions) == 0:
        return (None, nextCostLimit)

    # Find minimum of found solutions
    finalSolution = (None, float("inf"))
    for solution in solutions:
        if solution[1] < finalSolution[1]:
            finalSolution = solution
    return finalSolution


def heuristic(state):
    return 1


def isGoal():
    return False


def stepCost(state):
    probability = map.getProbability(state.getX(), state.getY())
    return math.log(probability)

map = mapInterface()
map.makeLattice()
startState = new State(250, 250, 5, 1)
solution = search(map, startState)
