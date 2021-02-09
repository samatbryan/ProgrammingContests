class Solution:
    def interpret(self, c: str) -> str:
        res = ""
        n = len(c)
        idx = 0
        while idx < n:
            if c[idx] == "G":
                res += "G"
                idx += 1
            elif c[idx] == "(":
                if c[idx+1] == "a":
                    res += "al"
                    idx += 4
                else:
                    res += "o"
                    idx += 2

        return res
