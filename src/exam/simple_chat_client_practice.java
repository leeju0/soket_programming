package exam;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class simple_chat_client_practice {
    public static void main(String[] args) throws IOException {
        //레이아웃 생성
        JFrame frame = new JFrame("chatting!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.setLocationRelativeTo(null); // 화면 중앙에 위치하도록

        //컴포넌트 쓰기위해 컨테이너 생성
        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout()); // container의 레이아웃 매니저를 BorderLayout으로 설정한다. BorderLayout은 컨테이너를 동,서,남,북 중앙으로 배치하는 레이아웃 메니저이다

        //채팅창
        JTextArea chatArea = new JTextArea();// 채팅창 컴포넌트
        chatArea.setLineWrap(true); //자동 줄바꿈
        chatArea.setEnabled(false); //사용자 입력 비활성화
        JScrollPane chatPane = new JScrollPane(chatArea);//채팅창을 스크럴 가능하게!
        container.add(chatPane, BorderLayout.CENTER);

        //전송 및 입력 창
        JTextField messageField = new JTextField(); //입력창
        JButton sendButton = new JButton("SEND");
        JPanel actionPanel = new JPanel(new BorderLayout()); //패널 = 입력창 + 전송버튼
        actionPanel.add(messageField, BorderLayout.CENTER); //패널 중앙
        actionPanel.add(sendButton, BorderLayout.EAST); //패널 우측
        container.add(actionPanel, BorderLayout.SOUTH); //전체 레이아웃 아래에 패널 위치시킴

        //소켓만들기 + 전송할객체 만들기
        Socket s = new Socket("117.16.243.99", 5502);
        PrintWriter pw = new PrintWriter(s.getOutputStream(), true); //소켓을 통해 출력 스트림을 얻어와, 그 스트림으로 pw객체 데이터 전송함

        //event
        messageField.addActionListener(e-> sendMessage(messageField,pw)); //enter칠때
        sendButton.addActionListener(e -> sendMessage(messageField,pw)); //버튼누를때

        //수신(비동기 통신) - 서버로 잘 보냈는지 확인하는 용도, 서버에 보내진 채팅 기록을 다시 가져와서 채팅창에 보여줌
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        new Thread(() -> br.lines().forEach((line) -> {
            chatArea.append(line + '\n');
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        })).start();

        frame.setVisible(true);
    }

    //채팅 전송
    private static void sendMessage(JTextField messageField, PrintWriter pw) {
        pw.println(messageField.getText()); //채팅창에 입력한것을 pw객체에 담아 전송
        messageField.setText(""); //전송후에 텍스트 필드 비워줌
        messageField.requestFocus(); //포커스 설정
    }
}
