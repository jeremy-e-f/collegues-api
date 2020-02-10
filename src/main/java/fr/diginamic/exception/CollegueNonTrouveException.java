package fr.diginamic.exception;

public class CollegueNonTrouveException extends RuntimeException {
	/**
	 * Constructeur
	 */
	public CollegueNonTrouveException() {
	}

	public CollegueNonTrouveException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;
}
