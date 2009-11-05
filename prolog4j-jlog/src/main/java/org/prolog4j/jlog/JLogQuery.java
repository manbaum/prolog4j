package org.prolog4j.jlog;

import java.util.Hashtable;

import org.prolog4j.Query;
import org.prolog4j.Solution;

import ubc.cs.JLog.Foundation.jPrologAPI;

/**
 * JLog implementation of the Query class.
 */
class JLogQuery extends Query {

	/** JLog engine. */
	private final jPrologAPI engine;

	/** Stores the initial binding of variables. */
	private Hashtable<String, Object> bindings;

	/**
	 * Creates an object that represents a Prolog query in JLog.
	 * 
	 * @param engine the JLog engine
	 * @param goal the Prolog goal
	 */
	JLogQuery(jPrologAPI engine, String goal) {
		super(goal);
		this.engine = engine;
		this.bindings = new Hashtable<String, Object>(inputVarNames.size());
	}

	@Override
	public <A> Solution<A> solve(Object... actualArgs) {
		int i = 0;
		for (String var: inputVarNames) {
			if (!bindings.contains(var)) {
				bindings.put(var, actualArgs[i++]);
			}
		}
		return new JLogSolution<A>(engine, getGoal(), bindings);
	}

	@Override
	public Query bind(int argument, Object value) {
		bindings.put(inputVarNames.get(argument), value);
		return this;
	}

	@Override
	public Query bind(String variable, Object value) {
		bindings.put(variable, value);
		return this;
	}

}