"""
https://leetcode.com/contest/weekly-contest-221/problems/determine-if-string-halves-are-alike/
1704. Determine if String Halves Are Alike
User Accepted:4128
User Tried:4248
Total Accepted:4229
Total Submissions:5264
Difficulty:Easy
You are given a string s of even length. Split this string into two halves of equal lengths, and let a be the first half and b be the second half.

Two strings are alike if they have the same number of vowels ('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'). Notice that s contains uppercase and lowercase letters.

Return true if a and b are alike. Otherwise, return false
"""


class Solution:

    def halvesAreAlike(self, s: str) -> bool:
        n = len(s)

        left = s[0:n//2]
        right = s[n//2:]

        left_c = Counter(left)
        right_c = Counter(right)

        vowels = ['a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U']
        left_count = 0
        right_count = 0

        for vowel in vowels:
            if vowel in left_c:
                left_count += left_c[vowel]
            if vowel in right_c:
                right_count += right_c[vowel]
        return left_count == right_count
