import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class simple_advice_client {

    public static void main(String[] args) {
        //소켓만들기 + 서버와 연결하기
        try { //연결시도
            Socket socket = new Socket("117.16.243.99", 5500);

            //연결 문제 없으면?
            //큐로 데이터 읽어오기! 입력버퍼만 신경쓰자 (입력 큐 : inputStream)
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // bufferedReader로 비트스트림에서 문자열을 읽을 준비가 되었음 이제 . . .
            // readLine() 줄바꿈이 있는것까지 가져옴, 구분자역할
            String str = bufferedReader.readLine();
            System.out.println("str = " + str);

        } catch (IOException e) { //연결 안되면 ? 입출력을 해야하는데, 안되는 그런 상황
            System.out.println("연결 끊김!");
        }


    }
}
