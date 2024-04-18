import astbuilder.ASTBuilderSuccess
import utils.PrintScriptLineReader

fun main() {
    println("\nReading from string\n")
    val input =
        """
        let a:string = "hola";
        """.trimIndent()
    val printScriptLineReader = PrintScriptLineReader()
    // val lines = printScriptLineReader.readLinesFromString(input)
    // performFromLines(lines)
    println("\nReading from file\n")
    val fileLines = printScriptLineReader.readLinesFromFile("cli/src/main/resources/script_example.txt")
    performFromLines(fileLines)
}

private fun performFromLines(fileLines: List<String>) {
    var interpreter = InterpreterImpl()
    for ((index, line) in fileLines.withIndex()) {
        val lexer = Lexer(line, 0, "utils/src/main/resources/tokenRegex.json")
        val tokens = lexer.tokenize()
        tokens.forEach { println(it) }
        val parser = Parser()
        val ast = parser.parse(tokens, index)
        println(ast)
        if (ast is ASTBuilderSuccess) {
            try {
                interpreter = interpreter.interpret(ast.astNode)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }
}
