import TokenType.ASSIGN
import TokenType.COLON
import TokenType.COMMA
import TokenType.CPAREN
import TokenType.DIV
import TokenType.ID
import TokenType.LET
import TokenType.MINUS
import TokenType.MODULE
import TokenType.MUL
import TokenType.NUMBER
import TokenType.OPAREN
import TokenType.PLUS
import TokenType.SEMICOLON
import TokenType.STRING
import TokenType.TYPE
import astbuilder.ASTBuilderFailure
import astbuilder.ASTBuilderSuccess
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParserTest {
    @Test
    fun parseDeclareEmptyNumberVariable() {
        val parser = Parser()
        val token =
            listOf(
                Token(LET.toString(), position = Position(start = 0, end = 3), value = "let"),
                Token(ID.toString(), position = Position(start = 4, end = 5), value = "a"),
                Token(COLON.toString(), position = Position(start = 5, end = 6), value = ":"),
                Token(type = TYPE.toString(), position = Position(start = 7, end = 13), value = "number"),
                Token(type = SEMICOLON.toString(), position = Position(start = 13, end = 14), value = ";"),
            )
        val result =
            ASTBuilderSuccess(
                astNode =
                    VariableDeclaration(
                        declarations =
                            listOf(
                                VariableDeclarator(
                                    id = Identifier(name = "a", start = 4, end = 5),
                                    type = TypeReference(type = "number", start = 7, end = 13),
                                    init = null,
                                    start = 4,
                                    end = 13,
                                ),
                            ),
                        kind = "let",
                        start = 0,
                        end = 14,
                    ),
            )
        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parseDeclareEmptyStringVariable() {
        val parser = Parser()
        val token =
            listOf(
                Token(LET.toString(), position = Position(start = 0, end = 3), value = "let"),
                Token(ID.toString(), position = Position(start = 4, end = 5), value = "a"),
                Token(COLON.toString(), position = Position(start = 5, end = 6), value = ":"),
                Token(type = TYPE.toString(), position = Position(start = 7, end = 13), value = "string"),
                Token(type = SEMICOLON.toString(), position = Position(start = 13, end = 14), value = ";"),
            )
        val result =
            ASTBuilderSuccess(
                astNode =
                    VariableDeclaration(
                        declarations =
                            listOf(
                                VariableDeclarator(
                                    id = Identifier(name = "a", start = 4, end = 5),
                                    type = TypeReference(type = "string", start = 7, end = 13),
                                    init = null,
                                    start = 4,
                                    end = 13,
                                ),
                            ),
                        kind = "let",
                        start = 0,
                        end = 14,
                    ),
            )
        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parseDeclareStringVariable() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = LET.toString(), position = Position(start = 0, end = 3), value = "let"),
                Token(type = ID.toString(), position = Position(start = 4, end = 5), value = "a"),
                Token(type = COLON.toString(), position = Position(start = 5, end = 6), value = ":"),
                Token(type = TYPE.toString(), position = Position(start = 7, end = 13), value = "string"),
                Token(type = ASSIGN.toString(), position = Position(start = 14, end = 15), value = "="),
                Token(type = STRING.toString(), position = Position(start = 16, end = 17), value = "A"),
                Token(type = SEMICOLON.toString(), position = Position(start = 17, end = 18), value = ";"),
            )
        val result =
            ASTBuilderSuccess(
                astNode =
                    VariableDeclaration(
                        declarations =
                            listOf(
                                VariableDeclarator(
                                    id = Identifier(name = "a", start = 4, end = 5),
                                    type = TypeReference(type = "string", start = 7, end = 13),
                                    init = StringLiteral(value = "A", start = 16, end = 17),
                                    start = 4,
                                    end = 17,
                                ),
                            ),
                        kind = "let",
                        start = 0,
                        end = 18,
                    ),
            )
        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parseMultiplicationOperation() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = ID.toString(), position = Position(start = 0, end = 1), value = "a"),
                Token(type = ASSIGN.toString(), position = Position(start = 2, end = 3), value = "="),
                Token(type = NUMBER.toString(), position = Position(start = 4, end = 5), value = "5"),
                Token(type = MUL.toString(), position = Position(start = 6, end = 7), value = "*"),
                Token(type = NUMBER.toString(), position = Position(start = 8, end = 9), value = "5"),
                Token(type = SEMICOLON.toString(), position = Position(start = 9, end = 10), value = ";"),
            )
        val result =
            ASTBuilderSuccess(
                astNode =
                    ExpressionStatement(
                        expression =
                            AssigmentExpression(
                                left =
                                    Identifier(
                                        name = "a",
                                        start = 0,
                                        end = 1,
                                    ),
                                right =
                                    BinaryExpression(
                                        left = NumberLiteral(value = 5.toBigDecimal(), start = 4, end = 5),
                                        right = NumberLiteral(value = 5.toBigDecimal(), start = 8, end = 9),
                                        operator = "*",
                                        start = 4,
                                        end = 9,
                                    ),
                                start = 0,
                                end = 9,
                            ),
                        start = 0,
                        end = 10,
                    ),
            )
        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parseDivisionOperation() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = ID.toString(), position = Position(start = 0, end = 1), value = "a"),
                Token(type = ASSIGN.toString(), position = Position(start = 2, end = 3), value = "="),
                Token(type = NUMBER.toString(), position = Position(start = 4, end = 5), value = "5"),
                Token(type = DIV.toString(), position = Position(start = 6, end = 7), value = "/"),
                Token(type = NUMBER.toString(), position = Position(start = 8, end = 9), value = "5"),
                Token(type = SEMICOLON.toString(), position = Position(start = 9, end = 10), value = ";"),
            )
        val result =
            ASTBuilderSuccess(
                astNode =
                    ExpressionStatement(
                        expression =
                            AssigmentExpression(
                                left =
                                    Identifier(
                                        name = "a",
                                        start = 0,
                                        end = 1,
                                    ),
                                right =
                                    BinaryExpression(
                                        left = NumberLiteral(value = 5.toBigDecimal(), start = 4, end = 5),
                                        right = NumberLiteral(value = 5.toBigDecimal(), start = 8, end = 9),
                                        operator = "/",
                                        start = 4,
                                        end = 9,
                                    ),
                                start = 0,
                                end = 9,
                            ),
                        start = 0,
                        end = 10,
                    ),
            )
        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parsePlusOperationInExpressionStatement() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = ID.toString(), position = Position(start = 0, end = 1), value = "a"),
                Token(type = ASSIGN.toString(), position = Position(start = 2, end = 3), value = "="),
                Token(type = NUMBER.toString(), position = Position(start = 4, end = 5), value = "5"),
                Token(type = PLUS.toString(), position = Position(start = 6, end = 7), value = "+"),
                Token(type = NUMBER.toString(), position = Position(start = 8, end = 9), value = "5"),
                Token(type = SEMICOLON.toString(), position = Position(start = 9, end = 10), value = ";"),
            )
        val result =
            ASTBuilderSuccess(
                astNode =
                    ExpressionStatement(
                        expression =
                            AssigmentExpression(
                                left =
                                    Identifier(
                                        name = "a",
                                        start = 0,
                                        end = 1,
                                    ),
                                right =
                                    BinaryExpression(
                                        left = NumberLiteral(value = 5.toBigDecimal(), start = 4, end = 5),
                                        right = NumberLiteral(value = 5.toBigDecimal(), start = 8, end = 9),
                                        operator = "+",
                                        start = 4,
                                        end = 9,
                                    ),
                                start = 0,
                                end = 9,
                            ),
                        start = 0,
                        end = 10,
                    ),
            )

        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parseMinusOperationInExpressionStatement() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = ID.toString(), position = Position(start = 0, end = 1), value = "a"),
                Token(type = ASSIGN.toString(), position = Position(start = 2, end = 3), value = "="),
                Token(type = NUMBER.toString(), position = Position(start = 4, end = 5), value = "5"),
                Token(type = MINUS.toString(), position = Position(start = 6, end = 7), value = "-"),
                Token(type = NUMBER.toString(), position = Position(start = 8, end = 9), value = "5"),
                Token(type = SEMICOLON.toString(), position = Position(start = 9, end = 10), value = ";"),
            )
        val result =
            ASTBuilderSuccess(
                astNode =
                    ExpressionStatement(
                        expression =
                            AssigmentExpression(
                                left =
                                    Identifier(
                                        name = "a",
                                        start = 0,
                                        end = 1,
                                    ),
                                right =
                                    BinaryExpression(
                                        left = NumberLiteral(value = 5.toBigDecimal(), start = 4, end = 5),
                                        right = NumberLiteral(value = 5.toBigDecimal(), start = 8, end = 9),
                                        operator = "-",
                                        start = 4,
                                        end = 9,
                                    ),
                                start = 0,
                                end = 9,
                            ),
                        start = 0,
                        end = 10,
                    ),
            )
        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parseBinaryExpressionWithAllOperatorsInStatementExpression() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = ID.toString(), position = Position(start = 0, end = 1), value = "a"),
                Token(type = ASSIGN.toString(), position = Position(start = 2, end = 3), value = "="),
                Token(type = NUMBER.toString(), position = Position(start = 4, end = 5), value = "1"),
                Token(type = PLUS.toString(), position = Position(start = 6, end = 7), value = "+"),
                Token(type = NUMBER.toString(), position = Position(start = 8, end = 9), value = "2"),
                Token(type = MUL.toString(), position = Position(start = 10, end = 11), value = "*"),
                Token(type = NUMBER.toString(), position = Position(start = 12, end = 13), value = "3"),
                Token(type = DIV.toString(), position = Position(start = 14, end = 15), value = "/"),
                Token(type = NUMBER.toString(), position = Position(start = 16, end = 17), value = "4"),
                Token(type = MINUS.toString(), position = Position(start = 17, end = 18), value = "-"),
                Token(type = NUMBER.toString(), position = Position(start = 19, end = 20), value = "5"),
                Token(type = MODULE.toString(), position = Position(start = 21, end = 22), value = "%"),
                Token(type = NUMBER.toString(), position = Position(start = 23, end = 24), value = "6"),
                Token(type = SEMICOLON.toString(), position = Position(start = 24, end = 25), value = ";"),
            )
        val result =
            ASTBuilderSuccess(
                astNode =
                    ExpressionStatement(
                        expression =
                            AssigmentExpression(
                                left = Identifier(name = "a", start = 0, end = 1),
                                right =
                                    BinaryExpression(
                                        left =
                                            BinaryExpression(
                                                left = NumberLiteral(value = 1.toBigDecimal(), start = 4, end = 5),
                                                right =
                                                    BinaryExpression(
                                                        left =
                                                            BinaryExpression(
                                                                left = NumberLiteral(value = 2.toBigDecimal(), start = 8, end = 9),
                                                                right = NumberLiteral(value = 3.toBigDecimal(), start = 12, end = 13),
                                                                operator = "*",
                                                                start = 8,
                                                                end = 13,
                                                            ),
                                                        right = NumberLiteral(value = 4.toBigDecimal(), start = 16, end = 17),
                                                        operator = "/",
                                                        start = 8,
                                                        end = 17,
                                                    ),
                                                operator = "+",
                                                start = 4,
                                                end = 17,
                                            ),
                                        right =
                                            BinaryExpression(
                                                left = NumberLiteral(value = 5.toBigDecimal(), start = 19, end = 20),
                                                right = NumberLiteral(value = 6.toBigDecimal(), start = 23, end = 24),
                                                operator = "%",
                                                start = 19,
                                                end = 24,
                                            ),
                                        operator = "-",
                                        start = 4,
                                        end = 24,
                                    ),
                                start = 0,
                                end = 24,
                            ),
                        start = 0,
                        end = 25,
                    ),
            )

        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parseBinaryExpressionsWithFunctionIdentifierAndNumbersInStatementExpression() {
        val parser = Parser()
        // function(1 + 2) * a / (4 + 5)
        val token =
            listOf(
                Token(type = ID.toString(), position = Position(start = 0, end = 8), value = "function"),
                Token(type = OPAREN.toString(), position = Position(start = 8, end = 9), value = "("),
                Token(type = NUMBER.toString(), position = Position(start = 9, end = 10), value = "1"),
                Token(type = PLUS.toString(), position = Position(start = 11, end = 12), value = "+"),
                Token(type = NUMBER.toString(), position = Position(start = 13, end = 14), value = "2"),
                Token(type = CPAREN.toString(), position = Position(start = 14, end = 15), value = ")"),
                Token(type = MUL.toString(), position = Position(start = 16, end = 17), value = "*"),
                Token(type = ID.toString(), position = Position(start = 18, end = 19), value = "a"),
                Token(type = DIV.toString(), position = Position(start = 20, end = 21), value = "/"),
                Token(type = OPAREN.toString(), position = Position(start = 22, end = 23), value = "("),
                Token(type = NUMBER.toString(), position = Position(start = 23, end = 24), value = "4"),
                Token(type = PLUS.toString(), position = Position(start = 25, end = 26), value = "-"),
                Token(type = NUMBER.toString(), position = Position(start = 27, end = 28), value = "5"),
                Token(type = CPAREN.toString(), position = Position(start = 28, end = 29), value = ")"),
                Token(type = SEMICOLON.toString(), position = Position(start = 29, end = 30), value = ";"),
            )
        val result =
            ASTBuilderSuccess(
                astNode =
                    ExpressionStatement(
                        expression =
                            BinaryExpression(
                                left =
                                    BinaryExpression(
                                        left =
                                            CallExpression(
                                                callee = Identifier(name = "function", start = 0, end = 8),
                                                arguments =
                                                    listOf(
                                                        BinaryExpression(
                                                            left = NumberLiteral(value = 1.toBigDecimal(), start = 9, end = 10),
                                                            right = NumberLiteral(value = 2.toBigDecimal(), start = 13, end = 14),
                                                            operator = "+",
                                                            start = 9,
                                                            end = 14,
                                                        ),
                                                    ),
                                                start = 0,
                                                end = 15,
                                            ),
                                        right = Identifier(name = "a", start = 18, end = 19),
                                        operator = "*",
                                        start = 0,
                                        end = 19,
                                    ),
                                right =
                                    BinaryExpression(
                                        left = NumberLiteral(value = 4.toBigDecimal(), start = 23, end = 24),
                                        right = NumberLiteral(value = 5.toBigDecimal(), start = 27, end = 28),
                                        operator = "-",
                                        start = 23,
                                        end = 28,
                                    ),
                                operator = "/",
                                start = 0,
                                end = 28,
                            ),
                        start = 0,
                        end = 30,
                    ),
            )
        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parseBinaryExpressionWithStringAndNumberInStatementExpression() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = STRING.toString(), position = Position(start = 0, end = 13), value = "Hello world"),
                Token(type = PLUS.toString(), position = Position(start = 14, end = 15), value = "+"),
                Token(type = NUMBER.toString(), position = Position(start = 16, end = 17), value = "5"),
                Token(type = SEMICOLON.toString(), position = Position(start = 17, end = 18), value = ";"),
            )
        val result =
            ASTBuilderSuccess(
                astNode =
                    ExpressionStatement(
                        expression =
                            BinaryExpression(
                                left = StringLiteral(value = "Hello world", start = 0, end = 13),
                                right = NumberLiteral(value = 5.toBigDecimal(), start = 16, end = 17),
                                operator = "+",
                                start = 0,
                                end = 17,
                            ),
                        start = 0,
                        end = 18,
                    ),
            )
        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parseBinaryExpressionWithMismatchedParenthesisInStatementExpressionReturnsFailure() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = OPAREN.toString(), position = Position(start = 0, end = 1), value = "("),
                Token(type = NUMBER.toString(), position = Position(start = 1, end = 2), value = "1"),
                Token(type = PLUS.toString(), position = Position(start = 3, end = 4), value = "+"),
                Token(type = NUMBER.toString(), position = Position(start = 5, end = 6), value = "2"),
                Token(type = CPAREN.toString(), position = Position(start = 6, end = 7), value = ")"),
                Token(type = CPAREN.toString(), position = Position(start = 7, end = 8), value = ")"),
                Token(type = SEMICOLON.toString(), position = Position(start = 8, end = 9), value = ";"),
            )
        val result = parser.parse(token)
        assert(result is ASTBuilderFailure && result.errorMessage.contains("Mismatched parenthesis"))
    }

    @Test
    fun parseDeclareNumberVariable() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = LET.toString(), position = Position(start = 0, end = 3), value = "let"),
                Token(type = ID.toString(), position = Position(start = 4, end = 5), value = "a"),
                Token(type = COLON.toString(), position = Position(start = 5, end = 6), value = ":"),
                Token(type = TYPE.toString(), position = Position(start = 7, end = 13), value = "string"),
                Token(type = ASSIGN.toString(), position = Position(start = 14, end = 15), value = "="),
                Token(type = NUMBER.toString(), position = Position(start = 16, end = 17), value = "5"),
                Token(type = SEMICOLON.toString(), position = Position(start = 17, end = 18), value = ";"),
            )
        val result =
            ASTBuilderSuccess(
                astNode =
                    VariableDeclaration(
                        declarations =
                            listOf(
                                VariableDeclarator(
                                    id = Identifier(name = "a", start = 4, end = 5),
                                    type = TypeReference(type = "string", start = 7, end = 13),
                                    init = NumberLiteral(value = 5.toBigDecimal(), start = 16, end = 17),
                                    start = 4,
                                    end = 17,
                                ),
                            ),
                        kind = "let",
                        start = 0,
                        end = 18,
                    ),
            )
        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parsePrintFunctionWithString() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = ID.toString(), position = Position(start = 0, end = 5), value = "print"),
                Token(type = OPAREN.toString(), position = Position(start = 5, end = 6), value = "("),
                Token(type = STRING.toString(), position = Position(start = 6, end = 19), value = "Hello world"),
                Token(type = CPAREN.toString(), position = Position(start = 19, end = 20), value = ")"),
                Token(type = SEMICOLON.toString(), position = Position(start = 20, end = 21), value = ";"),
            )
        val result =
            ASTBuilderSuccess(
                astNode =
                    ExpressionStatement(
                        expression =
                            CallExpression(
                                callee =
                                    Identifier(
                                        name = "print",
                                        start = 0,
                                        end = 5,
                                    ),
                                arguments = listOf(StringLiteral(value = "Hello world", start = 6, end = 19)),
                                start = 0,
                                end = 20,
                            ),
                        start = 0,
                        end = 21,
                    ),
            )
        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parseCallExpressionWithMultipleArguments() {
        val parser = Parser()
        // function(1,2,3,4);
        val token = listOf(
            Token(type=ID.toString(), position=Position(start=0, end=8), value="function"),
            Token(type=OPAREN.toString(), position=Position(start=8, end=9), value="("),
            Token(type=NUMBER.toString(), position=Position(start=9, end=10), value="1"),
            Token(type=COMMA.toString(), position=Position(start=10, end=11), value=","),
            Token(type=NUMBER.toString(), position=Position(start=11, end=12), value="2"),
            Token(type=COMMA.toString(), position=Position(start=12, end=13), value=","),
            Token(type=NUMBER.toString(), position=Position(start=13, end=14), value="3"),
            Token(type=COMMA.toString(), position=Position(start=14, end=15), value=","),
            Token(type=NUMBER.toString(), position=Position(start=15, end=16), value="4"),
            Token(type=CPAREN.toString(), position=Position(start=16, end=17), value=")"),
            Token(type=SEMICOLON.toString(), position=Position(start=17, end=18), value=";"),
        )
        val result = ASTBuilderSuccess(
            astNode=ExpressionStatement(
                expression=CallExpression(
                    callee=Identifier(name="function", start=0, end=8),
                    arguments=listOf(
                        NumberLiteral(value=1.toBigDecimal(), start=9, end=10),
                        NumberLiteral(value=2.toBigDecimal(), start=11, end=12),
                        NumberLiteral(value=3.toBigDecimal(), start=13, end=14),
                        NumberLiteral(value=4.toBigDecimal(), start=15, end=16),
                    ),
                    start=0,
                    end=17,
                ),
                start=0,
                end=18,
            ),
        )
        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parsePrintFunctionWithNumber() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = ID.toString(), position = Position(start = 0, end = 5), value = "print"),
                Token(type = OPAREN.toString(), position = Position(start = 5, end = 6), value = "("),
                Token(type = NUMBER.toString(), position = Position(start = 6, end = 7), value = "1"),
                Token(type = CPAREN.toString(), position = Position(start = 7, end = 8), value = ")"),
                Token(type = SEMICOLON.toString(), position = Position(start = 8, end = 9), value = ";"),
            )
        val result =
            ASTBuilderSuccess(
                astNode =
                    ExpressionStatement(
                        expression =
                            CallExpression(
                                callee =
                                    Identifier(
                                        name = "print",
                                        start = 0,
                                        end = 5,
                                    ),
                                arguments = listOf(NumberLiteral(value = 1.toBigDecimal(), start = 6, end = 7)),
                                start = 0,
                                end = 8,
                            ),
                        start = 0,
                        end = 9,
                    ),
            )
        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parseMultipleVariableDeclaration() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = LET.toString(), position = Position(start = 0, end = 3), value = "let"),
                Token(type = ID.toString(), position = Position(start = 4, end = 5), value = "a"),
                Token(type = COLON.toString(), position = Position(start = 5, end = 6), value = ":"),
                Token(type = TYPE.toString(), position = Position(start = 7, end = 13), value = "number"),
                Token(type = ASSIGN.toString(), position = Position(start = 14, end = 15), value = "="),
                Token(type = NUMBER.toString(), position = Position(start = 16, end = 17), value = "5"),
                Token(type = COMMA.toString(), position = Position(start = 17, end = 18), value = ","),
                Token(type = ID.toString(), position = Position(start = 19, end = 20), value = "b"),
                Token(type = COLON.toString(), position = Position(start = 20, end = 21), value = ":"),
                Token(type = TYPE.toString(), position = Position(start = 22, end = 28), value = "number"),
                Token(type = ASSIGN.toString(), position = Position(start = 29, end = 30), value = "="),
                Token(type = NUMBER.toString(), position = Position(start = 31, end = 33), value = "10"),
                Token(type = SEMICOLON.toString(), position = Position(start = 33, end = 34), value = ";"),
            )
        val result =
            ASTBuilderSuccess(
                astNode =
                    VariableDeclaration(
                        declarations =
                            listOf(
                                VariableDeclarator(
                                    id =
                                        Identifier(
                                            name = "a",
                                            start = 4,
                                            end = 5,
                                        ),
                                    type = TypeReference(type = "number", start = 7, end = 13),
                                    init = NumberLiteral(value = 5.toBigDecimal(), start = 16, end = 17),
                                    start = 4,
                                    end = 17,
                                ),
                                VariableDeclarator(
                                    id = Identifier(name = "b", start = 19, end = 20),
                                    type = TypeReference(type = "number", start = 22, end = 28),
                                    init = NumberLiteral(value = 10.toBigDecimal(), start = 31, end = 33),
                                    start = 19,
                                    end = 33,
                                ),
                            ),
                        kind = "let",
                        start = 0,
                        end = 34,
                    ),
            )
        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parseDeclareBinaryOperation() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = LET.toString(), position = Position(start = 0, end = 3), value = "let"),
                Token(type = ID.toString(), position = Position(start = 4, end = 5), value = "a"),
                Token(type = COLON.toString(), position = Position(start = 5, end = 6), value = ":"),
                Token(type = TYPE.toString(), position = Position(start = 7, end = 13), value = "number"),
                Token(type = ASSIGN.toString(), position = Position(start = 14, end = 15), value = "="),
                Token(type = NUMBER.toString(), position = Position(start = 16, end = 17), value = "5"),
                Token(type = PLUS.toString(), position = Position(start = 18, end = 19), value = "+"),
                Token(type = NUMBER.toString(), position = Position(start = 20, end = 21), value = "5"),
                Token(type = SEMICOLON.toString(), position = Position(start = 21, end = 22), value = ";"),
            )
        val result =
            ASTBuilderSuccess(
                astNode =
                    VariableDeclaration(
                        declarations =
                            listOf(
                                VariableDeclarator(
                                    id =
                                        Identifier(
                                            name = "a",
                                            start = 4,
                                            end = 5,
                                        ),
                                    type = TypeReference(type = "number", start = 7, end = 13),
                                    init =
                                        BinaryExpression(
                                            left = NumberLiteral(value = 5.toBigDecimal(), start = 16, end = 17),
                                            right = NumberLiteral(value = 5.toBigDecimal(), start = 20, end = 21),
                                            operator = "+",
                                            start = 16,
                                            end = 21,
                                        ),
                                    start = 4,
                                    end = 21,
                                ),
                            ),
                        kind = "let",
                        start = 0,
                        end = 22,
                    ),
            )
        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parseErrorForMissingSemicolon() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = LET.toString(), position = Position(start = 0, end = 3), value = "let"),
                Token(type = ID.toString(), position = Position(start = 4, end = 5), value = "a"),
                Token(type = COLON.toString(), position = Position(start = 5, end = 6), value = ":"),
                Token(type = TYPE.toString(), position = Position(start = 7, end = 13), value = "number"),
                Token(type = ASSIGN.toString(), position = Position(start = 14, end = 15), value = "="),
                Token(type = NUMBER.toString(), position = Position(start = 16, end = 17), value = "5"),
            )
        val result =
            ASTBuilderFailure(
                errorMessage =
                    "No valid statement found, errors: \nMissing semicolon at variable declaration\n" +
                        "Missing semicolon at expression statement\n",
            )
        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parseErrorForExtraSemicolon() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = LET.toString(), position = Position(start = 0, end = 3), value = "let"),
                Token(type = ID.toString(), position = Position(start = 4, end = 5), value = "a"),
                Token(type = COLON.toString(), position = Position(start = 5, end = 6), value = ":"),
                Token(type = TYPE.toString(), position = Position(start = 7, end = 13), value = "number"),
                Token(type = SEMICOLON.toString(), position = Position(start = 13, end = 14), value = ";"),
                Token(type = SEMICOLON.toString(), position = Position(start = 14, end = 15), value = ";"),
            )
        try {
            parser.parse(token)
        } catch (e: IllegalStateException) {
            assertEquals("Only one line of code is allowed at a time.", e.message)
        }
    }

    @Test
    fun parseErrorForMissingColonInDeclaration() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = LET.toString(), position = Position(start = 0, end = 3), value = "let"),
                Token(type = ID.toString(), position = Position(start = 4, end = 5), value = "a"),
                Token(type = TYPE.toString(), position = Position(start = 7, end = 13), value = "number"),
                Token(type = ASSIGN.toString(), position = Position(start = 14, end = 15), value = "="),
                Token(type = NUMBER.toString(), position = Position(start = 16, end = 17), value = "a"),
                Token(type = SEMICOLON.toString(), position = Position(start = 17, end = 18), value = ";"),
            )
        val result = parser.parse(token)
        assert(result is ASTBuilderFailure && result.errorMessage.contains("Missing colon"))
    }

    @Test
    fun parseErrorForMultipleColonInDeclaration() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = LET.toString(), position = Position(start = 0, end = 3), value = "let"),
                Token(type = ID.toString(), position = Position(start = 4, end = 5), value = "a"),
                Token(type = COLON.toString(), position = Position(start = 5, end = 6), value = ":"),
                Token(type = COLON.toString(), position = Position(start = 7, end = 8), value = ":"),
                Token(type = TYPE.toString(), position = Position(start = 9, end = 15), value = "number"),
                Token(type = SEMICOLON.toString(), position = Position(start = 15, end = 16), value = ";"),
            )
        val result = parser.parse(token)
        assert(result is ASTBuilderFailure && result.errorMessage.contains("Invalid type"))
    }

    @Test
    fun parseErrorForInvalidStatementDeclaration() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = ID.toString(), position = Position(start = 0, end = 1), value = "a"),
                Token(type = COLON.toString(), position = Position(start = 1, end = 2), value = ":"),
                Token(type = TYPE.toString(), position = Position(start = 3, end = 9), value = "number"),
                Token(type = SEMICOLON.toString(), position = Position(start = 9, end = 10), value = ";"),
            )
        val result = parser.parse(token)
        assert(result is ASTBuilderFailure && result.errorMessage.contains("Invalid start of variable declaration"))
    }

    @Test
    fun parseErrorForEmptyTokens() {
        val parser = Parser()
        val token = listOf<Token>()
        val result =
            ASTBuilderFailure(
                errorMessage =
                    "No valid statement found, errors: " +
                        "\nEmpty tokens\nEmpty tokens\n",
            )
        assertEquals(result, parser.parse(token))
    }

    @Test
    fun parseErrorExtraComasInDeclaration() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = LET.toString(), position = Position(start = 0, end = 3), value = "let"),
                Token(type = ID.toString(), position = Position(start = 4, end = 5), value = "a"),
                Token(type = COLON.toString(), position = Position(start = 5, end = 6), value = ":"),
                Token(type = TYPE.toString(), position = Position(start = 7, end = 13), value = "number"),
                Token(type = COMMA.toString(), position = Position(start = 13, end = 14), value = ","),
                Token(type = COMMA.toString(), position = Position(start = 14, end = 15), value = ","),
                Token(type = SEMICOLON.toString(), position = Position(start = 15, end = 16), value = ";"),
            )
        val result = parser.parse(token)
        assert(result is ASTBuilderFailure && result.errorMessage.contains("Not enough tokens for a variable declarator"))
    }

    @Test
    fun parseErrorFewerComasInDeclaration() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = LET.toString(), position = Position(start = 0, end = 3), value = "let"),
                Token(type = ID.toString(), position = Position(start = 4, end = 5), value = "a"),
                Token(type = COLON.toString(), position = Position(start = 5, end = 6), value = ":"),
                Token(type = TYPE.toString(), position = Position(start = 7, end = 13), value = "number"),
                Token(type = ASSIGN.toString(), position = Position(start = 14, end = 15), value = "="),
                Token(type = NUMBER.toString(), position = Position(start = 16, end = 17), value = "5"),
                Token(type = ID.toString(), position = Position(start = 18, end = 19), value = "b"),
                Token(type = COLON.toString(), position = Position(start = 19, end = 20), value = ":"),
                Token(type = TYPE.toString(), position = Position(start = 21, end = 27), value = "number"),
                Token(type = ASSIGN.toString(), position = Position(start = 28, end = 29), value = "="),
                Token(type = NUMBER.toString(), position = Position(start = 30, end = 32), value = "10"),
                Token(type = SEMICOLON.toString(), position = Position(start = 32, end = 33), value = ";"),
            )
        val result = parser.parse(token)
        assert(result is ASTBuilderFailure && result.errorMessage.contains("Invalid declarator: No valid assignable expression found"))
    }

    @Test
    fun parseErrorMissingCloseParenthesisInPrintFunction() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = ID.toString(), position = Position(start = 0, end = 5), value = "print"),
                Token(type = OPAREN.toString(), position = Position(start = 5, end = 6), value = "("),
                Token(type = STRING.toString(), position = Position(start = 6, end = 19), value = "Hello world"),
                Token(type = SEMICOLON.toString(), position = Position(start = 19, end = 20), value = ";"),
            )
        val result = parser.parse(token)
        assert(result is ASTBuilderFailure && result.errorMessage.contains("Call expression does not have close parenthesis"))
    }

    @Test
    fun parseErrorMissingOpenParenthesisInPrintFunction() {
        val parser = Parser()
        val token =
            listOf(
                Token(type = ID.toString(), position = Position(start = 0, end = 5), value = "print"),
                Token(type = STRING.toString(), position = Position(start = 6, end = 19), value = "Hello world"),
                Token(type = CPAREN.toString(), position = Position(start = 19, end = 20), value = ")"),
                Token(type = SEMICOLON.toString(), position = Position(start = 20, end = 21), value = ";"),
            )
        val result = parser.parse(token)
        assert(result is ASTBuilderFailure && result.errorMessage.contains("Call expression does not have open parenthesis"))
    }
}