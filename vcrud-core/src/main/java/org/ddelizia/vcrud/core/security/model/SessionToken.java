package org.ddelizia.vcrud.core.security.model;

import org.ddelizia.vcrud.model.basic.VcrudItem;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.ddelizia.vcrud.model.usermanagement.VcrudPrincipal;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 31/01/14
 * Time: 17:51
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Document(collection = "SessionToken")
public class SessionToken extends VcrudItem implements Comparable<SessionToken>{

    private String token;
    private VcrudPrincipal principal;

    public SessionToken() {
    }

    public SessionToken(VcrudPrincipal principal) {
        this.principal = principal;
        this.token = UUID.randomUUID().toString();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public VcrudPrincipal getPrincipal() {
        return principal;
    }

    public void setPrincipal(VcrudPrincipal principal) {
        this.principal = principal;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p/>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p/>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p/>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p/>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p/>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(SessionToken o) {
        return getLastModification().compareTo(o.getLastModification());
    }
}
