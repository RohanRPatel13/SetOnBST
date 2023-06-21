import java.util.Iterator;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.set.Set;
import components.set.SetSecondary;

/**
 * {@code Set} represented as a {@code BinaryTree} (maintained as a binary
 * search tree) of elements with implementations of primary methods.
 *
 * @param <T>
 *            type of {@code Set} elements
 * @mathdefinitions <pre>
 * IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]
 * </pre>
 * @convention IS_BST($this.tree)
 * @correspondence this = labels($this.tree)
 *
 * @author Put your name here
 *
 */
public class Set3a<T extends Comparable<T>> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private BinaryTree<T> tree;

    /**
     * Returns whether {@code x} is in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires IS_BST(t)
     * @ensures isInTree = (x is in labels(t))
     */
    private static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        // Implemented Jun 11.
        boolean out = false;
        if (t.size() != 0) {
            if (x.equals(t.root())) {
//                assert x.compareTo(root) == 0;
                out = true;
            } else {
                BinaryTree<T> left = t.newInstance(), right = t.newInstance();
                T root = t.disassemble(left, right);
                if (x.compareTo(root) < 0) {
                    // left
                    out = isInTree(left, x);
                } else {
                    // right
                    out = isInTree(right, x);
                }
                t.assemble(root, left, right);
            }
        }
        return out;
    }

    /**
     * Inserts {@code x} in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be inserted
     * @aliases reference {@code x}
     * @updates t
     * @requires IS_BST(t) and x is not in labels(t)
     * @ensures IS_BST(t) and labels(t) = labels(#t) union {x}
     */
    private static <T extends Comparable<T>> void insertInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        // Implemented Jun 11.
        if (t.size() == 0) {
            t.assemble(x, t.newInstance(), t.newInstance());
        } else {
            BinaryTree<T> left = t.newInstance(), right = t.newInstance();
            T root = t.disassemble(left, right);
//            assert !x.equals(root) && x.compareTo(root) != 0;
            if (x.compareTo(root) < 0) {
                // Left.
                insertInTree(left, x);
            } else {
                // right.
                insertInTree(right, x);
            }
            t.assemble(root, left, right);
        }

    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates t
     * @requires IS_BST(t) and |t| > 0
     * @ensures <pre>
     * IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}
     * </pre>
     */
    private static <T> T removeSmallest(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";

        // Implemented Jun 11.
//      assert t.size() > 0;
        BinaryTree<T> left = t.newInstance(), right = t.newInstance();
        T root = t.disassemble(left, right);
        T out;
        if (left.size() == 0) {
            out = root;
            t.transferFrom(right);
        } else {
            out = removeSmallest(left);
            t.assemble(root, left, right);
        }
        return out;
    }

    /**
     * Finds label {@code x} in {@code t}, removes it from {@code t}, and
     * returns it.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove label {@code x}
     * @param x
     *            the label to be removed
     * @return the removed label
     * @updates t
     * @requires IS_BST(t) and x is in labels(t)
     * @ensures <pre>
     * IS_BST(t)  and  removeFromTree = x  and
     *  labels(t) = labels(#t) \ {x}
     * </pre>
     */
    private static <T extends Comparable<T>> T removeFromTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        // Implemented Jun 11.
//      assert isInTree(t, x);
//      assert t.size() > 0;

        T out;
        BinaryTree<T> left = t.newInstance(), right = t.newInstance();
        T root = t.disassemble(left, right);

        if (x.equals(root)) {
//          assert x.compareTo(root) == 0;

            // base case
            out = root;
            if (left.size() == 0) {
                // left is empty.
                t.transferFrom(right);
            } else if (right.size() == 0) {
                // right is empty.
                t.transferFrom(left);
            } else {
                t.assemble(removeSmallest(right), left, right);
            }
        } else {
            // recursive case.
            if (x.compareTo(root) < 0) {
                // left.
                out = removeFromTree(left, x);
            } else {
                // right
//              assert x.compareTo(root) != 0;
                out = removeFromTree(right, x);
            }
            t.assemble(root, left, right);
        }
        return out;
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        // Implemented Jun 12.
        this.tree = new BinaryTree1<T>();
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Set3a() {
        // Implemented Jun 12.
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Set<T> newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Set<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Set3a<?> : ""
                + "Violation of: source is of dynamic type Set3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set3a<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set3a<T> localSource = (Set3a<T>) source;
        this.tree = localSource.tree;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert !this.contains(x) : "Violation of: x is not in this";
        // Implemented Jun 12.
        insertInTree(this.tree, x);
    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.contains(x) : "Violation of: x is in this";
        T removed = removeFromTree(this.tree, x);
        return removed;
    }

    @Override
    public final T removeAny() {
        assert this.size() > 0 : "Violation of: this /= empty_set";
        // Implemented Jun 12.
        T removed = removeSmallest(this.tree);
        return removed;
    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";
        // Implemented Jun 12.
        return isInTree(this.tree, x);
    }

    @Override
    public final int size() {
        // Implemented Jun 12.
        return this.tree.size();
    }

    @Override
    public final Iterator<T> iterator() {
        return this.tree.iterator();
    }

}
