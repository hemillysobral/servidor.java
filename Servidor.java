import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.file.Files;

public class Servidor {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Servir arquivos estáticos
        server.createContext("/", new StaticHandler());

        // Lidar com cálculo
        server.createContext("/calc", new CalcHandler());

        server.setExecutor(null);
        server.start();
        System.out.println("Servidor rodando na porta 8080");
    }

    static class StaticHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) path = "/index.html";

            File file = new File("public" + path);
            if (!file.exists()) {
                String resposta = "404 Not Found";
                exchange.sendResponseHeaders(404, resposta.length());
                exchange.getResponseBody().write(resposta.getBytes());
                exchange.getResponseBody().close();
                return;
            }
            byte[] bytes = Files.readAllBytes(file.toPath());
            // definir tipo de conteúdo
            String contentType = "text/html";
            if (path.endsWith(".css")) contentType = "text/css";
            if (path.endsWith(".js")) contentType = "application/javascript";
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.getResponseBody().close();
        }
    }

    static class CalcHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            String resultado = "Erro";
            if (query != null && query.startsWith("expr=")) {
                String expr = URLDecoder.decode(query.substring(5), "UTF-8");
                try {
                    var engine = new javax.script.ScriptEngineManager().getEngineByName("JavaScript");
                    Object res = engine.eval(expr);
                    resultado = String.valueOf(res);
                } catch (Exception e) {
                    resultado = "Erro";
                }
            }
            byte[] respBytes = resultado.getBytes();
            exchange.getResponseHeaders().set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, respBytes.length);
            exchange.getResponseBody().write(respBytes);
            exchange.getResponseBody().close();
        }
    }
}
