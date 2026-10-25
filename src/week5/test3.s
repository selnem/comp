bool t = true;
bool f = false;
print t & t;
print t & f;
print f | t;
print f | f;
print !f;
print !t;
print 1 < 2 & 4 > 3;
print 1 > 2 | 3 == 3;

bool c = 10 > 3;
print c;

// (2) if
int y = 3;
if (y > 2) then y = y + 2; else y = y - 1;
print y;

if (y == 5) then y = 0; else y = 9;
print y;

if (y == 0) then y = 7;
print y;
int x;
if (1 > 2) then print 1; else print 0;
if (!f & 2 <= 2) then x = 100; else x = -1;
print x;