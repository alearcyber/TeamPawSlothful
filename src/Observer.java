/**
 * This interface is follows the simple observer design.
 * it allows us to know when something changes in a different class.
 *
 * @Author
 * Aidan Lear
 *
 *
 * Date 2/15/2021
 *
 * Notes:
 * This class shouldn't be messed with, just a simple observer interface for
 * the observer design pattern.
 *
 * @param <Observered> what is being observered
 */

public interface Observer<Observered> {

    /**
     * update whatever it is that is being observed
     *
     * @param observered the object that is being observed
     */
    void update(Observered observered);
}