
exc e1;

let
    int j = 3; 
in 
    try {
       j = j / (j-3);
    }
    catch(e1)
       print "exception is caught";
end;
