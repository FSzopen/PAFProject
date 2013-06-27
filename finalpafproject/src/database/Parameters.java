package database;

public class Parameters {
	
	private int perDateType;
	private int perDateGraph;
	private int perCountryGraph;
	private int perIPGraph;
	private int perAttackGroupGraph;
	private int perUsernameGraph;
	
	public Parameters() {
		perDateType = 0;
		perDateGraph = 0;
		perCountryGraph = 0;
		perIPGraph = 0;
		perAttackGroupGraph = 0;
		perUsernameGraph = 0;
	}
	
	
	// GETTERS
	public int getPerDateType() { return perDateType; }
	public int getPerDateGraph() { return perDateGraph; }
	public int getPerCountryGraph() { return perCountryGraph; }
	public int getPerIPGraph() { return perIPGraph; }
	public int getPerAttackGroupGraph() { return perAttackGroupGraph; }
	public int getPerUsernameGraph() { return perUsernameGraph; }
	
	// SETTERS
	public void setPerDateType(int perDateType) { this.perDateType = perDateType; }
	public void setPerDateGraph(int perDateGraph) { this.perDateGraph = perDateGraph; }
	public void setPerCountryGraph(int perCountryGraph) { this.perCountryGraph = perCountryGraph; }
	public void setPerIPGraph(int perIPGraph) { this.perIPGraph = perIPGraph; }
	public void setPerAttackGroupGraph(int perAttackGroupGraph) { this.perAttackGroupGraph = perAttackGroupGraph; }
	public void setPerUsernameGraph(int perUsernameGraph) { this.perUsernameGraph = perUsernameGraph; }
	
}
