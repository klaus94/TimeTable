package Enumerations;

public enum EFilter {
	BYMORNINGTIME,
	BYAFTERNOONTIME,
	BYMINNUMBER,
	BYMAXNUMBER,
	BYMAXINROW,
	BYDOUBLECOURSES,
	BYFIXCOURSE;
	
	
	public Boolean needDays() {
		switch(this) {
			case BYMORNINGTIME: return true;
			case BYAFTERNOONTIME: return true;
			default: return false;
		}
	}
	
	public Boolean needParam() {
		switch(this) {
			case BYDOUBLECOURSES: return false;
			case BYFIXCOURSE: return false;
			default: return true;
		}
	}
	
	public Boolean needCourse(){
		switch(this) {
			case BYFIXCOURSE: return true;
			default: return false;
		}
	}
	
	public String toString() {
		switch (this) {
			case BYMORNINGTIME: return "by morningtime";
			case BYAFTERNOONTIME: return "by afternoontime";
			case BYMINNUMBER: return "by minimum number of courses on a day";
			case BYMAXNUMBER: return "by maximum number of courses on a day";
			case BYMAXINROW: return "by maximum courses in a row before a break";
			case BYDOUBLECOURSES: return "by double courses";
			case BYFIXCOURSE: return "by fix course";
			default: return "";
		}
	}
	
	public static EFilter toEnum(String type) {
		switch (type) {
			case "by morningtime": return BYMORNINGTIME; 
			case "by afternoontime": return BYAFTERNOONTIME;
			case "by minimum number of courses on a day": return BYMINNUMBER;
			case "by maximum number of courses on a day": return BYMAXNUMBER;
			case "by maximum courses in a row before a break": return BYMAXINROW;
			case "by double courses": return BYDOUBLECOURSES;
			case "by fix course": return BYFIXCOURSE;
			default: return null;
		}
	}
}
