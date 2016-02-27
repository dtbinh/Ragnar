package fr.isima.sma.gui;

import org.jdesktop.beansbinding.Validator;

public class AgentForJListValidator<Humanoid> extends Validator<Humanoid> {

	@Override
	public Validator<Humanoid>.Result validate(Humanoid arg0) {
		return (arg0==null) ? new Result(arg0, "Agent null") : null;
	}

}
