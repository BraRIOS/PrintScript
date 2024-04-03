package analyzers

import ASTNode
import ExpressionStatement

class ExpressionStatementAnalyzer : Analyzer {
    override fun analyze(
        astNode: ASTNode,
        configMap: Map<String, Any?>,
        lineIndex: Int,
    ): String {
        var result = ""
        if (astNode !is ExpressionStatement) return result
        val expression = astNode.expression
        result += ExpressionAnalyzer().analyze(expression, configMap, lineIndex)
        return result
    }
}