package priv.bajdcc.LALR1.grammar.runtime.service;

import priv.bajdcc.LALR1.grammar.runtime.RuntimeProcess;

/**
 * 【运行时】运行时服务
 *
 * @author bajdcc
 */
public class RuntimeService implements IRuntimeService {

	private RuntimeProcess process;
	private RuntimePipeService pipe;
	private RuntimeShareService share;

	public RuntimeService(RuntimeProcess process) {
		this.process = process;
		this.pipe = new RuntimePipeService();
		this.share = new RuntimeShareService();
	}

	@Override
	public IRuntimePipeService getPipeService() {
		return pipe;
	}

	@Override
	public IRuntimeShareService getShareService() {
		return share;
	}

	@Override
	public IRuntimeProcessService getProcessService() {
		return process;
	}
}
