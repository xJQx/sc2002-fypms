package enums;

/**
 * The {@link RequestType} enumeration represents the different types of
 * requests.
 * <ul>
 * <li>{@link #TRANSFER_STUDENT}: A request to transfer a student from one
 * supervisor to another.</li>
 * <li>{@link #CHANGE_PROJECT_TITLE}: A request to change the title of a
 * project.</li>
 * <li>{@link #ALLOCATE_PROJECT}: A request to allocate a project to a
 * student.</li>
 * <li>{@link #DEREGISTER_PROJECT}: A request to deregister a project.</li>
 * </ul>
 */
public enum RequestType {
	/**
	 * A request to transfer a student from one supervisor to another.
	 */
	TRANSFER_STUDENT,

	/**
	 * A request to change the title of a project.
	 */
	CHANGE_PROJECT_TITLE,

	/**
	 * A request to allocate a project to a student.
	 */
	ALLOCATE_PROJECT,

	/**
	 * A request to deregister a project.
	 */
	DEREGISTER_PROJECT
}
