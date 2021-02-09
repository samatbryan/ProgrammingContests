/*
https://leetcode.com/contest/weekly-contest-219/problems/partitioning-into-minimum-number-of-deci-binary-numbers/

1689. Partitioning Into Minimum Number Of Deci-Binary Numbers
User Accepted:3633
User Tried:3780
Total Accepted:3691
Total Submissions:4295
Difficulty:Medium
A decimal number is called deci-binary if each of its digits is either 0 or 1 without any leading zeros. For example, 101 and 1100 are deci-binary, while 112 and 3001 are not.

Given a string n that represents a positive decimal integer, return the minimum number of positive deci-binary numbers needed so that they sum up to n.
*/

class Solution
{
public:
    int minPartitions(string n)
    {
        int res = 0;
        for (int i = 0; i < n.size(); i++)
        {
            int c = n[i] - '0';
            res = max(res, c);
        }
        return res;
    }
};