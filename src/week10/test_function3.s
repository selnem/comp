int x = 5;
int y = 10;
fun int add(int x, int y = 1) {
	return x+y;
}

print add(x);    	// 6 출력
print add(x, y);		// 15 출력
