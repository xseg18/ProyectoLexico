Para crear la solucion del proyecto, primero se diseñaron las especificaciones léxicas en el estado inicial del lenguaje chilero, basandose en los tokens descritos en el archivo TokenConstants.
Sse tomaron en cuenta las reglas del lenguaje Cool dentro de su manual de usuario, donde indica que todas sus palabras reservadas son case insensitive, lo cual también se aplicó para sus contrapartes en español. No se realizó una traducción como tal, sino que al momento de definir las especificaciones se hicieron tanto en español como en ingles bajo la misma regla. Todas las reglas se realizaron bajo la misma sintaxis de [letraMayuscula,letraMinuscula] con ligeras variaciones dependiendo de la palabra reservada por motivos de simplificación. Después se continuaron con los símbolos u operadores validados por el lenguaje y por último las constantes de tipo int, bool o string que se agregaron a sus respectivas AbstractTables.

Al empezar con los strings, y según el manual de Jflex para realizar una solución correcta, se creó el estado adicional "String", el cual iniciaría al encontrar una comilla doble dentro del programa. El estado se dividió en secciones según: caracter nulo, caracter comilla doble, enter, escaped enter y caracteres aceptados. En cada uno de estos "sub-estados" se desarrolla cada una de las situaciones a suceder en caso encontrar los caracteres descritos. Para el caracter nulo y en caso de que dentro del string hubieran más de 1025 caracteres, se creo el estado StringRecover, el cual hace que el analizador léxico continue consumiendo información a pesar de caer en error, según la documentación del proyecto. Si se encontraban carácteres aceptados se agregaban a la varible string_buff el cual llevaba el registro de todo el contenido del string.

Al terminar con esto se crearon los estados para los comentarios:
- CommentP, que se encargaba de los comentarios con (* 
- CommentM que miraba por los comentarios con --. 

Ambos omiten el contenido dentro o despues de estos simbolos de definicióny retornaba al estado incial, al encontrar enter en el caso de CommentM, o al encontrar todos los parentesis de cierre y apertura con apoyo de una variable auxiliar llamada "counter" en CommentP. Al momento de encontrar un parentesis de cierre fuera del estado CommentP (es decir en el estado inicial) este autómaticamente devuelve Error como "Unmatched parenthesis".

Los errores de EOF dentro de comentarios o strings, se manejaron dentro de la configuración "eofval" donde por medio de un switch case de los estados disponibles, se dispuso que, primero se regresará al estado incial para seguir consumiendo información y que no se quedara dentro del error, y despues, retornaría el mensaje de error adecuado según el estado donde se encontró, para que por último retornara el token constant de EOF.

Del mismo modo, para probar el correcto funcionamiento de Lexer.java, dentro de App.java se realizó la implementación individual de un lector de pruebas unitarias y de archivos para la terminal con PicoCLI basandose en el código del Laboratorio #5.

Después de completar la creación de jflex para Chilero y el lector de pruebas unitarias y de archivos, se empezaron a ejecutar las pruebas con test.cl el cúal contenía 3 errores: 
1. caracteres no definidos "[]"
2. caracter no definido ">"
3. EOF in comment.

Para corregir estos errores, primero se debía identificar que función se suponía tenían que realizar los símbolos no válidos y completar el comentario faltante.
Los símbolos "[]" estaban siendo utilizados para un array de tipo númerico. Se reemplazaron por parentesis, siguiendo la documentación de cool para referirse a un objeto.
El operador ">" se utilizaba para comparar que la variable countdown fuera mayor a 0 dentro de un ciclo while. Para solucionar el fallo se refactorizó la condición del ciclo para que validara si cero era menor a countdown.
Por último, en la línea 93 se cerró el comentario inciado.
Al correr nuevamente el analizador no se encontró ningún error y se obtuvo la salida esperada al correr el programa.

Dentro de las pruebas también se realizaron pruebas unitarias tanto en inglés como en español y lectura de archivos aparte de los ya incluidos en el directorio cooltests. Comprobando nuevamente el buen funcionamiento del analizador léxico.

Para culminar con la solución y después de terminar con todas las pruebas y verificación de errores, se unió el analizador léxico creado junto con el compilador de coolc por medio del comando mycoolc.
