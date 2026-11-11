// Sint.java
// Interpreter for S
package week11;
import java.util.Scanner;

// [문제 3] S 언어의 예외를 나타낼 Java 예외 클래스 SException을 여기에 정의하세요.

public class Sint {
    static Scanner sc = new Scanner(System.in);
    static State state = new State();
    State Eval(Command c, State state) { 
	if (c instanceof Decl) {
	    Decls decls = new Decls();
	    decls.add((Decl) c);
	    return allocate(decls, state);
	    //Decl decl = (Decl)c;
            //if (decl.expr == null)
                //return state.push(decl.id, new Value(decl.type));
            //else
                //return state.push(decl.id, V(decl.expr, state));
	}

	if (c instanceof Stmt)
	    return Eval((Stmt) c, state); 

	throw new IllegalArgumentException("no command");
    }
  
    State Eval(Stmt s, State state) {
        if (s instanceof Empty) 
	        return Eval((Empty)s, state);
        if (s instanceof Assignment)  
	        return Eval((Assignment)s, state);
        if (s instanceof If)  
	        return Eval((If)s, state);
        if (s instanceof While)  
	        return Eval((While)s, state);
        if (s instanceof DoWhile)  
	        return Eval((DoWhile)s, state);
        if (s instanceof Stmts)  
	        return Eval((Stmts)s, state);
	    if (s instanceof Let)  
	        return Eval((Let)s, state);
	    if (s instanceof Read)  
	        return Eval((Read)s, state);
	    if (s instanceof Print)  
	        return Eval((Print)s, state);
        // [문제 3] Raise 문 디스패치 추가
        // [문제 4] Try 문 디스패치 추가
        throw new IllegalArgumentException("no statement");
    }

    // [문제 3] State Eval(Raise r, State state) 작성

    // [문제 4] State Eval(Try t, State state) 작성

    State Eval(Empty s, State state) {
        return state;
    }
  
    State Eval(Assignment a, State state) {
	// evaluate a.expr then update state; // hw 4.3
        //return null;
	Value v = V(a.expr, state);

	// assgnment to array hw6
        //return null;
	//
	if (a.ar == null)
       	    return state.set(a.id, v);
	else {
	    Array ar = a.ar;
	    Value[] av = state.get(ar.id).arrValue();
	    int index = V(ar.expr, state).intValue();
	    if (av!=null) {
	        av[index] = v; // array element is updated
	        return state;  // without state.set array element is updated
	    }
	    return null;
	}
    }

    State Eval(Read r, State state) {
        if (r.id.type == Type.INT) {
	        int i = sc.nextInt();
	        state.set(r.id, new Value(i));
	    } 

	    if (r.id.type == Type.BOOL) {
	        boolean b = sc.nextBoolean();	
            state.set(r.id, new Value(b));
	    }

	//
	// input string 
	//
	
	    return state;
    }

    State Eval(Print p, State state) {
	    System.out.println(V(p.expr, state));
        return state; 
    }
  
    State Eval(Stmts ss, State state) {
        for (Stmt s : ss.stmts) {
            state = Eval(s, state);
        }
        return state;
    }
  
    State Eval(If c, State state) {
        if (V(c.expr, state).boolValue( ))
            return Eval(c.stmt1, state);
        else
            return Eval(c.stmt2, state);
    }
 
    State Eval(While l, State state) {
        if (V(l.expr, state).boolValue( ))
            return Eval(l, Eval(l.stmt, state));
        else 
	        return state;
    }
 
    State Eval(DoWhile l, State state) {
        state = Eval(l.stmt, state);
        if (V(l.expr, state).boolValue( ))
            return Eval(l, state);
        else 
	    return state;
    }

    State Eval(Let l, State state) {
        State s = allocate(l.decls, state);
        s = Eval(l.stmts,s);
	    return free(l.decls, s);
    }

    State allocate (Decls ds, State state) {
    //
    // add entries for declared variables on the state
    //
	    if (ds != null)
            for (Decl decl:ds) {
		// rule for array declaration
		    if (decl.arraysize >= 0)  {
			    Value[] va = new Value[decl.arraysize];
			    for (int i=0; i<decl.arraysize; i++) 
				    va[i] = new Value(decl.type);
			    state.push(decl.id, new Value(va));
		    }
		    else if (decl.expr == null)
               		state.push(decl.id, new Value(decl.type));
           	    else
               		state.push(decl.id, V(decl.expr, state));
	    }

        return state;
    }

    State free (Decls ds, State state) {
    //
    // free the entries for declared variables from the state
    //
	if (ds != null)
            for (Decl decl:ds) state.pop();
        return state;
    }

    Value binaryOperation(Operator op, Value v1, Value v2) {
        check(!v1.undef && !v2.undef,"reference to undef value");
        switch (op.val) {
	case "+":
            return new Value(v1.intValue() + v2.intValue());
        case "-": 
            return new Value(v1.intValue() - v2.intValue());
        case "*": 
            return new Value(v1.intValue() * v2.intValue());
        case "/": 
            return new Value(v1.intValue() / v2.intValue());
        case ">": 
            return new Value(v1.intValue() > v2.intValue());
        case ">=": 
            return new Value(v1.intValue() >= v2.intValue());
        case "<": 
            return new Value(v1.intValue() < v2.intValue());
        case "<=": 
            return new Value(v1.intValue() <= v2.intValue());
        case "==": 
            return new Value(v1.intValue() == v2.intValue());
        case "&": 
            return new Value(v1.boolValue() && v2.boolValue());
        case "|": 
            return new Value(v1.boolValue() || v2.boolValue());
	//
	// relational operations  hw4.1
	//

    //
	// logical operations
	//

	    default:
	        throw new IllegalArgumentException("no operation");
	    }
    } 
    
    Value unaryOperation(Operator op, Value v) {
        check( !v.undef, "reference to undef value");
	switch (op.val) {
        case "!": 
            return new Value(!v.boolValue( ));
        case "-": 
            return new Value(-v.intValue( ));
        default:
            throw new IllegalArgumentException("no operation: " + op.val); 
        }
    } 

    static void check(boolean test, String msg) {
        if (test) return;
        System.err.println(msg);
    }

    Value V(Expr e, State state) {
        if (e instanceof Value) 
            return (Value) e;

        if (e instanceof Array) {
		Array ar = (Array) e;
		int index = V(ar.expr, state).intValue();
		return (state.get(ar.id)).arrValue()[index];
	    }

        if (e instanceof Identifier) {
	        Identifier v = (Identifier) e;
            return (Value)(state.get(v));
	    }

        if (e instanceof Binary) {
            Binary b = (Binary) e;
            Value v1 = V(b.expr1, state);
            Value v2 = V(b.expr2, state);
            return binaryOperation (b.op, v1, v2); 
        }

        if (e instanceof Unary) {
            Unary u = (Unary) e;
            Value v = V(u.expr, state);
            return unaryOperation(u.op, v); 
        }

        throw new IllegalArgumentException("no operation");
    }

    public static void main(String args[]) {
	    if (args.length == 0) {
	        Sint sint = new Sint(); Lexer.interactive = true;
            System.out.println("Language S Interpreter 1.0");
            System.out.print(">> ");
	        Parser parser  = new Parser(new Lexer());

	        do { // Program = Command*
	            if (parser.token == Token.EOF)
		        parser.token = parser.lexer.getToken();
	       
	            Command command=null;
                try {
	                command = parser.command();
                    command.type = TypeChecker.Check(command); 
                } catch (Exception e) {
                    System.out.println("Error: "+e);
		            System.out.print(">> ");
                    continue;
                }

	            if (command.type != Type.ERROR) {
                    System.out.println("\nInterpreting..." + command);
                    try {
                        state = sint.Eval(command, state);
                    } catch (Exception e) {
                         System.err.println("Exception : "+e);  
                    }
                }
		    System.out.print(">> ");
	        } while (true);
	    }
        else {
	        System.out.println("Begin parsing... " + args[0]);
	        Command command = null;
	        Parser parser  = new Parser(new Lexer(args[0]));
	        Sint sint = new Sint();

	        do {	// Program = Command*
	            if (parser.token == Token.EOF)
                    break;

                try {
		            command = parser.command();
                    command.type = TypeChecker.Check(command);    
                } catch (Exception e) {
                    System.out.println("Error: "+e);
                    continue;
                }

	            if (command.type!=Type.ERROR) {
                    System.out.println("\nInterpreting..." + args[0] + command);
                    try {
                        state = sint.Eval(command, state);
                    } catch (Exception e) {
                        System.err.println("Exception : "+e);  
                    }
                }
	        } while (command != null);
        }        
    }
}
