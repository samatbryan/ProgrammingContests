# class Tree:
#     def __init__(self, val, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right
class Solution:
    def solve(self, root):
        res = 0

        def f(node):
            if not node:
                return 0

            nonlocal res
            left = f(node.left)
            right = f(node.right)

            if node.left and node.right:
                if left % 2 == 0 and right % 2 == 1:
                    res += 1
                if left % 2 == 1 and right % 2 == 0:
                    res += 1
            return left + right + node.val

        f(root)
        return res
