class VirtualArray
{
    map<long long, long long> intervals;

public:
    VirtualArray()
    {
        intervals.insert({LLONG_MIN, LLONG_MIN});
        intervals.insert({LLONG_MAX, LLONG_MAX});
    }

    void set(int start, int end)
    {
        long long s = start;
        long long e = end;

        auto ptr = intervals.upper_bound(s);
        ptr--;
        if (ptr->second >= s)
        {
            s = min(s, ptr->first);
            e = max(e, ptr->second);
            intervals.erase(ptr->first);
        }
        long long next_start = s;
        while (intervals.upper_bound(next_start) != intervals.end())
        {
            auto pair = intervals.upper_bound(next_start);
            if (pair->first > e)
                break;

            next_start = pair->first;
            e = max(e, pair->second);
            intervals.erase(pair->first);
        }
        intervals.insert({s, e});
    }

    bool get(int idx)
    {
        auto ptr = intervals.upper_bound(idx);
        ptr--;
        return ptr->second >= idx;
    }
};