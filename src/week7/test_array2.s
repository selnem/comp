int x[10];
int i;
print x[3];

while (i<10) {
	x[i] = i+1;
	print x[i];
	i = i+1;
}  

i = 1;
print x[i+5];
print x[8]-1;
print x[x[8]-1];
x[i+5] = x[x[8]-1];
print x[6];
