package exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

//파일 읽기
public class simple_advice_client_practice {
    public static void main(String[] args) {
        try {
            Socket socket=  new Socket("117.16.243.99", 5500);

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str = br.readLine();
            System.out.println(str);

            br.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("연결끊김");
        }
    }

}
