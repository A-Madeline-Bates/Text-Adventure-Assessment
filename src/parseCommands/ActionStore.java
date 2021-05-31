package parseCommands;

public class ActionStore{
	private String subjectName;
	private int subjectPosition;
	private String locationType;

	public void setSubjectName(String subjectName){
		this.subjectName = subjectName;
	}

	public void setPosition(int subjectPosition){
		this.subjectPosition = subjectPosition;
	}

	public void setLocationType(String locationType){
		this.locationType = locationType;
	}

	public String getSubjectName(){
		return subjectName;
	}

	public int getPosition(){
		return subjectPosition;
	}

	public String getLocationType(){
		return locationType;
	}
}
