package priv.bajdcc.LALR1.interpret;

import priv.bajdcc.LALR1.grammar.runtime.RuntimeMachine;
import priv.bajdcc.LALR1.interpret.module.IInterpreterModule;
import priv.bajdcc.LALR1.interpret.module.ModuleBase;

/**
 * 【运行时】扩展/内建虚拟机
 *
 * @author bajdcc
 */
public class Interpreter extends RuntimeMachine {

	public Interpreter() throws Exception {
		debug = false;
		builtin();
	}

	private void builtin() throws Exception {
		buildModule();
	}

	private void buildModule() throws Exception {
		IInterpreterModule[] modules = new IInterpreterModule[] { new ModuleBase() };

		for (IInterpreterModule module : modules) {
			run(module.getModuleName(), module.getCodePage());
		}
	}
}