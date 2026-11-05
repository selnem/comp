int x = 10;
int y = 5;
fun int f1(int a, int b) {
    if (a>b) then
        a = a+1;
    else
	b = b+1;
    print a;
    print b;
    return a+b;
}
print (f1(x, y)+1);
