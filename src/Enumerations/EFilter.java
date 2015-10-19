package Enumerations;

public enum EFilter {
	BYMORNIGTIME,
	BYAFTERNOONTIME,
	BYMINNUMBER,
	BYMAXNUMBER,
	BYMAXINROW;
	
	public int toInt() {
		switch(this) {
			case BYMORNIGTIME: return 1;
			case BYAFTERNOONTIME: return 1;
			default: return 0;
		}
	}
}
