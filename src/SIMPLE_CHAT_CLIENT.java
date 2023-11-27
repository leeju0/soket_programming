import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//1. GUI가 주어졌을때, 어떤 컴포넌트를 어떻게 배치해야 원하는 레이아웃 구성이 되는지 할 줄 알아야함
//2. 특정 컴포넌트에 action리스너 등록해줄 수 있어야함.
//3. 수신부는 쓰레드를 만들어서 별도로 동작하게 구현해야함.

public class SIMPLE_CHAT_CLIENT {
    public static void main(String[] args) throws IOException {
        //frame만들기
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);


        //채팅창 만들기 (시험) : 어떤 모양을 만드세요~ GUI 구성하기
        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());
        JTextArea chatArea = new JTextArea();
        chatArea.setLineWrap(true);
        chatArea.setEnabled(false);
        JScrollPane chatPane = new JScrollPane(chatArea);
        container.add(chatPane, BorderLayout.CENTER);

        JTextField messageField = new JTextField();

        JButton sendButton = new JButton("SEND");
        JPanel actionPanel = new JPanel(new BorderLayout());
        actionPanel.add(messageField, BorderLayout.CENTER);
        actionPanel.add(sendButton, BorderLayout.EAST);
        container.add(actionPanel,BorderLayout.SOUTH);


        //소켓 생성
        Socket s = new Socket("117.16.243.99", 5502);
        PrintWriter pw = new PrintWriter(s.getOutputStream(),true);


        //EVENT 처리하기
        //enter칠때
        messageField.addActionListener(e -> sendMessage(messageField,pw));
        //send 버튼 누를때
        sendButton.addActionListener(e-> sendMessage(messageField,pw));

        //수신 (비동기 통신) => 아래는 멀티스레드를 쓰는 고전적인 방법이니,  NIO 라이브러리를 활용하세요!
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        new Thread(() -> br.lines().forEach((line) -> {
            chatArea.append(line + '\n');
            chatArea.setCaretPosition(chatArea.getDocument().getLength());//자동스크롤


        })).start(); //스트림, 람다 expression은 익숙해져야한다. 인자값이 없다 : ()
        //쓰레드가 이벤트가 일어나면 호출하는 함수가 있다! 라는 뜻
        //줄바꿈이 있는 문자가 오면 출력하는 쓰레드임. 그래서 뭔가 송신이 오지 않더라도, 정상동작함 -> 비동기


        frame.setVisible(true);

    }

    //메세지 서버로 전송 메소드
    private static void sendMessage(JTextField messageField, PrintWriter pw) {
        pw.println(messageField.getText());

        messageField.setText("");
        messageField.requestFocus();
    }

}
