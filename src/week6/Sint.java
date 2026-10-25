// Sint.java
// Interpreter for S
package week6;

import java.util.Scanner;
public class Sint {
    static Scanner sc = new Scanner(System.in);
    static State state = new State();

    State Eval(Command c, State state) {
        if (c instanceof Decl) {
            Decls decls = new Decls();
            decls.add((Decl) c);
            return allocate(decls, state);
    //	    Decl decl = (Decl)c;
    //            if (decl.expr == null)
    //                return state.push(decl.id, new Value(decl.type));
    //            else
    //                return state.push(decl.id, V(decl.expr, state));
        }

        if (c instanceof Function) {
            Function f = (Function) c;
            state.push(f.id, new Value(f));
            return state;
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
        if (s instanceof Stmts)  
	        return Eval((Stmts)s, state);
	    if (s instanceof Let)  
	        return Eval((Let)s, state);
	    if (s instanceof Read)  
	        return Eval((Read)s, state);
	    if (s instanceof Print)  
	        return Eval((Print)s, state);
        if (s instanceof Call) 
	        return Eval((Call)s, state);
	    if (s instanceof Return) 
	        return Eval((Return)s, state);
        if (s instanceof DoWhile)
            return Eval((DoWhile)s, state);
        throw new IllegalArgumentException("no statement");
    }

    // call without return value
    State Eval(Call c, State state) {
	//
	// evaluate call without return value
	//
	return null;
    }

    // value-returning call 
    Value V (Call c, State state) { 
	    Value v = state.get(c.fid);  	 // find function
        Function f = v.funValue();
        State s = newFrame(state, c, f); // create new frame on the stack
        System.out.println("Calling " + c.fid);
        s = Eval(f.stmt, s); 		// interpret the call
        System.out.println("Returning " + c.fid); 
	    v = s.pop().val;   		// remove the return value
        s = deleteFrame(s, c, f); 	// delete the frame on the stack
    	return v;
    }

    State Eval(Return r, State state) {
        Value v = V(r.expr, state);
        return state.push(r.fid, v);
    }

    State newFrame (State state, Call c, Function f) {
        if (c.args.size() == 0) 
            return state;
	//
	// evaluate arguments
	//

	//
	// activate a new stack frame in the stack 
	//
	//
        return null;
    }

    State deleteFrame (State state, Call c, Function f) {
	//
	// free a stack frame from the stack
	//
	return null;
    }

    State Eval(Empty s, State state) {
        return state;
    }
  
    State Eval(Assignment a, State state) {
	// evaluate a.expr then update state; // hw 4.3
        Value v = V(a.expr,state);
        if(a.ar != null) {
            Value arr = state.get(a.ar.id);
            int i = V(a.ar.expr, state).intValue();
            arr.arrValue()[i] = v;
            return state;
        }
        return state.set(a.id, v);
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
	    // V(c.expr, state).boolValue( ) 검사
        if(V(c.expr, state).boolValue()){
            state = Eval(c.stmt1, state);
        } else {
            state = Eval(c.stmt2, state);
        }
	    return state;
    }
 
    State Eval(While l, State state) {
        if(V(l.expr,state).boolValue()){
            return Eval(l,Eval(l.stmt,state));
        } else return state;
    }

    State Eval(DoWhile l, State state) {
        state = Eval(l.stmt, state);
        if(V(l.expr,state).boolValue()){
            return Eval(l, state);
        }
        return state;
    }

    State Eval(Let l, State state) {
        State s = allocate(l.decls, state);
        s = Eval(l.stmts,s);
	    return free(l.decls, s);
    }
    State allocate (Decls ds, State state) {
        if (ds != null) {
            for (Decl d : ds) {
                if (d.arraysize != -1) {
                    Value[] arr = new Value[d.arraysize];
                    for(int i = 0; i < d.arraysize; i++){
                        arr[i] = new Value(d.type);
                    }
                    state = state.push(d.id, new Value(arr));
                } else if (d.expr == null)
                    state = state.push(d.id, new Value(d.type));
                else if(d.arraysize == -1)
                    state = state.push(d.id, V(d.expr, state));
            }
        }

        return state;
    }

    State free (Decls ds, State state) {

    // free the entries for declared variables from the state
    //
        if (ds != null){
            for(int i = 0; i < ds.size(); i++){
                state.pop();
            }
        }

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
            //
            // relational operations  hw4.1
            //

            case "==":
                return new Value(v1.intValue() == v2.intValue());
            case "!=":
                return new Value(v1.intValue() != v2.intValue());
            case "<":
                return new Value(v1.intValue() < v2.intValue());
            case ">":
                return new Value(v1.intValue() > v2.intValue());
            case "<=":
                return new Value(v1.intValue() <= v2. intValue());
            case ">=":
                return new Value(v1.intValue() >= v2.intValue());

            //
            // logical operations
            //
            case "&":
                return new Value(v1.boolValue() && v2.boolValue());
            case "|":
                return new Value(v1.boolValue() || v2.boolValue());
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

        if (e instanceof Call) 
    	    return V((Call)e, state);

        if (e instanceof Array) {
            Array a = (Array) e;
            Value arr = state.get(a.id);
            int i = V(a.expr, state).intValue();
            return arr.arrValue()[i];
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
                    //command.type = TypeChecker.Check(command); 
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
                    //command.type = TypeChecker.Check(command);    
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
