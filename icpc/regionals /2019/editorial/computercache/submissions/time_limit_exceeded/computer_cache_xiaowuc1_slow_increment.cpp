#include <algorithm>
#include <bitset>
#include <cassert>
#include <chrono>
#include <cstring>
#include <iomanip>
#include <iostream>
#include <map>
#include <queue>
#include <random>
#include <set>
#include <stack>
#include <vector>

using namespace std;

typedef long long ll;
typedef pair<int, int> pii;
typedef pair<pii, int> state;

struct node {
  node* lc;
  node* rc;
  int lhs, rhs;
  int lazy;
};

int qry(node* n, int i) {
  while(true) {
    if(n->lhs == i && n->rhs == i) return n->lazy;
    if(i <= n->lc->rhs) n = n->lc;
    else n = n->rc;
  }
}

inline node* makeNode(int lhs, int rhs, int val) {
  node* ret = new node;
  ret->lc = NULL;
  ret->rc = NULL;
  ret->lhs = lhs;
  ret->rhs = rhs;
  ret->lazy = val;
  return ret;
}

node* init(int lhs, int rhs, vector<int>& v) {
  if(lhs == rhs) {
    return makeNode(lhs, rhs, v[lhs]);
  }
  node* ret = makeNode(lhs, rhs, 0);
  int mid = (lhs+rhs)/2;
  ret->lc = init(lhs, mid, v);
  ret->rc = init(mid+1, rhs, v);
  return ret;
}
node* initSegTree(vector<int>& v) {
  return init(0, v.size()-1, v);
}
node* roots[1<<19];

const int SEGTREE_DEPTH = 19;
const int SEGTREE_SZ = 1 << SEGTREE_DEPTH;
state segtree[2 * SEGTREE_SZ];
void push(int id) {
  assert(id >= SEGTREE_SZ);
  for(int d = SEGTREE_DEPTH; d > 0; d--) {
    int i = id >> d;
    if(segtree[i].second == 0) continue;
    segtree[2*i] = {segtree[i].first, segtree[i].second};
    segtree[2*i+1] = {segtree[i].first, segtree[i].second + (1 << (d-1))};
    segtree[i] = {{0, 0}, 0};
  }
}
void upd(int idx, pii id, int size) {
  int lhs = idx + SEGTREE_SZ;
  int rhs = lhs + size - 1;
  push(lhs);
  push(rhs);
  int amt = 1;
  int lhsIdx = 1;
  int rhsIdx = size;
  while(lhs <= rhs) {
    if(lhs == rhs) {
      assert(rhsIdx - lhsIdx == amt-1);
      segtree[lhs] = {id, lhsIdx};
      break;
    }
    if(lhs%2) {
      segtree[lhs++] = {id, lhsIdx};
      lhsIdx += amt;
    }
    if(rhs%2==0) {
      segtree[rhs--] = {id, rhsIdx-amt+1};
      rhsIdx -= amt;
    }
    amt *= 2;
    lhs /= 2;
    rhs /= 2;
  }
}
state qry(int i) {
  i += SEGTREE_SZ;
  push(i);
  return segtree[i];
}

vector<pii> increments[1 << 19];

void solve() {
  int n, m, q;
  cin >> n >> m >> q;
  vector<int> sizes;
  for(int i = 0; i < m; i++) {
    vector<int> v;
    int t;
    cin >> t;
    v.resize(t);
    sizes.push_back(t);
    for(int a = 0; a < t; a++) cin >> v[a];
    roots[i] = initSegTree(v);
  }
  while(q--) {
    int type;
    cin >> type;
    if(type == 1) {
      int i, p;
      cin >> i >> p;
      i--;
      p--;
      upd(p, {i, increments[i].size()}, sizes[i]);
    }
    else if(type == 2) {
      int x;
      cin >> x;
      x--;
      state curr = qry(x);
      if(--curr.second < 0) {
        cout << "0\n";
        continue;
      }
      int ret = qry(roots[curr.first.first], curr.second);
      for(int i = 0; i < curr.first.second; i++) {
        if(increments[curr.first.first][i].first <= curr.second && curr.second <= increments[curr.first.first][i].second) {
          ret = (ret+1)&255;
        }
      }
      cout << ret << "\n";
    }
    else {
      int i, l, r;
      cin >> i >> l >> r;
      i--; l--; r--;
      increments[i].push_back({l, r});
    }
  }
}

int main() {
  solve();
}
