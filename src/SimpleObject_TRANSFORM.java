//객체 전송하기

import server.SimpleObjectServer.DrawPoint;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SimpleObject_TRANSFORM {
    public static void main(String[] args) {
        //프레임 구성
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,200);
        frame.setLocationRelativeTo(null);

        //컴포넌트들 채워넣기
        Container container = frame.getContentPane();

        //텍스트필드 두개
        JPanel panel = new JPanel(new GridLayout(0,2));//1헹 2열짜리 두개
        JTextField x = new JTextField();
        JTextField y = new JTextField();
        panel.add(x);
        panel.add(y);
        container.add(BorderLayout.NORTH, panel);

        //버튼
        JButton button = new JButton("SEND");
        container.add(button);



        //객체를 서버로 전송

        try {
            Socket s = new Socket("117.16.243.99", 5503); //서버에 연결되면?

            //데이터 주고받을 통로
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            button.addActionListener((e)-> send(oos, x.getText(), y.getText()));



            frame.setVisible(true); //레이아웃 창 보여줘~
        } catch (IOException ignore) {}



    }


    //버튼눌리면 이런일을 한다.
    private static void send(ObjectOutputStream oos, String x, String y) {
        try {
            int a = Integer.parseInt(x); //x가져와서 정수로
            int b = Integer.parseInt(y); //y가져와서 정수로
            System.out.printf("%d %d\n",a, b);
            DrawPoint dp = new DrawPoint(a, b);
            oos.writeObject(dp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


