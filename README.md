# Artificially Intelligent Agent for Saving Westeros

This is a java implemention for an intelligent agent who should search for a solution to kill all white walkers in Westeros using minimum number of dragon glasses without looping infinitely using one of the following search techinques : 
- **BFS**
- **DFS**
- **Greedy**
- **Iterative Deepning**
- **A Star Algorithm**

Westeros is represented as a 2D grid. Cells in westeros are one of the following types:
- **Empty** : John can freely move to this cell.
- **Obstacle** : John can't move to this cell.
- **Dragon Stone** : John can move to this cell and he can pick dragon glasses used to kill white walkers.
- **White Walker** : John can't move to this cell until he kills the white walker in it.

Operations that john can do are one of the following:
- **Moving** : John can move in one of the four directions (UP, DOWN, LEFT, RIGHT).
- **Picking** : John can pick Dragon Glasses if he is on the Dragon Stone cell.
- **Killing** : John can use one of his Dragon Glasses to kill all Walkers around him.
