//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//
////** 두개의 인텔리제이를 사용해서 해야 동작함
////** 공유기를 통해 네트워크가 물려 있으면, 안들어가짐 공인아이피는 들어가지지만, 사설아이피는 안들어가짐
//public class simple_advice_server {
//
//    public void go() {
//        try (ServerSocket serverSocket = new ServerSocket(5500)){
//            while(true){
//                Socket socket = serverSocket.accept();
//                String remote = socket.getRemoteSocketAddress().toString();
//                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
//                String advice = getAdvice();
//                printWriter.println(advice);
//                printWriter.close();
//            }
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private String getAdvice() throws IOException {
//        Process fortune = Runtime.getRuntime().exec("fortune");
//        new BufferedReader(new )
//    }
//
//}
