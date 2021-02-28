class Solution:
    def longestNiceSubstring(self, s: str) -> str:
        def valid(ss):
            have = set(ss)
            for lc in ascii_lowercase:
                if lc in have and upper(lc) not in have:
                    return False
                if upper(lc) in have and lc not in have:
                    return False
            return True

        for l in range(1, len(s)):
            for i in range(len(s)):
                if i + l - 1 >= len(s):
                    break
                if valid(s[i:i+l]):
                    return s[i:i+l]
        return ""
