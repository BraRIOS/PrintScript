import java.math.BigDecimal

class BinaryExpressionInterpreter(
    private val variableMap: Map<String, VariableInfo>,
) : Interpreter {
    override fun interpret(node: ASTNode): Any {
        require(node is BinaryExpression) { "Node must be an BinaryExpression" }
        val leftValue =
            when (val left = node.left) {
                is NumberLiteral -> left.value
                is StringLiteral -> left.value
                is BinaryExpression -> interpret(left)
                is Identifier -> IdentifierInterpreter(variableMap).interpret(left)
                else -> throw IllegalArgumentException("Invalid expression")
            }
        val rightValue =
            when (val right = node.right) {
                is NumberLiteral -> right.value
                is StringLiteral -> right.value
                is BinaryExpression -> interpret(right)
                is Identifier -> IdentifierInterpreter(variableMap).interpret(right)
                else -> throw IllegalArgumentException("Invalid expression ")
            }
        return handleOperation(leftValue, rightValue, node.operator)
    }

    @Throws(IllegalArgumentException::class)
    private fun handleOperation(
        leftValue: Any,
        rightValue: Any,
        operator: String,
    ): Any {
        val result =
            when (operator) {
                "+" ->
                    when {
                        leftValue is String && rightValue is String -> leftValue + rightValue
                        leftValue is Number && rightValue is Number -> leftValue as BigDecimal + rightValue as BigDecimal
                        leftValue is Number && rightValue is String || leftValue is String && rightValue is Number ->
                            leftValue.toString() +
                                rightValue.toString()
                        else -> throw IllegalArgumentException("Invalid operands for '+': $leftValue, $rightValue")
                    }
                "-", "*", "/" -> when {
                    leftValue is Number && rightValue is Number -> when (operator) {
                        "-" -> (leftValue as BigDecimal) - (rightValue as BigDecimal)
                        "*" -> (leftValue as BigDecimal) * (rightValue as BigDecimal)
                        "/" -> (leftValue as BigDecimal) / (rightValue as BigDecimal)
                        else -> throw IllegalArgumentException("Invalid operator: $operator")
                    }
                    else -> throw IllegalArgumentException("Invalid operands for '$operator': $leftValue, $rightValue")
                }
                else -> throw IllegalArgumentException("Invalid operator: $operator")
            }
        return result
    }
}
