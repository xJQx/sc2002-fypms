package enums;

/**
 * The {@link ProjectStatus} enumeration represents the various states a project
 * can be in.
 * <ul>
 * <li>{@link #AVAILABLE}: The project is open for students to reserve.</li>
 * <li>{@link #RESERVED}: The project is reserved by a student and awaiting
 * approval.</li>
 * <li>{@link #UNAVAILABLE}: The project is not available for reservation.</li>
 * <li>{@link #ALLOCATED}: The project is assigned to a student.</li>
 * </ul>
 */
public enum ProjectStatus {
	/**
	 * The project is open for students to reserve.
	 */
	AVAILABLE,

	/**
	 * The project is reserved by a student and awaiting approval.
	 */
	RESERVED,

	/**
	 * The project is not available for reservation.
	 */
	UNAVAILABLE,

	/**
	 * The project is assigned to a student.
	 */
	ALLOCATED
}
