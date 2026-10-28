int x = 10;
int y = 5;
fun int f1(int a, int b) {
    if (a>b) then
        a = a+1;
    else
	b = b+1;
    return a+b;
}
f1(x, y);
