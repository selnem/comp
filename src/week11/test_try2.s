
exc eee;

int i = 0;

fun int f(int x) {
     i = i+100;
     print i;
     raise eee;
     i = i + 1000; 
     print i;
     return i;
}


fun int g(int x) {
     i = i+10;
     print i;
     i = f(3)+1;
     i = i+10000;
     print i;
     return i;
}

print "=====================";
let
    int j; 
in 
    i = i+1; 
    print i;
    try {
    print g(2);
    i = i+100000;
    }
    catch(eee)
	print "exception is caught";
    i = i+1000000; 
    print i;
end;
print "=====================";
