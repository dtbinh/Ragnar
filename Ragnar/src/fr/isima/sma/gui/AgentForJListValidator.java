package fr.isima.sma.gui;

import org.jdesktop.beansbinding.Validator;

/**
 * Validator for using non null Agents in the AgentsView.
 * @param <Humanoid> Agent to test
 */
public class AgentForJListValidator<Humanoid> extends Validator<Humanoid> {

	/**
	 * Method for validate agents.
	 */
	@Override
	public Validator<Humanoid>.Result validate(Humanoid arg0) {
		return (arg0==null) ? new Result(arg0, "Agent null") : null;
	}

}
