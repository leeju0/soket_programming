package exam;
import server.SimpleObjectServer.DrawPoint;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

//object 전송
public class SimpleObject_TRANSFORM {
    public static void main(String[] args) {
        //프레임생성
        JFrame frame = new JFrame("object 전송하기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,200);
        frame.setLocationRelativeTo(null);

        //컴포넌트를 배치하기 위한 컨테이너 생성
        Container container = frame.getContentPane();

        //1행 2열 텍스트필드 두개
        JPanel panel = new JPanel(new GridLayout(0, 2)); //1행 2열짜리 두개의 텍스트 필드를 감쌀 패널 생성
        JTextField x = new JTextField();
        JTextField y = new JTextField();
        panel.add(x);
        panel.add(y);
        container.add(BorderLayout.NORTH, panel);

        //버튼
        JButton button = new JButton("SEND");
        container.add(BorderLayout.CENTER, button);

        //객체를 서버로 전송함
        try{
            Socket s = new Socket("117.16.243.99", 5503); //소켓으로 서버와 연결하면?

            ObjectOutputStream oss = new ObjectOutputStream(s.getOutputStream()); //데이터 보낼 아웃풋 스트림 가져와서
            button.addActionListener((e) -> send(oss, x.getText(), y.getText())); //버튼누르면, x,y값 갖는 object를 전송한다.

            frame.setVisible(true);

        }catch(IOException ignore){}



    }

    private static void send(ObjectOutputStream oss, String x, String y) {
        int a = Integer.parseInt(x);
        int b = Integer.parseInt(y);
        System.out.printf("%d %d\n", a, b);
        DrawPoint dp = new DrawPoint(a, b);
    }
}