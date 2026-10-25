// AST.java
// AST for S
package week7;

import java.util.ArrayList;

abstract class Command {
    // Command = Decl | Function | Stmt
    Type type =Type.UNDEFINED;
}

class Decls extends ArrayList<Decl> {
    // Decls = Decl*

    Decls() { super(); };
    Decls(Decl d) {
	this.add(d);
    }
}

class Decl extends Command {
    // Decl = Type type; Identifier id 
    Identifier id;
    Expr expr = null;
    int arraysize = -1;

    Decl (String s, Type t) {
        id = new Identifier(s); type = t;
    } // declaration 

    Decl (String s, Type t, Expr e) {
        id = new Identifier(s); type = t; expr = e;
    } // declaration 
    //
    Decl (String s, Type t, int n) {
        id = new Identifier(s); type = t; arraysize = n;
    } // array declaration

}

class Functions extends ArrayList<Function> {
    // Functions = Function*
}

class Function extends Command  {
    // Function = Type type; Identifier id; Decls params; Stmt stmt
    Identifier id;
    Decls params;
    Stmt stmt;
   
    Function(String s, Type t) { 
        id = new Identifier(s); type = t; params = null; stmt = null;
    }

    public String toString ( ) { 
       return id.toString(); 
    }

}

class Type {
    // Type = int | bool | string | void
    final static Type INT = new Type("int");
    final static Type BOOL = new Type("bool");
    final static Type STRING = new Type("string");
    final static Type VOID = new Type("void");
    final static Type FUN = new Type("fun");
    final static Type ARRAY = new Type("array");
    final static Type UNDEFINED = new Type("undefined");
    final static Type ERROR = new Type("error");

    protected Type elementType = null;
    protected String id = "";
    protected Type(String s) { id = s; };
    protected Type (String s, Type e) {id = s; elementType = e;}
    public String toString ( ) {
        if(elementType == null)
            return id;
        else
            return elementType + "[]";

    }
}

class ProtoType extends Type {
   // the type of a function and its parameters
   Type result;  
   Decls params;
   ProtoType (Type t, Decls ds) {
      super(t.id);
      result = t;
      params = ds;
   }
}

abstract class Stmt extends Command {
    // Stmt = Empty | Stmts | Assignment | If  | While | Let | Read | Print

}

class Empty extends Stmt {
}

class Stmts extends Stmt {
    // Stmts = Stmt*
    public ArrayList<Stmt> stmts = new ArrayList<Stmt>();

}

class Assignment extends Stmt {
    // Assignment = Identifier id; Expr expr
    // Assignment = Array ar; Expr expr
    Identifier id;
    Expr expr;
    Array ar = null;

    Assignment (Identifier t, Expr e) {
        id = t;
        expr = e;
    }

    Assignment (Array a, Expr e) {
        ar = a;
        expr = e;
    }
}

class If extends Stmt {
    // If = Expr expr; Stmt stmt1, stmt2;
    Expr expr;
    Stmt stmt1;
    Stmt stmt2;

    If(Expr e, Stmt s1, Stmt s2) {
	    expr = e;
	    stmt1 = s1;
	    stmt2 = s2;
    }
    
}

class While extends Stmt {
    // While = Expr expr; Stmt stmt;
    Expr expr;
    Stmt stmt;
    While(Expr e, Stmt s) {
	    expr = e;
	    stmt = s;
    }
}

class DoWhile extends Stmt {
    // DoWhile = Stmt stmt; Expr expr;
    Stmt stmt;
    Expr expr;
    DoWhile(Stmt s, Expr e) {
	    stmt = s;
	    expr = e;
    }
}

class Let extends Stmt {
    // Let = Decls decls; Functions funs; Stmts stmts;
    Decls decls;
    Functions funs;
    Stmts stmts;

    Let(Decls ds, Functions fs, Stmts ss) {
        decls = ds;
	funs = fs;
        stmts = ss;
    }

}

class Read extends Stmt {
    // Read = Identifier id
    Identifier id;

    Read (Identifier v) {
        id = v;
    }

}

class Print extends Stmt {
    // Print =  Expr expr
    Expr expr;

    Print (Expr e) {
        expr = e;
    }

}

class Return extends Stmt {
    Identifier fid;
    Expr expr;

    Return (String s, Expr e) {
        fid = new Identifier(s);
        expr = e;
    }

}

class Exprs extends ArrayList<Expr> {
    /// Exprs = Expr*
}

abstract class Expr extends Stmt {
    // Expr = Identifier | Value | Binary | Unary | Call

}

class Call extends Expr { 
    Identifier fid;  
    Exprs args;

    Call(Identifier id, Exprs a) {
       fid = id;
       args = a;
    }

}

class Identifier extends Expr {
    // Identifier = String id
    private String id;

    Identifier(String s) { id = s; }

    public String toString( ) { return id; }
    
    public boolean equals (Object obj) {
        String s = ((Identifier) obj).id;
        return id.equals(s);
    }
}

class Array extends Expr {
    // Array = Identifier id; Expr expr
    Identifier id;
    Expr expr = null;

    Array(Identifier s, Expr e) {id = s; expr = e;}

    public String toString( ) { return id.toString()+"[]"; }

    public boolean equals (Object obj) {
        String s = ((Array) obj).id.toString();
        return id.equals(s);
    }
}


class Value extends Expr {
    // Value = int | bool | string | array | function 
    protected boolean undef = true;
    Object value = null; // Type type;
    
    Value(Type t) {
        type = t;  
        if (type == Type.INT) value = new Integer(0);
        if (type == Type.BOOL) value = new Boolean(false);
        if (type == Type.STRING) value = "";
        undef = false;
    }

    Value(Object v) {
        if (v instanceof Integer) type = Type.INT;
        if (v instanceof Boolean) type = Type.BOOL;
        if (v instanceof String) type = Type.STRING;
        if (v instanceof Function) type = Type.FUN;
        if (v instanceof Value[]){
            type = Type.ARRAY;
        }
        value = v; undef = false; 
    }

    Object value() { return value; }

    int intValue( ) { 
        if (value instanceof Integer) 
            return ((Integer) value).intValue(); 
        else return 0;
    }
    
    boolean boolValue( ) { 
        if (value instanceof Boolean) 
            return ((Boolean) value).booleanValue(); 
        else return false;
    } 

    String stringValue ( ) {
        if (value instanceof String) 
            return (String) value; 
        else return "";
    }

    Function funValue ( ) {
        if (value instanceof Function) 
            return (Function) value; 
        else return null;
    }

    Value[] arrValue ( ) {
        if (value instanceof Value[]) 
            return (Value[]) value; 
        else return null;
    }

    Type type ( ) { return type; }

    public String toString( ) {
        //if (undef) return "undef";
        if (type == Type.INT) return "" + intValue(); 
        if (type == Type.BOOL) return "" + boolValue();
	    if (type == Type.STRING) return "" + stringValue();
        if (type == Type.FUN) return "" + funValue();
        if (type == Type.ARRAY) return "" + arrValue();
        return "undef";
    }
}

class Binary extends Expr {
// Binary = Operator op; Expr expr1; Expr expr2;
    Operator op;
    Expr expr1, expr2;

    Binary (Operator o, Expr e1, Expr e2) {
        op = o; expr1 = e1; expr2 = e2;
    } // binary
}

class Unary extends Expr {
    // Unary = Operator op; Expr expr
    Operator op;
    Expr expr;

    Unary (Operator o, Expr e) {
        op = o; 
        expr = e;
    } // unary

}

class Operator {
    String val;
    
    Operator (String s) { 
	    val = s; 
    }

    public String toString( ) { 
	    return val; 
    }

    public boolean equals(Object obj) { 
	    return val.equals(obj); 
    }

    public static void print(Object node) {
        print(node, "", true);
    }

    private static void print(Object node, String indent, boolean isLast) {
        if (node == null) return;

        System.out.print(indent);
        System.out.print(isLast ? "└── " : "├── ");
        String childIndent = indent + (isLast ? "    " : "│   ");

        // AST 노드 타입별 출력 처리
        if (node instanceof Function) {
            Function f = (Function) node;
            System.out.println("Function: " + f.id + " (Type: " + f.type + ")");
            print(f.params, childIndent, false);
            print(f.stmt, childIndent, true);

        } else if (node instanceof Decl) {
            Decl d = (Decl) node;
            System.out.println("Decl: " + d.id + " : " + d.type);
            if (d.expr != null) {
                print(d.expr, childIndent, true);
            }

        } else if (node instanceof Decls) {
            Decls ds = (Decls) node;
            System.out.println("Decls");
            for (int i = 0; i < ds.size(); i++) {
                print(ds.get(i), childIndent, i == ds.size() - 1);
            }

        } else if (node instanceof Let) {
            Let l = (Let) node;
            System.out.println("LetStmt");
            print(l.decls, childIndent, false);
            print(l.stmts, childIndent, true);

        } else if (node instanceof Stmts) {
            Stmts ss = (Stmts) node;
            System.out.println("Stmts");
            for (int i = 0; i < ss.stmts.size(); i++) {
                print(ss.stmts.get(i), childIndent, i == ss.stmts.size() - 1);
            }

        } else if (node instanceof If) {
		/*
            If stmt = (If) node;
            System.out.println("IfStmt");
            print(stmt.expr, childIndent, false);
            boolean hasElse = !(stmt.stmt2 instanceof Empty);
            print(stmt.stmt1, childIndent, !hasElse);
            if (hasElse) {
                print(stmt.stmt2, childIndent, true);
            }
	    */

        } else if (node instanceof Assignment) {
            Assignment a = (Assignment) node;
            System.out.println("Assignment");
            print(a.id, childIndent, false);
            print(a.expr, childIndent, true);

        } else if (node instanceof Return) {
            Return r = (Return) node;
            System.out.println("ReturnStmt (" + r.fid + ")");
            print(r.expr, childIndent, true);

        } else if (node instanceof Binary) {
            Binary b = (Binary) node;
            System.out.println("Binary: " + b.op);
            print(b.expr1, childIndent, false);
            print(b.expr2, childIndent, true);

        } else if (node instanceof Unary) {
            Unary u = (Unary) node;
            System.out.println("Unary: " + u.op);
            print(u.expr, childIndent, true);

        } else if (node instanceof Call) {
            Call c = (Call) node;
            System.out.println("Call: " + c.fid);
            print(c.args, childIndent, true);

        } else if (node instanceof Exprs) {
            Exprs es = (Exprs) node;
            System.out.println("Arguments");
            for (int i = 0; i < es.size(); i++) {
                print(es.get(i), childIndent, i == es.size() - 1);
            }

        } else if (node instanceof Array) {
            Array array = (Array) node;
            System.out.println("Array: " + array.id);
	    print(array.expr);

        } else if (node instanceof Identifier) {
            Identifier id = (Identifier) node;
            System.out.println("Identifier: " + id);

        } else if (node instanceof Value) {
            Value v = (Value) node;
            System.out.println("Value: " + v.value);

        } else if (node instanceof Empty) {
            System.out.println("EmptyStmt");

        } else {
            System.out.println(node.getClass().getSimpleName());
        }
    }
}

