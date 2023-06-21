import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    @Test
    public void testConstructor() {
        Set<String> test = this.constructorTest();
        Set<String> ref = this.constructorRef();
        assertEquals(ref, test);
    }

    /**
     * Helper method that tests {@code add} method.
     *
     * @param x
     *            element to be added
     * @param elements
     *            elements in original set.
     */
    private void testAdd(String x, String... elements) {
        Set<String> test = this.createFromArgsTest(elements);
        Set<String> ref = this.createFromArgsRef(elements);
        test.add(x);
        ref.add(x);
        assertEquals(ref, test);
    }

    @Test
    public void testAdd1() {
        this.testAdd("");
    }

    @Test
    public void testAdd2() {
        this.testAdd("a");
    }

    @Test
    public void testAdd3() {
        this.testAdd("b", "a");
    }

    @Test
    public void testAdd4() {
        this.testAdd("c", "a", "b");
    }

    @Test
    public void testAdd5() {
        this.testAdd("a", "");
    }

    @Test
    public void testAdd6() {
        this.testAdd("ab", "a", "b", "c");
    }

    /**
     * Helper method that tests the {@code remove} method.
     *
     * @param x
     *            the element to be removed.
     * @param elements
     *            the elements in the original set.
     */
    private void testRemove(String x, String... elements) {
        Set<String> test = this.createFromArgsTest(elements);
        Set<String> ref = this.createFromArgsRef(elements);
        String outT = test.remove(x);
        String outR = ref.remove(x);
        assertEquals(ref, test);
        assertEquals(outR, outT);
    }

    @Test
    public void testRemove1() {
        this.testRemove("", "");
    }

    @Test
    public void testRemove2() {
        this.testRemove("a", "a", "b");
    }

    @Test
    public void testRemove3() {
        this.testRemove("a", "a");
    }

    @Test
    public void testRemove4() {
        this.testRemove("b", "a", "b", "c");
    }

    @Test
    public void testRemove5() {
        this.testRemove("ab", "abc", "cbbc", "ab");
    }

    /**
     * Helper method that tests {@code removeAny} method.
     *
     * @param elements
     *            the elements in the original set.
     */
    private void testRemoveAny(String... elements) {
        Set<String> test = this.createFromArgsTest(elements);
        Set<String> ref = this.createFromArgsRef(elements);
        String out = test.removeAny();
        assertTrue(ref.contains(out));
        ref.remove(out);
        assertEquals(ref, test);

    }

    @Test
    public void testRemoveAny1() {
        this.testRemoveAny("");
    }

    @Test
    public void testRemoveAny2() {
        this.testRemoveAny("a", "b");
    }

    @Test
    public void testRemoveAny3() {
        this.testRemoveAny("a", "ab", "c");
    }

    /**
     * Helper method that tests the method {@code contains}.
     *
     * @param x
     *            the element to be queried.
     * @param elements
     *            the elements in the original set.
     */
    private void testContains(String x, String... elements) {
        Set<String> test = this.createFromArgsTest(elements);
        Set<String> ref = this.createFromArgsRef(elements);
        assertEquals(ref.contains(x), test.contains(x));
        assertEquals(ref, test);
    }

    @Test
    public void testContains1() {
        this.testContains("");
    }

    @Test
    public void testContains2() {
        this.testContains("", "");
    }

    @Test
    public void testContains3() {
        this.testContains("ab", "a", "ab", "ba");
    }

    @Test
    public void testContains4() {
        this.testContains("b", "a", "ab", "ba");
    }

    /**
     * Helper method that tests the method {@code size}.
     *
     * @param elements
     *            the elements in the set.
     */
    private void testSize(String... elements) {
        Set<String> test = this.createFromArgsTest(elements);
        Set<String> ref = this.createFromArgsRef(elements);
        assertEquals(ref.size(), test.size());
        assertEquals(ref, test);
    }

    @Test
    public void testSize1() {
        this.testSize();
    }

    @Test
    public void testSize2() {
        this.testSize("a", "b");
    }

    @Test
    public void testSize3() {
        this.testSize("A", "a", "ab");
    }
}
