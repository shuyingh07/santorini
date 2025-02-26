package main.java.com.example;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.SimpleWebServer;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * This class serve frontend requests and return responses to update the ga
 */
public class App extends SimpleWebServer {
    private static final int PORT = 8080;
    private Game game;

    /**
     * The main method to start the server application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    /**
     * Start the server at :8080 port.
     */
    public App() throws IOException {
        super(null, PORT, new File("/var/www/html"), true);
        this.game = new Game(); // Initialize the game
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning!\n");
    }

    /**
     * Handles HTTP requests and returns responses, based on the URL request from frontend.
     *
     * @param session The HTTP session containing the request data.
     * @return Updated game state in JSON format.
     */
    @Override
    public Response serve(IHTTPSession session) {
        // Extract the URI and parameters from the HTTP session
        String uri = session.getUri();
        @SuppressWarnings("deprecation")
        Map<String, String> params = session.getParms();
        GameState gameState = new GameState();

        switch (uri) {
            case "/newGame":
                this.game = new Game();
                break;
            case "/initialize":
                this.game = this.game.initializeWorker(
                        Integer.parseInt(params.get("x1")), Integer.parseInt(params.get("y1")),
                        Integer.parseInt(params.get("x2")), Integer.parseInt(params.get("y2")));
                break;
            case "/move":
                this.game = this.game.move(
                        Integer.parseInt(params.get("x1")), Integer.parseInt(params.get("y1")),
                        Integer.parseInt(params.get("x2")), Integer.parseInt(params.get("y2")));
                break;
            case "/build":
                this.game = this.game.build(
                        Integer.parseInt(params.get("x1")), Integer.parseInt(params.get("y1")));
                break;
            case "/setGodClass":
            this.game = this.game.setGodClass(params.get("godCard"));
            break;
            case "/passAction":
                this.game = this.game.passAction();
                break;
            default:
                return super.serve(session);
        }

        gameState.updateFromGame(this.game);
        // System.out.println(gameState.toJson()); // Test current game state
        Response response = newFixedLengthResponse(Response.Status.OK, "application/json", gameState.toJson());
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        return response;
    }

}
