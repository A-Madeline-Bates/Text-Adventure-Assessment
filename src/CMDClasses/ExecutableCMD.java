package CMDClasses;

public abstract class ExecutableCMD extends CMDState {

	public ExecutableCMD(){
		execute();
	}

	public abstract void execute();
}
