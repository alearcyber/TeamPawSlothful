/**Implements observer design pattern
 * @param <Observed> The object being observed
 */
public interface Observer<Observed> {
    /**Update the object being observed
     * @param observed The object that is being observed
     */
    void update(Observed observed);
}