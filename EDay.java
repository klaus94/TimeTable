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
}