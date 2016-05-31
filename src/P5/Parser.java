//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";


package P5;


//#line 2 "p5.y"
	import java.lang.Math;
	import java.io.*;
	import java.util.StringTokenizer;
//#line 21 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short IF=257;
public final static short ELSE=258;
public final static short WHILE=259;
public final static short FOR=260;
public final static short COMP=261;
public final static short DIFERENTES=262;
public final static short MAY=263;
public final static short MEN=264;
public final static short MAYI=265;
public final static short MENI=266;
public final static short FNCT=267;
public final static short NUMBER=268;
public final static short VAR=269;
public final static short AND=270;
public final static short OR=271;
public final static short FUNC=272;
public final static short RETURN=273;
public final static short PARAMETRO=274;
public final static short PROC=275;
public final static short UNARYMINUS=276;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    1,    1,    1,    1,    3,    3,    3,
    3,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    4,    7,    8,   11,   11,   11,    6,
   10,   12,    5,    9,    9,    9,
};
final static short yylen[] = {                            2,
    0,    2,    3,    2,    1,    3,    2,   14,   11,   10,
   16,    1,    1,    2,    3,    3,    3,    3,    3,    3,
    3,    3,    7,    7,    7,    7,    7,    7,    7,    7,
    2,    1,    4,    1,    1,    1,    0,    1,    3,    0,
    1,    0,    0,    0,    1,    3,
};
final static short yydefred[] = {                         1,
    0,   34,   35,   36,    0,    0,    0,   32,    2,    0,
    0,    0,    0,    5,    0,    0,    0,    0,   14,    0,
    0,    0,    0,    3,    0,    7,    0,    0,    0,    0,
    0,    4,    0,    0,    0,    0,    0,    0,   20,    0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   33,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   23,   24,   27,   25,   28,   26,   29,   30,    0,
    0,    0,    0,    0,    0,    0,   43,    0,    0,   43,
   10,    0,    0,    9,    0,    0,    0,    0,    0,    8,
    0,   43,   11,
};
final static short yydgoto[] = {                          1,
   12,   13,   14,   15,   62,  110,   16,   17,   51,   18,
   53,    0,
};
final static short yysindex[] = {                         0,
   -3,    0,    0,    0,  -91,  -46,  -37,    0,    0,  -37,
  -37,   15,   31,    0,  -13,   -8,    5,   12,    0,  -37,
    9,   25,   37,    0,   50,    0,  -37,  -37,  -37,  -37,
  -37,    0,  -37,  -37,  -37,  -37,  -37,  123,    0,   68,
    0,  -43,    9,  -19,  -19,  -45,  123,  123,  123,  123,
   13,  123,   72,  -90,  -60,  -55,  -53,  -49,  -41,  -36,
  -35,   40,   53,  -37,   39,    0,  -37,  -37,  -37,  -37,
  -37,  -37,  -37,  -37,  -37,  -20,  -16,  123,  -37,  123,
   57,   63,   75,   82,   89,   98,  109,  117,  -39,  -39,
  123,    0,    0,    0,    0,    0,    0,    0,    0,  -39,
  -39,   52,    1,    3,  -37, -146,    0,   13,   -4,    0,
    0,   92,  -39,    0,   14,  -39,  -39,   23,  -39,    0,
   32,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,   -1,  -23,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   -5,  103,  -28,    0,    0,
    0,  -33,   18,  166,  262,  153,  145,  122,  122,   -6,
   91,  105,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   94,    0,  114,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   91,    0,    0,    0,    0,    0,    0,    0,    0,   42,
   42,    0,    0,    0,  128,  -10,    0,  122,    0,    0,
    0,    0,    0,    0,    0,   42,    0,    0,   42,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -84,  362,   74,    0,  303,    0,    0,    0,   66,    0,
    0,    0,
};
final static int YYTABLESIZE=481;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         40,
   10,   33,   10,   33,  100,  101,    9,   21,   21,   21,
   21,   21,   15,   19,   20,   15,   41,   12,   12,   12,
   12,   12,   31,   12,   24,   21,   34,   33,  116,   40,
   15,   35,  119,   68,   45,   12,   10,   45,   44,   13,
   13,   13,   13,   13,   36,   13,   31,   31,   31,   31,
   31,   37,   45,   44,   10,   33,   64,   13,   22,   22,
   22,   22,   22,   69,   31,   39,   31,   29,   70,   30,
   71,   33,   31,   29,   72,   30,   22,   33,   31,   29,
   76,   30,   73,   33,   11,   26,   11,   74,   75,   32,
   21,   31,   29,   77,   30,   15,   33,   79,   31,   29,
   12,   30,   89,   33,   31,   29,   90,   30,   41,   33,
  105,  109,   66,   40,   40,   67,   31,   29,  113,   30,
   11,   33,   13,   31,   29,  106,   30,  107,   33,   31,
   31,   29,  115,   30,   46,   33,  117,   46,   11,   31,
   29,   22,   30,   37,   33,   38,   37,  120,   38,   43,
   31,   29,   46,   30,   39,   33,  122,   39,   31,   29,
   40,   30,   43,   33,   31,   29,   43,   30,   44,   33,
  108,   44,    0,   26,   26,    0,    0,    0,    0,    0,
   92,    0,    0,    0,    0,   19,   93,    0,   19,   26,
    0,    0,   26,   16,   16,   16,   16,   16,   94,    0,
    0,    0,    0,   19,    0,   95,   17,    0,   17,   17,
   17,   16,   96,    0,    0,   27,   28,    2,   28,    3,
    4,   97,    0,    0,   17,    0,    0,   21,    5,    6,
    5,    6,   98,    7,    8,    7,    8,   12,   12,    0,
   99,   27,   28,    0,    0,    0,   40,    0,   40,   40,
    0,    0,    0,    2,    0,    3,    4,   40,   40,   13,
   13,    0,   40,   40,    5,    6,   31,   31,   19,    7,
    8,    2,    0,    3,    4,    0,   16,    0,   22,   22,
    0,    0,    5,    6,    0,   27,   28,    7,    8,   17,
    0,   27,   28,    0,    0,    0,    0,   27,   28,    0,
    0,    0,   18,    0,   18,   18,   18,    0,    0,    0,
   27,   28,    0,    0,    0,    0,    0,   27,   28,    0,
   18,    0,    0,   27,   28,    0,    0,    0,   54,   55,
   56,   57,   58,   59,    0,   27,   28,   60,   61,    0,
    0,    0,   27,   28,    0,    0,    0,    0,    0,   27,
   28,   63,    0,   65,    0,    0,    0,    0,   27,   28,
    0,    0,    0,    0,    0,    0,    0,    0,   21,   27,
   28,   22,   23,   25,    0,    0,    0,   27,   28,    0,
    0,   38,    0,   27,   28,   18,    0,    0,   42,   43,
   44,   45,   46,  102,   47,   48,   49,   50,   52,    0,
    0,    0,  103,  104,    0,    0,    0,    0,    0,  111,
  112,    0,  114,    0,    0,    0,    0,    0,  118,    0,
    0,  121,    0,    0,  123,   78,    0,    0,   80,   81,
   82,   83,   84,   85,   86,   87,   88,    0,    0,    0,
   91,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   25,   25,    0,    0,    0,   50,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   25,    0,    0,
   25,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         10,
   40,   47,   40,   47,   89,   90,   10,   41,   42,   43,
   44,   45,   41,  105,   61,   44,   40,   41,   42,   43,
   44,   45,   42,   47,   10,   59,   40,   47,  113,   40,
   59,   40,  117,  124,   41,   59,   40,   44,   44,   41,
   42,   43,   44,   45,   40,   47,   41,   42,   43,   44,
   45,   40,   59,   59,   40,   47,   44,   59,   41,   42,
   43,   44,   45,  124,   59,   41,   42,   43,  124,   45,
  124,   47,   42,   43,  124,   45,   59,   47,   42,   43,
   41,   45,  124,   47,  124,   12,  124,  124,  124,   59,
  124,   42,   43,   41,   45,  124,   47,   59,   42,   43,
  124,   45,  123,   47,   42,   43,  123,   45,   59,   47,
   59,  258,   41,  124,  125,   44,   42,   43,  123,   45,
  124,   47,  124,   42,   43,  125,   45,  125,   47,  124,
   42,   43,   41,   45,   41,   47,  123,   44,  124,   42,
   43,  124,   45,   41,   47,   41,   44,  125,   44,   59,
   42,   43,   59,   45,   41,   47,  125,   44,   42,   43,
  124,   45,   41,   47,   42,   43,  125,   45,   41,   47,
  105,   44,   -1,  100,  101,   -1,   -1,   -1,   -1,   -1,
  124,   -1,   -1,   -1,   -1,   41,  124,   -1,   44,  116,
   -1,   -1,  119,   41,   42,   43,   44,   45,  124,   -1,
   -1,   -1,   -1,   59,   -1,  124,   41,   -1,   43,   44,
   45,   59,  124,   -1,   -1,  261,  262,  257,  262,  259,
  260,  124,   -1,   -1,   59,   -1,   -1,  261,  268,  269,
  268,  269,  124,  273,  274,  273,  274,  261,  262,   -1,
  124,  261,  262,   -1,   -1,   -1,  257,   -1,  259,  260,
   -1,   -1,   -1,  257,   -1,  259,  260,  268,  269,  261,
  262,   -1,  273,  274,  268,  269,  261,  262,  124,  273,
  274,  257,   -1,  259,  260,   -1,  124,   -1,  261,  262,
   -1,   -1,  268,  269,   -1,  261,  262,  273,  274,  124,
   -1,  261,  262,   -1,   -1,   -1,   -1,  261,  262,   -1,
   -1,   -1,   41,   -1,   43,   44,   45,   -1,   -1,   -1,
  261,  262,   -1,   -1,   -1,   -1,   -1,  261,  262,   -1,
   59,   -1,   -1,  261,  262,   -1,   -1,   -1,  261,  262,
  263,  264,  265,  266,   -1,  261,  262,  270,  271,   -1,
   -1,   -1,  261,  262,   -1,   -1,   -1,   -1,   -1,  261,
  262,   49,   -1,   51,   -1,   -1,   -1,   -1,  261,  262,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,    7,  261,
  262,   10,   11,   12,   -1,   -1,   -1,  261,  262,   -1,
   -1,   20,   -1,  261,  262,  124,   -1,   -1,   27,   28,
   29,   30,   31,   91,   33,   34,   35,   36,   37,   -1,
   -1,   -1,  100,  101,   -1,   -1,   -1,   -1,   -1,  107,
  108,   -1,  110,   -1,   -1,   -1,   -1,   -1,  116,   -1,
   -1,  119,   -1,   -1,  122,   64,   -1,   -1,   67,   68,
   69,   70,   71,   72,   73,   74,   75,   -1,   -1,   -1,
   79,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  100,  101,   -1,   -1,   -1,  105,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  116,   -1,   -1,
  119,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=276;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,"'\\n'",null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,"'!'",null,null,null,null,null,null,"'('","')'","'*'","'+'",
"','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'",null,"'='",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'i'",null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'{'","'|'","'}'",null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"IF","ELSE","WHILE","FOR",
"COMP","DIFERENTES","MAY","MEN","MAYI","MENI","FNCT","NUMBER","VAR","AND","OR",
"FUNC","RETURN","PARAMETRO","PROC","UNARYMINUS",
};
final static String yyrule[] = {
"$accept : list",
"list :",
"list : list '\\n'",
"list : list linea '\\n'",
"linea : exp ';'",
"linea : stmt",
"linea : linea exp ';'",
"linea : linea stmt",
"stmt : if_ '(' exp stop_ ')' '{' linea stop_ '}' ELSE '{' linea stop_ '}'",
"stmt : if_ '(' exp stop_ ')' '{' linea stop_ '}' nop stop_",
"stmt : while_ '(' exp stop_ ')' '{' linea stop_ '}' stop_",
"stmt : for_ '(' instrucciones stop_ ';' exp stop_ ';' instrucciones stop_ ')' '{' linea stop_ '}' stop_",
"exp : VAR",
"exp : NUMBER",
"exp : NUMBER 'i'",
"exp : VAR '=' exp",
"exp : exp '*' exp",
"exp : exp '+' exp",
"exp : exp '-' exp",
"exp : exp '/' exp",
"exp : '(' exp ')'",
"exp : exp COMP exp",
"exp : exp DIFERENTES exp",
"exp : '|' exp '|' COMP '|' exp '|'",
"exp : '|' exp '|' DIFERENTES '|' exp '|'",
"exp : '|' exp '|' MEN '|' exp '|'",
"exp : '|' exp '|' MENI '|' exp '|'",
"exp : '|' exp '|' MAY '|' exp '|'",
"exp : '|' exp '|' MAYI '|' exp '|'",
"exp : '|' exp '|' AND '|' exp '|'",
"exp : '|' exp '|' OR '|' exp '|'",
"exp : RETURN exp",
"exp : PARAMETRO",
"exp : nombreProc '(' arglist ')'",
"if_ : IF",
"while_ : WHILE",
"for_ : FOR",
"arglist :",
"arglist : exp",
"arglist : arglist ',' exp",
"nop :",
"nombreProc : VAR",
"null_ :",
"stop_ :",
"instrucciones :",
"instrucciones : exp",
"instrucciones : instrucciones ',' exp",
};

//#line 216 "p5.y"

TablaDeSimbolos tablaDeSimbolos = new TablaDeSimbolos();
MaquinaDePila maquina = new MaquinaDePila(tablaDeSimbolos);
Complejo complex = new Complejo();
int i = 0;
int j = 0;
boolean huboError;

String ins;
StringTokenizer st;

void yyerror(String s){
	huboError = true;
	System.out.println("error:"+s);
}

boolean newline;
int yylex(){
	String s;
	int tok = 0;
	Double d;
	if (!st.hasMoreTokens()) {
		if (!newline) {
			newline=true;
			return '\n'; //So we look like classic YACC example
		}
		else
			return 0;
	}
	s = st.nextToken();
	try {		
		d = Double.valueOf(s);/*this may fail*/
		yylval = new ParserVal(d.doubleValue()); //SEE BELOW
		return NUMBER;
	}
	catch (Exception e) {}
	if (esVariable(s)) {
		if (s.charAt(0) == '$') {
			yylval = new ParserVal((int)Integer.parseInt(s.substring(1)));
			return PARAMETRO;
		}

		if (s.equals("proc"))	return PROC;		
		if(s.equals("return"))	return RETURN;
		if(s.equals("func"))	return FUNC;
		if(s.equals("=="))		return COMP;
		if(s.equals("!="))		return DIFERENTES;
		if(s.equals("<"))		return MEN;
		if(s.equals("<="))		return MENI;
		if(s.equals(">"))		return MAY;
		if(s.equals(">="))		return MAYI;
		if(s.equals("&&"))		return AND;
		if(s.equals("||"))		return OR;
		if(s.equals("if"))		return IF;
		if(s.equals("else"))	return ELSE;
		if(s.equals("while"))	return WHILE;
		if(s.equals("for"))		return FOR;

		yylval = new ParserVal(s);
		return VAR;		
	}
	else {
		tok = s.charAt(0);
	}
	//System.out.println("Token: " + s.charAt(0));
	return tok;
}

String reservados[] = {"=", "{", "}", ",", "*", "+", "-", "(", ")", "|", "[", "]", ";", "!", "i"};

boolean esVariable(String s) {
	boolean cumple = true;
	for (int i = 0; i < reservados.length; i++)
		if (s.equals(reservados[i]))
			cumple = false;
	return cumple;
}

void dotest() throws Exception {
	tablaDeSimbolos.insertar("Imprimir", new MaquinaDePila.Imprimir());
	tablaDeSimbolos.insertar("Mag", new MaquinaDePila.Mag());
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	while (true) {
		huboError = false;
		try {
			ins = in.readLine();
		}
		catch (Exception e) {}
		st = new StringTokenizer(ins);
		newline = false;
		//maquina = new MaquinaDePila(tablaDeSimbolos);
		yyparse();
		if (!huboError)
			maquina.ejecutar();
	}
}

public static void main(String args[]) throws Exception {
	Parser par = new Parser(false);
	par.dotest();
}
//#line 464 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 4:
//#line 30 "p5.y"
{yyval = val_peek(1);}
break;
case 5:
//#line 31 "p5.y"
{yyval = val_peek(0);}
break;
case 6:
//#line 32 "p5.y"
{yyval = val_peek(2);
					yyval = new ParserVal(maquina.agregarOperacion("Imprimir"));
					maquina.agregar(val_peek(1).obj);
				}
break;
case 7:
//#line 36 "p5.y"
{yyval = val_peek(1);}
break;
case 8:
//#line 39 "p5.y"
{
				yyval = val_peek(13);
				maquina.agregar(val_peek(7).ival, val_peek(13).ival + 1);
				maquina.agregar(val_peek(2).ival, val_peek(13).ival + 2);
				maquina.agregar(maquina.numeroDeElementos() - 1, val_peek(13).ival + 3);
			}
break;
case 9:
//#line 45 "p5.y"
{
				yyval = val_peek(10);
				maquina.agregar(val_peek(4).ival, val_peek(10).ival + 1);
				maquina.agregar(val_peek(1).ival, val_peek(10).ival + 2);
				maquina.agregar(maquina.numeroDeElementos() - 1, val_peek(10).ival + 3);
			}
break;
case 10:
//#line 51 "p5.y"
{
				yyval = val_peek(9);
				maquina.agregar(val_peek(3).ival, val_peek(9).ival + 1);
				maquina.agregar(val_peek(0).ival, val_peek(9).ival + 2);
			}
break;
case 11:
//#line 56 "p5.y"
{
				yyval = val_peek(15);
				maquina.agregar(val_peek(10).ival, val_peek(15).ival + 1);
				maquina.agregar(val_peek(7).ival, val_peek(15).ival + 2);
				maquina.agregar(val_peek(3).ival, val_peek(15).ival + 3);
				maquina.agregar(val_peek(0).ival, val_peek(15).ival + 4);
			}
break;
case 12:
//#line 65 "p5.y"
{				
				yyval = new ParserVal(maquina.agregarOperacion("varPush_Eval")); 
				maquina.agregar(val_peek(0).sval);
			}
break;
case 13:
//#line 69 "p5.y"
{
				yyval = new ParserVal(maquina.agregarOperacion("constPush"));
				complex = new Complejo(val_peek(0).dval, 0);
				maquina.agregar(complex);
			}
break;
case 14:
//#line 74 "p5.y"
{
				yyval = new ParserVal(maquina.agregarOperacion("constPush"));
				complex = new Complejo(val_peek(1).dval, 0);
				maquina.agregar(complex);
				maquina.agregarOperacion("imag");
				yyval = new ParserVal(val_peek(1).dval);
			}
break;
case 15:
//#line 81 "p5.y"
{
				yyval = new ParserVal(val_peek(0).ival);
				maquina.agregarOperacion("varPush");
		        maquina.agregar(val_peek(2).sval);
		        maquina.agregarOperacion("asignar");
		        maquina.agregarOperacion("varPush_Eval"); 
				maquina.agregar(val_peek(2).sval);
			}
break;
case 16:
//#line 89 "p5.y"
{
				yyval = new ParserVal(val_peek(2).ival);
				maquina.agregarOperacion("multiplicar");
			}
break;
case 17:
//#line 93 "p5.y"
{
				yyval = new ParserVal(val_peek(2).ival);
				maquina.agregarOperacion("sumar");
			}
break;
case 18:
//#line 97 "p5.y"
{
				yyval = new ParserVal(val_peek(2).ival);
				maquina.agregarOperacion("restar");
			}
break;
case 19:
//#line 101 "p5.y"
{
				yyval  = new ParserVal(val_peek(2).ival);
				maquina.agregarOperacion("dividir");
			}
break;
case 20:
//#line 105 "p5.y"
{
				yyval = new ParserVal(val_peek(1).ival);
			}
break;
case 21:
//#line 118 "p5.y"
{
				 maquina.agregarOperacion("comparar");
				 yyval = val_peek(2);
			}
break;
case 22:
//#line 122 "p5.y"
{
				 maquina.agregarOperacion("compararNot");
				 yyval = val_peek(2);
			}
break;
case 23:
//#line 126 "p5.y"
{
				 maquina.agregarOperacion("mag_comparar");
				 yyval = val_peek(5);
			}
break;
case 24:
//#line 130 "p5.y"
{
				 maquina.agregarOperacion("mag_compararNot");
				 yyval = val_peek(5);
			}
break;
case 25:
//#line 134 "p5.y"
{
				 maquina.agregarOperacion("menor");
				 yyval = val_peek(5);
			}
break;
case 26:
//#line 138 "p5.y"
{
				 maquina.agregarOperacion("menorIgual");
				 yyval = val_peek(5);
			}
break;
case 27:
//#line 142 "p5.y"
{
				 maquina.agregarOperacion("mayor");
				 yyval = val_peek(5);
			}
break;
case 28:
//#line 146 "p5.y"
{
				 maquina.agregarOperacion("mayorIgual");
				 yyval = val_peek(5);
			}
break;
case 29:
//#line 150 "p5.y"
{
				maquina.agregarOperacion("and");
				 yyval = val_peek(5);
			}
break;
case 30:
//#line 154 "p5.y"
{
				maquina.agregarOperacion("or");
				 yyval = val_peek(5);
			}
break;
case 31:
//#line 164 "p5.y"
{ yyval = val_peek(0); maquina.agregarOperacion("_return"); }
break;
case 32:
//#line 166 "p5.y"
{ yyval = new ParserVal(maquina.agregarOperacion("push_parametro")); maquina.agregar((int)val_peek(0).ival); }
break;
case 33:
//#line 168 "p5.y"
{ yyval = new ParserVal(maquina.agregarOperacionEn("invocar",(val_peek(3).ival))); maquina.agregar(null); }
break;
case 34:
//#line 171 "p5.y"
{
			yyval = new ParserVal(maquina.agregarOperacion("_if_then_else"));
	        maquina.agregarOperacion("stop");/*then*/
	        maquina.agregarOperacion("stop");/*else*/
	        maquina.agregarOperacion("stop");/*siguiente comando*/
		}
break;
case 35:
//#line 179 "p5.y"
{
			yyval = new ParserVal(maquina.agregarOperacion("_while"));
	        maquina.agregarOperacion("stop");/*cuerpo*/
	        maquina.agregarOperacion("stop");/*final*/
		}
break;
case 36:
//#line 186 "p5.y"
{
			yyval = new ParserVal(maquina.agregarOperacion("_for"));
	        maquina.agregarOperacion("stop");/*condicion*/
	        maquina.agregarOperacion("stop");/*instrucción final*/
	        maquina.agregarOperacion("stop");/*cuerpo*/
	        maquina.agregarOperacion("stop");/*final*/
		}
break;
case 38:
//#line 195 "p5.y"
{yyval = val_peek(0); maquina.agregar("Limite");}
break;
case 39:
//#line 196 "p5.y"
{yyval = val_peek(2); maquina.agregar("Limite");}
break;
case 40:
//#line 199 "p5.y"
{yyval = new ParserVal(maquina.agregarOperacion("nop"));}
break;
case 41:
//#line 202 "p5.y"
{yyval = new ParserVal(maquina.agregar(val_peek(0).sval));}
break;
case 42:
//#line 205 "p5.y"
{maquina.agregar(null);}
break;
case 43:
//#line 208 "p5.y"
{yyval = new ParserVal(maquina.agregarOperacion("stop"));}
break;
case 44:
//#line 211 "p5.y"
{ yyval = new ParserVal(maquina.agregarOperacion("nop"));}
break;
case 45:
//#line 212 "p5.y"
{yyval = val_peek(0);}
break;
case 46:
//#line 213 "p5.y"
{yyval = val_peek(2);}
break;
//#line 883 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
