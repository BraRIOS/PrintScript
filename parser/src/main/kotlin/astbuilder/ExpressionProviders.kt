package astbuilder

import Expression
import Statement
import Token

class AssignableExpressionProvider(
    tokens: List<Token>,
    lineIndex: Int,
) {
    private val assignableExpressionBuilders: List<AbstractASTBuilder> =
        listOf(
            CallExpressionBuilder(tokens, lineIndex),
            BinaryExpressionBuilder(tokens, lineIndex),
            NumberLiteralBuilder(tokens, lineIndex),
            StringLiteralBuilder(tokens, lineIndex),
            IdentifierBuilder(tokens, lineIndex),
        )

    fun getAssignableExpressionResult(): ASTBuilderResult {
        var errorMessages = ""
        for (expressionBuilder in assignableExpressionBuilders) {
            val astBuilderResult = expressionBuilder.verifyAndBuild()
            if (astBuilderResult is ASTBuilderSuccess && astBuilderResult.astNode is Expression) {
                return astBuilderResult
            }
            if (astBuilderResult is ASTBuilderFailure &&
                expressionBuilder !is StringLiteralBuilder &&
                expressionBuilder !is NumberLiteralBuilder &&
                expressionBuilder !is IdentifierBuilder
            ) {
                errorMessages += astBuilderResult.errorMessage + "\n"
            }
        }
        errorMessages =
            errorMessages
                .lines()
                .filterNot {
                    it == "No operator found in binary expression" ||
                        it == "Not enough members for call expression" ||
                        it == "Binary expression must have at least 3 tokens"
                }.joinToString("\n")
        return ASTBuilderFailure("No valid assignable expression found")
    }
}

class ExpressionProvider(
    tokens: List<Token>,
    lineIndex: Int,
) {
    private val expressionBuilders: List<AbstractASTBuilder> =
        listOf(
            AssignmentExpressionBuilder(tokens, lineIndex),
            CallExpressionBuilder(tokens, lineIndex),
            BinaryExpressionBuilder(tokens, lineIndex),
            NumberLiteralBuilder(tokens, lineIndex),
            StringLiteralBuilder(tokens, lineIndex),
            IdentifierBuilder(tokens, lineIndex),
        )

    fun getVerifiedExpressionResult(): ASTBuilderResult {
        var errorMessages = ""
        for (expressionBuilder in expressionBuilders) {
            val astBuilderResult = expressionBuilder.verifyAndBuild()
            if (astBuilderResult is ASTBuilderSuccess && astBuilderResult.astNode is Expression) {
                return astBuilderResult
            }
            if (astBuilderResult is ASTBuilderFailure &&
                expressionBuilder !is StringLiteralBuilder &&
                expressionBuilder !is NumberLiteralBuilder &&
                expressionBuilder !is IdentifierBuilder
            ) {
                errorMessages += astBuilderResult.errorMessage + "\n"
            }
        }
        errorMessages =
            errorMessages
                .lines()
                .filterNot {
                    it == "No operator found in binary expression" ||
                        it == "Not enough tokens to build assignment expression" ||
                        it == "Invalid assignment expression" ||
                        it == "Not enough members for call expression" ||
                        it == "Binary expression must have at least 3 tokens"
                }.joinToString("\n")
        return ASTBuilderFailure("No valid expression found: \n$errorMessages")
    }
}

class StatementProvider(
    val tokens: List<Token>,
    val lineIndex: Int,
) {
    private val statementBuilders: List<AbstractASTBuilder> =
        listOf(
            VariableDeclarationBuilder(tokens, lineIndex),
            ExpressionStatementBuilder(tokens, lineIndex),
        )

    fun getVerifiedStatementResult(): ASTBuilderResult {
        var errorMessages = ""
        if (tokens.isEmpty()) {
            return ASTBuilderFailure("Empty tokens")
        }
        if (tokens.last().type != "SEMICOLON") {
            return ASTBuilderFailure("Missing semicolon at ($lineIndex, ${tokens.last().position.start}")
        }
        for (statementBuilder in statementBuilders) {
            val astBuilderResult = statementBuilder.verifyAndBuild()
            if (astBuilderResult is ASTBuilderSuccess && astBuilderResult.astNode is Statement) {
                return astBuilderResult
            }
            if (astBuilderResult is ASTBuilderFailure) {
                if (astBuilderResult.errorMessage != "Invalid start of variable declaration") {
                    errorMessages = astBuilderResult.errorMessage
                    break
                }
                errorMessages += astBuilderResult.errorMessage
            }
        }
        return ASTBuilderFailure(errorMessages)
    }
}
