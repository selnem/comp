// 배열 선언: int | bool | string, 크기 n
int a[3];
bool b[2];
string s[2];

// 일반 선언과 구분
int x = 1;

// 배열 지정문: 리터럴 인덱스, 변수 인덱스
a[0] = 10;
a[x] = a[0] + 1;
b[0] = true;
b[1] = false;
s[0] = "hi";
s[1] = "ok";

// 배열 식: print, 이항 연산, 조건, 반복
print a[0];
print a[0] + a[1];
if (a[0] > 5) then print b[0]; else print b[1];
while (a[x] < 20)
    a[x] = a[x] + 1;

// let 안의 배열 선언과 지정문
let
    int c[2];
in
    c[0] = 1;
    c[1] = c[0] + 4;
    print c[1];
end;
