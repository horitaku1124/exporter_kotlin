import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ExporterTest1 {
    private static final int EXPOSE_PORT = 9092;
    public static void main(String[] args) throws IOException {
        var server = HttpServer.create(new InetSocketAddress(EXPOSE_PORT), 0);
        var context = server.createContext("/");
        context.setHandler(ExporterTest1::handleRequest);
        System.out.println("started at " + EXPOSE_PORT);
        server.start();
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {
        var body = "";
        Runtime runtime = Runtime.getRuntime();
        body += "java_runtime_free_memory\t" + runtime.freeMemory() + "\n";
        var response = body.getBytes();
        exchange.sendResponseHeaders(200, response.length);
        var output = exchange.getResponseBody();
        output.write(response);
        output.close();
    }
}
