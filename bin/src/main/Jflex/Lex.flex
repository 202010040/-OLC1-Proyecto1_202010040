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
InputCharacter       = [^\r\n]
DocumentationComment = "<!" {CommentContent} "!"+ ">"
CommentContent       = ( [^*] | \*+ [^/*] )*

/* Definicion de conjuntos */
CharacterConj        = [\x20-\x7D][^a-zA-Z0-9] | {letra} | {digito}
Separador            = "-" | "~"
RangConj             = {CharacterConj} {Separador} {CharacterConj}

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
	return symbol(ParserSym.EOF);
%eofval}
%%

/* Llaves de apertura y cierre */
<YYINITIAL>"{" {return symbol (ParserSym.Llave_A, yytext());}
<YYINITIAL>"}" {return symbol (ParserSym.Llave_C, yytext());}

/* Definicion de los conjuntos */
<YYINITIAL> "CONJ"   {return symbol (ParserSym.CONJ, yytext());}
<YYINITIAL> ":"      {return symbol (ParserSym.DOBDOT, yytext());}
<YYINITIAL> "->"     {return symbol (ParserSym.FLECH, yytext());}
<YYINITIAL> RangConj {return symbol (ParserSym.RANCONJ, yytext());}
 
/* Definicion de los ID */
<YYINITIAL>{letra}({letra}|{digito})* {return symbol (ParserSym.ID, yytext());}
<YYINITIAL>{digito}({digito})*        {return symbol (ParserSym.NUM, yytext());}

/* Expresiones Regulares (OPERACIONES) */
<YYINITIAL>"(" {return symbol (ParserSym.PAR_A, yytext());}
<YYINITIAL>")" {return symbol (ParserSym.PAR_C, yytext());}
<YYINITIAL>"." {return symbol (ParserSym.DOT, yytext());}
<YYINITIAL>"|" {return symbol (ParserSym.PIPE, yytext());}
<YYINITIAL>"*" {return symbol (ParserSym.MULTIPLY, yytext());}
<YYINITIAL>"+" {return symbol (ParserSym.PLUS, yytext());}
<YYINITIAL>"?" {return symbol (ParserSym.INTERROGACION, yytext());}
<YYINITIAL>";" {return symbol (ParserSym.PCOMMA, yytext());}

/* Expresiones Regulares (Caracteres especiales) */
<YYINITIAL> {BACKSLASH}{SQUOTE} {return symbol (ParserSym.ESCSQT, yytext());}
<YYINITIAL> {BACKSLASH}{DQUOTE} {return symbol (ParserSym.ESCDQT, yytext());}
<YYINITIAL> {BACKSLASH} ("n") {return symbol (ParserSym.NEWLINE, yytext());}
<YYINITIAL> {SQUOTE}          {return symbol (ParserSym.SQT, yytext());}
<YYINITIAL> {DQUOTE}          {return symbol (ParserSym.DQT, yytext());}

/* Ignora los comentarios y los espacios en blanco INICIO */

<YYINITIAL> {
    {Comment}                      { /* ignore */ }
    {Whitespace}                   { /* ignore */ }
}

/* Ignora los comentarios y los espacios en blanco FIN */

\n {yychar=1;} 
. {System.err.println("Caracter no reconocido '" + yytext()+"' -- ignored" + " at : "+ (yyline+1) + " " + (yycolumn+1) + " " + yychar);}
