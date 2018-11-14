
package domain;

public class Constant {

	public enum currency {
		EUR,
		USD
}
	public enum requestStatus {
		PENDING,
		REJECTED,
		ACCEPTED
	}

	public enum difficultyLevels {
		EASY,
		MEDIUM,
		DIFFICULT
	}

	// Monedas
	public static final String	CURRENCY_EURO						= "EUR";
	public static final String	CURRENCY_DOLLAR						= "USD";
	
	
	
	// Offer states
	public static final String	OFFER_CREATED						= "CREATED";
	public static final String	OFFER_IN_DEVELOPMENT				= "IN_DEVELOPMENT";
	public static final String	OFFER_ABANDONED						= "ABANDONED";
	public static final String	OFFER_PRESENTED						= "PRESENTED";
	public static final String	OFFER_WINNED						= "WINNED";
	public static final String	OFFER_LOSED							= "LOSED";
	public static final String	OFFER_CHALLENGED					= "CHALLENGED";
	public static final String	OFFER_DENIED						= "DENIED";

	// Subsection sections
	public static final String	SECTION_ADMINISTRATIVE_ACREDITATION	= "ADMINISTRATIVE_ACREDITATION";
	public static final String	SECTION_TECHNICAL_OFFER				= "TECHNICAL_OFFER";
	public static final String	SECTION_ECONOMICAL_OFFER			= "ECONOMICAL_OFFER";

	// Tender interests
	public static final String	TENDER_INTEREST_UNDEFINED			= "UNDEFINED";
	public static final String	TENDER_INTEREST_LOW					= "LOW";
	public static final String	TENDER_INTEREST_MEDIUM				= "MEDIUM";
	public static final String	TENDER_INTEREST_HIGH				= "HIGH";

	// Company result states
	public static final String	COMPANY_RESULT_WINNER				= "WINNER";
	public static final String	COMPANY_RESULT_LOSER				= "LOSER";
	public static final String	COMPANY_RESULT_RECKLESS_OFFER		= "RECKLESS_OFFER";
	
	// File parent types
	public static final String	FILE_MESSAGE						= "message";
	public static final String	FILE_INCIDENCE						= "incidence";
	public static final String	FILE_TENDER							= "tender";
	public static final String	FILE_TENDER_RESULT					= "tenderResult";
}
