# SetOnBST

## Objectives
1. Familiarity with writing a kernel class for a new type and its kernel operations (Set layered on BinaryTree, using a binary search tree).
2. Familiarity with developing and using specification-based test plans.

## The Problem
The problem is to complete and carefully test implementations of the constructor and all kernel methods defined in interface SetKernel, building the data structure representing a Set object by layering it on top of BinaryTree. The algorithmic approach is to use a binary search tree to reduce search time from linear (as in Set2) to logarithmic in the number of elements in the set; at least, there is this reduction in execution time "on average", though not in the worst case.
