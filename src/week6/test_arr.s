// 선언 직후 원소는 0
int a[3];
print a[0];

// 대입과 식 안의 배열 접근
a[0] = 10;
a[1] = a[0] + 1;
a[2] = a[1] * 2;
print a[0];
print a[1];
print a[2];

// 인덱스가 변수
int i = 0;
a[i] = 7;
print a[0];
i = 1;
print a[i];

// 조건식 안의 배열
if (a[0] > 5) then print 1; else print 0;

// while로 채우기
int k = 0;
int m[3];
while (k < 3) {
    m[k] = k;
    k = k + 1;
}
print m[0];
print m[1];
print m[2];

bool b[2];
b[0] = true;
b[1] = false;
print b[0];
print b[1];

string s[2];
s[0] = "hi";
s[1] = "ok";
print s[0];
print s[1];

let
    int c[2];
in
    c[0] = 1;
    c[1] = c[0] + 4;
    print c[0];
    print c[1];
end;