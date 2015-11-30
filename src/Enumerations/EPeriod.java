package Enumerations;
public enum EPeriod
{
	EACHWEEK,
	EVENWEEK,
	ODDWEEK,
	UNDEFINED;
	
	public static EPeriod getPeriod(String str) throws IllegalArgumentException{
    	switch(str) {
    		case "wöch.": return EACHWEEK;
    		case "1. Wo.": return ODDWEEK;
    		case "2. Wo.": return EVENWEEK;
    		case "?": return UNDEFINED;
    		default: throw new IllegalArgumentException();
    	}
    }
}