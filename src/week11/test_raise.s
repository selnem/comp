
exc e1;

int i = 0;

let
    int j = 10; 
in {
    i = i+1; 
    print i;
    i = i+100000;
    raise e1;
    print i;
}
end;
print "=====================";
