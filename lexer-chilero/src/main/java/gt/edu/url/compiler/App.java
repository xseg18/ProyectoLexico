package gt.edu.url.compiler;

import java_cup.runtime.Symbol;
import picocli.CommandLine;
import gt.edu.url.compiler.Utilities;
import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "lexer", mixinStandardHelpOptions = true, version = "0.0.1",
        description = "IdLexer")
public class App implements Callable<Integer>
{
    @CommandLine.Option(names = {"-f","--file"}, description = "File to read", required = false )
    private File f;

    @Override
    public Integer call() throws Exception {
        if(f != null){
            BufferedReader bf = Files.newBufferedReader(f.toPath());
            CoolLexer lex = new CoolLexer(bf);
            lex.set_filename(f.getName());
            Symbol s = lex.next_token();
            while(s.sym != TokenConstants.EOF){
                    Utilities.dumpToken(System.out, lex.get_curr_lineno(), s);
                    s = lex.next_token();
            }
        }
        else{
            Scanner scan = new Scanner(System.in);
            String entrada = "";
            while(!entrada.equals("exit")){
                System.out.print("Ingrese cadena: ");
                entrada = scan.nextLine();
                if(entrada.equals("exit")){
                    break;
                }
                CoolLexer lex = new CoolLexer(new StringReader(entrada));
                Symbol s = lex.next_token();
                Utilities.dumpToken(System.out, lex.get_curr_lineno(), s);
            }
        }
        return 0;
    }
    public static void main (String[] args){
        int exitcode = new CommandLine(new App()).execute(args);
        System.exit(exitcode);
    }
}
