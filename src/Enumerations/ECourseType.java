package Enumerations;

public enum ECourseType {
	LECTURE,
	EXERCISE;
	
	public String toString() {
    	switch(this) {
      		case LECTURE: return "Vorlesung";
      		case EXERCISE: return "Übung";
      		default: throw new IllegalArgumentException();
    	}
	}
}
