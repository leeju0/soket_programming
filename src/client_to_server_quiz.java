
import java.io.*;
import java.lang.reflect.Member;
import java.net.Socket;

//object 전송
public class client_to_server_quiz {
    public static void main(String[] args) {
        Socket socket;

        {
            try {
                socket = new Socket("117.16.243.99",5510);


                OutputStream outputStream = socket.getOutputStream();

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

                String str = "201901703 이주영";
                objectOutputStream.writeObject(str);

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                Member member = (Member) ois.readObject();
                //받은 포트 번호로, 새로 연결해서, 객체보내는걸 받음




                ois.close();


                objectOutputStream.close();

                outputStream.close();
                socket.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

}