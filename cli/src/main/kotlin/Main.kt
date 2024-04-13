import utils.PrintScriptLineReader

fun main() {
    println("\nReading from string\n")
    val input =
        """
        const a:bool = true;
        let b:int;
        if(a){
            b = 2;
        } else {
            b = 3;
        }
        """.trimIndent()
    val printScriptLineReader = PrintScriptLineReader()
    val lines = printScriptLineReader.readLinesFromString(input)
    performFromLines(lines)
//    println("\nReading from file\n")
//    val fileLines = printScriptLineReader.readLinesFromFile("app/src/main/resources/script_example.txt")
//    performFromLines(fileLines)
}

private fun performFromLines(fileLines: List<String>) {
    var interpreter = InterpreterImpl()
    for ((index, line) in fileLines.withIndex()) {
        println(line + "\n")
        println("Lexer output")
        val lexer = Lexer(line, 0, "utils/src/main/resources/tokenRegex.json")
        val tokens = lexer.tokenize()
        tokens.forEach { println(it) }
        val parser = Parser()
        val ast = parser.parse(tokens, index)
        println("\nParser Output")
        println("$ast\n")
//        if (ast is ASTBuilderSuccess) {
//            try {
//                interpreter = interpreter.interpret(ast.astNode)
//            } catch (e: Exception) {
//                println(e.message)
//            }
//        }
    }
}
