/*LICENSE*/

package com.sun.sgs.client;

import java.nio.ByteBuffer;

/**
 * Listener for events relating to a {@link ClientChannel}.
 * <p>
 * When the server adds a client session to a channel, the client's
 * {@link ServerSessionListener}'s {@link ServerSessionListener#joinedChannel
 * joinedChannel} method is invoked with that client channel, returning the
 * client's {@code ClientChannelListener} for the channel. A
 * {@code ClientChannelListener} for a client channel is notified as follows:
 * <ul>
 * <li>When a message is received on a client channel, the listener's
 * {@link ClientChannelListener#receivedMessage receivedMessage} method is
 * invoked with the channel and the message. The listener <i>is</i> notified of
 * messages that its client sends on its associated channel; that is, a sender
 * receives its own broadcasts.</li>
 * <li>When the associated client leaves a channel, the listener's
 * {@link ClientChannelListener#leftChannel leftChannel} method is invoked with
 * the channel. Once a client has been removed from a channel, that client can
 * no longer send messages on that channel.</li>
 * </ul>
 */
public interface ClientChannelListener {

	/**
	 * Notifies this listener that the specified {@code message} was received on
	 * the specified {@code channel}. This listener is notified of messages that
	 * its associated client sends.
	 * <p>
	 * 
	 * If the message originated from a client, the server-side application may
	 * have altered the {@code message} (for application-specific reasons) from
	 * the original message sent.
	 * 
	 * @param channel
	 *            a client channel
	 * @param message
	 *            a message
	 */
	void receivedMessage(ClientChannel channel, ByteBuffer message);

	/**
	 * Notifies this listener that the associated client was removed from the
	 * specified {@code channel}. The associated client can no longer send
	 * messages on the specified {@code channel}.
	 * 
	 * @param channel
	 *            a client channel
	 */
	void leftChannel(ClientChannel channel);
}
