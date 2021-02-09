class FrontMiddleBackQueue:

    def __init__(self):
        from collections import deque
        self.q = deque()

    def pushFront(self, val: int) -> None:
        self.q.appendleft(val)

    def pushMiddle(self, val: int) -> None:
        idx = len(self.q)
        insert_idx = idx // 2
        self.q.insert(insert_idx, val )

    def pushBack(self, val: int) -> None:
        self.q.append(val)

    def popFront(self) -> int:
        if len(self.q) == 0:
            return -1
        
        return self.q.popleft()
    
    def popMiddle(self) -> int:
        
        if len(self.q) == 0:
            return -1
        
        idx = len(self.q) // 2
        if len(self.q) % 2 == 0:
            idx -=1 
        x = self.q[idx]
        del self.q[idx]
        return x

    def popBack(self) -> int:
        if len(self.q) == 0:
            return -1

        return self.q.pop()

# Your FrontMiddleBackQueue object will be instantiated and called as such:
# obj = FrontMiddleBackQueue()
# obj.pushFront(val)
# obj.pushMiddle(val)
# obj.pushBack(val)
# param_4 = obj.popFront()
# param_5 = obj.popMiddle()
# param_6 = obj.popBack()