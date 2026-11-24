
exc ZeroException;

let
    int j = 3; 
in 
    try {
       j = j / (j-3);
    }
    catch(ZeroException)
       print "exception is caught";
end;
