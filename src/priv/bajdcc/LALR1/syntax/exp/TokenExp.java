package priv.bajdcc.LALR1.syntax.exp;

import priv.bajdcc.LALR1.syntax.ISyntaxComponent;
import priv.bajdcc.LALR1.syntax.ISyntaxComponentVisitor;
import priv.bajdcc.util.VisitBag;
import priv.bajdcc.util.lexer.token.TokenType;

/**
 * 文法规则（终结符）
 *
 * @author bajdcc
 */
public class TokenExp implements ISyntaxComponent {

	/**
	 * 终结符ID
	 */
	public int id = -1;

	/**
	 * 终结符名称
	 */
	public String name = null;

	/**
	 * 终结符对应的正则表达式
	 */
	public TokenType kType = null;

	/**
	 * 终结符对应的正则表达式解析组件（用于语义分析中的单词流解析）
	 */
	public Object object = null;

	public TokenExp(int id, String name, TokenType type, Object obj) {
		this.id = id;
		this.name = name;
		kType = type;
		object = obj;
	}

	@Override
	public void visit(ISyntaxComponentVisitor visitor) {
		VisitBag bag = new VisitBag();
		visitor.visitBegin(this, bag);
		if (bag.bVisitEnd) {
			visitor.visitEnd(this);
		}
	}

	@Override
	public String toString() {
		return String.format("%d: `%s`，%s，%s", id, name,
				kType.getName(),
				object == null ? "(null)" : object.toString());
	}
}
