package view;

/**
* Interface for all of the graphical user interface controllers.
* Includes a method which initiates the controller with a ViewHardler class.
*/
public interface ViewController {

	/**
	 * Initiates the controller with a ViewHardler class.
	 * @param viewHandler  The class which controls the view changes and functions.
	 */
	public void init(ViewHandler viewHandler);
}
