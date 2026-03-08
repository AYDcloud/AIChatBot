import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class AIChatbotServer {

    private static final String API_KEY = "Enter Gemini API Key";

    private static final String API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=";

    private static HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", exchange -> sendFile(exchange, "web/index.html", "text/html"));

        server.createContext("/style.css", exchange ->
                sendFile(exchange, "web/style.css", "text/css"));

        server.createContext("/script.js", exchange ->
                sendFile(exchange, "web/script.js", "application/javascript"));

        server.createContext("/chat", AIChatbotServer::handleChat);

        server.start();

        System.out.println("AI Chatbot running → http://localhost:8080");
    }

    private static void sendFile(HttpExchange exchange, String path, String type) throws IOException {

        File file = new File(path);

        if (!file.exists()) {
            String response = "File not found";
            exchange.sendResponseHeaders(404, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            return;
        }

        byte[] bytes = Files.readAllBytes(file.toPath());

        exchange.getResponseHeaders().add("Content-Type", type);
        exchange.sendResponseHeaders(200, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    private static void handleChat(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equals("POST")) return;

        String userInput = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

        String reply;

        try {
            reply = askGemini(userInput);
        } catch (Exception e) {
            reply = "AI service unavailable.";
        }

        byte[] response = reply.getBytes();

        exchange.sendResponseHeaders(200, response.length);

        OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();
    }

    private static String askGemini(String message) throws Exception {

        String body = """
        {
          "contents":[{"parts":[{"text":"%s"}]}]
        }
        """.formatted(message);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return extract(response.body());
    }

    private static String extract(String json) {

        try {
            int start = json.indexOf("\"text\": \"") + 9;
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } catch (Exception e) {
            return "Error reading AI response.";
        }
    }

}
