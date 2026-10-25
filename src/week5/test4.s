// let: allocate / free. 안쪽 x가 끝난 뒤 바깥 x가 다시 보여야 함
int x = 1;
let
    int x = 2;
in
    let
        int x = 3;
    in
        print x;
    end;
    print x;
end;
print x;

// 초기식 없는 선언은 0, 앞 선언은 뒤 선언에서 사용 가능
let
    int a;
    int b = a + 5;
in
    print a;
    print b;
end;

// while: 조건이 처음부터 거짓이면 본문은 실행되지 않음
int n = 5;
while (n < 5)
    n = n + 1;
print n;

// while: 0, 1, 2를 더하면 3
int i = 0;
int s = 0;
while (i < 3) {
    s = s + i;
    i = i + 1;
    print i;
}
print s;

// do-while: 조건이 거짓이어도 실행
do {
    n = n + 1;
} while (n < 5);
print n;