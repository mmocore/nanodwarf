/*LICENSE*/

package com.sun.sgs.impl.auth;

import com.sun.sgs.auth.Identity;
import com.sun.sgs.auth.IdentityAuthenticator;
import com.sun.sgs.auth.IdentityCredentials;

import java.util.Properties;

import javax.security.auth.login.CredentialException;

/**
 * A very simple implementation of <code>IdentityAuthenticator</code> that
 * always authenticates a user based on <code>NamePasswordCredentials</code>.
 * This is primarily useful for testing applications.
 */
public class NullAuthenticator implements IdentityAuthenticator {

	/**
	 * Creates an instance of <code>NullAuthenticator</code>.
	 * 
	 * @param properties
	 *            the application's configuration properties
	 */
	public NullAuthenticator(@SuppressWarnings("unused") Properties properties) {
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getSupportedCredentialTypes() {
		return new String[] { NamePasswordCredentials.TYPE_IDENTIFIER };
	}

	/**
	 * Always authenticates the given user unless the credentials are not of
	 * type <code>NamePasswordCredentials</code>, in which case a
	 * <code>CredentialException</code> is thrown.
	 * 
	 * @param credentials
	 *            the identity's credentials, which must be an instance of
	 *            <code>NamePasswordCredentials</code>
	 * 
	 * @return the identity of the given user
	 * 
	 * @throws CredentialException
	 *             if the wrong type of credentials were provided
	 */
	public Identity authenticateIdentity(IdentityCredentials credentials)
			throws CredentialException {
		if (!(credentials instanceof NamePasswordCredentials)) {
			throw new CredentialException("unsupported credentials type");
		}
		return new IdentityImpl(
				((NamePasswordCredentials) credentials).getName());
	}

}
