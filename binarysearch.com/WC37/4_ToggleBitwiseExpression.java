/*
https://binarysearch.com/room/Weekly-Contest-37-u2kU8duwTB?questionsetIndex=
You are given a string s representing a bitwise expression with the following characters: "0", "1", "&", "|", "(", and ")".

In one operation, you can:

Toggle "0" to "1"
Toggle "1" to "0"
Change "&" to "|"
Change "|" to "&"
Return the minimum number of operations required to toggle the value of the expression.

Note: the precedence of the operators don't matter, since they'll be wrapped in brackets when necessary. For example, "1&0" and "1&(0&1)" are possible inputs but "1&0&1" will not occur.

Constraints

n ≤ 100,000 where n is the length of s

Solution:
Expression Evaluation + Dynamic Programming 

Prerequisite - using a stack to parse infix notations (with parentheses, so this should be easier). This editorial will assume that you are able to use a stack to evaluate the expression.

The core idea behind this problem is that for each "calculation" of "a op b", there are only two possible outputs - 0 and 1, and eight possible inputs - a = [0,1], b = [0,1], op=[&,|]. Therefore, it is sufficient to keep track of the cheapest way to get to that output (or state) in the current calculation stack. At the end, it will cost 0 to get to the unchanged answer, and the other one is the answer we seek.

Implementation
This implementation uses dynamic programming, similar to a sequence suffix dynamic programming, where your states are the progress of your last calculation, and the possible outputs of 0 and 1.

The transitions are annotated for "or" in the code below, but it is simply an exhaustive enumeration of the possible transitions. Comments are inline in code for the "or" case.

Time Complexity
\mathcal{O}(n)O(n) - each item will be pushed or popped off the stack once (with the help of the parens), so this is linear time.

Space Complexity
\mathcal{O}(n)O(n) - the stack will solve this problem efficiently

*/

class Solution:
    def solve(self, st):
        if len(st) == 1:
            return 1

        st = "(" + st + ")"
        s = []

        for x in st:
            if x == ")":
                # az = min number of edits get to zero on the "a" side
                # ao = min number of edits to get to one on the "a" side
                az, ao = s.pop()
                op = s.pop()
                # bz = min number of edits get to zero on the "a" side
                # bo = min number of edits to get to one on the "a" side
                bz, bo = s.pop()

                nz, no = None, None
                if op == "|":
                    # These are just the "standard" way without edits, the new way to get to zero is if both the left and the right are zeroes
                    nz = az + bz
                    # We take the cheapest way to get to one by enumerating all the possibilities of a|b ->
                    # the cost of (a = 0, b = 1), (a = 1, b = 1), (a = 1, b = 0)
                    no = min(az + bo, ao + bo, ao + bz)

                    # These are the cost of changing either the left or the right
                    # another way to get to zero is starting from (a = 0, b = 1), and changing b = 0, with a cost of bo, or (a = 1, b = 0), and changing a = 0, with a cost of ao
                    nz = min(nz, az + bo + bz, ao + bz + az)
                    # if a = 0, b = 0, we either change b to 1 (with the cost of bo) or we change a to 1 (with cost of ao)
                    no = min(no, az + bz + bo, az + bz + ao)

                    # we can also change the sign - we can get to zero if we change to "and", and it will cost "1" to change the operator
                    nz = min(nz, min(az + bz, az + bo, ao + bz) + 1)
                    # we can get to one if we already have ones and change the signs to "and".  (Note, this is never going to be true as 1|1 == 1&1, but here for symmetry)
                    no = min(no, ao + bo + 1)
                else:
                    # Same logic, but for "and"
                    nz = min(az + bz, az + bo, ao + bz)
                    no = ao + bo

                    nz = min(nz, ao + bo + az, ao + bo + bz)
                    no = min(no, az + bo + ao, ao + bz + bo)

                    nz = min(nz, az + bz + 1)
                    no = min(no, min(az + bo, ao + bo, ao + bz) + 1)

                s.append((nz, no))
            elif x in "01":
                if int(x) == 0:
                    s.append((0, 1))
                else:
                    s.append((1, 0))
            elif x not in "(":
                s.append(x)

        return max(s[0])
