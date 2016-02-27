package fr.isima.sma.world;

import java.util.ArrayList;
import java.util.List;

public class Group extends Humanoid {
	
	private static int groupCount = 0;
	protected List<Humanoid> membres;
	protected GroupType groupType;

	public enum GroupType {
		ANONYME, HEROES, VILAINS, WITHPRISONERS
	}
	
	static private String incGroupCount() {
		return String.valueOf(++groupCount);
	}
	
	public Group(GroupType groupType, Location loc) {
		super(AgentType.GROUP, "Groupe", incGroupCount(), 1);
		this.type = AgentType.GROUP;
		membres = new ArrayList<Humanoid>(8);
	}

	public void add(Humanoid membre) {
		membres.add(membre);
	}

	public void add(List<Humanoid> mbs) {
		membres.addAll(mbs);
	}
	
	public void remove(Humanoid membre) {
		membres.remove(membre);
	}
	
	public Humanoid get(int i) {
		return membres.get(i);
	}

	@Override
	public void live() {
		// TODO Auto-generated method stub
		
	}

	public GroupType getGroupType() {
		return groupType;
	}

	public void setGroupType(GroupType groupType) {
		this.groupType = groupType;
	}

}
