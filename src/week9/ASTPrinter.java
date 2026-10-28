package week9;


public class ASTPrinter {

    // 들여쓰기 공백 출력 헬퍼 함수
    private static void printIndent(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("  ");
        }
    }

    // 메인 출력 진입점
    public static void print(Command c, int indent) {
        if (c == null) {
            printIndent(indent);
            System.out.println("null");
            return;
        }

        printIndent(indent);

        // --- Statements & Commands ---
        if (c instanceof Let) {
            Let node = (Let) c;
            System.out.println("Let");
            
            printIndent(indent + 1); System.out.println("Decls:");
            if (node.decls != null) {
                for (Decl d : node.decls) {
                    print(d, indent + 2);
                }
            }
            
            printIndent(indent + 1); System.out.println("Functions:");
            if (node.funs != null) {
                for (Function f : node.funs) {
                    print(f, indent + 2);
                }
            }
            
            printIndent(indent + 1); System.out.println("Stmts:");
            print(node.stmts, indent + 2);

        } else if (c instanceof Stmts) {
            Stmts node = (Stmts) c;
            System.out.println("Stmts");
            for (Stmt s : node.stmts) {
                print(s, indent + 1);
            }

        } else if (c instanceof Assignment) {
            Assignment node = (Assignment) c;
            System.out.println("Assignment");
            if (node.ar != null) {
                printIndent(indent + 1);
                System.out.println("Target (Array):");
                print(node.ar, indent + 2);
            } else {
                printIndent(indent + 1);
                System.out.println("Target (Id): " + node.id);
            }
            printIndent(indent + 1);
            System.out.println("Expr:");
            print(node.expr, indent + 2);

        } else if (c instanceof If) {
            If node = (If) c;
            System.out.println("If");
            print(node.expr,indent+1);
            print(node.stmt1, indent+1);
            if (!(node.stmt2 instanceof Empty)) {
                print(node.stmt2, indent + 1);
            }
        } else if (c instanceof While) {
            While node = (While) c;
            System.out.println("While");
            print(node.expr, indent + 1);
            print(node.stmt, indent + 1);

        } else if (c instanceof DoWhile) {
            DoWhile node = (DoWhile) c;
            System.out.println("DoWhile");

            print(node.stmt,indent + 1);
            print(node.expr,indent + 1);

        } else if (c instanceof Print) {
            Print node = (Print) c;
            System.out.println("Print");
            print(node.expr, indent + 1);

        } else if (c instanceof Read) {
            Read node = (Read) c;
            System.out.println("Read(" + node.id + ")");

        } else if (c instanceof Return) {
            Return node = (Return) c;
            System.out.println("Return (Function: " + node.fid + ")");
            print(node.expr, indent + 1);

        } else if (c instanceof Empty) {
            System.out.println("Empty");

        // --- Declarations & Functions ---
        } else if (c instanceof Decl) {
            Decl node = (Decl) c;
            if (node.arraysize != -1)
                System.out.println("Decl: " + node.type + " " + node.id + "[" + node.arraysize + "]");
            else
                System.out.println("Decl: " + node.type + " " + node.id);

            if (node.expr != null) {
                printIndent(indent + 1); System.out.println("Initial Expr:");
                print(node.expr, indent + 2);
            }

        } else if (c instanceof Function) {
            Function node = (Function) c;
            System.out.println("Function: " + node.type + " " + node.id);
            printIndent(indent + 1); System.out.println("Parameters:");
            if (node.params != null) {
                for (Decl d : node.params) {
                    print(d, indent + 2);
                }
            }
            printIndent(indent + 1); System.out.println("Body:");
            print(node.stmt, indent + 2);

        // --- Expressions ---
        } else if (c instanceof Binary) {
            Binary node = (Binary) c;
            System.out.println("Binary Op: " + node.op);
            print(node.expr1, indent + 1);
            print(node.expr2, indent + 1);

        } else if (c instanceof Unary) {
            Unary node = (Unary) c;
            System.out.println("Unary Op: " + node.op);
            print(node.expr, indent + 1);

        } else if (c instanceof Call) {
            Call node = (Call) c;
            System.out.println("Call: " + node.fid);
            printIndent(indent + 1); System.out.println("Arguments:");
            if (node.args != null) {
                for (Expr e : node.args) {
                    print(e, indent + 2);
                }
            }

        } else if (c instanceof Identifier) {
            System.out.println("Identifier(" + c + ")");

        } else if (c instanceof Value) {
            System.out.println("Value(" + c + ")");

        } else if (c instanceof Array){
            Array node = (Array) c;
            System.out.println("Array:");
            print(node.id, indent+1);
            print(node.expr, indent+1);

        }
        else {
            System.out.println("Unknown Node: " + c.getClass().getSimpleName());
        }
    }
}
