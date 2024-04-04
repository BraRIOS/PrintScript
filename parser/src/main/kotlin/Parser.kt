import astbuilder.ASTBuilderResult
import astbuilder.StatementProvider

class Parser {
    @Throws(IllegalArgumentException::class)
    fun parse(
        tokens: List<Token>,
        lineIndex: Int,
    ): ASTBuilderResult {
        if (tokens.count { it.value == ";" || it.type == "SEMICOLON" } > 1) {
            error("Only one line of code is allowed at a time.")
        }
        val statementProvider = StatementProvider(tokens, lineIndex)
        return statementProvider.getVerifiedStatementResult()
    }
}
