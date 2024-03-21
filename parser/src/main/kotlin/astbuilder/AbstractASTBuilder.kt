package astbuilder

import Token

abstract class AbstractASTBuilder(
    val tokens: List<Token>,
) : ASTBuilder {
    protected open fun verify(): Boolean = true
}
