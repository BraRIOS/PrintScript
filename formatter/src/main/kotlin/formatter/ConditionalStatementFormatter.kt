package formatter

import ASTNode
import CallExpression
import ConditionalStatement
import ExpressionStatement
import Statement

class ConditionalStatementFormatter : Formatter {
    override fun format(
        astNode: ASTNode,
        configMap: Map<String, Any?>,
    ): String {
        astNode as ConditionalStatement
        return "if(${astNode.test.name}) {\n" +
            contentFormatter(astNode.consequent, configMap) +
            (
                if (astNode.alternate.isNotEmpty()) {
                    "} else {\n" +
                        contentFormatter(astNode.alternate, configMap) + "\n" +
                        "}\n"
                } else {
                    "}"
                }
                )
    }

    private fun repeatIndentation(configMap: Map<String, Any?>): String {
        val indentationCount = (configMap["identationInsideConditionals"] as? Number)?.toInt()?.takeIf { it >= 1 } ?: 1
        return "    ".repeat(indentationCount)
    }

    private fun contentFormatter(
        astNode: List<Statement>,
        configMap: Map<String, Any?>,
    ): String {
        return if (astNode.isNotEmpty()) {
            val aux: MutableList<String> = mutableListOf()
            for (statement in astNode) {
                when (statement) {
                    is ConditionalStatement -> {
                        aux.add(
                            repeatIndentation(configMap) + "if(${statement.test.name}) {\n" +
                                contentFormatter(statement.consequent, configMap).split("\n")
                                    .joinToString("\n") { repeatIndentation(configMap) + it } + "\n" +
                                (
                                    if (statement.alternate.isNotEmpty()) {
                                        repeatIndentation(configMap) + "} else {\n" +
                                            contentFormatter(statement.alternate, configMap).split("\n")
                                                .joinToString("\n") { repeatIndentation(configMap) + it } + "\n" +
                                            repeatIndentation(configMap) + "}\n"
                                    } else {
                                        repeatIndentation(configMap) + "}"
                                    }
                                    ),
                        )
                        continue
                    }

                    is ExpressionStatement -> {
                        if (statement.expression is CallExpression) {
                            val aux2 =
                                CallExpressionFormatter().format(statement.expression as CallExpression, configMap)
                                    .split("\n") as MutableList<String>
                            for ((index, value) in aux2.withIndex()) {
                                aux2[index] = repeatIndentation(configMap) + value
                            }
                            aux += aux2.joinToString("\n") + ";"
                        } else {
                            aux.add(repeatIndentation(configMap) + StatementFormatter().format(statement, configMap))
                        }
                        continue
                    }

                    else -> {
                        aux.add(repeatIndentation(configMap) + StatementFormatter().format(statement, configMap))
                    }
                }
            }
            aux.joinToString("\n")
        } else {
            ""
        }
    }
}
