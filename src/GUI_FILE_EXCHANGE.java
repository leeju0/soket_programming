import javax.swing.*;
import java.awt.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;


public class GUI_FILE_EXCHANGE {
    static Socket socket;

    public static void main(String[] args) {
        JFrame frame = new JFrame("프레임 사용하기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//jframe은 기본적으로, x로 창을 닫아도 process가 끝나지 않는다. 아래는 이를 해결
        frame.setSize(400, 300);

        Container container = frame.getContentPane();


        JButton connectButton = new JButton("Connect");
        JButton sendButton = new JButton("Send");

        container.add(connectButton, BorderLayout.NORTH);
        container.add(sendButton, BorderLayout.CENTER);

        connectButton.addActionListener(e -> {
            try {
                socket = new Socket("117.16.243.99", 5501);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        sendButton.addActionListener(e->{
            JFileChooser chooser = new JFileChooser();
            int ret = chooser.showOpenDialog(null);

            //선택되면
            if ( ret == JFileChooser.APPROVE_OPTION){

                try {
                    System.out.println("선택됨");
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                    /*
                    프로토콜 정의
                    dos.writeInt(파일명길이), dos.write(파일명),
                    dos.writeLong(파일길이), dos.write(파일)
                    */

                    //파일명 가져오기
                    String name = chooser.getSelectedFile().getName();
                    String path = chooser.getSelectedFile().getPath();

                    //파일명 바이트상으로 몇바이트인지 +  내보내기
                    dos.writeInt(name.getBytes().length);
                    dos.write(name.getBytes());

                    //파일 크기 얻어와서, 파일 전송하기
                    File file = new File(path);
                    dos.writeLong(file.length());
                    FileInputStream fis = new FileInputStream((file));
                    byte[] buffer = new byte[1024];
                    int nRead = 0;
                    while((nRead = fis.read(buffer, 0 , buffer.length)) != -1){
                        dos.write(buffer, 0 , nRead);
                        dos.flush();
                    }
                    fis.close();
                    dos.close();
                    socket.close();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }


        });






        frame.setVisible(true);


    }
}
