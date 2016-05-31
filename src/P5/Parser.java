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


//#line 2 "p.y"
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
    3,    3,    3,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    4,    7,    8,   14,
   14,   14,    6,   13,   10,   11,   12,    5,    9,    9,
    9,
};
final static short yylen[] = {                            2,
    0,    2,    3,    2,    1,    3,    2,   14,   11,   10,
   16,    8,    8,    1,    1,    2,    3,    3,    3,    3,
    3,    3,    3,    3,    7,    7,    7,    7,    7,    7,
    7,    7,    4,    2,    1,    4,    1,    1,    1,    0,
    1,    3,    0,    1,    1,    1,    0,    0,    0,    1,
    3,
};
final static short yydefred[] = {                         1,
    0,   37,   38,   39,    0,    0,    0,   45,    0,   35,
   44,    2,    0,    0,    0,    0,    5,    0,    0,    0,
    0,    0,    0,    0,   16,    0,    0,    0,    0,    3,
    0,    7,    0,    0,    0,    0,    0,    4,    0,    0,
    0,    0,   46,    0,    0,    0,    0,    0,   22,    0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   33,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   36,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   25,   26,   29,   27,   30,   28,   31,   32,    0,    0,
    0,    0,    0,    0,    0,    0,   12,   13,    0,   48,
    0,    0,   48,   10,    0,    0,    9,    0,    0,    0,
    0,    0,    8,    0,   48,   11,
};
final static short yydgoto[] = {                          1,
   15,   16,   17,   18,   75,  133,   19,   20,   61,   21,
   22,  122,   23,   64,
};
final static short yysindex[] = {                         0,
    9,    0,    0,    0,  -80,  -92,  -41,    0,  -30,    0,
    0,    0,  -30,  -30,   28,   -1,    0,   -4,   11,   14,
 -229,   19, -229,  -30,    0,  -30,   15,   -8,   18,    0,
   57,    0,  -30,  -30,  -30,  -30,  -30,    0,  -30,  -30,
  -30,  -30,    0,   35,  -30,   36,   76,  134,    0,  219,
    0,  -31,   15,   10,   10,  -35,  134,  134,  134,  134,
   25,   42,  134,  -26,   43,    0,  -37,  -23,  -21,  -19,
  -18,  -15,  -14,  -13,   71,   79,  -30,   58,    3,    0,
  -30,    5,  -30,  -30,  -30,  -30,  -30,  -30,  -30,  -30,
   16,   22,  134,  -30,  -39,  134,  -39,   82,   89,  101,
  117,  128,  143,  149,  162,  -39,  -39,  134,  -39,  -39,
    0,    0,    0,    0,    0,    0,    0,    0,  -39,  -39,
   90,   13,   26,   32,   33,  -30,    0,    0, -123,    0,
   25,   27,    0,    0,  122,  -39,    0,   44,  -39,  -39,
   49,  -39,    0,   62,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  -16,  -38,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   29,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -36,    0,    0,    4,    0,    0,  -27,    0,    0,
    0,   48,   37,  343,  386,  283,   54,  127,  127,   96,
  123,    0,    6,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  139,    0,    0,   12,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  123,   64,   64,
    0,    0,    0,    0,    0,    0,    0,    0,   68,   68,
    0,    0,    0,    0,    0,   23,    0,    0,  -10,    0,
  127,    0,    0,    0,    0,    0,    0,    0,   68,    0,
    0,   68,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   59,  332,  197,    0,  324,    0,    0,    0,   74,    0,
    1,   87,    0,    0,
};
final static int YYTABLESIZE=510;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         43,
   13,   46,   14,   14,   14,   14,   14,   49,   14,   13,
   24,   39,   25,   17,   80,   39,   17,   81,   12,   26,
   14,   44,   49,   46,   15,   15,   15,   15,   15,   43,
   15,   17,   49,   37,   35,   40,   36,   30,   39,   43,
   37,   35,   15,   36,   40,   39,   41,   40,   13,   41,
   41,   37,   42,   42,   14,   42,   39,   38,   45,   37,
   35,   39,   36,   49,   39,   17,   49,   13,   77,   34,
   34,   34,   34,   34,   62,   65,   15,   24,   24,   24,
   24,   24,   79,   82,   14,   14,   83,   34,   23,   23,
   23,   23,   23,   14,   21,   24,   17,   21,   37,   35,
   84,   36,   85,   39,   86,   87,   23,   15,   88,   89,
   90,   91,   21,   43,   43,   51,   94,   37,   35,   92,
   36,   34,   39,   37,   35,   95,   36,   97,   39,   24,
   37,   35,   14,   36,  132,   39,   50,  127,  106,   50,
   23,   50,   37,   35,  107,   36,   21,   39,  126,  136,
  128,   14,   34,  109,   50,  110,  129,  130,   37,   35,
   24,   36,  138,   39,  119,  120,  140,   48,   66,   37,
   35,   23,   36,  143,   39,   37,   35,   21,   36,   51,
   39,   48,   51,    0,   37,   35,  145,   36,   47,   39,
   37,   35,   48,   36,  139,   39,  123,   51,  142,  131,
    0,    0,    0,   37,   35,  111,   36,    0,   39,    0,
    0,   32,  112,    0,    0,    0,    0,    2,    0,    3,
    4,    0,   14,   14,  113,   33,   34,    5,    6,    7,
   34,    0,    8,    9,   10,   11,    5,    6,    7,    0,
  114,    0,    9,   10,   15,   15,   43,    0,   43,   43,
    0,  115,   33,   34,    0,    0,   43,   43,   43,   33,
   34,   43,   43,   43,   43,    2,  116,    3,    4,    0,
   33,   34,  117,    0,    0,    5,    6,    7,   33,   34,
    8,    9,   10,   11,    2,  118,    3,    4,    0,   34,
   34,    0,    0,    0,    5,    6,    7,   24,   24,    8,
    9,   10,   11,    0,    0,   32,   32,    0,   23,    0,
    0,    0,    0,    0,    0,   32,   32,   33,   34,    0,
    0,    0,    0,   18,   18,   18,   18,   18,    0,    0,
    0,    0,    0,    0,    0,   32,   33,   34,   32,    0,
   27,   18,   33,   34,   28,   29,   31,    0,    0,   33,
   34,    0,    0,    0,    0,   47,    0,   48,    0,    0,
    0,   33,   34,    0,   52,   53,   54,   55,   56,    0,
   57,   58,   59,   60,    0,   18,   63,   33,   34,    0,
    0,    0,   76,   19,   78,   19,   19,   19,   33,   34,
    0,    0,    0,    0,   33,   34,    0,    0,    0,    0,
    0,   19,    0,   33,   34,    0,   18,    0,   93,   33,
   34,    0,   96,    0,   98,   99,  100,  101,  102,  103,
  104,  105,   33,   34,    0,  108,   20,    0,   20,   20,
   20,  121,    0,    0,    0,   19,    0,    0,    0,    0,
   31,   31,  124,  125,   20,    0,    0,    0,    0,    0,
   31,   31,    0,  134,  135,    0,  137,   60,    0,    0,
    0,    0,  141,    0,    0,  144,   19,    0,  146,    0,
   31,    0,    0,   31,    0,    0,    0,    0,   20,   67,
   68,   69,   70,   71,   72,    0,    0,    0,   73,   74,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   20,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         10,
   40,   40,   41,   42,   43,   44,   45,   44,   47,   40,
   91,   47,  105,   41,   41,   47,   44,   44,   10,   61,
   59,   21,   59,   23,   41,   42,   43,   44,   45,   40,
   47,   59,   41,   42,   43,   40,   45,   10,   47,  269,
   42,   43,   59,   45,   41,   47,   41,   44,   40,   44,
   40,   42,   41,   40,   93,   44,   47,   59,   40,   42,
   43,   47,   45,   41,   47,   93,   44,   40,   44,   41,
   42,   43,   44,   45,   40,   40,   93,   41,   42,   43,
   44,   45,   41,   41,  124,  124,  124,   59,   41,   42,
   43,   44,   45,  124,   41,   59,  124,   44,   42,   43,
  124,   45,  124,   47,  124,  124,   59,  124,  124,  124,
  124,   41,   59,  124,  125,   59,   59,   42,   43,   41,
   45,   93,   47,   42,   43,  123,   45,  123,   47,   93,
   42,   43,  124,   45,  258,   47,   41,  125,  123,   44,
   93,  124,   42,   43,  123,   45,   93,   47,   59,  123,
  125,  124,  124,   95,   59,   97,  125,  125,   42,   43,
  124,   45,   41,   47,  106,  107,  123,   41,   93,   42,
   43,  124,   45,  125,   47,   42,   43,  124,   45,   41,
   47,   59,   44,   -1,   42,   43,  125,   45,  125,   47,
   42,   43,  125,   45,  136,   47,  110,   59,  140,  126,
   -1,   -1,   -1,   42,   43,  124,   45,   -1,   47,   -1,
   -1,   15,  124,   -1,   -1,   -1,   -1,  257,   -1,  259,
  260,   -1,  261,  262,  124,  261,  262,  267,  268,  269,
  262,   -1,  272,  273,  274,  275,  267,  268,  269,   -1,
  124,   -1,  273,  274,  261,  262,  257,   -1,  259,  260,
   -1,  124,  261,  262,   -1,   -1,  267,  268,  269,  261,
  262,  272,  273,  274,  275,  257,  124,  259,  260,   -1,
  261,  262,  124,   -1,   -1,  267,  268,  269,  261,  262,
  272,  273,  274,  275,  257,  124,  259,  260,   -1,  261,
  262,   -1,   -1,   -1,  267,  268,  269,  261,  262,  272,
  273,  274,  275,   -1,   -1,  109,  110,   -1,  261,   -1,
   -1,   -1,   -1,   -1,   -1,  119,  120,  261,  262,   -1,
   -1,   -1,   -1,   41,   42,   43,   44,   45,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  139,  261,  262,  142,   -1,
    9,   59,  261,  262,   13,   14,   15,   -1,   -1,  261,
  262,   -1,   -1,   -1,   -1,   24,   -1,   26,   -1,   -1,
   -1,  261,  262,   -1,   33,   34,   35,   36,   37,   -1,
   39,   40,   41,   42,   -1,   93,   45,  261,  262,   -1,
   -1,   -1,   59,   41,   61,   43,   44,   45,  261,  262,
   -1,   -1,   -1,   -1,  261,  262,   -1,   -1,   -1,   -1,
   -1,   59,   -1,  261,  262,   -1,  124,   -1,   77,  261,
  262,   -1,   81,   -1,   83,   84,   85,   86,   87,   88,
   89,   90,  261,  262,   -1,   94,   41,   -1,   43,   44,
   45,  108,   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,
  109,  110,  119,  120,   59,   -1,   -1,   -1,   -1,   -1,
  119,  120,   -1,  130,  131,   -1,  133,  126,   -1,   -1,
   -1,   -1,  139,   -1,   -1,  142,  124,   -1,  145,   -1,
  139,   -1,   -1,  142,   -1,   -1,   -1,   -1,   93,  261,
  262,  263,  264,  265,  266,   -1,   -1,   -1,  270,  271,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  124,
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
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
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
"stmt : funcion nombreProc '(' ')' '{' linea null_ '}'",
"stmt : procedimiento nombreProc '(' ')' '{' linea null_ '}'",
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
"exp : FNCT '[' exp ']'",
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
"procedimiento : PROC",
"funcion : FUNC",
"nombreProc : VAR",
"null_ :",
"stop_ :",
"instrucciones :",
"instrucciones : exp",
"instrucciones : instrucciones ',' exp",
};

//#line 225 "p.y"

TablaDeSimbolos tablaDeSimbolos = new TablaDeSimbolos();
MaquinaDePila maquina = new MaquinaDePila(tablaDeSimbolos);
Complejo complex = new Complejo();
int i = 0;
int j = 0;
Funcion funcionAux;
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
		
		boolean esFuncion = false;
		Object objeto = tablaDeSimbolos.encontrar(s);
		if (objeto instanceof Funcion) {
			funcionAux = (Funcion)objeto;
			yylval = new ParserVal(objeto);			
			esFuncion = true;
			return FNCT;
		}
		if (!esFuncion) {
			yylval = new ParserVal(s);
			return VAR;
		}
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
//#line 492 "Parser.java"
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
//#line 30 "p.y"
{yyval = val_peek(1);}
break;
case 5:
//#line 31 "p.y"
{yyval = val_peek(0);}
break;
case 6:
//#line 32 "p.y"
{yyval = val_peek(2);}
break;
case 7:
//#line 33 "p.y"
{yyval = val_peek(1);}
break;
case 8:
//#line 36 "p.y"
{
				yyval = val_peek(13);
				maquina.agregar(val_peek(7).ival, val_peek(13).ival + 1);
				maquina.agregar(val_peek(2).ival, val_peek(13).ival + 2);
				maquina.agregar(maquina.numeroDeElementos() - 1, val_peek(13).ival + 3);
			}
break;
case 9:
//#line 42 "p.y"
{
				yyval = val_peek(10);
				maquina.agregar(val_peek(4).ival, val_peek(10).ival + 1);
				maquina.agregar(val_peek(1).ival, val_peek(10).ival + 2);
				maquina.agregar(maquina.numeroDeElementos() - 1, val_peek(10).ival + 3);
			}
break;
case 10:
//#line 48 "p.y"
{
				yyval = val_peek(9);
				maquina.agregar(val_peek(3).ival, val_peek(9).ival + 1);
				maquina.agregar(val_peek(0).ival, val_peek(9).ival + 2);
			}
break;
case 11:
//#line 53 "p.y"
{
				yyval = val_peek(15);
				maquina.agregar(val_peek(10).ival, val_peek(15).ival + 1);
				maquina.agregar(val_peek(7).ival, val_peek(15).ival + 2);
				maquina.agregar(val_peek(3).ival, val_peek(15).ival + 3);
				maquina.agregar(val_peek(0).ival, val_peek(15).ival + 4);
			}
break;
case 14:
//#line 64 "p.y"
{				
				yyval = new ParserVal(maquina.agregarOperacion("varPush_Eval")); 
				maquina.agregar(val_peek(0).sval);
			}
break;
case 15:
//#line 68 "p.y"
{
				yyval = new ParserVal(maquina.agregarOperacion("constPush"));
				complex = new Complejo(val_peek(0).dval, 0);
				maquina.agregar(complex);
			}
break;
case 16:
//#line 73 "p.y"
{
				yyval = new ParserVal(maquina.agregarOperacion("constPush"));
				complex = new Complejo(val_peek(1).dval, 0);
				maquina.agregar(complex);
				maquina.agregarOperacion("imag");
				yyval = new ParserVal(val_peek(1).dval);
			}
break;
case 17:
//#line 80 "p.y"
{
				yyval = new ParserVal(val_peek(0).ival);
				maquina.agregarOperacion("varPush");
		        maquina.agregar(val_peek(2).sval);
		        maquina.agregarOperacion("asignar");
		        maquina.agregarOperacion("varPush_Eval"); 
				maquina.agregar(val_peek(2).sval);
			}
break;
case 18:
//#line 88 "p.y"
{
				yyval = new ParserVal(val_peek(2).ival);
				maquina.agregarOperacion("multiplicar");
			}
break;
case 19:
//#line 92 "p.y"
{
				yyval = new ParserVal(val_peek(2).ival);
				maquina.agregarOperacion("sumar");
			}
break;
case 20:
//#line 96 "p.y"
{
				yyval = new ParserVal(val_peek(2).ival);
				maquina.agregarOperacion("restar");
			}
break;
case 21:
//#line 100 "p.y"
{
				yyval  = new ParserVal(val_peek(2).ival);
				maquina.agregarOperacion("dividir");
			}
break;
case 22:
//#line 104 "p.y"
{
				yyval = new ParserVal(val_peek(1).ival);
			}
break;
case 23:
//#line 117 "p.y"
{
				 maquina.agregarOperacion("comparar");
				 yyval = val_peek(2);
			}
break;
case 24:
//#line 121 "p.y"
{
				 maquina.agregarOperacion("compararNot");
				 yyval = val_peek(2);
			}
break;
case 25:
//#line 125 "p.y"
{
				 maquina.agregarOperacion("mag_comparar");
				 yyval = val_peek(5);
			}
break;
case 26:
//#line 129 "p.y"
{
				 maquina.agregarOperacion("mag_compararNot");
				 yyval = val_peek(5);
			}
break;
case 27:
//#line 133 "p.y"
{
				System.out.println("ymenor");
				 maquina.agregarOperacion("menor");
				 yyval = val_peek(5);
			}
break;
case 28:
//#line 138 "p.y"
{
				 maquina.agregarOperacion("menorIgual");
				 yyval = val_peek(5);
			}
break;
case 29:
//#line 142 "p.y"
{
				 maquina.agregarOperacion("mayor");
				 yyval = val_peek(5);
			}
break;
case 30:
//#line 146 "p.y"
{
				 maquina.agregarOperacion("mayorIgual");
				 yyval = val_peek(5);
			}
break;
case 31:
//#line 150 "p.y"
{
				maquina.agregarOperacion("and");
				 yyval = val_peek(5);
			}
break;
case 32:
//#line 154 "p.y"
{
				maquina.agregarOperacion("or");
				 yyval = val_peek(5);
			}
break;
case 33:
//#line 164 "p.y"
{ 
				yyval = new ParserVal(val_peek(1).ival);
      			maquina.agregar((Funcion)(val_peek(3).obj));
			}
break;
case 34:
//#line 168 "p.y"
{ yyval = val_peek(0); maquina.agregarOperacion("_return"); }
break;
case 35:
//#line 170 "p.y"
{ yyval = new ParserVal(maquina.agregarOperacion("push_parametro")); maquina.agregar((int)val_peek(0).ival); }
break;
case 36:
//#line 172 "p.y"
{ yyval = new ParserVal(maquina.agregarOperacionEn("invocar",(val_peek(3).ival))); maquina.agregar(null); }
break;
case 37:
//#line 175 "p.y"
{
			yyval = new ParserVal(maquina.agregarOperacion("_if_then_else"));
	        maquina.agregarOperacion("stop");/*then*/
	        maquina.agregarOperacion("stop");/*else*/
	        maquina.agregarOperacion("stop");/*siguiente comando*/
		}
break;
case 38:
//#line 183 "p.y"
{
			yyval = new ParserVal(maquina.agregarOperacion("_while"));
	        maquina.agregarOperacion("stop");/*cuerpo*/
	        maquina.agregarOperacion("stop");/*final*/
		}
break;
case 39:
//#line 190 "p.y"
{
			yyval = new ParserVal(maquina.agregarOperacion("_for"));
	        maquina.agregarOperacion("stop");/*condicion*/
	        maquina.agregarOperacion("stop");/*instrucción final*/
	        maquina.agregarOperacion("stop");/*cuerpo*/
	        maquina.agregarOperacion("stop");/*final*/
		}
break;
case 41:
//#line 199 "p.y"
{yyval = val_peek(0); maquina.agregar("Limite");}
break;
case 42:
//#line 200 "p.y"
{yyval = val_peek(2); maquina.agregar("Limite");}
break;
case 43:
//#line 203 "p.y"
{yyval = new ParserVal(maquina.agregarOperacion("nop"));}
break;
case 44:
//#line 206 "p.y"
{ maquina.agregarOperacion("declaracion"); }
break;
case 45:
//#line 208 "p.y"
{ maquina.agregarOperacion("declaracion"); }
break;
case 46:
//#line 211 "p.y"
{yyval = new ParserVal(maquina.agregar(val_peek(0).sval));}
break;
case 47:
//#line 214 "p.y"
{maquina.agregar(null);}
break;
case 48:
//#line 217 "p.y"
{yyval = new ParserVal(maquina.agregarOperacion("stop"));}
break;
case 49:
//#line 220 "p.y"
{ yyval = new ParserVal(maquina.agregarOperacion("nop"));}
break;
case 50:
//#line 221 "p.y"
{yyval = val_peek(0);}
break;
case 51:
//#line 222 "p.y"
{yyval = val_peek(2);}
break;
//#line 924 "Parser.java"
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
