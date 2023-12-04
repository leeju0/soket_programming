package exam;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;


//파일 전송
public class gui_file_exchange_practice {
    static Socket socket;
    public static void main(String[] args) {
        //레이아웃 생성
        JFrame frame = new JFrame("Data transfer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        Container container = frame.getContentPane();

        JButton connectButton = new JButton("Connect");
        JButton sendButton = new JButton("send");
        container.add(connectButton, BorderLayout.NORTH);
        container.add(sendButton, BorderLayout.CENTER);

        //서버 연결
        connectButton.addActionListener(e-> {
                try {
                    socket = new Socket("117.16.243.99", 5501);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

        });

        //파일 전송
        sendButton.addActionListener(e-> {
            JFileChooser chooser = new JFileChooser();
            int ret = chooser.showOpenDialog(null);

            if( ret == JFileChooser.APPROVE_OPTION){
                try{
                    System.out.println("선택됨");

                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());// 출력스트림 생성

                    String name = chooser.getSelectedFile().getName(); // 이름 가져오기
                    String path = chooser.getSelectedFile().getPath(); //경로 가져오기
                    dos.writeInt(name.getBytes().length); //전송 1 : 이름 길이
                    dos.write(name.getBytes()); //전송 2 : 파일 이름 전송

                    File file = new File(path);
                    dos.writeLong(file.length()); //전송 3 : 파일 크기 전송

                    FileInputStream fis = new FileInputStream(file); // 선택된 파일 읽기위한 인풋 스트림 생성
                    byte[] buffer = new byte[1024];//버퍼 설정 : 1024씩 읽을 것

                    //전송
                    int nRead = 0; //읽은 바이트 수 저장
                    while ((nRead = fis.read(buffer, 0, buffer.length)) != -1) {// 최대 버퍼길이만큼 데이터를 읽어, 읽은 바이트수를 전송
                        dos.write(buffer, 0, nRead); // 읽어들인 데이터를 소켓을 통해 전송
                        dos.flush(); // 출력 스트림을 비워서 모든 데이터를 소켓에 전송
                    }
                    fis.close();
                    dos.close();
                    socket.close();




                }catch (IOException ex){
                    throw new RuntimeException(ex);
                }
            }
        } );

        frame.setVisible(true);
    }
}
