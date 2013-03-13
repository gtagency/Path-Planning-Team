class Location:
    def __init__(self):
        self.children = []

    def getChildren(self):
        return self.children

    def populate(self, num):
        for x in range(num):
            self.children.append((Location(), 5))