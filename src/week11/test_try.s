
exc e1;

int i = 0;

let
    int j; 
in 
    i = i+1; 
    print i;
    try {
       i = i+100000;
       raise e1;
    }
    catch(e1)
	print "exception is caught";
    i = i+1000000; 
    print i;
end;
print "=====================";
