import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//간단하게 한줄 넘겨주는 서버이다
public class simple_advice_server {
    private ArrayList<PrintWriter> clientOutputStreams;

    private void broadcastMessage(String message) {
        for (PrintWriter clientOutputStream : clientOutputStreams) {
            clientOutputStream.print(message);
        }
    }

    public void go() {
        System.out.println("Running ChatServer");
        clientOutputStreams = new ArrayList<>();
        boolean ENABLE_AUTO_FLUSH = true;

        try (ServerSocket serverSocket = new ServerSocket(5501)) {
            while (true) {
                Socket socket = serverSocket.accept();
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), ENABLE_AUTO_FLUSH);
                clientOutputStreams.add(printWriter);

                String remote = socket.getRemoteSocketAddress().toString();
                System.out.println("[ChatServer 🤠] " + remote + " connected");
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private class ClientHandler implements Runnable {
        private final String remote;
        private final BufferedReader bufferedReader;
        public ClientHandler(Socket socket) throws IOException {
            this.remote = socket.getRemoteSocketAddress().toString();
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = bufferedReader.readLine()) != null) {
                    System.out.println("[ChatServer 🤠] " + remote + ": " + message);
                    broadcastMessage(message);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
