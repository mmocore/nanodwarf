/*LICENSE*/

package com.sun.sgs.client;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Represents a client's view of a channel. A channel is a communication group,
 * consisting of multiple clients and the server.
 * <p>
 * The server is solely responsible for creating channels and adding and
 * removing clients from channels. If desired, a client can request that a
 * channel be created or its session be joined to or removed from the channel by
 * sending an application-specific message to the server (using its
 * {@link ServerSession}).
 * <p>
 * When the server adds a client session to a channel, the client's
 * {@link ServerSessionListener}'s {@link ServerSessionListener#joinedChannel
 * joinedChannel} method is invoked with that client channel, returning the
 * client's {@link ClientChannelListener} for the channel. A
 * {@code ClientChannelListener} for a client channel is notified as follows:
 * <ul>
 * <li>When a message is received on a client channel, the listener's
 * {@link ClientChannelListener#receivedMessage receivedMessage} method is
 * invoked with the channel and the message. The listener <i>is</i> notified of
 * messages that its client sends on its associated channel; that is, a sender
 * receives its own broadcasts.</li>
 * 
 * <li>When the associated client leaves a channel, the listener's
 * {@link ClientChannelListener#leftChannel leftChannel} method is invoked with
 * the channel. Once a client has been removed from a channel, that client can
 * no longer send messages on that channel.</li>
 * </ul>
 */
public interface ClientChannel {

	/**
	 * Returns the name of this channel. A channel's name is set when it is
	 * created by the server-side application.
	 * 
	 * @return the name of this channel
	 */
	String getName();

	/**
	 * Sends the message contained in the specified {@code ByteBuffer} to this
	 * channel. The message starts at the buffer's current position and ends at
	 * the buffer's limit. The buffer's position is not modified by this
	 * operation.
	 * 
	 * <p>
	 * If the server-side application does not filter messages on this channel,
	 * the message will be delivered unaltered to all channel members, including
	 * the sender. However, the server-side application may <i>alter the
	 * message</i>, <i>discard the message</i>, or <i>modify the list of
	 * recipients</i> for application-specific reasons. If the channel message
	 * is not delivered to the sender (because it is discarded by the
	 * application, for example), the sender's {@link ClientChannelListener}
	 * will not receive a {@link ClientChannelListener#receivedMessage
	 * receivedMessage} notification for that message.
	 * 
	 * <p>
	 * The {@code ByteBuffer} may be reused immediately after this method
	 * returns. Changes made to the buffer after this method returns will have
	 * no effect on the message sent to the channel by this invocation.
	 * 
	 * @param message
	 *            a message to send
	 * 
	 * @throws IllegalStateException
	 *             if the sender is not a member of this channel
	 * @throws IOException
	 *             if a synchronous I/O problem occurs
	 */
	void send(ByteBuffer message) throws IOException;
}
