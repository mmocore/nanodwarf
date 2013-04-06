/*LICENSE*/

package com.sun.sgs.client.simple;

import com.sun.sgs.client.ServerSessionListener;
import java.net.PasswordAuthentication;

/**
 * A listener used in conjunction with a {@link SimpleClient}.
 * <p>
 * A {@code SimpleClientListener}, specified when a {@code SimpleClient} is
 * constructed, is notified of connection-related events generated during login
 * session establishment, client reconnection, and client logout, and also is
 * notified of message receipt.
 * 
 * @see SimpleClient
 */
public interface SimpleClientListener extends ServerSessionListener {

	/**
	 * Requests a login credential for the client associated with this listener.
	 * 
	 * @return a login credential for the client
	 */
	PasswordAuthentication getPasswordAuthentication();

	/**
	 * Notifies this listener that a session has been established with the
	 * server as a result of a successful login.
	 */
	void loggedIn();

	/**
	 * Notifies this listener that a session could not be established with the
	 * server due to some failure logging in such as failure to verify a login
	 * credential.
	 * 
	 * @param reason
	 *            a description of the failure
	 */
	void loginFailed(String reason);

}
