%{
	import java.lang.Math;
	import java.io.*;
	import java.util.StringTokenizer;
%}

%token IF ELSE WHILE FOR COMP DIFERENTES MAY MEN MAYI MENI FNCT NUMBER VAR
%token AND OR FUNC RETURN PARAMETRO PROC
%right '='
%left '+' '-'
%left '*'
%left ';'
%left COMP
%left DIFERENTES
%left MAY
%left MAYI
%left MEN
%left MENI
%left '!' UNARYMINUS
%left AND
%left OR
%right RETURN
%%

	list:   
		| list'\n'
	  	| list linea '\n'
		;

	linea: exp ';' {$$ = $1;}
		|stmt {$$ = $1;}
		|linea exp ';' {$$ = $1;}
		|linea stmt {$$ = $1;}
		;

	stmt:	if_ '(' exp stop_ ')' '{' linea stop_ '}' ELSE '{' linea stop_'}' {
				$$ = $1;
				maquina.agregar($7.ival, $1.ival + 1);
				maquina.agregar($12.ival, $1.ival + 2);
				maquina.agregar(maquina.numeroDeElementos() - 1, $1.ival + 3);
			}
		|	if_ '(' exp stop_ ')' '{' linea stop_ '}' nop stop_{
				$$ = $1;
				maquina.agregar($7.ival, $1.ival + 1);
				maquina.agregar($10.ival, $1.ival + 2);
				maquina.agregar(maquina.numeroDeElementos() - 1, $1.ival + 3);
			}
		|	while_ '(' exp stop_ ')' '{' linea stop_ '}' stop_{
				$$ = $1;
				maquina.agregar($7.ival, $1.ival + 1);
				maquina.agregar($10.ival, $1.ival + 2);
			}
		|	for_ '(' instrucciones stop_ ';' exp stop_ ';' instrucciones stop_ ')' '{' linea stop_ '}' stop_{
				$$ = $1;
				maquina.agregar($6.ival, $1.ival + 1);
				maquina.agregar($9.ival, $1.ival + 2);
				maquina.agregar($13.ival, $1.ival + 3);
				maquina.agregar($16.ival, $1.ival + 4);
			}
		;
		
	exp:	VAR {				
				$$ = new ParserVal(maquina.agregarOperacion("varPush_Eval")); 
				maquina.agregar($1.sval);
			}
		|	NUMBER {
				$$ = new ParserVal(maquina.agregarOperacion("constPush"));
				complex = new Complejo($1.dval, 0);
				maquina.agregar(complex);
			}
		|	NUMBER 'i' {
				$$ = new ParserVal(maquina.agregarOperacion("constPush"));
				complex = new Complejo($1.dval, 0);
				maquina.agregar(complex);
				maquina.agregarOperacion("imag");
				$$ = new ParserVal($1.dval);
			}
		| 	VAR '=' exp {
				$$ = new ParserVal($3.ival);
				maquina.agregarOperacion("varPush");
		        maquina.agregar($1.sval);
		        maquina.agregarOperacion("asignar");
		        maquina.agregarOperacion("varPush_Eval"); 
				maquina.agregar($1.sval);
			}
		| 	exp '*' exp {
				$$ = new ParserVal($1.ival);
				maquina.agregarOperacion("multiplicar");
			}
		|	exp '+' exp {
				$$ = new ParserVal($1.ival);
				maquina.agregarOperacion("sumar");
			}
		|	exp '-' exp {
				$$ = new ParserVal($1.ival);
				maquina.agregarOperacion("restar");
			}
		|	exp '/' exp {
				$$  = new ParserVal($1.ival);
				maquina.agregarOperacion("dividir");
			}
		|	'(' exp ')' {
				$$ = new ParserVal($2.ival);
			}
			/*
		|	exp 'i'	{
				maquina.agregarOperacion("imag");
				$$ = new ParserVal($1.ival);
			}
		|	'-' exp %prec UNARYMINUS {
				maquina.agregarOperacion("unaryminus");
				$$ = $2;
			}
			*/
		|	exp COMP exp {
				 maquina.agregarOperacion("comparar");
				 $$ = $1;
			}
		|	exp DIFERENTES exp {
				 maquina.agregarOperacion("compararNot");
				 $$ = $1;
			}
		|	'|' exp '|' COMP '|' exp '|' {
				 maquina.agregarOperacion("mag_comparar");
				 $$ = $2;
			}
		|	'|' exp '|' DIFERENTES '|' exp '|' {
				 maquina.agregarOperacion("mag_compararNot");
				 $$ = $2;
			}
		|	'|' exp '|' MEN '|' exp '|' {
				 maquina.agregarOperacion("menor");
				 $$ = $2;
			}
		|	'|' exp '|' MENI '|' exp '|' {
				 maquina.agregarOperacion("menorIgual");
				 $$ = $2;
			}
		|	'|' exp '|' MAY '|' exp '|' {
				 maquina.agregarOperacion("mayor");
				 $$ = $2;
			}
		|	'|' exp '|' MAYI '|' exp '|' {
				 maquina.agregarOperacion("mayorIgual");
				 $$ = $2;
			}
		|	'|' exp '|' AND '|' exp '|' {
				maquina.agregarOperacion("and");
				 $$ = $2;
			}
		|	'|' exp '|' OR '|' exp '|' {
				maquina.agregarOperacion("or");
				 $$ = $2;
			}
			/*
		|	'!' exp {
				maquina.agregarOperacion("negar");
				$$ = $2;
			}
			*/
		|	RETURN exp { $$ = $2; maquina.agregarOperacion("_return"); } 
		
		|	PARAMETRO { $$ = new ParserVal(maquina.agregarOperacion("push_parametro")); maquina.agregar((int)$1.ival); }
		
		|	nombreProc '(' arglist ')' { $$ = new ParserVal(maquina.agregarOperacionEn("invocar",($1.ival))); maquina.agregar(null); } //instrucciones tiene la estructura necesaria para la lista de argumentos
		;

	if_: IF {
			$$ = new ParserVal(maquina.agregarOperacion("_if_then_else"));
	        maquina.agregarOperacion("stop");//then
	        maquina.agregarOperacion("stop");//else
	        maquina.agregarOperacion("stop");//siguiente comando
		}
		;

	while_: WHILE {
			$$ = new ParserVal(maquina.agregarOperacion("_while"));
	        maquina.agregarOperacion("stop");//cuerpo
	        maquina.agregarOperacion("stop");//final
		}
		;

	for_ : FOR {
			$$ = new ParserVal(maquina.agregarOperacion("_for"));
	        maquina.agregarOperacion("stop");//condicion
	        maquina.agregarOperacion("stop");//instrucción final
	        maquina.agregarOperacion("stop");//cuerpo
	        maquina.agregarOperacion("stop");//final
		}			
		
	arglist: 
		|exp {$$ = $1; maquina.agregar("Limite");}
		|arglist ',' exp {$$ = $1; maquina.agregar("Limite");}
		;
	
	nop: {$$ = new ParserVal(maquina.agregarOperacion("nop"));}
		;

	nombreProc: VAR {$$ = new ParserVal(maquina.agregar($1.sval));}
		;
		
	null_: {maquina.agregar(null);}
		;
		
	stop_: {$$ = new ParserVal(maquina.agregarOperacion("stop"));} 
		;

	instrucciones: { $$ = new ParserVal(maquina.agregarOperacion("nop"));}
		|exp {$$ = $1;}
		|instrucciones ',' exp {$$ = $1;}
		;
%%

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