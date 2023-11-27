package server;


import java.io.Serial;
import java.io.Serializable;

public class SimpleObjectServer {
    //객체 , x,y좌표 저장하는 객체임, 그리고 직렬화할거임
//Serializable은 구현할 메소드가 없어서 선언만으로도 완료
    public static class DrawPoint implements Serializable {
        //버전 강제하기
        @Serial
        private static final long serialVersionUID = 123412341234L;


        // final - 값을 한번만들어놓고 안바꿀거니까
        private final int x;
        private final int y;


        public DrawPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
