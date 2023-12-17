package compi1.proyecto1;
 
import java.io.*;
import java_cup.runtime.*;
 
%%
%public
%class lexEx2
%cup
%function next_token

/* Caracteres basicos */
digito = [0-9]
letra = [a-zA-Z]

/* Espacios y saltos de linea */
Whitespace = [ \t\f] | {LineTerminator} 
LineTerminator = \r|\n|\r\n

/* Comentarios */
Comment              = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
TraditionalComment   = "<!" [^!] ~"!>"  |  "<!" "!"+ "!>"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
InputCharacter       = [^\r\n\"]
DocumentationComment = "<!" {CommentContent} "!"+ ">"
CommentContent       = ( [^*] | \*+ [^/*] )*

/* Definicion de conjuntos */
CharacterConj        = [^\x20-\x2F\x3A-\x40\x5B-\x60\x7B-\x7D] | {letra} | {digito}
guion                = "-"
tilde                = "~"
Separador            = {guion} | {tilde}
ItemConj             = {CharacterConj} {Separador} {CharacterConj}

Character = {SQUOTE} {InputCharacter}+ {SQUOTE} | {DQUOTE} {InputCharacter}+ {DQUOTE}


/* Caracteres especiales */
SQUOTE     =   "\'"
DQUOTE     =   "\""
BACKSLASH  =   "\\"



%{
	StringBuffer string = new StringBuffer();
	
	private Symbol symbol(int type){
		return new Symbol(type, yyline, yycolumn);
	}
	private Symbol symbol(int type, Object value){
		return new Symbol(type, yyline, yycolumn, value);
	}
%}

%eofval{
	System.out.println("Token reconocido: EOF" + yytext()); return symbol(ParserSym.EOF);
%eofval}
%%
 
/* Llaves de apertura y cierre */
<YYINITIAL>"{" {System.out.println("Token reconocido: Llave_A" + yytext()); return symbol (ParserSym.Llave_A, yytext());}
<YYINITIAL>"}" {System.out.println("Token reconocido: Llave_C" + yytext()); return symbol (ParserSym.Llave_C, yytext());}


/* Definicion de los conjuntos */
<YYINITIAL> ";"      {System.out.println("Token reconocido: PCOMMA " + yytext()); return symbol (ParserSym.PCOMMA, yytext());}
<YYINITIAL> "CONJ"   {System.out.println("Token reconocido: CONJ " + yytext()); return symbol (ParserSym.CONJ, yytext());}
<YYINITIAL> ":"      {System.out.println("Token reconocido: DOBDOT " + yytext()); return symbol (ParserSym.DOBDOT, yytext());}
<YYINITIAL> "->"     {System.out.println("Token reconocido: FLECH " + yytext()); return symbol (ParserSym.FLECH, yytext());}
<YYINITIAL> {ItemConj} {System.out.println("Token reconocido: RCONJ " + yytext()); return symbol (ParserSym.RCONJ, yytext());}
<YYINITIAL> "," {System.out.println("Token reconocido: COMMA " + yytext()); return symbol (ParserSym.COMMA, yytext());}

/* Definicion de los ID */
<YYINITIAL>{letra}({letra}|{digito})* {System.out.println("Token reconocido: ID " + yytext()); return symbol (ParserSym.ID, yytext());}
<YYINITIAL>{digito}({digito})*        {System.out.println("Token reconocido: DIGITO " + yytext()); return symbol (ParserSym.NUM, yytext());}

/* Expresiones Regulares (OPERACIONES) */
<YYINITIAL>"(" {System.out.println("Token reconocido: PAR_A " + yytext()); return symbol (ParserSym.PAR_A, yytext());}
<YYINITIAL>")" {System.out.println("Token reconocido: PAR_C " + yytext()); return symbol (ParserSym.PAR_C, yytext());}
<YYINITIAL>"." {System.out.println("Token reconocido: DOT " + yytext()); return symbol (ParserSym.DOT, yytext());}
<YYINITIAL>"|" {System.out.println("Token reconocido: PIPE " + yytext()); return symbol (ParserSym.PIPE, yytext());}
<YYINITIAL>"*" {System.out.println("Token reconocido: MULTIPLY " + yytext()); return symbol (ParserSym.MULTIPLY, yytext());}
<YYINITIAL>"+" {System.out.println("Token reconocido: PLUS " + yytext()); return symbol (ParserSym.PLUS, yytext());}
<YYINITIAL>"?" {System.out.println("Token reconocido: INTERROGACION " + yytext()); return symbol (ParserSym.INTERROGACION, yytext());}

<YYINITIAL> {Character} {System.out.println("Token reconocido: CHAR " + yytext()); return symbol (ParserSym.CHAR, yytext());}

/* Expresiones Regulares (Caracteres especiales) */
<YYINITIAL> {BACKSLASH}{SQUOTE} {System.out.println("Token reconocido: ESCSQT " + yytext()); return symbol (ParserSym.ESCSQT, yytext());}
<YYINITIAL> {BACKSLASH}{DQUOTE} {System.out.println("Token reconocido: ESCDQT " + yytext()); return symbol (ParserSym.ESCDQT, yytext());}
<YYINITIAL> {BACKSLASH} ("n") {System.out.println("Token reconocido: NEWLINE " + yytext()); return symbol (ParserSym.NEWLINE, yytext());}
<YYINITIAL> {SQUOTE}          {System.out.println("Token reconocido: SQT " + yytext()); return symbol (ParserSym.SQT, yytext());}
<YYINITIAL> {DQUOTE}          {System.out.println("Token reconocido: SQT " + yytext()); return symbol (ParserSym.SQT, yytext());}

/* Ignora los comentarios y los espacios en blanco INICIO */

<YYINITIAL> {
    {Comment}                      { /* ignore */ }
    {Whitespace}                   { /* ignore */ }
}

/* Ignora los comentarios y los espacios en blanco FIN */

\n {yychar=1;} 
. {System.err.println("Caracter no reconocido '" + yytext()+"' -- ignored" + " at : "+ (yyline+1) + " " + (yycolumn+1) + " " + yychar);}
