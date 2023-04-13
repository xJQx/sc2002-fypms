package enums;

/**
 * The {@link UserRole} enumeration represents the different roles a user can
 * have in the system.
 * <ul>
 * <li>{@link #STUDENT}: A student user.</li>
 * <li>{@link #SUPERVISOR}: A supervisor user.</li>
 * <li>{@link #FYPCOORDINATOR}: A Final Year Project Coordinator user.</li>
 * </ul>
 */
public enum UserRole {
	/**
	 * Represents a user with the role of a student.
	 */
	STUDENT,

	/**
	 * Represents a user with the role of a supervisor.
	 */
	SUPERVISOR,

	/**
	 * Represents a user with the role of a Final Year Project Coordinator.
	 */
	FYPCOORDINATOR
}
