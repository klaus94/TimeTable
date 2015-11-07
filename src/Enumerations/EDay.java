package Enumerations;
public enum EDay
{
	MONTAG,
	DIENSTAG,
	MITTWOCH,
	DONNERSTAG,
	FREITAG;

	public String toString() {
    	switch(this) {
      		case MONTAG: return "Montag";
      		case DIENSTAG: return "Dienstag";
      		case MITTWOCH: return "Mittwoch";
      		case DONNERSTAG: return "Donnerstag";
      		case FREITAG: return "Freitag";
      		default: throw new IllegalArgumentException();
    	}
	}

    public int toInt() {
    	switch(this) {
    		case MONTAG: return 1;
    		case DIENSTAG: return 2;
    		case MITTWOCH: return 3;
    		case DONNERSTAG: return 4;
    		case FREITAG: return 5;
    		default: throw new IllegalArgumentException();
    	}
    }
    
    public static EDay getDay(String str) throws IllegalArgumentException{
    	switch(str) {
    		case "Montag": return MONTAG;
    		case "Dienstag": return DIENSTAG;
    		case "Mittwoch": return MITTWOCH;
    		case "Donnerstag": return DONNERSTAG;
    		case "Freitag": return FREITAG;
    		default: throw new IllegalArgumentException();
    	}
    }
}
