package fr.isima.sma.world;

import java.util.ArrayList;
import java.util.List;

public class AgentsList<T> extends AbstractModelObject {
	protected List<T> agents;
	protected String name;

	public AgentsList() {
		this("agents");
	}

	public AgentsList(String pName) {
		agents = new ArrayList<>();
		name = pName;
	}

	public void addAgent(T elt) {
		List<T> oldValue = agents;
		agents = new ArrayList<T>(agents);
		agents.add(elt);
		firePropertyChange("agents", oldValue, agents);
		firePropertyChange("agentsCount", oldValue.size(), agents.size());
	}

	public void removeAgent(T elt) {
		List<T> oldValue = agents;
		agents = new ArrayList<T>(agents);
		agents.remove(elt);
		firePropertyChange("agents", oldValue, agents);
		firePropertyChange("agentsCount", oldValue.size(), agents.size());
	}

	public List<T> getAgents() {
		return agents;
	}
	
	public T getAgent(int i) {
		return agents.get(i);
	}

	public int size() {
		return agents.size();
	}
}
