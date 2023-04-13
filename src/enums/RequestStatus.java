package enums;

/**
 * The {@link RequestStatus} enumeration represents the different states a
 * request can be in.
 * <ul>
 * <li>{@link #PENDING}: The request is awaiting approval or rejection.</li>
 * <li>{@link #APPROVED}: The request has been approved.</li>
 * <li>{@link #REJECTED}: The request has been rejected.</li>
 * </ul>
 */
public enum RequestStatus {
	PENDING,
	APPROVED,
	REJECTED
}
