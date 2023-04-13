package interfaces;

import models.Request;

/**
 * The {@link IRequestView} interface defines a contract for displaying request
 * information.
 */
public interface IRequestView {
	/**
	 * Displays information about the given request.
	 *
	 * @param request the {@link Request} object whose information should be
	 *                displayed
	 */
	public void displayRequestInfo(Request request);
}
